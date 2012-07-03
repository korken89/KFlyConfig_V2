import gui.*;
import java.awt.EventQueue;
import communication.*;
import java.util.*;

public class StartKFlyConfig {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
					frame.PopulateComPorts();
					//frame.DisableUntilConnected();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		byte test = 4;
		KFlyCommand wee = KFlyCommand.values()[test];
		
		System.out.println(wee);
		
		List<Byte> newList = new ArrayList<Byte>();
		newList.add((byte) 0xa6);
		newList.add((byte) 0x01);
		newList.add((byte) 0x02);
		
		newList.add((byte) 0xa6);
		newList.add((byte) 0xbb);
		
		for (byte b: MessageManipulation.MessagetoByteArray(MessageManipulation.CRCMessage(newList)))
			System.out.print(IntToHex(b & 0xff) + " ");
	}
	
	public static String IntToHex(int value) {
		String hex = Integer.toHexString(value);
		hex = (hex.length() % 2 == 0) ? "" + hex : "0" + hex;
		return hex;
	}
}
