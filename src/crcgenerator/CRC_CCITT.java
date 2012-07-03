package crcgenerator;
import java.util.*;

public class CRC_CCITT {
	public static List<Byte> GenerateCRC(List<Byte> message)
	{
		List<Byte> ret = new ArrayList<Byte>();
        int crc = 0xffff;
        final int polynomial = 0x1021; 

        for (byte b : message) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7-i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                
                crc <<= 1;
                
                if (c15 ^ bit)
                	crc ^= polynomial;
             }
        }

        crc &= 0xffff;
        ret.add((byte)((crc >> 8) & 0x00ff));
        ret.add((byte)(crc & 0x00ff));
        
		return ret;
	}
}
