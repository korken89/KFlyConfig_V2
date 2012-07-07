package gui;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;
import communication.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jssc.*;

public class GUI extends JFrame implements WindowListener {
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
	private JButton btnExitBootloader;
	private JButton btnFirmwarePathBrowse;
	private JPanel panelRegulator;
	private JPanel panelInputs;
	private JPanel panelMixer;
	private JButton btnConnect;
	private JSpinner rKp_Pitch;
	private JSpinner rKp_Roll;
	private JSpinner rKp_Yaw;
	private JSpinner rKi_Pitch;
	private JSpinner rKi_Roll;
	private JSpinner rKi_Yaw;
	private JSpinner rIL_Pitch;
	private JSpinner rIL_Roll;
	private JSpinner rIL_Yaw;
	private JSpinner aKp_Pitch;
	private JSpinner aKp_Roll;
	private JSpinner aKp_Yaw;
	private JSpinner aKi_Pitch;
	private JSpinner aKi_Roll;
	private JSpinner aKi_Yaw;
	private JSpinner aIL_Pitch;
	private JSpinner aIL_Roll;
	private JSpinner aIL_Yaw;
	private JSpinner Ch1_Min;
	private JSpinner Ch2_Min;
	private JSpinner Ch3_Min;
	private JSpinner Ch4_Min;
	private JSpinner Ch5_Min;
	private JSpinner Ch6_Min;
	private JSpinner Ch7_Min;
	private JSpinner Ch8_Min;
	private JSpinner Ch1_Center;
	private JSpinner Ch2_Center;
	private JSpinner Ch3_Center;
	private JSpinner Ch4_Center;
	private JSpinner Ch5_Center;
	private JSpinner Ch6_Center;
	private JSpinner Ch7_Center;
	private JSpinner Ch8_Center;
	private JSpinner Ch1_Max;
	private JSpinner Ch2_Max;
	private JSpinner Ch3_Max;
	private JSpinner Ch4_Max;
	private JSpinner Ch5_Max;
	private JSpinner Ch6_Max;
	private JSpinner Ch7_Max;
	private JSpinner Ch8_Max;
	private JComboBox Ch1_Role;
	private JComboBox Ch2_Role;
	private JComboBox Ch3_Role;
	private JComboBox Ch4_Role;
	private JComboBox Ch5_Role;
	private JComboBox Ch6_Role;
	private JComboBox Ch7_Role;
	private JComboBox Ch8_Role;
	private JComboBox Ch1_Type;
	private JComboBox Ch2_Type;
	private JComboBox Ch3_Type;
	private JComboBox Ch4_Type;
	private JComboBox Ch5_Type;
	private JComboBox Ch6_Type;
	private JComboBox Ch7_Type;
	private JComboBox Ch8_Type;
	private JTextField Ch3_Speed;
	private JTextField Ch4_Speed;
	private JTextField Ch6_Speed;
	private JTextField Ch8_Speed;
	private JComboBox Ch1_Speed;
	private JSpinner Ch1_Mix1;
	private JSpinner Ch2_Mix1;
	private JSpinner Ch3_Mix1;
	private JSpinner Ch4_Mix1;
	private JSpinner Ch5_Mix1;
	private JSpinner Ch6_Mix1;
	private JSpinner Ch7_Mix1;
	private JSpinner Ch8_Mix1;
	private JSpinner Ch1_Mix2;
	private JSpinner Ch2_Mix2;
	private JSpinner Ch3_Mix2;
	private JSpinner Ch4_Mix2;
	private JSpinner Ch5_Mix2;
	private JSpinner Ch6_Mix2;
	private JSpinner Ch7_Mix2;
	private JSpinner Ch8_Mix2;
	private JSpinner Ch1_Mix3;
	private JSpinner Ch2_Mix3;
	private JSpinner Ch3_Mix3;
	private JSpinner Ch4_Mix3;
	private JSpinner Ch5_Mix3;
	private JSpinner Ch6_Mix3;
	private JSpinner Ch7_Mix3;
	private JSpinner Ch8_Mix3;
	private JSpinner Ch1_Mix4;
	private JSpinner Ch2_Mix4;
	private JSpinner Ch3_Mix4;
	private JSpinner Ch4_Mix4;
	private JSpinner Ch5_Mix4;
	private JSpinner Ch6_Mix4;
	private JSpinner Ch7_Mix4;
	private JSpinner Ch8_Mix4;
	private JTextField Ch2_Speed;
	private JComboBox Ch5_Speed;
	private JComboBox Ch7_Speed;
	private JPanel panel_10;
	private JSpinner spinner_9;
	private JSpinner spinner_10;
	private JSpinner spinner_11;
	private JLabel lblMaximumSpeedms;
	private JSpinner spinner_12;
	private JSpinner spinner_13;
	private JSpinner spinner_14;
	private JLabel lblMaximumDistancem;
	private JLabel lblForwardreverse;
	private JLabel lblSideToSide;
	private JLabel lblUpdown;
	
	public GUI() {
		setResizable(false);
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
		addWindowListener(this);
		
		setTitle("KFly Config 2.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 528, 378); /* For windowbuilder init */
		
		if (isWindows())
			setBounds(100, 100, 528, 378);
		else
			setBounds(100, 100, 528, 360);
		
		JPanel contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 540, 385);
		if (isWindows())
			tabbedPane.setBounds(0, 0, 540, 385);
		else
			tabbedPane.setBounds(0, 0, 528, 365);
		contentPane.add(tabbedPane);
		
		JPanel panelConnection = new JPanel();
		panelConnection.setBackground(Color.WHITE);
		tabbedPane.addTab("Connection", null, panelConnection, null);
		panelConnection.setLayout(null);
		
		labelFirmwareProgress = new JLabel("Status: Downloading... (47%)");
		labelFirmwareProgress.setHorizontalAlignment(SwingConstants.CENTER);
		labelFirmwareProgress.setBounds(-3, 309, 525, 16);
		panelConnection.add(labelFirmwareProgress);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "Connection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 10, 499, 169);
		panelConnection.add(panel_1);
		
		comboPortName = new JComboBox();
		comboPortName.setMaximumRowCount(100);
		comboPortName.setEditable(true);
		comboPortName.setBounds(15, 39, 145, 20);
		panel_1.add(comboPortName);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (connectedToComPort == true) {
					try {
						serialCom.ClosePort();
						btnConnect.setText("Connect");
						connectedToComPort = false;
					} catch (SerialPortException e) {}
				} else {
					if (comboPortName.getSelectedItem().toString().trim() != "") {
						try {
							serialCom.ConnectToPort(comboPortName.getSelectedItem().toString().trim(),
													Integer.parseInt(comboBaudrate.getSelectedItem().toString()));
							btnConnect.setText("Disconnect");
							connectedToComPort = true;
						} catch (SerialPortException e) {
							JOptionPane.showMessageDialog(null, "Unable to connect to port (" + e.getExceptionType() + ").", "Connection problem", JOptionPane.ERROR_MESSAGE);
						}
					}	
				}
			}
		});
		btnConnect.setBounds(293, 36, 89, 25);
		panel_1.add(btnConnect);
		
		JLabel lblBootloader = new JLabel("Bootloader");
		lblBootloader.setBounds(16, 67, 470, 14);
		panel_1.add(lblBootloader);
		
		txtBootloader = new JTextField();
		txtBootloader.setText("Awaiting connection...");
		txtBootloader.setEditable(false);
		txtBootloader.setColumns(10);
		txtBootloader.setBounds(15, 84, 471, 22);
		panel_1.add(txtBootloader);
		
		txtFirmware = new JTextField();
		txtFirmware.setText("Awaiting connection...");
		txtFirmware.setEditable(false);
		txtFirmware.setColumns(10);
		txtFirmware.setBounds(15, 130, 471, 22);
		panel_1.add(txtFirmware);
		
		JLabel lblFirmware = new JLabel("Firmware");
		lblFirmware.setBounds(15, 113, 470, 14);
		panel_1.add(lblFirmware);
		
		JLabel lblPort = new JLabel("Com Ports");
		lblPort.setBounds(16, 22, 110, 14);
		panel_1.add(lblPort);
		
		comboBaudrate = new JComboBox();
		comboBaudrate.setModel(new DefaultComboBoxModel(new String[] {"256000", "115200", "57600", "38400", "19200", "9600", "4800"}));
		comboBaudrate.setSelectedIndex(1);
		comboBaudrate.setMaximumRowCount(100);
		comboBaudrate.setBounds(172, 39, 111, 20);
		panel_1.add(comboBaudrate);
		
		JLabel lblBaudrate = new JLabel("Baudrate");
		lblBaudrate.setBounds(173, 22, 110, 14);
		panel_1.add(lblBaudrate);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(null, "Update Firmware", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 186, 499, 76);
		panelConnection.add(panel_2);
		
		FirmwarePath = new JTextField();
		FirmwarePath.setColumns(10);
		FirmwarePath.setBounds(15, 39, 375, 22);
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
		btnFirmwarePathBrowse.setBounds(400, 38, 89, 25);
		panel_2.add(btnFirmwarePathBrowse);
		
		JLabel lblPathToFile = new JLabel("Path to file");
		lblPathToFile.setBounds(17, 23, 374, 14);
		panel_2.add(lblPathToFile);
		
		progressBar = new JProgressBar();
		progressBar.setValue(47);
		progressBar.setForeground(new Color(50, 205, 50));
		progressBar.setBounds(-5, 308, 535, 18);
		panelConnection.add(progressBar);
		
		btnFirmwareFlash = new JButton("Flash");
		btnFirmwareFlash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Byte> newList = new ArrayList<Byte>();
				/* Header starts here */
				newList.add((byte) 0xa6);
				newList.add((byte) 0x0d);
				newList.add((byte) 0x00);
				
				try {
					serialCom.WirteBytes(null);
				} catch (SerialPortException e) {
				}
			}
		});
		btnFirmwareFlash.setBounds(14, 272, 89, 25);
		panelConnection.add(btnFirmwareFlash);
		
		btnExitBootloader = new JButton("Exit Bootloader");
		btnExitBootloader.setBounds(113, 272, 150, 25);
		panelConnection.add(btnExitBootloader);
		
		JButton btnSaveAllChanges = new JButton("Save to Flash");
		btnSaveAllChanges.setBounds(355, 272, 150, 25);
		panelConnection.add(btnSaveAllChanges);
		tabbedPane.setEnabledAt(0, true);		
		
		panelRegulator = new JPanel();
		panelRegulator.setBackground(Color.WHITE);
		panelRegulator.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent arg0) {
				System.out.println("Regulator Options Tab opened.");
			}
		});
		tabbedPane.addTab("Regulator", null, panelRegulator, null);
		tabbedPane.setEnabledAt(1, true);
		panelRegulator.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBorder(new TitledBorder(null, "Rate Coefficients", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 10, 500, 128);
		panelRegulator.add(panel_3);
		panel_3.setLayout(null);
		
		rKp_Pitch = new JSpinner();
		rKp_Pitch.setBounds(76, 43, 94, 20);
		panel_3.add(rKp_Pitch);
		rKp_Pitch.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		rKi_Pitch = new JSpinner();
		rKi_Pitch.setBounds(226, 43, 94, 20);
		panel_3.add(rKi_Pitch);
		rKi_Pitch.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		rIL_Pitch = new JSpinner();
		rIL_Pitch.setBounds(380, 43, 94, 20);
		panel_3.add(rIL_Pitch);
		rIL_Pitch.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		JLabel lblNewLabel = new JLabel("Pitch");
		lblNewLabel.setBounds(25, 44, 39, 16);
		panel_3.add(lblNewLabel);
				
		rKi_Roll = new JSpinner();
		rKi_Roll.setBounds(226, 68, 94, 20);
		panel_3.add(rKi_Roll);
		rKi_Roll.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		rIL_Roll = new JSpinner();
		rIL_Roll.setBounds(380, 68, 94, 20);
		panel_3.add(rIL_Roll);
		rIL_Roll.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		rKp_Roll = new JSpinner();
		rKp_Roll.setBounds(76, 68, 94, 20);
		panel_3.add(rKp_Roll);
		rKp_Roll.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		JLabel lblRoll = new JLabel("Roll");
		lblRoll.setBounds(25, 69, 39, 16);
		panel_3.add(lblRoll);
		
		rKi_Yaw = new JSpinner();
		rKi_Yaw.setBounds(226, 93, 94, 20);
		panel_3.add(rKi_Yaw);
		rKi_Yaw.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		rIL_Yaw = new JSpinner();
		rIL_Yaw.setBounds(380, 93, 94, 20);
		panel_3.add(rIL_Yaw);
		rIL_Yaw.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		rKp_Yaw = new JSpinner();
		rKp_Yaw.setBounds(76, 93, 94, 20);
		panel_3.add(rKp_Yaw);
		rKp_Yaw.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		JLabel lblYaw = new JLabel("Yaw");
		lblYaw.setBounds(25, 94, 39, 16);
		panel_3.add(lblYaw);
				
		JLabel lblNewLabel_1 = new JLabel("Kp");
		lblNewLabel_1.setBounds(76, 22, 94, 16);
		panel_3.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblKi = new JLabel("Ki");
		lblKi.setBounds(226, 22, 94, 16);
		panel_3.add(lblKi);
		lblKi.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblIntegralLimit = new JLabel("Integral Limit");
		lblIntegralLimit.setBounds(370, 22, 114, 16);
		panel_3.add(lblIntegralLimit);
		lblIntegralLimit.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setLayout(null);
		panel_4.setBorder(new TitledBorder(null, "Attitude Coefficients", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(10, 145, 500, 128);
		panelRegulator.add(panel_4);
		
		aKp_Pitch = new JSpinner();
		aKp_Pitch.setBounds(76, 43, 94, 20);
		panel_4.add(aKp_Pitch);
		aKp_Pitch.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		aKi_Pitch = new JSpinner();
		aKi_Pitch.setBounds(226, 43, 94, 20);
		panel_4.add(aKi_Pitch);
		aKi_Pitch.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		aIL_Pitch = new JSpinner();
		aIL_Pitch.setBounds(380, 43, 94, 20);
		panel_4.add(aIL_Pitch);
		aIL_Pitch.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		JLabel label = new JLabel("Pitch");
		label.setBounds(25, 44, 39, 16);
		panel_4.add(label);
				
		aKi_Roll = new JSpinner();
		aKi_Roll.setBounds(226, 68, 94, 20);
		panel_4.add(aKi_Roll);
		aKi_Roll.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		aIL_Roll = new JSpinner();
		aIL_Roll.setBounds(380, 68, 94, 20);
		panel_4.add(aIL_Roll);
		aIL_Roll.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		aKp_Roll = new JSpinner();
		aKp_Roll.setBounds(76, 68, 94, 20);
		panel_4.add(aKp_Roll);
		aKp_Roll.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		JLabel label_1 = new JLabel("Roll");
		label_1.setBounds(25, 69, 39, 16);
		panel_4.add(label_1);
		
		aKi_Yaw = new JSpinner();
		aKi_Yaw.setBounds(226, 93, 94, 20);
		panel_4.add(aKi_Yaw);
		aKi_Yaw.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		aIL_Yaw = new JSpinner();
		aIL_Yaw.setBounds(380, 93, 94, 20);
		panel_4.add(aIL_Yaw);
		aIL_Yaw.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		aKp_Yaw = new JSpinner();
		aKp_Yaw.setBounds(76, 93, 94, 20);
		panel_4.add(aKp_Yaw);
		aKp_Yaw.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		
		JLabel label_2 = new JLabel("Yaw");
		label_2.setBounds(25, 94, 39, 16);
		panel_4.add(label_2);
		
		JLabel label_3 = new JLabel("Kp");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(76, 22, 94, 16);
		panel_4.add(label_3);
		
		JLabel label_4 = new JLabel("Ki");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(226, 22, 94, 16);
		panel_4.add(label_4);
		
		JLabel label_5 = new JLabel("Integral Limit");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(370, 22, 114, 16);
		panel_4.add(label_5);
		
		JButton btnRegulatorSaveChanges = new JButton("Save changes");
		btnRegulatorSaveChanges.setBounds(371, 288, 140, 25);
		panelRegulator.add(btnRegulatorSaveChanges);
		
		panelMixer = new JPanel();
		panelMixer.setBackground(Color.WHITE);
		panelMixer.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent arg0) {
				System.out.println("Mixer Tab opened.");
			}
		});
		
		panelInputs = new JPanel();
		panelInputs.setBackground(Color.WHITE);
		panelInputs.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent arg0) {
				System.out.println("Inputs Tab opened.");
			}
		});
		tabbedPane.addTab("Inputs", null, panelInputs, null);
		tabbedPane.setEnabledAt(2, true);
		panelInputs.setLayout(null);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(UIManager.getColor("Button.disabledShadow"));
		panel_6.setBorder(new TitledBorder(null, "Calibrate Centers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setBounds(10, 257, 150, 60);
		panelInputs.add(panel_6);
		panel_6.setLayout(null);
		
		JButton btnCalibrateCenters = new JButton("Calibrate");
		btnCalibrateCenters.setBounds(15, 23, 120, 25);
		panel_6.add(btnCalibrateCenters);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(UIManager.getColor("Button.disabledShadow"));
		panel_5.setBorder(new TitledBorder(null, "Calibrate Endpoints", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(172, 257, 180, 60);
		panelInputs.add(panel_5);
		panel_5.setLayout(null);
		
		JButton btnStartCalibrateEndpoints = new JButton("Start");
		btnStartCalibrateEndpoints.setBounds(14, 24, 70, 25);
		panel_5.add(btnStartCalibrateEndpoints);
		
		JButton btnStopCalibrateEndpoints = new JButton("Stop");
		btnStopCalibrateEndpoints.setEnabled(false);
		btnStopCalibrateEndpoints.setBounds(96, 24, 70, 25);
		panel_5.add(btnStopCalibrateEndpoints);
		
		JButton btnInputsSaveChanges = new JButton("Save changes");
		btnInputsSaveChanges.setBounds(371, 288, 140, 25);
		panelInputs.add(btnInputsSaveChanges);
		
		Ch1_Role = new JComboBox();
		Ch1_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch1_Role.setSelectedIndex(0);
		Ch1_Role.setBounds(319, 25, 91, 20);
		panelInputs.add(Ch1_Role);
		
		JLabel lblCh = new JLabel("Ch. 1");
		lblCh.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh.setBounds(10, 28, 54, 14);
		panelInputs.add(lblCh);
		
		Ch1_Min = new JSpinner();
		Ch1_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch1_Min.setBounds(49, 25, 80, 20);
		panelInputs.add(Ch1_Min);
		
		Ch1_Max = new JSpinner();
		Ch1_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch1_Max.setBounds(229, 25, 80, 20);
		panelInputs.add(Ch1_Max);
		
		JLabel lblMin = new JLabel("Min");
		lblMin.setHorizontalAlignment(SwingConstants.CENTER);
		lblMin.setBounds(49, 6, 80, 14);
		panelInputs.add(lblMin);
		
		JLabel lblMax = new JLabel("Max");
		lblMax.setHorizontalAlignment(SwingConstants.CENTER);
		lblMax.setBounds(229, 6, 80, 14);
		panelInputs.add(lblMax);
		
		JLabel lblRole = new JLabel("Role");
		lblRole.setHorizontalAlignment(SwingConstants.CENTER);
		lblRole.setBounds(319, 6, 91, 14);
		panelInputs.add(lblRole);
		
		Ch2_Role = new JComboBox();
		Ch2_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch2_Role.setSelectedIndex(1);
		Ch2_Role.setBounds(319, 53, 91, 20);
		panelInputs.add(Ch2_Role);
		
		Ch2_Max = new JSpinner();
		Ch2_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch2_Max.setBounds(229, 53, 80, 20);
		panelInputs.add(Ch2_Max);
		
		Ch2_Min = new JSpinner();
		Ch2_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch2_Min.setBounds(49, 53, 80, 20);
		panelInputs.add(Ch2_Min);
		
		JLabel lblCh_1 = new JLabel("Ch. 2");
		lblCh_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh_1.setBounds(10, 56, 54, 14);
		panelInputs.add(lblCh_1);
		
		Ch3_Role = new JComboBox();
		Ch3_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch3_Role.setSelectedIndex(2);
		Ch3_Role.setBounds(319, 81, 91, 20);
		panelInputs.add(Ch3_Role);
		
		Ch3_Max = new JSpinner();
		Ch3_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch3_Max.setBounds(229, 81, 80, 20);
		panelInputs.add(Ch3_Max);
		
		Ch3_Min = new JSpinner();
		Ch3_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch3_Min.setBounds(49, 81, 80, 20);
		panelInputs.add(Ch3_Min);
		
		JLabel lblCh_2 = new JLabel("Ch. 3");
		lblCh_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh_2.setBounds(10, 84, 54, 14);
		panelInputs.add(lblCh_2);
		
		Ch4_Role = new JComboBox();
		Ch4_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch4_Role.setSelectedIndex(3);
		Ch4_Role.setBounds(319, 109, 91, 20);
		panelInputs.add(Ch4_Role);
		
		Ch4_Max = new JSpinner();
		Ch4_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch4_Max.setBounds(229, 109, 80, 20);
		panelInputs.add(Ch4_Max);
		
		Ch4_Min = new JSpinner();
		Ch4_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch4_Min.setBounds(49, 109, 80, 20);
		panelInputs.add(Ch4_Min);
		
		JLabel lblCh_3 = new JLabel("Ch. 4");
		lblCh_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh_3.setBounds(10, 112, 54, 14);
		panelInputs.add(lblCh_3);
		
		Ch5_Role = new JComboBox();
		Ch5_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch5_Role.setSelectedIndex(4);
		Ch5_Role.setBounds(319, 137, 91, 20);
		panelInputs.add(Ch5_Role);
		
		JLabel lblCh_4 = new JLabel("Ch. 5");
		lblCh_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh_4.setBounds(10, 140, 54, 14);
		panelInputs.add(lblCh_4);
		
		Ch5_Min = new JSpinner();
		Ch5_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch5_Min.setBounds(49, 137, 80, 20);
		panelInputs.add(Ch5_Min);
		
		Ch5_Max = new JSpinner();
		Ch5_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch5_Max.setBounds(229, 137, 80, 20);
		panelInputs.add(Ch5_Max);
		
		Ch6_Role = new JComboBox();
		Ch6_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch6_Role.setSelectedIndex(5);
		Ch6_Role.setBounds(319, 165, 91, 20);
		panelInputs.add(Ch6_Role);
		
		Ch6_Max = new JSpinner();
		Ch6_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch6_Max.setBounds(229, 165, 80, 20);
		panelInputs.add(Ch6_Max);
		
		Ch6_Min = new JSpinner();
		Ch6_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch6_Min.setBounds(49, 165, 80, 20);
		panelInputs.add(Ch6_Min);
		
		JLabel lblCh_5 = new JLabel("Ch. 6");
		lblCh_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh_5.setBounds(10, 168, 54, 14);
		panelInputs.add(lblCh_5);
		
		Ch7_Role = new JComboBox();
		Ch7_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch7_Role.setSelectedIndex(6);
		Ch7_Role.setBounds(319, 193, 91, 20);
		panelInputs.add(Ch7_Role);
		
		Ch7_Max = new JSpinner();
		Ch7_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch7_Max.setBounds(229, 193, 80, 20);
		panelInputs.add(Ch7_Max);
		
		Ch7_Min = new JSpinner();
		Ch7_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch7_Min.setBounds(49, 193, 80, 20);
		panelInputs.add(Ch7_Min);
		
		JLabel lblCh_6 = new JLabel("Ch. 7");
		lblCh_6.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh_6.setBounds(10, 196, 54, 14);
		panelInputs.add(lblCh_6);
		
		Ch8_Role = new JComboBox();
		Ch8_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch8_Role.setSelectedIndex(7);
		Ch8_Role.setBounds(319, 221, 91, 20);
		panelInputs.add(Ch8_Role);
		
		Ch8_Max = new JSpinner();
		Ch8_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch8_Max.setBounds(229, 221, 80, 20);
		panelInputs.add(Ch8_Max);
		
		Ch8_Min = new JSpinner();
		Ch8_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch8_Min.setBounds(49, 221, 80, 20);
		panelInputs.add(Ch8_Min);
		
		JLabel lblCh_7 = new JLabel("Ch. 8");
		lblCh_7.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh_7.setBounds(10, 224, 54, 14);
		panelInputs.add(lblCh_7);
		
		Ch8_Type = new JComboBox();
		Ch8_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch8_Type.setBounds(420, 221, 91, 20);
		panelInputs.add(Ch8_Type);
		
		Ch7_Type = new JComboBox();
		Ch7_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch7_Type.setBounds(420, 193, 91, 20);
		panelInputs.add(Ch7_Type);
		
		Ch6_Type = new JComboBox();
		Ch6_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch6_Type.setBounds(420, 165, 91, 20);
		panelInputs.add(Ch6_Type);
		
		Ch5_Type = new JComboBox();
		Ch5_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch5_Type.setBounds(420, 137, 91, 20);
		panelInputs.add(Ch5_Type);
		
		Ch4_Type = new JComboBox();
		Ch4_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch4_Type.setSelectedIndex(1);
		Ch4_Type.setBounds(420, 109, 91, 20);
		panelInputs.add(Ch4_Type);
		
		Ch3_Type = new JComboBox();
		Ch3_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch3_Type.setSelectedIndex(1);
		Ch3_Type.setBounds(420, 81, 91, 20);
		panelInputs.add(Ch3_Type);
		
		Ch2_Type = new JComboBox();
		Ch2_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch2_Type.setSelectedIndex(1);
		Ch2_Type.setBounds(420, 53, 91, 20);
		panelInputs.add(Ch2_Type);
		
		Ch1_Type = new JComboBox();
		Ch1_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch1_Type.setSelectedIndex(1);
		Ch1_Type.setBounds(420, 25, 91, 20);
		panelInputs.add(Ch1_Type);
		
		JLabel lblType = new JLabel("Type");
		lblType.setHorizontalAlignment(SwingConstants.CENTER);
		lblType.setBounds(420, 6, 91, 14);
		panelInputs.add(lblType);
		
		Ch1_Center = new JSpinner();
		Ch1_Center.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch1_Center.setBounds(139, 25, 80, 20);
		panelInputs.add(Ch1_Center);
		
		JLabel lblCenter = new JLabel("Center");
		lblCenter.setHorizontalAlignment(SwingConstants.CENTER);
		lblCenter.setBounds(129, 6, 100, 14);
		panelInputs.add(lblCenter);
		
		Ch2_Center = new JSpinner();
		Ch2_Center.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch2_Center.setBounds(139, 53, 80, 20);
		panelInputs.add(Ch2_Center);
		
		Ch3_Center = new JSpinner();
		Ch3_Center.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch3_Center.setBounds(139, 81, 80, 20);
		panelInputs.add(Ch3_Center);
		
		Ch4_Center = new JSpinner();
		Ch4_Center.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch4_Center.setBounds(139, 109, 80, 20);
		panelInputs.add(Ch4_Center);
		
		Ch5_Center = new JSpinner();
		Ch5_Center.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch5_Center.setBounds(139, 137, 80, 20);
		panelInputs.add(Ch5_Center);
		
		Ch6_Center = new JSpinner();
		Ch6_Center.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch6_Center.setBounds(139, 165, 80, 20);
		panelInputs.add(Ch6_Center);
		
		Ch7_Center = new JSpinner();
		Ch7_Center.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch7_Center.setBounds(139, 193, 80, 20);
		panelInputs.add(Ch7_Center);
		
		Ch8_Center = new JSpinner();
		Ch8_Center.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(5000), new Integer(1)));
		Ch8_Center.setBounds(139, 221, 80, 20);
		panelInputs.add(Ch8_Center);
		tabbedPane.addTab("Mixer", null, panelMixer, null);
		panelMixer.setLayout(null);
		
		Ch2_Speed = new JTextField();
		Ch2_Speed.setText("400 Hz");
		Ch2_Speed.setEditable(false);
		Ch2_Speed.setBounds(420, 52, 91, 22);
		panelMixer.add(Ch2_Speed);
		Ch2_Speed.setColumns(10);
		
		Ch3_Speed = new JTextField();
		Ch3_Speed.setText("400 Hz");
		Ch3_Speed.setEditable(false);
		Ch3_Speed.setColumns(10);
		Ch3_Speed.setBounds(420, 80, 91, 22);
		panelMixer.add(Ch3_Speed);
		
		Ch4_Speed = new JTextField();
		Ch4_Speed.setText("400 Hz");
		Ch4_Speed.setEditable(false);
		Ch4_Speed.setColumns(10);
		Ch4_Speed.setBounds(420, 108, 91, 22);
		panelMixer.add(Ch4_Speed);
		
		Ch6_Speed = new JTextField();
		Ch6_Speed.setText("400 Hz");
		Ch6_Speed.setEditable(false);
		Ch6_Speed.setColumns(10);
		Ch6_Speed.setBounds(420, 164, 91, 22);
		panelMixer.add(Ch6_Speed);
		
		Ch8_Speed = new JTextField();
		Ch8_Speed.setText("400 Hz");
		Ch8_Speed.setEditable(false);
		Ch8_Speed.setColumns(10);
		Ch8_Speed.setBounds(420, 220, 91, 22);
		panelMixer.add(Ch8_Speed);
		tabbedPane.setEnabledAt(3, true);
		
		Ch1_Speed = new JComboBox();
		Ch1_Speed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ch2_Speed.setText((String) Ch1_Speed.getSelectedItem());
				Ch3_Speed.setText((String) Ch1_Speed.getSelectedItem());
				Ch4_Speed.setText((String) Ch1_Speed.getSelectedItem());
			}
		});
		Ch1_Speed.setModel(new DefaultComboBoxModel(new String[] {"400 Hz", "50 Hz"}));
		Ch1_Speed.setBounds(420, 25, 91, 20);
		panelMixer.add(Ch1_Speed);
		
		JLabel label_6 = new JLabel("Ch. 1");
		label_6.setHorizontalAlignment(SwingConstants.LEFT);
		label_6.setBounds(10, 28, 54, 14);
		panelMixer.add(label_6);
		
		Ch1_Mix1 = new JSpinner();
		Ch1_Mix1.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		Ch1_Mix1.setBounds(49, 25, 80, 20);
		panelMixer.add(Ch1_Mix1);
		
		Ch1_Mix3 = new JSpinner();
		Ch1_Mix3.setBounds(229, 25, 80, 20);
		Ch1_Mix3.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch1_Mix3);
		
		JLabel lblThrottle = new JLabel("Throttle");
		lblThrottle.setHorizontalAlignment(SwingConstants.CENTER);
		lblThrottle.setBounds(39, 6, 100, 14);
		panelMixer.add(lblThrottle);
		
		JLabel lblRoll_1 = new JLabel("Roll");
		lblRoll_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoll_1.setBounds(229, 6, 80, 14);
		panelMixer.add(lblRoll_1);
		
		JLabel lblSpeed = new JLabel("Speed");
		lblSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpeed.setBounds(420, 6, 91, 14);
		panelMixer.add(lblSpeed);
		
		Ch2_Mix3 = new JSpinner();
		Ch2_Mix3.setBounds(229, 53, 80, 20);
		Ch2_Mix3.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch2_Mix3);
		
		Ch2_Mix1 = new JSpinner();
		Ch2_Mix1.setBounds(49, 53, 80, 20);
		Ch2_Mix1.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch2_Mix1);
		
		JLabel label_10 = new JLabel("Ch. 2");
		label_10.setHorizontalAlignment(SwingConstants.LEFT);
		label_10.setBounds(10, 56, 54, 14);
		panelMixer.add(label_10);
		
		Ch3_Mix3 = new JSpinner();
		Ch3_Mix3.setBounds(229, 81, 80, 20);
		Ch3_Mix3.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch3_Mix3);
		
		Ch3_Mix1 = new JSpinner();
		Ch3_Mix1.setBounds(49, 81, 80, 20);
		Ch3_Mix1.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch3_Mix1);
		
		JLabel label_11 = new JLabel("Ch. 3");
		label_11.setHorizontalAlignment(SwingConstants.LEFT);
		label_11.setBounds(10, 84, 54, 14);
		panelMixer.add(label_11);
		
		Ch4_Mix3 = new JSpinner();
		Ch4_Mix3.setBounds(229, 109, 80, 20);
		Ch4_Mix3.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch4_Mix3);
		
		Ch4_Mix1 = new JSpinner();
		Ch4_Mix1.setBounds(49, 109, 80, 20);
		Ch4_Mix1.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch4_Mix1);
		
		JLabel label_12 = new JLabel("Ch. 4");
		label_12.setHorizontalAlignment(SwingConstants.LEFT);
		label_12.setBounds(10, 112, 54, 14);
		panelMixer.add(label_12);
		
		Ch5_Speed = new JComboBox();
		Ch5_Speed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ch6_Speed.setText((String) Ch5_Speed.getSelectedItem());
			}
		});
		Ch5_Speed.setModel(new DefaultComboBoxModel(new String[] {"400 Hz", "50 Hz"}));
		Ch5_Speed.setBounds(420, 137, 91, 20);
		panelMixer.add(Ch5_Speed);
		
		JLabel label_13 = new JLabel("Ch. 5");
		label_13.setHorizontalAlignment(SwingConstants.LEFT);
		label_13.setBounds(10, 140, 54, 14);
		panelMixer.add(label_13);
		
		Ch5_Mix1 = new JSpinner();
		Ch5_Mix1.setBounds(49, 137, 80, 20);
		Ch5_Mix1.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch5_Mix1);
		
		Ch5_Mix3 = new JSpinner();
		Ch5_Mix3.setBounds(229, 137, 80, 20);
		Ch5_Mix3.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch5_Mix3);
		
		Ch6_Mix3 = new JSpinner();
		Ch6_Mix3.setBounds(229, 165, 80, 20);
		Ch6_Mix3.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch6_Mix3);
		
		Ch6_Mix1 = new JSpinner();
		Ch6_Mix1.setBounds(49, 165, 80, 20);
		Ch6_Mix1.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch6_Mix1);
		
		JLabel label_14 = new JLabel("Ch. 6");
		label_14.setHorizontalAlignment(SwingConstants.LEFT);
		label_14.setBounds(10, 168, 54, 14);
		panelMixer.add(label_14);
		
		Ch7_Speed = new JComboBox();
		Ch7_Speed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ch8_Speed.setText((String) Ch7_Speed.getSelectedItem());
			}
		});
		Ch7_Speed.setModel(new DefaultComboBoxModel(new String[] {"400 Hz", "50 Hz"}));
		Ch7_Speed.setBounds(420, 193, 91, 20);
		panelMixer.add(Ch7_Speed);
		
		Ch7_Mix3 = new JSpinner();
		Ch7_Mix3.setBounds(229, 193, 80, 20);
		Ch7_Mix3.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch7_Mix3);
		
		Ch7_Mix1 = new JSpinner();
		Ch7_Mix1.setBounds(49, 193, 80, 20);
		Ch7_Mix1.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch7_Mix1);
		
		JLabel label_15 = new JLabel("Ch. 7");
		label_15.setHorizontalAlignment(SwingConstants.LEFT);
		label_15.setBounds(10, 196, 54, 14);
		panelMixer.add(label_15);
		
		Ch8_Mix3 = new JSpinner();
		Ch8_Mix3.setBounds(229, 221, 80, 20);
		Ch8_Mix3.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch8_Mix3);
		
		Ch8_Mix1 = new JSpinner();
		Ch8_Mix1.setBounds(49, 221, 80, 20);
		Ch8_Mix1.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch8_Mix1);
		
		JLabel label_16 = new JLabel("Ch. 8");
		label_16.setHorizontalAlignment(SwingConstants.LEFT);
		label_16.setBounds(10, 224, 54, 14);
		panelMixer.add(label_16);
		
		Ch1_Mix2 = new JSpinner();
		Ch1_Mix2.setBounds(139, 25, 80, 20);
		Ch1_Mix2.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch1_Mix2);
		
		JLabel lblPitch = new JLabel("Pitch");
		lblPitch.setHorizontalAlignment(SwingConstants.CENTER);
		lblPitch.setBounds(129, 6, 100, 14);
		panelMixer.add(lblPitch);
		
		Ch2_Mix2 = new JSpinner();
		Ch2_Mix2.setBounds(139, 53, 80, 20);
		Ch2_Mix2.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch2_Mix2);
		
		Ch3_Mix2 = new JSpinner();
		Ch3_Mix2.setBounds(139, 81, 80, 20);
		Ch3_Mix2.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch3_Mix2);
		
		Ch4_Mix2 = new JSpinner();
		Ch4_Mix2.setBounds(139, 109, 80, 20);
		Ch4_Mix2.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch4_Mix2);
		
		Ch5_Mix2 = new JSpinner();
		Ch5_Mix2.setBounds(139, 137, 80, 20);
		Ch5_Mix2.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch5_Mix2);
		
		Ch6_Mix2 = new JSpinner();
		Ch6_Mix2.setBounds(139, 165, 80, 20);
		Ch6_Mix2.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch6_Mix2);
		
		Ch7_Mix2 = new JSpinner();
		Ch7_Mix2.setBounds(139, 193, 80, 20);
		Ch7_Mix2.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch7_Mix2);
		
		Ch8_Mix2 = new JSpinner();
		Ch8_Mix2.setBounds(139, 221, 80, 20);
		Ch8_Mix2.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch8_Mix2);
		
		JLabel lblYaw_1 = new JLabel("Yaw");
		lblYaw_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblYaw_1.setBounds(321, 6, 80, 14);
		panelMixer.add(lblYaw_1);
		
		Ch1_Mix4 = new JSpinner();
		Ch1_Mix4.setBounds(321, 25, 80, 20);
		Ch1_Mix4.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch1_Mix4);
		
		Ch2_Mix4 = new JSpinner();
		Ch2_Mix4.setBounds(321, 53, 80, 20);
		Ch2_Mix4.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch2_Mix4);
		
		Ch3_Mix4 = new JSpinner();
		Ch3_Mix4.setBounds(321, 81, 80, 20);
		Ch3_Mix4.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch3_Mix4);
		
		Ch4_Mix4 = new JSpinner();
		Ch4_Mix4.setBounds(321, 109, 80, 20);
		Ch4_Mix4.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch4_Mix4);
		
		Ch5_Mix4 = new JSpinner();
		Ch5_Mix4.setBounds(321, 137, 80, 20);
		Ch5_Mix4.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch5_Mix4);
		
		Ch6_Mix4 = new JSpinner();
		Ch6_Mix4.setBounds(321, 165, 80, 20);
		Ch6_Mix4.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch6_Mix4);
		
		Ch7_Mix4 = new JSpinner();
		Ch7_Mix4.setBounds(321, 193, 80, 20);
		Ch7_Mix4.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch7_Mix4);
		
		Ch8_Mix4 = new JSpinner();
		Ch8_Mix4.setBounds(321, 221, 80, 20);
		Ch8_Mix4.setModel(new SpinnerNumberModel(new Float(0), new Float(-1), new Float(1), new Float(0.001)));
		panelMixer.add(Ch8_Mix4);
		
		JButton button = new JButton("Save changes");
		button.setBounds(371, 288, 140, 25);
		panelMixer.add(button);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		panel_7.setBorder(new TitledBorder(null, "Presets", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_7.setBounds(10, 254, 351, 59);
		panelMixer.add(panel_7);
		panel_7.setLayout(null);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.setBounds(171, 25, 80, 23);
		panel_7.add(btnLoad);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(261, 25, 80, 23);
		panel_7.add(btnSave);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(10, 26, 151, 20);
		panel_7.add(comboBox);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.WHITE);
		tabbedPane.addTab("Flight Limits", null, panel_8, null);
		panel_8.setLayout(null);
		
		JPanel panel_9 = new JPanel();
		panel_9.setLayout(null);
		panel_9.setBorder(new TitledBorder(null, "Rate and Angle Constraints", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_9.setBackground(Color.WHITE);
		panel_9.setBounds(10, 11, 500, 128);
		panel_8.add(panel_9);
		
		JSpinner Pitch_MaxAngle = new JSpinner();
		Pitch_MaxAngle.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Pitch_MaxAngle.setBounds(187, 41, 94, 20);
		panel_9.add(Pitch_MaxAngle);
		
		JSpinner Roll_MaxAngle = new JSpinner();
		Roll_MaxAngle.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Roll_MaxAngle.setBounds(291, 41, 94, 20);
		panel_9.add(Roll_MaxAngle);
		
		JSpinner Yaw_MaxAngle = new JSpinner();
		Yaw_MaxAngle.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Yaw_MaxAngle.setBounds(395, 41, 94, 20);
		panel_9.add(Yaw_MaxAngle);
		
		JLabel lblMaximumAngledeg = new JLabel("Maximum angle (deg)");
		lblMaximumAngledeg.setBounds(10, 44, 170, 16);
		panel_9.add(lblMaximumAngledeg);
		
		JSpinner Roll_MaxRate = new JSpinner();
		Roll_MaxRate.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Roll_MaxRate.setBounds(291, 66, 94, 20);
		panel_9.add(Roll_MaxRate);
		
		JSpinner Yaw_MaxRate = new JSpinner();
		Yaw_MaxRate.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Yaw_MaxRate.setBounds(395, 66, 94, 20);
		panel_9.add(Yaw_MaxRate);
		
		JSpinner Pitch_MaxRate = new JSpinner();
		Pitch_MaxRate.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Pitch_MaxRate.setBounds(187, 66, 94, 20);
		panel_9.add(Pitch_MaxRate);
		
		JLabel lblMaximumRatedegs = new JLabel("Maximum rate (deg/s)");
		lblMaximumRatedegs.setBounds(10, 69, 170, 16);
		panel_9.add(lblMaximumRatedegs);
		
		JSpinner Roll_MaxRateAttitude = new JSpinner();
		Roll_MaxRateAttitude.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Roll_MaxRateAttitude.setBounds(291, 91, 94, 20);
		panel_9.add(Roll_MaxRateAttitude);
		
		JSpinner Yaw_MaxRateAttitude = new JSpinner();
		Yaw_MaxRateAttitude.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Yaw_MaxRateAttitude.setBounds(395, 91, 94, 20);
		panel_9.add(Yaw_MaxRateAttitude);
		
		JSpinner Pitch_MaxRateAttitude = new JSpinner();
		Pitch_MaxRateAttitude.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Pitch_MaxRateAttitude.setBounds(187, 91, 94, 20);
		panel_9.add(Pitch_MaxRateAttitude);
		
		JLabel lblAttitudeMaximumRate = new JLabel("Maximum rate (Attitude)");
		lblAttitudeMaximumRate.setBounds(10, 94, 227, 16);
		panel_9.add(lblAttitudeMaximumRate);
		
		JLabel lblPitch_1 = new JLabel("Pitch");
		lblPitch_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPitch_1.setBounds(187, 20, 94, 16);
		panel_9.add(lblPitch_1);
		
		JLabel lblRoll_2 = new JLabel("Roll");
		lblRoll_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoll_2.setBounds(291, 20, 94, 16);
		panel_9.add(lblRoll_2);
		
		JLabel lblYaw_2 = new JLabel("Yaw");
		lblYaw_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblYaw_2.setBounds(395, 20, 94, 16);
		panel_9.add(lblYaw_2);
		
		panel_10 = new JPanel();
		panel_10.setLayout(null);
		panel_10.setBorder(new TitledBorder(null, "Speed and Hight Constraints (MPC Mode)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_10.setBackground(Color.WHITE);
		panel_10.setBounds(10, 150, 500, 103);
		panel_8.add(panel_10);
		
		spinner_9 = new JSpinner();
		spinner_9.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		spinner_9.setBounds(187, 41, 94, 20);
		panel_10.add(spinner_9);
		
		spinner_10 = new JSpinner();
		spinner_10.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		spinner_10.setEnabled(false);
		spinner_10.setBounds(291, 41, 94, 20);
		panel_10.add(spinner_10);
		
		spinner_11 = new JSpinner();
		spinner_11.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		spinner_11.setEnabled(false);
		spinner_11.setBounds(395, 41, 94, 20);
		panel_10.add(spinner_11);
		
		lblMaximumSpeedms = new JLabel("Maximum speed (m/s)");
		lblMaximumSpeedms.setBounds(10, 44, 170, 16);
		panel_10.add(lblMaximumSpeedms);
		
		spinner_12 = new JSpinner();
		spinner_12.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		spinner_12.setEnabled(false);
		spinner_12.setBounds(291, 66, 94, 20);
		panel_10.add(spinner_12);
		
		spinner_13 = new JSpinner();
		spinner_13.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		spinner_13.setEnabled(false);
		spinner_13.setBounds(395, 66, 94, 20);
		panel_10.add(spinner_13);
		
		spinner_14 = new JSpinner();
		spinner_14.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(0.001)));
		spinner_14.setEnabled(false);
		spinner_14.setBounds(187, 66, 94, 20);
		panel_10.add(spinner_14);
		
		lblMaximumDistancem = new JLabel("Maximum distance (m)");
		lblMaximumDistancem.setBounds(10, 69, 170, 16);
		panel_10.add(lblMaximumDistancem);
		
		lblForwardreverse = new JLabel("For-/Backward");
		lblForwardreverse.setHorizontalAlignment(SwingConstants.CENTER);
		lblForwardreverse.setBounds(177, 20, 114, 16);
		panel_10.add(lblForwardreverse);
		
		lblSideToSide = new JLabel("Side to Side");
		lblSideToSide.setHorizontalAlignment(SwingConstants.CENTER);
		lblSideToSide.setBounds(291, 20, 94, 16);
		panel_10.add(lblSideToSide);
		
		lblUpdown = new JLabel("Up/Down");
		lblUpdown.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdown.setBounds(395, 20, 94, 16);
		panel_10.add(lblUpdown);
		
		JButton button_1 = new JButton("Save changes");
		button_1.setBounds(371, 288, 140, 25);
		panel_8.add(button_1);
		
		JPanel panelDebug = new JPanel();
		panelDebug.setBackground(Color.WHITE);
		tabbedPane.addTab("Debug", null, panelDebug, null);
		tabbedPane.setEnabledAt(5, true);
		panelDebug.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(null, "Debug Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 499, 270);
		panelDebug.add(panel);
		panel.setLayout(null);
		
		txtrDebugData = new JTextArea();
		txtrDebugData.setBackground(new Color(250, 250, 250));
		txtrDebugData.setText("Awaiting debug data...");
		txtrDebugData.setEditable(false);
		txtrDebugData.setBounds(15, 23, 469, 231);
		txtrDebugData.setBorder(new LineBorder(new Color(160, 160, 160), 1, true));
		panel.add(txtrDebugData);
		
		JButton btnDebugClear = new JButton("Clear");
		btnDebugClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtrDebugData.setText("");
			}
		});
		btnDebugClear.setBounds(210, 290, 100, 25);
		panelDebug.add(btnDebugClear);
	}
	
	public void PopulateComPorts() {
		for (String str: SerialCom.getPorts())
			comboPortName.addItem(str);
	}
	
	public void BootloaderAvalible(boolean tof) {
		btnFirmwareFlash.setEnabled(tof);
		btnExitBootloader.setEnabled(tof);
		btnFirmwarePathBrowse.setEnabled(tof);
		FirmwarePath.setEditable(tof);
		tabbedPane.setEnabledAt(1, !tof);
		tabbedPane.setEnabledAt(2, !tof);
		tabbedPane.setEnabledAt(3, !tof);
	}
	
	public void DisableUntilConnected() {
		btnFirmwareFlash.setEnabled(false);
		btnExitBootloader.setEnabled(false);
		btnFirmwarePathBrowse.setEnabled(false);
		FirmwarePath.setEditable(false);
		tabbedPane.setEnabledAt(1, false);
		tabbedPane.setEnabledAt(2, false);
		tabbedPane.setEnabledAt(3, false);
	}
	
	public static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("win") >= 0);
	}
	
	public void windowClosing(WindowEvent e) {
		try {
			serialCom.ClosePort();
		} catch (SerialPortException e1) {
		} catch (NullPointerException e2) {}
	}
	public void windowClosed(WindowEvent e) {
		try {
			serialCom.ClosePort();
		} catch (SerialPortException e1) {
		} catch (NullPointerException e2) {}
	}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}
