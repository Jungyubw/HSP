package gov.nist.pml.hsp.util;

import gov.nist.pml.hsp.domain.Bead;
import gov.nist.pml.hsp.domain.Parameters;
import gov.nist.pml.hsp.domain.SurfaceBeads;
import gov.nist.pml.hsp.domain.Tip;

public class BasicCalculation {

	public double calculateDistance2D(double x1, double y1, double x2, double y2){
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
	
	public double calculateDistance3D(double x1, double y1, double z1, double x2, double y2, double z2){
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) + Math.pow(z1-z2, 2));
	}
	
	public double calculateForce(double Zij){
		return (Parameters.A12 * Parameters.r12) / (6 * Math.pow(Zij, 2));
	}
	
	public double calculateSurfaceDistance(Tip tip, Bead bead){
		return this.calculateDistance3D(tip.getX(), tip.getY(), tip.getZ(), bead.getX(), bead.getY(), bead.getZ()) - tip.getR() - bead.getR();
	}
	
	public double calculateNormalForce(double Fij, Tip tip, Bead bead){
		return Fij * (tip.getZ() - bead.getZ()) / this.calculateDistance3D(tip.getX(), tip.getY(), tip.getZ(), bead.getX(), bead.getY(), bead.getZ());
	}
	
	public double calculateTotalForce(SurfaceBeads sb, Tip tip){
		double totalForce = 0;
		
		for(Bead bead:sb.getBeads()){
			double Zij = this.calculateSurfaceDistance(tip, bead);
			double Fij = this.calculateForce(Zij);
			double normalForce = this.calculateNormalForce(Fij, tip, bead);
			
			totalForce = totalForce + normalForce;
		}
		
		return totalForce;
	}
	
	public SurfaceBeads findRelevantBeads(SurfaceBeads base_bs, Tip tip){
		SurfaceBeads sb = new SurfaceBeads();
		
		
		for(Bead b:base_bs.getBeads()){
			if(tip.getR() + b.getR() > this.calculateDistance2D(tip.getX(), tip.getY(), b.getX(), b.getY())){
				sb.addBead(b);
			}
		}
		
		return sb;
		
	}
	
	public Tip calculateZForTip(Tip tip, SurfaceBeads sb, double Z0){
		double maxZ = 0;
		for(Bead b:sb.getBeads()){
			double currentZ = Math.sqrt(Math.pow(tip.getR() + b.getR() + Z0, 2) - Math.pow(this.calculateDistance2D(b.getX(), b.getX(), tip.getX(), tip.getY()), 2)) + b.getZ();
			if(currentZ > maxZ) maxZ = currentZ;
		}
		tip.setZ(maxZ);
		
		return tip;
	}
}
