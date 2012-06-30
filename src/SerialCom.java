import java.util.*;
import jssc.*;

public class SerialCom {
	private SerialPort serialPort;
	
	public List<String> getPorts() {
		return Arrays.asList(SerialPortList.getPortNames());
    }
	
	public void ConnectToCom(String port, int speed) throws SerialPortException	{
		serialPort = new SerialPort(port); 
		serialPort.openPort();
		serialPort.setParams(speed, 8, 1, 0);
		serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
		serialPort.addEventListener(new SerialPortReader());
	}
	
	public void CloseCom() throws SerialPortException {
		serialPort.closePort();
	}
	
	class SerialPortReader implements SerialPortEventListener {
		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR()) {
				try {
					byte buffer[] = serialPort.readBytes(event.getEventValue());
					System.out.println(buffer);
				}
				catch (SerialPortException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
