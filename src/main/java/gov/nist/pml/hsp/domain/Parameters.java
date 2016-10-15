package gov.nist.pml.hsp.domain;

public class Parameters {
    public final static double Z0 = 0.25E-9;
    public final static double A1 = 79E-21;
    public final static double A2 = 65E-21;
    public final static double A12 = Math.sqrt(A1 * A2);
    public final static double r1 = 2500E-9;
    public final static double r2 = 250E-9;
    public final static double L = 500E-9;
    public final static double lamda = 1.0;
    public final static double lamda4defect = 0.1;
    public final static double pixelsize = 2E-9;
    public final static double Rstdv = 0.05;
    public final static double r12 = 1 / (1 / r1 + 1 / (lamda * r2));
    public final static double theta = 30;
    public final static double delta = 5E-9;
	
}
