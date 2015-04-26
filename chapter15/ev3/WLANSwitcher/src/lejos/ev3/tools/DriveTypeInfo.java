package lejos.ev3.tools;

import java.io.*;
import javax.swing.filechooser.*;

public class DriveTypeInfo
{
public static void main(String[] args)
{
    System.out.println("File system roots returned byFileSystemView.getFileSystemView():");
    FileSystemView fsv = FileSystemView.getFileSystemView();
    File[] roots = fsv.getRoots();
    for (int i = 0; i < roots.length; i++)
    {
        System.out.println("Root: " + roots[i]);
    }

    System.out.println("Home directory: " + fsv.getHomeDirectory());

    System.out.println("File system roots returned by File.listRoots():");
    File[] f = File.listRoots();
    for (int i = 0; i < f.length; i++)
    {
        System.out.println("Drive: " + f[i]);
        System.out.println("Display name: " + fsv.getSystemDisplayName(f[i]));
        System.out.println("Is drive: " + fsv.isDrive(f[i]));
        System.out.println("Is floppy: " + fsv.isFloppyDrive(f[i]));
        System.out.println("Readable: " + f[i].canRead());
        System.out.println("Writable: " + f[i].canWrite());
        System.out.println("Total space: " + f[i].getTotalSpace());
        System.out.println("Usable space: " + f[i].getUsableSpace());
    }
}
}
