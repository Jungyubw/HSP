package gov.nist.pml.hsp.domain;

public class Bead {
	private Integer i;
	private Integer j;
	
	private double x;
	private double y;
	private double z;
	
	private double r;
	

	public Bead(Integer i, Integer j, double x, double y, double z, double r) {
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
	
	public Bead(Integer i, Integer j, double theta, double r, double randNormalValue, boolean index) {
		super();
		this.i = i;
		this.j = j;
		this.r = r;
		
		this.x = 2 * r * Math.cos(Math.toRadians(theta)) * i;
		this.y = 2 * r * j - 2 * r * Math.sin(Math.toRadians(theta)) * i;
		this.z = randNormalValue;
	}
	
	public Bead(Integer i, Integer j, double theta, double r, double L) {
		super();
		this.i = i;
		this.j = j;
		this.r = r;
				
		this.x = 2 * L * Math.cos(Math.toRadians(theta)) * i;
		this.y = 2 * L * j - 2 * L * Math.sin(Math.toRadians(theta)) * i;
		this.z = 0;
	}
	
	public Bead() {
		super();
	}

	
	
	public Integer getI() {
		return i;
	}

	public void setI(Integer i) {
		this.i = i;
	}

	public Integer getJ() {
		return j;
	}

	public void setJ(Integer j) {
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
