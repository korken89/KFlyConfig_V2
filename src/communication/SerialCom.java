package communication;

import java.util.*;
import jssc.*;

public class SerialCom {
	private SerialPort serialPort;
	
	public static List<String> getPorts() {
		return Arrays.asList(SerialPortList.getPortNames());
    }
	
	public void ConnectToPort(String port, int speed) throws SerialPortException {
		serialPort = new SerialPort(port); 
		serialPort.openPort();
		serialPort.setParams(speed, 8, 1, 0);
		serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
		serialPort.addEventListener(new SerialPortReader());
	}
	
	public void ClosePort() throws SerialPortException {
		serialPort.closePort();
	}
	
	public void WirteBytes(byte[] message) throws SerialPortException {
		serialPort.writeBytes(message);
	}
	
	class SerialPortReader implements SerialPortEventListener {
		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR()) {
				try {
					byte buffer[] = serialPort.readBytes();
					for (byte i: buffer)
						System.out.print(IntToHex((int)i & 0xff) + " ");
						
				}
				catch (SerialPortException ex) {
						ex.printStackTrace();
				}
			}
		}
	}
	
	/* *
	 * This method makes hex numbers in correct length.
	 * */
	public static String IntToHex(int value) {
		String hex = Integer.toHexString(value);
		hex = (hex.length() % 2 == 0) ? hex : "0" + hex;
		return hex;
	}
}
