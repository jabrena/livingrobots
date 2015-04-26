package lejos.ev3.tools;

import javax.swing.filechooser.FileSystemView;

import java.io.File;

public class Drives {

	public static void main(String x[])
	{
	    File paths[];
	    paths = File.listRoots();
	    FileSystemView fsv = FileSystemView.getFileSystemView();
	    for(File path: paths)
	    {
	            System.out.println("Drive Name: "+path);
	            System.out.println("Description: "+fsv.getSystemTypeDescription(path));


	    }
	    
	    //FileSystemView fsv = FileSystemView.getFileSystemView();
	    File dev = fsv.getChild(fsv.getRoots()[0], "dev");
	    for (String listed: dev.list()) {
	        System.out.println(listed);
	    }

		File r = new File("/Volumes/LEJOS2");//roots[driveDropdown.getSelectedIndex()];
		long space =  r.getTotalSpace() / (1024*1024);
		File[] files = r.listFiles();
		
		System.out.println("Directory is " + r.getPath());
		System.out.println("Space on drive is " + space  + "Mb");


	}
	
}
