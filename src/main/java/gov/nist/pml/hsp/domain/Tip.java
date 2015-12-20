package gov.nist.pml.hsp.domain;

public class Tip {

	private double x;
	private double y;
	private double z;
	
	private double r;

	public Tip(){
		super();
	}
	
	public Tip(double x, double y, double z, double r) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
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
		return "Tip [x=" + x + ", y=" + y + ", z=" + z + ", r=" + r + "]";
	}
	
	
}
