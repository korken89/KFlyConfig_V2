import gui.*;
import java.awt.EventQueue;
import java.util.*;
import java.io.*;
import communication.StateMachine;

public class StartKFlyConfig {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
					frame.PopulateComPorts();
					
					if (GUI.isLinux())
						GUI.fixFont(frame, 13.0f);
					
					//frame.DisableUntilConnected();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//byte test = 4;
		//KFlyCommand wee = KFlyCommand.values()[test];
		StateMachine sm = new StateMachine();
		
		List<Byte> newList = new ArrayList<Byte>();
		/* Header starts here */
		newList.add((byte) 0xa6);
		newList.add((byte) 0xa6);
		newList.add((byte) 0x01);
		newList.add((byte) 0x00);
		newList.add((byte) 0x20);
		/* Header ends here - Data starts here */
		//newList.add((byte) 0xaa);
		//newList.add((byte) 0xbb);
		/* Data ends here */
		
		for (byte b: newList)
			sm.SerialManager(b);
			
		System.out.println();
		System.out.println();
		
		byte[] content = read("/home/korken/Desktop/main.bin");
		System.out.println("Number of bytes read: " + content.length);
		
		int nPackages = content.length/64;
		int LastSize = ((content.length - nPackages*64) == 0) ? 64 : (content.length - nPackages*64);
		
		if (LastSize != 64)
			nPackages++;
		
		System.out.println(nPackages + " packages to send and the last package will contain: " + LastSize + " bytes");
	}

	public static void printMat(float[][] mat) {

		for (float[] row: mat) {
			for (float col: row) {
				System.out.printf("%.4f", col);
				System.out.print("\t");
			}
			System.out.print("\n");
		}
			
	}
	
	public static byte[] read(String aInputFileName){
		File file = new File(aInputFileName);
		byte[] result = new byte[(int)file.length()];
		
		try {
			InputStream input = new BufferedInputStream(new FileInputStream(file));
			
			try {
				int totalBytesRead = 0;
				
				while(totalBytesRead < result.length){
					int bytesRemaining = result.length - totalBytesRead;
					int bytesRead = input.read(result, totalBytesRead, bytesRemaining); 
					if (bytesRead > 0)
						totalBytesRead += bytesRead;
				}
			}
			finally {
				input.close();
			}
	    }
		catch (FileNotFoundException ex) {
	    	System.out.println("File not found.");
		}
		catch (IOException ex) {
			System.out.println(ex);
	    }
		
		return result;
	}
	
	public static void write(byte[] aInput, String aOutputFileName) {
		try {
	   		OutputStream output = new BufferedOutputStream(new FileOutputStream(aOutputFileName));
	   		
	   		try {
	   			output.write(aInput);
	   		}
	   		finally {
	   			output.close();
	   		}
	   	}
	   	catch(FileNotFoundException ex){
	   		System.out.println("File not found.");
	   	}
	   	catch(IOException ex){
	   		System.out.println(ex);
	   	}
	}
}
