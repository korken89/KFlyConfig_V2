import gui.GUI;
import java.awt.EventQueue;

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
	}
	
	public static String IntToHex(int value) {
		String hex = Integer.toHexString(value);
		hex = (hex.length() % 2 == 0) ? "" + hex : "0" + hex;
		return hex;
	}
}
