import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.*;
import java.io.*;

import jssc.SerialPortException;

public class GUI extends JFrame {
	private SerialCom serialCom;
	private boolean connectedToComPort = false;
	
	private static final long serialVersionUID = -949825819154771934L;
	private JTextField txtBootloader;
	private JTextField FirmwarePath;
	private JTextField txtFirmware;
	private JTabbedPane tabbedPane;
	private JLabel labelFirmwareProgress;
	private JComboBox comboPortName;
	private JProgressBar progressBar;
	private JTextArea txtrDebugData;
	private JComboBox comboBaudrate;
	private JButton btnFirmwareFlash;
	private JButton btnFirmwareVerify;
	private JButton btnFirmwareRead;
	private JButton btnFirmwarePathBrowse;
	private JPanel panelRegulator;
	private JPanel panelInputs;
	private JPanel panelMixer;
	private JButton btnConnect;
	
	public GUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		serialCom = new SerialCom();
		
		setTitle("KFly Config V2.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 410);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSaveConfiguration = new JMenuItem("Save Configuration");
		mntmSaveConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Saving Config...");
			}
		});
		mnFile.add(mntmSaveConfiguration);
		
		JMenuItem mntmLoadConfiguration = new JMenuItem("Load Configuration");
		mntmLoadConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Loading Config...");
			}
		});
		mnFile.add(mntmLoadConfiguration);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}

        });
		mnFile.add(mntmExit);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 524, 352);
		contentPane.add(tabbedPane);
		
		JPanel panelConnection = new JPanel();
		tabbedPane.addTab("Connection", null, panelConnection, null);
		panelConnection.setLayout(null);
		
		labelFirmwareProgress = new JLabel("Status: Downloading (47%)");
		labelFirmwareProgress.setHorizontalAlignment(SwingConstants.CENTER);
		labelFirmwareProgress.setBounds(-3, 308, 524, 14);
		panelConnection.add(labelFirmwareProgress);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "Connection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 11, 499, 169);
		panelConnection.add(panel_1);
		
		comboPortName = new JComboBox();
		comboPortName.setMaximumRowCount(100);
		comboPortName.setEditable(true);
		comboPortName.setBounds(15, 39, 111, 20);
		panel_1.add(comboPortName);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (connectedToComPort == true) {
					try {
						serialCom.CloseCom();
						btnConnect.setText("Connect");
						connectedToComPort = false;
					} catch (SerialPortException e) {}
				} else {
					if (comboPortName.getSelectedItem().toString().trim() != "") {
						try {
							serialCom.ConnectToCom(	comboPortName.getSelectedItem().toString().trim(),
													Integer.parseInt(comboBaudrate.getSelectedItem().toString()));
							btnConnect.setText("Disconnect");
							connectedToComPort = true;
						} catch (SerialPortException e) {
							JOptionPane.showMessageDialog(null, "Unable to connect to port.", "Connection problem", JOptionPane.PLAIN_MESSAGE);
						}
						
						
					}	
				}
			}
		});
		btnConnect.setBounds(257, 37, 89, 23);
		panel_1.add(btnConnect);
		
		JLabel lblBootloader = new JLabel("Bootloader");
		lblBootloader.setBounds(16, 67, 470, 14);
		panel_1.add(lblBootloader);
		
		txtBootloader = new JTextField();
		txtBootloader.setText("Awaiting connection...");
		txtBootloader.setEditable(false);
		txtBootloader.setColumns(10);
		txtBootloader.setBounds(15, 84, 471, 20);
		panel_1.add(txtBootloader);
		
		txtFirmware = new JTextField();
		txtFirmware.setText("Awaiting connection...");
		txtFirmware.setEditable(false);
		txtFirmware.setColumns(10);
		txtFirmware.setBounds(15, 130, 471, 20);
		panel_1.add(txtFirmware);
		
		JLabel lblFirmware = new JLabel("Firmware");
		lblFirmware.setBounds(15, 113, 470, 14);
		panel_1.add(lblFirmware);
		
		JLabel lblPort = new JLabel("Com Ports");
		lblPort.setBounds(16, 22, 58, 14);
		panel_1.add(lblPort);
		
		comboBaudrate = new JComboBox();
		comboBaudrate.setModel(new DefaultComboBoxModel(new String[] {"230400", "115200", "57600", "38400", "19200", "9600", "4800", "2400"}));
		comboBaudrate.setSelectedIndex(1);
		comboBaudrate.setMaximumRowCount(100);
		comboBaudrate.setBounds(136, 39, 111, 20);
		panel_1.add(comboBaudrate);
		
		JLabel lblBaudrate = new JLabel("Baudrate");
		lblBaudrate.setBounds(137, 22, 58, 14);
		panel_1.add(lblBaudrate);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(null, "Firmware (Bootloader Mode)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 186, 499, 108);
		panelConnection.add(panel_2);
		
		btnFirmwareFlash = new JButton("Flash");
		btnFirmwareFlash.setBounds(15, 71, 89, 23);
		panel_2.add(btnFirmwareFlash);
		
		btnFirmwareVerify = new JButton("Verify");
		btnFirmwareVerify.setBounds(114, 71, 89, 23);
		panel_2.add(btnFirmwareVerify);
		
		FirmwarePath = new JTextField();
		FirmwarePath.setColumns(10);
		FirmwarePath.setBounds(15, 40, 375, 20);
		panel_2.add(FirmwarePath);
		
		btnFirmwarePathBrowse = new JButton("Browse");
		btnFirmwarePathBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileopen = new JFileChooser();
				fileopen.addChoosableFileFilter(new FileNameExtensionFilter("ARM binary file (*.bin)", "bin"));

				if (fileopen.showDialog(null, "Open file") == JFileChooser.APPROVE_OPTION) {
					File file = fileopen.getSelectedFile();
					FirmwarePath.setText(file.toString());
				}
			}
		});
		btnFirmwarePathBrowse.setBounds(400, 39, 89, 23);
		panel_2.add(btnFirmwarePathBrowse);
		
		btnFirmwareRead = new JButton("Read");
		btnFirmwareRead.setBounds(213, 71, 89, 23);
		panel_2.add(btnFirmwareRead);
		
		JLabel lblPathToFile = new JLabel("Path to file");
		lblPathToFile.setBounds(17, 23, 374, 14);
		panel_2.add(lblPathToFile);
		
		progressBar = new JProgressBar();
		progressBar.setValue(47);
		progressBar.setForeground(new Color(50, 205, 50));
		progressBar.setBounds(-3, 306, 524, 21);
		panelConnection.add(progressBar);
		tabbedPane.setEnabledAt(0, true);		
		
		panelRegulator = new JPanel();
		tabbedPane.addTab("Regulator Options", null, panelRegulator, null);
		tabbedPane.setEnabledAt(1, true);
		panelRegulator.setLayout(null);
		
		panelInputs = new JPanel();
		tabbedPane.addTab("Inputs", null, panelInputs, null);
		tabbedPane.setEnabledAt(2, true);
		panelInputs.setLayout(null);
		
		panelMixer = new JPanel();
		tabbedPane.addTab("Output Mixer", null, panelMixer, null);
		tabbedPane.setEnabledAt(3, true);
		panelMixer.setLayout(null);
		
		JPanel panelDebug = new JPanel();
		tabbedPane.addTab("Debug", null, panelDebug, null);
		tabbedPane.setEnabledAt(4, true);
		panelDebug.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Debug Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 499, 270);
		panelDebug.add(panel);
		panel.setLayout(null);
		
		txtrDebugData = new JTextArea();
		txtrDebugData.setText("Awaiting debug data...");
		txtrDebugData.setEditable(false);
		txtrDebugData.setBounds(10, 20, 479, 239);
		txtrDebugData.setBorder(new LineBorder(new Color(160, 160, 160), 1, true));
		panel.add(txtrDebugData);
		
		JButton btnDebugClear = new JButton("Clear Window");
		btnDebugClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtrDebugData.setText("");
			}
		});
		btnDebugClear.setBounds(210, 290, 100, 23);
		panelDebug.add(btnDebugClear);
	}
	
	public void PopulateComPorts(){
		for (String str: serialCom.getPorts())
			comboPortName.addItem(str);
	}
	
	public void BootloaderAvalible(boolean tof) {
		btnFirmwareFlash.setEnabled(tof);
		btnFirmwareVerify.setEnabled(tof);
		btnFirmwareRead.setEnabled(tof);
		btnFirmwarePathBrowse.setEnabled(tof);
		FirmwarePath.setEditable(tof);
		tabbedPane.setEnabledAt(1, !tof);
		tabbedPane.setEnabledAt(2, !tof);
		tabbedPane.setEnabledAt(3, !tof);
	}
	
	public void DisableUntilConnected() {
		btnFirmwareFlash.setEnabled(false);
		btnFirmwareVerify.setEnabled(false);
		btnFirmwareRead.setEnabled(false);
		btnFirmwarePathBrowse.setEnabled(false);
		FirmwarePath.setEditable(false);
		tabbedPane.setEnabledAt(1, false);
		tabbedPane.setEnabledAt(2, false);
		tabbedPane.setEnabledAt(3, false);
	}
}
