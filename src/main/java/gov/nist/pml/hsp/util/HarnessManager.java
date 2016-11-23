package gov.nist.pml.hsp.util;

import gov.nist.pml.hsp.domain.Bead;
import gov.nist.pml.hsp.domain.Parameters;
import gov.nist.pml.hsp.domain.SurfaceBeads;
import gov.nist.pml.hsp.domain.Tip;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class HarnessManager {

    private Logger logger = Logger.getLogger(getClass().getName());

    private int threadPoolSize;

    public HarnessManager(int threadPoolSize) {
        if (threadPoolSize < 1) {
            throw new IllegalArgumentException("'threadPoolSize' parameter must be positive.");
        }
        this.threadPoolSize = threadPoolSize;
    }

    public SurfaceBeads populateBeadSurface(int start_i, int end_i, int start_j, int end_j, double theta, double r) {
        List<Bead> beadList = new ArrayList();
        Random rand = new Random();

        for (int i = start_i; i < end_i + 1; i++) {
            for (int j = start_j; j < end_j + 1; j++) {
            	double randNormalValue = rand.nextGaussian();
            	
            	if  (i == 0 & j==0) {
            		Bead b = new Bead(i, j, theta, (Parameters.lamda * r * 1) +  (r * (randNormalValue)*Parameters.Rstdv), Parameters.L, randNormalValue, true);
            		beadList.add(b);
            	}
            	else {               

                Bead b = new Bead(i, j, theta, (Parameters.lamda * r * 1) +  (r * (randNormalValue)*Parameters.Rstdv), Parameters.L, randNormalValue, true);
//				Bead b = new Bead(i, j, theta, 1.0*r*(1 + (randNormalValue) * 0.02), randNormalValue, true);
//				Bead b = new Bead(i, j, theta, 0.3*r*(1 + (Math.random() - 0.5) * 0.03), (1 + (Math.random() - 0.5) * 0.03) * r);
                beadList.add(b);        
            	}
            
            }
        }
        return new SurfaceBeads(beadList);
    }

    public String[] experience(int min_x_index, int max_x_index, int min_y_index, int max_y_index, SurfaceBeads base_sb) {
        logger.info("enter experience(" + min_x_index + ", " + max_x_index + ", " + min_y_index + ", " + max_y_index + ", " + base_sb + ")");
        long s = System.currentTimeMillis();

        List<Task> tasks = makeTasksByOrder(min_x_index, max_x_index, min_y_index, max_y_index, base_sb);
        Map<String, TaskResult> taskResults = parallelExecute(tasks);

        StringBuilder resultTotalForce = new StringBuilder();
        StringBuilder resultTipHeight = new StringBuilder();
        arrangeResultByOrder(min_x_index, max_x_index, min_y_index, max_y_index,
                resultTotalForce, resultTipHeight, taskResults);


        logger.info("leave experience(" + min_x_index + ", " + max_x_index + ", " + min_y_index + ", " + max_y_index + ", " + base_sb + ") " +
                "elapsed time: " + (System.currentTimeMillis() - s) + " ms");
        return new String[]{resultTotalForce.toString(), resultTipHeight.toString()};
    }

    private List<Task> makeTasksByOrder(int min_x_index, int max_x_index, int min_y_index, int max_y_index, SurfaceBeads base_sb) {
        List<Task> tasks = new ArrayList();
        for (int i = min_x_index; i < max_x_index + 1; i++) {
            for (int j = min_y_index; j < max_y_index + 1; j++) {
                Task task = new Task(min_x_index, max_x_index, min_y_index, max_y_index, base_sb, i, j);
                tasks.add(task);
            }
        }
        return tasks;
    }

    private Map<String, TaskResult> parallelExecute(List<Task> tasks) {
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        Map<String, TaskResult> taskResults;
        try {
            taskResults = executorService.invokeAll(tasks).stream()
                    .map(f -> {
                        try {
                            return f.get();
                        } catch (InterruptedException e) {
                            throw new IllegalStateException(e);
                        } catch (ExecutionException e) {
                            throw new IllegalStateException(e.getCause());
                        }
                    })
                    .collect(Collectors.toMap(TaskResult::getKey, Function.identity()));
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        } finally {
            executorService.shutdown();
        }
        return taskResults;
    }

    private void arrangeResultByOrder(int min_x_index, int max_x_index, int min_y_index, int max_y_index,
                                      StringBuilder resultTotalForce, StringBuilder resultTipHeight,
                                      Map<String, TaskResult> taskResults) {
        for (int i = min_x_index; i < max_x_index + 1; i++) {
            for (int j = min_y_index; j < max_y_index + 1; j++) {
                String key = hashKey(min_x_index, max_x_index, min_y_index, max_y_index, i, j);
                TaskResult result = taskResults.get(key);
                resultTotalForce.append(result.getResultTotalForce());
                resultTipHeight.append(result.getResultTipHeight());
            }

            resultTotalForce.append(System.lineSeparator());
            resultTipHeight.append(System.lineSeparator());
        }
    }

    private String[] doJob(int min_x_index, int max_x_index, int min_y_index, int max_y_index, SurfaceBeads base_sb, int i, int j) {
        String resultTotalForce = "";
        String resultTipHeight = "";

        Tip tip = new Tip(i * Parameters.pixelsize, j * Parameters.pixelsize, 0, Parameters.r1);
        tip = BasicCalculation.calculateZForTip(tip, base_sb, Parameters.Z0);
        double totalForce = BasicCalculation.calculateTotalForce(base_sb, tip);

        if (j == min_y_index) {
            resultTotalForce = resultTotalForce + totalForce;
        } else {
            resultTotalForce = resultTotalForce + "\t" + totalForce;
        }

        if (j == min_y_index) {
            resultTipHeight = resultTipHeight + tip.getZ();
        } else {
            resultTipHeight = resultTipHeight + "\t" + tip.getZ();
        }

        return new String[]{resultTotalForce, resultTipHeight};
    }

    public void experience2(int i, int j, SurfaceBeads base_sb) {
        Tip tip = new Tip(i * 1E-9, j * 1E-9, 0, Parameters.r1);
        tip = BasicCalculation.calculateZForTip(tip, base_sb, Parameters.Z0);
        double totalForce = BasicCalculation.calculateTotalForce(base_sb, tip);

        System.out.println(totalForce);
    }

    public String hashKey(int min_x_index, int max_x_index, int min_y_index, int max_y_index, int i, int j) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
        md.update(intToByteArray(min_x_index));
        md.update(intToByteArray(max_x_index));
        md.update(intToByteArray(min_y_index));
        md.update(intToByteArray(max_y_index));
        md.update(intToByteArray(i));
        md.update(intToByteArray(j));
        return Base64.getEncoder().encodeToString(md.digest());
    }

    public final byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }

    private class Task implements Callable<TaskResult> {

        private int min_x_index;
        private int max_x_index;
        private int min_y_index;
        private int max_y_index;
        private SurfaceBeads base_sb;
        private int i;
        private int j;

        public Task(int min_x_index, int max_x_index, int min_y_index, int max_y_index, SurfaceBeads base_sb, int i, int j) {
            this.min_x_index = min_x_index;
            this.max_x_index = max_x_index;
            this.min_y_index = min_y_index;
            this.max_y_index = max_y_index;
            this.base_sb = base_sb;
            this.i = i;
            this.j = j;
        }

        @Override
        public TaskResult call() throws Exception {
            String[] result = doJob(min_x_index, max_x_index, min_y_index, max_y_index, base_sb, i, j);
            return makeTaskResult(result);
        }

        private TaskResult makeTaskResult(String[] result) {
            String resultTotalForce = result[0];
            String resultTipHeight = result[1];
            String key = hashKey(min_x_index, max_x_index, min_y_index, max_y_index, i, j);
            return new TaskResult(resultTotalForce, resultTipHeight, key);
        }
    }

    private class TaskResult {
        private String resultTotalForce;
        private String resultTipHeight;
        private String key;

        public TaskResult(String resultTotalForce, String resultTipHeight, String key) {
            this.resultTotalForce = resultTotalForce;
            this.resultTipHeight = resultTipHeight;
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public String getResultTotalForce() {
            return resultTotalForce;
        }

        public String getResultTipHeight() {
            return resultTipHeight;
        }
    }

}
