package gov.nist.pml.hsp.util;

import gov.nist.pml.hsp.domain.Bead;
import gov.nist.pml.hsp.domain.Parameters;
import gov.nist.pml.hsp.domain.SurfaceBeads;
import gov.nist.pml.hsp.domain.Tip;

import java.util.ArrayList;
import java.util.List;

public class BasicCalculation {

    // To prevent instantiation
    private BasicCalculation() {}

    public static final double calculateDistance2D(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static final double calculateDistance3D(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
    }

    // calculate R12
    public static final double calculateR12(double R1, double R2) {
        return (R1 * R2) / (R1 + R2);
    }

    // calculate R12 for individual beads
    public static final double calculateR12ij(Tip tip, Bead bead) {
        return calculateR12(tip.getR(), bead.getR());
    }

//	public static final double calculateForce(double Zij){
//		return (Parameters.A12 * Parameters.r12) / (6 * Math.pow(Zij, 2));
//	}

    public static final double calculateForceR12ij(double Zij, double R12ij) {
        return (Parameters.A12 * R12ij) / (6 * Math.pow(Zij, 2));
    }

    public static final double calculateSurfaceDistance(Tip tip, Bead bead) {
        return calculateDistance3D(tip.getX(), tip.getY(), tip.getZ(), bead.getX(), bead.getY(), bead.getZ()) - tip.getR() - bead.getR();
    }

    public static final double calculateNormalForce(double Fij, Tip tip, Bead bead) {
        return Fij * (tip.getZ() - bead.getZ()) / calculateDistance3D(tip.getX(), tip.getY(), tip.getZ(), bead.getX(), bead.getY(), bead.getZ());
    }

    public static final double calculateTotalForce(SurfaceBeads sb, Tip tip) {
        double totalForce = 0;

        for (Bead bead : sb.getBeads()) {
            double Zij = calculateSurfaceDistance(tip, bead);

            if (Zij < Parameters.Z0) {
                Zij = Parameters.Z0;
            }

//			System.out.println("------------------------");
//			System.out.println(tip.toString());
//			System.out.println(bead.toString());
//			System.out.println("Surface Distance:" + Zij);

//			double Fij = this.calculateForce(Zij);
            double R12ij = calculateR12ij(tip, bead); // New version for individual r12 for particle size variation
            double Fij = calculateForceR12ij(Zij, R12ij); // New version for individual r12 for particle size variation


//			System.out.println("Force:" + Fij);
            double normalForce = calculateNormalForce(Fij, tip, bead);
//			System.out.println("Normal Force:" + normalForce);
            totalForce = totalForce + normalForce;
        }

        return totalForce;
    }

    private static final SurfaceBeads findRelevantBeads(SurfaceBeads base_sb, Tip tip) {
        List<Bead> beadList = new ArrayList();
        for (Bead b : base_sb.getBeads()) {
            if (tip.getR() + b.getR() > calculateDistance2D(tip.getX(), tip.getY(), b.getX(), b.getY())) {
                beadList.add(b);
            }
        }
        return new SurfaceBeads(beadList);
    }

    public static final Tip calculateZForTip(Tip tip, SurfaceBeads base_sb, double Z0) {
        SurfaceBeads relevantBeads = findRelevantBeads(base_sb, tip);
        double maxZ = 0;
        for (Bead b : relevantBeads.getBeads()) {
            //double currentZ = Math.sqrt(Math.pow(tip.getR() + b.getR() + Z0 , 2) - Math.pow(this.calculateDistance2D(b.getX(), b.getY(), tip.getX(), tip.getY()), 2)) + b.getZ();
            double currentZ = Math.sqrt(Math.pow(tip.getR() + b.getR() + Z0 - Parameters.delta, 2) - Math.pow(calculateDistance2D(b.getX(), b.getY(), tip.getX(), tip.getY()), 2)) + b.getZ();
            //double currentZ = Math.sqrt(Math.pow(tip.getR() + b.getR() + Z0 - Parameters.delta, 2) - Math.pow(this.calculateDistance2D(b.getX(), b.getY(), tip.getX(), tip.getY()), 2)) + b.getZ();
            if (currentZ > maxZ) maxZ = currentZ;
        }
        tip.setZ(maxZ);

        return tip;
    }
}
