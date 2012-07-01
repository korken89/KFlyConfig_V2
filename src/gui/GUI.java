package gui;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;
import communication.*;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.*;
import java.io.*;
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
	private JButton btnFirmwareVerify;
	private JButton btnFirmwareRead;
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
		addWindowListener(this);
		
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
				dispose();
			}

        });
		mnFile.add(mntmExit);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 526, 352);
		contentPane.add(tabbedPane);
		
		JPanel panelConnection = new JPanel();
		panelConnection.setBackground(Color.WHITE);
		tabbedPane.addTab("Connection", null, panelConnection, null);
		panelConnection.setLayout(null);
		
		labelFirmwareProgress = new JLabel("Status: Downloading (47%)");
		labelFirmwareProgress.setHorizontalAlignment(SwingConstants.CENTER);
		labelFirmwareProgress.setBounds(-3, 308, 526, 16);
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
		comboPortName.setBounds(15, 39, 111, 20);
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
							JOptionPane.showMessageDialog(null, "Unable to connect to port.", "Connection problem", JOptionPane.ERROR_MESSAGE);
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
		panel_2.setBackground(Color.WHITE);
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(null, "Update Firmware", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 186, 499, 76);
		panelConnection.add(panel_2);
		
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
		
		JLabel lblPathToFile = new JLabel("Path to file");
		lblPathToFile.setBounds(17, 23, 374, 14);
		panel_2.add(lblPathToFile);
		
		progressBar = new JProgressBar();
		progressBar.setValue(47);
		progressBar.setForeground(new Color(50, 205, 50));
		progressBar.setBounds(-3, 308, 524, 18);
		panelConnection.add(progressBar);
		
		btnFirmwareFlash = new JButton("Flash");
		btnFirmwareFlash.setBounds(26, 272, 89, 23);
		panelConnection.add(btnFirmwareFlash);
		
		btnFirmwareVerify = new JButton("Verify");
		btnFirmwareVerify.setBounds(125, 272, 89, 23);
		panelConnection.add(btnFirmwareVerify);
		
		btnFirmwareRead = new JButton("Read");
		btnFirmwareRead.setBounds(224, 272, 89, 23);
		panelConnection.add(btnFirmwareRead);
		
		JButton btnSaveAllChanges = new JButton("Save all changes to Flash");
		btnSaveAllChanges.setBounds(322, 271, 185, 25);
		panelConnection.add(btnSaveAllChanges);
		tabbedPane.setEnabledAt(0, true);		
		
		panelRegulator = new JPanel();
		panelRegulator.setBackground(Color.WHITE);
		panelRegulator.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent arg0) {
				System.out.println("Regulator Options Tab opened.");
			}
		});
		tabbedPane.addTab("Regulator Options", null, panelRegulator, null);
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
		lblIntegralLimit.setBounds(380, 22, 94, 16);
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
		label_5.setBounds(380, 22, 94, 16);
		panel_4.add(label_5);
		
		JButton btnRegulatorSaveChanges = new JButton("Save changes");
		btnRegulatorSaveChanges.setBounds(407, 288, 103, 25);
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
		panel_6.setBounds(10, 257, 167, 60);
		panelInputs.add(panel_6);
		panel_6.setLayout(null);
		
		JButton btnCalibrateCenters = new JButton("Calibrate Centers");
		btnCalibrateCenters.setBounds(13, 23, 142, 25);
		panel_6.add(btnCalibrateCenters);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(UIManager.getColor("Button.disabledShadow"));
		panel_5.setBorder(new TitledBorder(null, "Calibrate Endpoints", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(187, 257, 167, 60);
		panelInputs.add(panel_5);
		panel_5.setLayout(null);
		
		JButton btnStartCalibrateEndpoints = new JButton("Start");
		btnStartCalibrateEndpoints.setBounds(12, 24, 65, 25);
		panel_5.add(btnStartCalibrateEndpoints);
		
		JButton btnStopCalibrateEndpoints = new JButton("Stop");
		btnStopCalibrateEndpoints.setEnabled(false);
		btnStopCalibrateEndpoints.setBounds(89, 24, 65, 25);
		panel_5.add(btnStopCalibrateEndpoints);
		
		JButton btnInputsSaveChanges = new JButton("Save changes");
		btnInputsSaveChanges.setBounds(408, 288, 103, 25);
		panelInputs.add(btnInputsSaveChanges);
		
		JComboBox Ch1_Role = new JComboBox();
		Ch1_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch1_Role.setSelectedIndex(0);
		Ch1_Role.setBounds(366, 25, 66, 20);
		panelInputs.add(Ch1_Role);
		
		JLabel lblCh = new JLabel("Ch. 1");
		lblCh.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh.setBounds(10, 28, 29, 14);
		panelInputs.add(lblCh);
		
		JSlider Ch1_Center = new JSlider();
		Ch1_Center.setMajorTickSpacing(10);
		Ch1_Center.setValue(10);
		Ch1_Center.setBackground(Color.WHITE);
		Ch1_Center.setBounds(103, 25, 200, 23);
		panelInputs.add(Ch1_Center);
		
		JSpinner Ch1_Min = new JSpinner();
		Ch1_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch1_Min.setBounds(49, 25, 48, 20);
		panelInputs.add(Ch1_Min);
		
		JSpinner Ch1_Max = new JSpinner();
		Ch1_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch1_Max.setBounds(308, 25, 48, 20);
		panelInputs.add(Ch1_Max);
		
		JLabel lblMin = new JLabel("Min");
		lblMin.setHorizontalAlignment(SwingConstants.CENTER);
		lblMin.setBounds(49, 6, 48, 14);
		panelInputs.add(lblMin);
		
		JLabel lblMax = new JLabel("Max");
		lblMax.setHorizontalAlignment(SwingConstants.CENTER);
		lblMax.setBounds(308, 6, 48, 14);
		panelInputs.add(lblMax);
		
		JLabel lblRole = new JLabel("Role");
		lblRole.setHorizontalAlignment(SwingConstants.CENTER);
		lblRole.setBounds(366, 6, 66, 14);
		panelInputs.add(lblRole);
		
		JComboBox Ch2_Role = new JComboBox();
		Ch2_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch2_Role.setSelectedIndex(1);
		Ch2_Role.setBounds(366, 53, 66, 20);
		panelInputs.add(Ch2_Role);
		
		JSpinner Ch2_Max = new JSpinner();
		Ch2_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch2_Max.setBounds(308, 53, 48, 20);
		panelInputs.add(Ch2_Max);
		
		JSlider Ch2_Center = new JSlider();
		Ch2_Center.setValue(10);
		Ch2_Center.setBackground(Color.WHITE);
		Ch2_Center.setBounds(103, 53, 200, 23);
		panelInputs.add(Ch2_Center);
		
		JSpinner Ch2_Min = new JSpinner();
		Ch2_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch2_Min.setBounds(49, 53, 48, 20);
		panelInputs.add(Ch2_Min);
		
		JLabel lblCh_1 = new JLabel("Ch. 2");
		lblCh_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh_1.setBounds(10, 56, 29, 14);
		panelInputs.add(lblCh_1);
		
		JComboBox Ch3_Role = new JComboBox();
		Ch3_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch3_Role.setSelectedIndex(2);
		Ch3_Role.setBounds(366, 81, 66, 20);
		panelInputs.add(Ch3_Role);
		
		JSpinner Ch3_Max = new JSpinner();
		Ch3_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch3_Max.setBounds(308, 81, 48, 20);
		panelInputs.add(Ch3_Max);
		
		JSlider Ch3_Center = new JSlider();
		Ch3_Center.setValue(10);
		Ch3_Center.setBackground(Color.WHITE);
		Ch3_Center.setBounds(103, 81, 200, 23);
		panelInputs.add(Ch3_Center);
		
		JSpinner Ch3_Min = new JSpinner();
		Ch3_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch3_Min.setBounds(49, 81, 48, 20);
		panelInputs.add(Ch3_Min);
		
		JLabel lblCh_2 = new JLabel("Ch. 3");
		lblCh_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh_2.setBounds(10, 84, 29, 14);
		panelInputs.add(lblCh_2);
		
		JComboBox Ch4_Role = new JComboBox();
		Ch4_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch4_Role.setSelectedIndex(3);
		Ch4_Role.setBounds(366, 109, 66, 20);
		panelInputs.add(Ch4_Role);
		
		JSpinner Ch4_Max = new JSpinner();
		Ch4_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch4_Max.setBounds(308, 109, 48, 20);
		panelInputs.add(Ch4_Max);
		
		JSlider Ch4_Center = new JSlider();
		Ch4_Center.setValue(10);
		Ch4_Center.setBackground(Color.WHITE);
		Ch4_Center.setBounds(103, 109, 200, 23);
		panelInputs.add(Ch4_Center);
		
		JSpinner Ch4_Min = new JSpinner();
		Ch4_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch4_Min.setBounds(49, 109, 48, 20);
		panelInputs.add(Ch4_Min);
		
		JLabel lblCh_3 = new JLabel("Ch. 4");
		lblCh_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh_3.setBounds(10, 112, 29, 14);
		panelInputs.add(lblCh_3);
		
		JComboBox Ch5_Role = new JComboBox();
		Ch5_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch5_Role.setSelectedIndex(4);
		Ch5_Role.setBounds(366, 137, 66, 20);
		panelInputs.add(Ch5_Role);
		
		JLabel lblCh_4 = new JLabel("Ch. 5");
		lblCh_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh_4.setBounds(10, 140, 29, 14);
		panelInputs.add(lblCh_4);
		
		JSlider Ch5_Center = new JSlider();
		Ch5_Center.setValue(10);
		Ch5_Center.setBackground(Color.WHITE);
		Ch5_Center.setBounds(103, 137, 200, 23);
		panelInputs.add(Ch5_Center);
		
		JSpinner Ch5_Min = new JSpinner();
		Ch5_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch5_Min.setBounds(49, 137, 48, 20);
		panelInputs.add(Ch5_Min);
		
		JSpinner Ch5_Max = new JSpinner();
		Ch5_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch5_Max.setBounds(308, 137, 48, 20);
		panelInputs.add(Ch5_Max);
		
		JComboBox Ch6_Role = new JComboBox();
		Ch6_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch6_Role.setSelectedIndex(5);
		Ch6_Role.setBounds(366, 165, 66, 20);
		panelInputs.add(Ch6_Role);
		
		JSpinner Ch6_Max = new JSpinner();
		Ch6_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch6_Max.setBounds(308, 165, 48, 20);
		panelInputs.add(Ch6_Max);
		
		JSlider Ch6_Center = new JSlider();
		Ch6_Center.setValue(10);
		Ch6_Center.setBackground(Color.WHITE);
		Ch6_Center.setBounds(103, 165, 200, 23);
		panelInputs.add(Ch6_Center);
		
		JSpinner Ch6_Min = new JSpinner();
		Ch6_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch6_Min.setBounds(49, 165, 48, 20);
		panelInputs.add(Ch6_Min);
		
		JLabel lblCh_5 = new JLabel("Ch. 6");
		lblCh_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh_5.setBounds(10, 168, 29, 14);
		panelInputs.add(lblCh_5);
		
		JComboBox Ch7_Role = new JComboBox();
		Ch7_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch7_Role.setSelectedIndex(6);
		Ch7_Role.setBounds(366, 193, 66, 20);
		panelInputs.add(Ch7_Role);
		
		JSpinner Ch7_Max = new JSpinner();
		Ch7_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch7_Max.setBounds(308, 193, 48, 20);
		panelInputs.add(Ch7_Max);
		
		JSlider Ch7_Center = new JSlider();
		Ch7_Center.setValue(10);
		Ch7_Center.setBackground(Color.WHITE);
		Ch7_Center.setBounds(103, 193, 200, 23);
		panelInputs.add(Ch7_Center);
		
		JSpinner Ch7_Min = new JSpinner();
		Ch7_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch7_Min.setBounds(49, 193, 48, 20);
		panelInputs.add(Ch7_Min);
		
		JLabel lblCh_6 = new JLabel("Ch. 7");
		lblCh_6.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh_6.setBounds(10, 196, 29, 14);
		panelInputs.add(lblCh_6);
		
		JComboBox Ch8_Role = new JComboBox();
		Ch8_Role.setModel(new DefaultComboBoxModel(new String[] {"Throttle", "Pitch", "Roll", "Yaw", "Aux 1", "Aux 2", "Aux 3", "Aux 4"}));
		Ch8_Role.setSelectedIndex(7);
		Ch8_Role.setBounds(366, 221, 66, 20);
		panelInputs.add(Ch8_Role);
		
		JSpinner Ch8_Max = new JSpinner();
		Ch8_Max.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch8_Max.setBounds(308, 221, 48, 20);
		panelInputs.add(Ch8_Max);
		
		JSlider Ch8_Center = new JSlider();
		Ch8_Center.setValue(10);
		Ch8_Center.setBackground(Color.WHITE);
		Ch8_Center.setBounds(103, 221, 200, 23);
		panelInputs.add(Ch8_Center);
		
		JSpinner Ch8_Min = new JSpinner();
		Ch8_Min.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		Ch8_Min.setBounds(49, 221, 48, 20);
		panelInputs.add(Ch8_Min);
		
		JLabel lblCh_7 = new JLabel("Ch. 8");
		lblCh_7.setHorizontalAlignment(SwingConstants.LEFT);
		lblCh_7.setBounds(10, 224, 29, 14);
		panelInputs.add(lblCh_7);
		
		JComboBox Ch8_Type = new JComboBox();
		Ch8_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch8_Type.setBounds(442, 221, 69, 20);
		panelInputs.add(Ch8_Type);
		
		JComboBox Ch7_Type = new JComboBox();
		Ch7_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch7_Type.setBounds(442, 193, 69, 20);
		panelInputs.add(Ch7_Type);
		
		JComboBox Ch6_Type = new JComboBox();
		Ch6_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch6_Type.setBounds(442, 165, 69, 20);
		panelInputs.add(Ch6_Type);
		
		JComboBox Ch5_Type = new JComboBox();
		Ch5_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch5_Type.setBounds(442, 137, 69, 20);
		panelInputs.add(Ch5_Type);
		
		JComboBox Ch4_Type = new JComboBox();
		Ch4_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch4_Type.setSelectedIndex(1);
		Ch4_Type.setBounds(442, 109, 69, 20);
		panelInputs.add(Ch4_Type);
		
		JComboBox Ch3_Type = new JComboBox();
		Ch3_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch3_Type.setSelectedIndex(1);
		Ch3_Type.setBounds(442, 81, 69, 20);
		panelInputs.add(Ch3_Type);
		
		JComboBox Ch2_Type = new JComboBox();
		Ch2_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch2_Type.setSelectedIndex(1);
		Ch2_Type.setBounds(442, 53, 69, 20);
		panelInputs.add(Ch2_Type);
		
		JComboBox Ch1_Type = new JComboBox();
		Ch1_Type.setModel(new DefaultComboBoxModel(new String[] {"Off", "Analog", "3-state", "On/Off"}));
		Ch1_Type.setSelectedIndex(1);
		Ch1_Type.setBounds(442, 25, 69, 20);
		panelInputs.add(Ch1_Type);
		
		JLabel lblType = new JLabel("Type");
		lblType.setHorizontalAlignment(SwingConstants.CENTER);
		lblType.setBounds(442, 6, 69, 14);
		panelInputs.add(lblType);
		tabbedPane.addTab("Output Mixer", null, panelMixer, null);
		tabbedPane.setEnabledAt(3, true);
		panelMixer.setLayout(null);
		
		JPanel panelDebug = new JPanel();
		panelDebug.setBackground(Color.WHITE);
		tabbedPane.addTab("Debug", null, panelDebug, null);
		tabbedPane.setEnabledAt(4, true);
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
		txtrDebugData.setBounds(10, 20, 479, 239);
		txtrDebugData.setBorder(new LineBorder(new Color(160, 160, 160), 1, true));
		panel.add(txtrDebugData);
		
		JButton btnDebugClear = new JButton("Clear");
		btnDebugClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtrDebugData.setText("");
			}
		});
		btnDebugClear.setBounds(210, 290, 100, 23);
		panelDebug.add(btnDebugClear);
	}
	
	public void PopulateComPorts() {
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
	
	
	public void windowClosing(WindowEvent e) {
		System.out.println("Closing 2");
	}
	public void windowClosed(WindowEvent e) {
		System.out.println("Closing 1");
	}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}
