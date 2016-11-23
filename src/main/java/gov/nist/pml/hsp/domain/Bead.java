package gov.nist.pml.hsp.domain;

public class Bead {
    private int i;
    private int j;

    private double x;
    private double y;
    private double z;

    private double r;

    public Bead(int i, int j, double x, double y, double z, double r) {
        super();
        this.i = i;
        this.j = j;
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
    }

    public Bead(double x, double y, double z, double r) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
    }

    public Bead(int i, int j, double theta, double r, double randNormalValue, boolean index) {
        super();
        this.i = i;
        this.j = j;
        this.r = r;

        this.x = 1 * r * Math.cos(Math.toRadians(theta)) * i;
        this.y = 1 * r * j - 1 * r * Math.sin(Math.toRadians(theta)) * i;
        //this.z = 0.03*r*randNormalValue;
        this.z = 0;
    }

    public Bead(int i, int j, double theta, double r, double L, double randNormalValue, boolean index) {
        super();
        this.i = i;
        this.j = j;
        this.r = r;

        double s = r*(0 + 0.5*(randNormalValue) * Parameters.Rstdv);
        
        this.x = (1 * L * Math.cos(Math.toRadians(theta)) * i)+s;
        this.y = (1 * L * j - 1 * L * Math.sin(Math.toRadians(theta)) * i)+s;
        //this.z = 0.03*r*randNormalValue;
        this.z = 0;
    }

    public Bead(int i, int j, double theta, double r, double L) {
        super();
        this.i = i;
        this.j = j;
        this.r = r;

        this.x = 1 * L * Math.cos(Math.toRadians(theta)) * i;
        this.y = 1 * L * j - 1 * L * Math.sin(Math.toRadians(theta)) * i;
        this.z = 0;
    }

    public Bead() {
        super();
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    @Override
    public String toString() {
        return "Bead [i=" + i + ", j=" + j + ", x=" + x + ", y=" + y + ", z=" + z + ", r=" + r + "]";
    }


}
