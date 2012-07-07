import gui.*;
import java.awt.EventQueue;
import java.util.*;
import java.io.*;

import MatrixOperations.MatrixOps;

import communication.StateMachine;

public class StartKFlyConfig {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//GUI frame = new GUI();
					//frame.setVisible(true);
					//frame.PopulateComPorts();
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
		
		float[][] b = {{2,0,0,0,0,0},{4,3,0,0,0,0},{7,8,4,0,0,0},{11,2,7,9,0,0},{5,7,11,5,9,0},{15,7,5,11,8,4}};
		MatrixOps.InvertLMatrix(b, b.length);
		printMat(b);
		
		float[][] mat13x13 = {{3.47414311487909f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f},
							{1.58549740030430f,1.38012538499289f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f},
							{4.75111024419178f,3.39851338426837f,4.45451626267899f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f},
							{0.172230402514544f,3.27549001986920f,4.79645712602722f,3.08022338073320f,0f,0f,0f,0f,0f,0f,0f,0f,0f},
							{2.19372179828199f,0.813058675973153f,2.73607764981902f,2.36644424451365f,2.65398776504486f,0f,0f,0f,0f,0f,0f,0f,0f},
							{1.90779228546504f,0.594988407791883f,0.693122214143396f,1.75829753531498f,3.89583615051006f,1.31485642270072f,0f,0f,0f,0f,0f,0f,0f},
							{3.82758394074501f,2.49182025991072f,0.746470027795287f,4.15414313948145f,4.67005342114592f,3.27039549238391f,0.533263850902922f,0f,0f,0f,0f,0f,0f},
							{3.97599950568532f,4.79871979258041f,1.28754127061868f,2.92632045576362f,0.649531042368651f,3.44607251570004f,4.80949040427527f,0.727694901923585f,0f,0f,0f,0f,0f},
							{0.934363022771893f,1.70192863333067f,4.20358627991831f,2.74861804145570f,2.84411830436096f,3.74075796411855f,0.0231711206703372f,0.680342793543319f,0.919538941412084f,0f,0f,0f,0f},
							{2.44882197894116f,2.92633875489889f,1.27141089485766f,4.58596831914905f,2.34695320529103f,2.25270799251249f,3.87455232355751f,4.34646103820045f,1.19976262832451f,1.20845642956916f,0f,0f,0f},
							{2.22793100355450f,1.11905969745568f,4.07142413034408f,1.42919509410187f,0.0595103475062070f,0.419106889984663f,4.08651610326717f,2.89852293682785f,2.08633534542185f,2.01956072794057f,3.24557737478226f,0f,0f},
							{3.23156505055632f,3.75633529652826f,1.21762484362495f,3.78600114555361f,1.68561322199441f,1.14488484358409f,4.34347352681755f,2.74930100918166f,0.248272151628711f,0.482272625841943f,3.65861192829335f,4.64692985484365f,0f},
							{3.54682415429036f,1.27547557729635f,4.64631811593614f,3.76864547139248f,0.810911540966214f,4.56668680750835f,0.422179227554552f,0.724773991118634f,4.51358054957641f,0.659866463031675f,3.23872981568153f,3.87856339304201f,1.75363551788442f}};
		MatrixOps.InvertLMatrix(mat13x13, mat13x13.length);
		printMat(mat13x13);		
	}

	public static void printMat(float[][] mat) {

		for (float[] row: mat) {
			for (float col: row) {
				System.out.print(col);
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
