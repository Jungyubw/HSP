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
		
		SurfaceBeads base_sb = hm.populateBeadSurface(-3, 3, -3, 3, Parameters.theta, Parameters.r2);
		
//		hm.experience2(0,289, base_sb);
		
		String result = hm.experience(-100, 100, -100, 100, base_sb);
		
		FileUtils.writeStringToFile(new File("result_5nm_1000nms_0.3r2.txt"), result);
	}

}
