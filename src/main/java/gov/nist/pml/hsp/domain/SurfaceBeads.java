package gov.nist.pml.hsp.domain;

import java.util.ArrayList;
import java.util.List;

public class SurfaceBeads {

	private List<Bead> beads = new ArrayList<Bead>();

	public List<Bead> getBeads() {
		return beads;
	}

	public void setBeads(List<Bead> beads) {
		this.beads = beads;
	}
	
	public void addBead(Bead bead){
		this.beads.add(bead);
	}
	
	public int getSize(){
		return this.beads.size();
	}
}
