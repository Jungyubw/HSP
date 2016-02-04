package gov.nist.pml.hsp.util;

import gov.nist.pml.hsp.domain.Bead;
import gov.nist.pml.hsp.domain.Parameters;
import gov.nist.pml.hsp.domain.SurfaceBeads;
import gov.nist.pml.hsp.domain.Tip;

import java.util.Random;


public class HarnessManager {

	public SurfaceBeads populateBeadSurface(int start_i, int end_i, int start_j, int end_j, double theta, double r) {
		SurfaceBeads sb = new SurfaceBeads();
		Random rand = new Random();
		
		for (Integer i = start_i; i < end_i + 1; i++) {
			for (Integer j = start_j; j < end_j + 1; j++) {
				double randNormalValue = rand.nextGaussian();
				
				Bead b = new Bead(i, j, theta, 1.0*r*(1 + (randNormalValue) * 0.02), Parameters.L);
//				Bead b = new Bead(i, j, theta, 1.0*r*(1 + (randNormalValue) * 0.02), randNormalValue, true);
//				Bead b = new Bead(i, j, theta, 0.3*r*(1 + (Math.random() - 0.5) * 0.03), (1 + (Math.random() - 0.5) * 0.03) * r);
								sb.addBead(b);
			}
		}
		return sb;
	}
	
	public String[] experience(int min_x_index, int max_x_index, int min_y_index, int max_y_index, SurfaceBeads base_sb){
		String resultTotalForce = "";
		String resultTipHeight = "";
		
		for (int i = min_x_index; i < max_x_index + 1; i++) {
			for (int j = min_y_index; j < max_y_index + 1; j++) {
				
				
				Tip tip = new Tip(i*3E-9, j*3E-9, 0,Parameters.r1);
				tip = new BasicCalculation().calculateZForTip(tip, base_sb, Parameters.Z0);
				double totalForce = new BasicCalculation().calculateTotalForce(base_sb, tip);
				
//				if(totalForce > 1){
//					System.out.println("Warning");
//					System.out.println(tip.toString());
//				}
				
				if(j == min_y_index) resultTotalForce = resultTotalForce + totalForce;
				else resultTotalForce = resultTotalForce + "\t" + totalForce;
				
				if(j == min_y_index) resultTipHeight = resultTipHeight + tip.getZ();
				else resultTipHeight = resultTipHeight + "\t" + tip.getZ();
				
				
				System.out.println("(" + i + "," + j + ")");
			}
			
			resultTotalForce = resultTotalForce + System.lineSeparator();
			resultTipHeight = resultTipHeight + System.lineSeparator();
		}
		
		return new String[]{resultTotalForce,resultTipHeight};
	}

	public void experience2(int i, int j, SurfaceBeads base_sb) {
		Tip tip = new Tip(i*1E-9, j*1E-9, 0,Parameters.r1);
		tip = new BasicCalculation().calculateZForTip(tip, base_sb, Parameters.Z0);
		double totalForce = new BasicCalculation().calculateTotalForce(base_sb, tip);
		
		System.out.println(totalForce);
		
	}
	
	
}
