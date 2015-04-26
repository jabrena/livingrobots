package lejos.ev3.tools;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

public class EV3SDCard extends JFrame {
	private static final long serialVersionUID = 2112749235155851987L;
	private static String[] drives = new String[0];
	private JComboBox<String> driveDropdown;
	private static File[] roots = new File[0];
	private URI uri;
	private File zipFile, jreFile;
	private JLabel cardDescription = new JLabel();
	private JProgressBar progressBar = new JProgressBar();
	private static int FILES_TO_COPY = 15; // For progress bar. Total files, directories unzipped or copied.
	private JButton exitButton = new JButton("Exit");
		
	public int run () {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("EV3 SD Card Creator");
		setPreferredSize(new Dimension(500, 300));
		
		getCandidateDrives();

		JButton refreshButton = new JButton("Refresh");
		
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getCandidateDrives();
				driveDropdown.removeAllItems();
				for(String drive: drives) {
					driveDropdown.addItem(drive);
				}
			}		
		});
		
		// Drive panel
		JPanel drivePanel = new JPanel();
		JLabel driveLabel = new JLabel("Select SD drive: ");
		drivePanel.add(driveLabel);
		driveDropdown = new JComboBox<String>(drives);
		drivePanel.add(driveDropdown);
		drivePanel.add(refreshButton);
		drivePanel.setBorder(BorderFactory.createEtchedBorder());
		getContentPane().add(drivePanel, BorderLayout.PAGE_START);

		// Files panel
		JPanel filesPanel = new JPanel();
		JLabel instructions = new JLabel(
				"Select the SD card image zip file from your leJOS EV3 installation");
		filesPanel.add(instructions);
		final JTextField zipFileName = new JTextField(32);
		JButton zipButton = new JButton("Zip file");
		filesPanel.add(zipFileName);
		filesPanel.add(zipButton);

		final JFileChooser zipChooser = new JFileChooser(System.getProperty("user.home") + "/Documents/DATA/2014/RESEARCH/robotics/lejos/SD_CARD_LEJOS/ROM");
		
		zipFile = new File(System.getenv("EV3_HOME") + File.separator + "lejosimage.zip");
		
		if (zipFile.exists()) {
			zipFileName.setText(zipFile.getPath());
		} else {
			System.out.println(zipFile.getPath() + " does not exist");
		}
		
		zipChooser.setAcceptAllFileFilterUsed(false);
		
		zipChooser.addChoosableFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				
				if (f.isDirectory()) return true;
				String extension = getExtension(f);
				return extension != null && extension.equals("zip");
			}

			@Override
			public String getDescription() {
				return "zip";
			}					
		});
		
		final JFileChooser gzChooser = new JFileChooser(System.getProperty("user.home") + "/Documents/DATA/2014/RESEARCH/robotics/lejos/SD_CARD_LEJOS/ROM");
		
		gzChooser.setAcceptAllFileFilterUsed(false);
		
		gzChooser.addChoosableFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				
				if (f.isDirectory()) return true;
				String extension = getExtension(f);
				return extension != null && extension.equals("gz");
			}

			@Override
			public String getDescription() {
				return "gz files";
			}					
		});

		JLabel instructions2 = new JLabel(
				"Download the EV3 Oracle JRE and the select the latest ejre .gz file");
		filesPanel.add(instructions2);
		final JTextField jreFileName = new JTextField(32);
		JButton jreButton = new JButton("JRE");
		filesPanel.add(jreFileName);
		filesPanel.add(jreButton);

		try {
			uri = new URI("http://java.com/legomindstorms");
		} catch (URISyntaxException e1) {
		}

		JButton getJREButton = new JButton();
		getJREButton
				.setText("<html>Click the <font color=\"#000099\"><u>link</u></font>"
						+ " to download the EV3 Oracle JRE.</html>");

		getJREButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (uri != null)
					open(uri);
			}
		});

		filesPanel.add(getJREButton);
		
		progressBar.setMaximum(FILES_TO_COPY);
		progressBar.setStringPainted(true);
		filesPanel.add(progressBar, BorderLayout.LINE_END);		
		
		filesPanel.setBorder(BorderFactory.createEtchedBorder());
		getContentPane().add(filesPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		
		JButton createButton = new JButton("Create");
		
		buttonPanel.add(cardDescription);
		buttonPanel.add(createButton);
		buttonPanel.add(exitButton);
		buttonPanel.setBorder(BorderFactory.createEtchedBorder());
		getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		driveDropdown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (driveDropdown.getSelectedIndex() < 0) return;
				cardDescription.setText(drives[driveDropdown.getSelectedIndex()]);
			}
		});

		zipButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int r = zipChooser.showOpenDialog(EV3SDCard.this);
				
				if (r == JFileChooser.APPROVE_OPTION) {
					zipFile = zipChooser.getSelectedFile();
					zipFileName.setText(zipFile.getPath());
				} else {
					showMessage("Not a valid lejos SD image zip file");
				}
			}
		});

		jreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int r = gzChooser.showOpenDialog(EV3SDCard.this);
				if (r == JFileChooser.APPROVE_OPTION) {
					jreFile = gzChooser.getSelectedFile();
					jreFileName.setText(jreFile.getPath());
				}
			}
		});
		
		createButton.addActionListener(new FileTransfer());
		pack();
		setVisible(true);
		
		return 0;
	}
	
	private class FileTransfer extends SwingWorker<Void, Void> implements ActionListener {
		
		public Void doInBackground() {
			exitButton.setEnabled(false);
			
			if (drives.length == 0) {
				showMessage("No SD drive selected");
				exitButton.setEnabled(true);
				return null;
			}
			
			File r = new File("/Volumes/LEJOS2");//roots[driveDropdown.getSelectedIndex()];
			long space =  r.getTotalSpace() / (1024*1024);
			File[] files = r.listFiles();
			
			System.out.println("Directory is " + r.getPath());
			System.out.println("Space on drive is " + space  + "Mb");
			
			if (space < 400) {
				showMessage("Insufficient space on drive");
				exitButton.setEnabled(true);
				return null;
			} else if (zipFile == null || !zipFile.exists()) {
				showMessage("Zip file not selected");
				exitButton.setEnabled(true);
				return null;
			} else if (jreFile == null || !jreFile.exists()) {
				showMessage("JRE file not selected");
				exitButton.setEnabled(true);
				return null;
			} else if (files.length > 0) {
				String message ="Drive is not empty, files might be overwritten. Do you want to continue?";
				if (showConfirm(message) == JOptionPane.CANCEL_OPTION) {
					exitButton.setEnabled(true);
					return null;
				}
			} 
			
			// Unzip the leJos image to the drive
			System.out.println("Unzipping " + zipFile.getPath() + " to " + r.getPath());
			unZip(zipFile.getPath(), r.getPath());
			
			// Copy the Oracle JRE
			System.out.println("Copying " + jreFile.getPath() + " to " + r.getPath());
			try {
				copyFile(jreFile, new File(r.getPath() + jreFile.getName()));
				progressBar.setValue(progressBar.getValue()+1);
			} catch (IOException e1) {
				showMessage("Failed to copy the Oracle JRE: " + e1);
			}
			
			showMessage("SD card created. Now safely eject it, then insert it into \nthe EV3 and power on the brick to continue install.");
			
			exitButton.setEnabled(true);
			return null;
		}
		
		public void actionPerformed(ActionEvent e) {
			this.execute();
		}
	}
	
	private void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	
	private int showConfirm(String msg) {
		return JOptionPane.showConfirmDialog(this, msg, "Select an Option", JOptionPane.OK_CANCEL_OPTION);
	}
	
	private void getCandidateDrives() {
		File[] roots = new File[2];
		roots[0] = new File("/Volumes/LEJOS2"); //File.listRoots();
		roots[1] = new File("/Volumes/LEJOS3");
		ArrayList<String> driveList = new ArrayList<String>();
		ArrayList<File> rootList = new ArrayList<File>();
		for (File r : roots) {
			String s1 = FileSystemView.getFileSystemView()
					.getSystemDisplayName(r);
			String s2 = FileSystemView.getFileSystemView()
					.getSystemTypeDescription(r);
			if (!r.getPath().equals("C:\\") && r.getTotalSpace() > 0) {
				rootList.add(r);
				System.out.print("Name : " + s1);
				System.out.print(" , Description : " + s2);
				System.out.println(", Size : " + r.getTotalSpace()
						/ (1024 * 1024) + "Mb");
				driveList.add(s1);
			}
		}
		if (driveList.size() == 0) {
			System.out.println("No SD card found");
			drives = new String[0]; 
			EV3SDCard.roots = new File[0];
			cardDescription.setText("No card selected");
		} else {
			drives = driveList.toArray(new String[0]);
			EV3SDCard.roots = rootList.toArray(new File[0]);
			cardDescription.setText(drives[0]);
		}
	}
	
	/**
	 * Command line entry point
	 */
	public static void main(String args[])
	{
		ToolStarter.startSwingTool(EV3SDCard.class, args);
	}
	
	public static int start(String[] args)
	{
		return new EV3SDCard().run();
	}

	private static void open(URI uri) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) {
				System.err.println("IOException getting desktop");
			}
		} else {
			System.err.println("Desktop not supported");
		}
	}

	/**
	 * Unzip
	 * 
	 * @param zipFile
	 *            input zip file
	 * @param output
	 *            zip file output folder
	 */
	public void unZip(String zipFile, String outputFolder) {
		byte[] buffer = new byte[1024];

		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry ze = zis.getNextEntry();
			int len;

			while (ze != null) {
				if (!ze.isDirectory()) {
					String fileName = ze.getName();
					File newFile = new File(outputFolder + File.separator + fileName);
					System.out.println("Unzipping " + fileName + " to : " + newFile.getAbsoluteFile());
	
					new File(newFile.getParent()).mkdirs();
					FileOutputStream fos = new FileOutputStream(newFile);
	
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();	
				}
				progressBar.setValue(progressBar.getValue()+1);
				progressBar.setString((int)(progressBar.getPercentComplete()*100) + "%");
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

		} catch (IOException ex) {
			System.err.println("Error unzipping files: " + ex);
		}
	}
	
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if(!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        }
        finally {
            if(source != null) {
                source.close();
            }
            if(destination != null) {
                destination.close();
            }
        }
    }
}
