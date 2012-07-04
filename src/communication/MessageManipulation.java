package communication;
import java.util.*;
import crcgenerator.*;

/* *
 * Note that this method makes assumptions about messages send to it.
 * The first 3 bytes are SYNC, CMD and DATA LENGTH. After that it adds the first CRC.
 * If the message sent to it is longer than 3 byte, that means that the message has data bytes
 * as well and at the end a CRC16 will be added.
 * */
public class MessageManipulation {
	public static List<Byte> CRCMessage(List<Byte> message) {
		List<Byte> newList = new ArrayList<Byte>();
		int i = 0;
		
		while (i < message.size()) {
			newList.add(message.get(i++));
			if (i == 3)
				newList.add(CRC8.GenerateCRC(newList));
		}
		
		if (message.size() > 3)
			newList.addAll(CRC_CCITT.GenerateCRC(newList));
		
		return newList;
	}
	
	/* *
	 * CRCMessage must be called before this one.
	 * This method prepares the data for being sent over the Serial Port.
	 * */
	public static byte[] MessagetoByteArray(List<Byte> message) {
		/* Replace all SYNC bytes (except the first one) with double SYNC byte to represent data */
		List<Byte> newList = new ArrayList<Byte>();
		
		for(byte b: message) {
			newList.add(b);
			if (b == KFlyConstants.SYNC)
				newList.add(b);
		}
		
		newList.remove(0); /* Remove one of the two SYNC-bytes at the start. Only one SYNC-byte denotes Sync */
		
		byte[] ByteArray = new byte[newList.size()];
		for (int i = 0; i < newList.size(); i++) 
			ByteArray[i] = (byte)newList.get(i);
			
		return ByteArray;
	}
}
