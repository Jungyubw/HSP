package gov.nist.pml.hsp.manager;

import gov.nist.pml.hsp.domain.Parameters;
import gov.nist.pml.hsp.domain.SurfaceBeads;
import gov.nist.pml.hsp.util.HarnessManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        HarnessManager hm = new HarnessManager(Runtime.getRuntime().availableProcessors() + 1);

        SurfaceBeads base_sb = hm.populateBeadSurface(-10, 10, -10, 10, Parameters.theta, Parameters.r2);

//		hm.experience2(0,289, base_sb);

        String result[] = hm.experience(-125 * 8, 125 * 8, -125 * 8, 125 * 8, base_sb);

        FileUtils.writeStringToFile(new File("R_2nm_4um_r2_0.28x250nm_L500nm_STV3%_inLR_defl20.txt"), result[0]);
        FileUtils.writeStringToFile(new File("R_2nm_4um_r2_0.28x250nm_L500nm_STV3%_inLR_defl20_H.txt"), result[1]);
    }

}
