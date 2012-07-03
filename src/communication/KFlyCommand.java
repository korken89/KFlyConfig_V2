package communication;

/* *
 * All the commands must be in order in this list!
 * */
public enum KFlyCommand {
	None(0),
	Ping(1),
	DebugMessage(2),
	GetRunningMode(3),
	WriteFirmware(4),
	ReadFirmware(5),
	ExitBootloader(6),
	GetBootloaderVersion(7),
	GetFirmwareVersion(8),
	SaveToFlash(9),
	GetRegulatorData(10),
	SetRegulatorData(11),
	GetChannelMix(12),
	SetChannelMi(13),
	StartRCCalibration(14),
	StopRCCalibration(15),
	CalibrateRCCenters(16),
	GetRCCalibration(17),
	SetRCCalibration(18),
	GetRCValues(19),
	GetDataDump(20);
	
	private int id;
	//private int length;
	
	private KFlyCommand(int i) {
		this.id = i;
	}
	
	public int id() {
		return id;
	}
}
