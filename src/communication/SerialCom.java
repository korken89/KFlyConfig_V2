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
	
	class SerialPortReader implements SerialPortEventListener {
		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR()) {
				try {
					byte buffer[] = serialPort.readBytes();
					for (byte i: buffer)
						System.out.print((char)i);
				}
				catch (SerialPortException ex) {
						ex.printStackTrace();
				}
			}
		}
	}
}
