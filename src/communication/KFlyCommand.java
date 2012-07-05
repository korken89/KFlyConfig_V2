package communication;

import java.util.*;
import crcgenerator.*;


/* *
 * All the commands must be in order in this list!
 * 
 * Observe!
 * A command is build up by 8 bits and the 7-th bit is the ACK Request bit.
 * So all command must not use the ACK bit unless they need an ACK.
 * Command 0bxAxx xxxx <- A is ACK-bit.
 * Also the value 0xa6/166d (0b1010 0110) is reserved as the SYNC-byte.
 * */
public abstract class KFlyCommand
{
	public static final byte SYNC = (byte)0xa6;
	public static final byte ACK_BIT = (byte)0x40;
	public static final byte ACK_MASK = (byte)~0x40;
	
	public enum Command {
		None(0),
		ACK(0),
		Ping(0),
		DebugMessage(255),
		GetRunningMode(255),
		PrepareWriteFirmware(2),		/* Bootloader specific, shall always require ACK */
		WriteFirmwarePackage(0),		/* Bootloader specific, shall always require ACK */
		WriteLastFirmwarePackage(0),	/* Bootloader specific, shall always require ACK */
		ReadFirmwarePackage(66),		/* Bootloader specific, shall always require ACK */
		ReadLastFirmwarePackage(255),	/* Bootloader specific, shall always require ACK */
		NextPackage(0),					/* Bootloader specific, shall always require ACK */
		ExitBootloader(0),				/* Bootloader specific, shall always require ACK */
		GetBootloaderVersion(255),
		GetFirmwareVersion(255),
		SaveToFlash(0),
		GetRegulatorData(255),
		SetRegulatorData(0),
		GetChannelMix(255),
		SetChannelMi(0),
		StartRCCalibration(0),
		StopRCCalibration(0),
		CalibrateRCCenters(0),
		GetRCCalibration(255),
		SetRCCalibration(0),
		GetRCValues(255),
		GetDataDump(255),
		NoCommand(255);
		
		private int length;
		//private int length;
		
		private Command(int i) {
			this.length = i;
		}
		
		public int length() {
			return length;
		}
	}
	
	public static byte[] CreateMessage(Command cmd, boolean ACK, int size, List<Byte> message) {
		List<Byte> newMessage = new ArrayList<Byte>();

		/* First add SYNC-byte */
		newMessage.add((byte)SYNC);	
		
		/* Then add command */
		if (ACK)
			newMessage.add((byte) ((byte)cmd.ordinal() | 0x40));
		else
			newMessage.add((byte) cmd.ordinal());
		
		/* And then size */
		newMessage.add((byte) (size & 0xff));
		
		/* If there is a message, add it */
		if ((message == null) && (size == 0)) {
			return MessagetoByteArray(CRCMessage(newMessage));
		}
		else if (size == message.size()) {
			if (size > 0)
				newMessage.addAll(message);
			
			return MessagetoByteArray(CRCMessage(newMessage));
		}
		else
			return null;
	}
	
	private static List<Byte> CRCMessage(List<Byte> message) {
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
	private static byte[] MessagetoByteArray(List<Byte> message) {
		/* Replace all SYNC bytes (except the first one) with double SYNC byte to represent data */
		List<Byte> newList = new ArrayList<Byte>();
		
		for(byte b: message) {
			newList.add(b);
			if (b == SYNC)
				newList.add(b);
		}
		
		newList.remove(0); /* Remove one of the two SYNC-bytes at the start. Only one SYNC-byte denotes Sync */
		
		byte[] ByteArray = new byte[newList.size()];
		for (int i = 0; i < newList.size(); i++) 
			ByteArray[i] = (byte)newList.get(i);
			
		return ByteArray;
	}
}