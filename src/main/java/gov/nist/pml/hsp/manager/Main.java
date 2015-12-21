package gov.nist.pml.hsp.manager;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import gov.nist.pml.hsp.domain.Parameters;
import gov.nist.pml.hsp.domain.SurfaceBeads;
import gov.nist.pml.hsp.util.HarnessManager;

public class Main {

	public static void main(String[] args) throws IOException {
		HarnessManager hm = new HarnessManager();
		
		SurfaceBeads sb = hm.populateBeadSurface(-100, 100, -100, 100, Parameters.theta, Parameters.r2);
		
		String result = hm.experience(-300, 300, -300, 300, sb);
		
		FileUtils.writeStringToFile(new File("result_10nm.txt"), result);
	}

}
