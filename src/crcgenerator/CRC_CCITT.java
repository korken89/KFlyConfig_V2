package crcgenerator;
import java.util.*;

public class CRC_CCITT {
	public static byte[] GenerateCRC(List<Byte> message)
	{
		byte[] ret = new byte[2];
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
        ret[0] = (byte)((crc >> 8) & 0x00ff);
        ret[1] = (byte)(crc & 0x00ff);
        
		return ret;
	}
}
