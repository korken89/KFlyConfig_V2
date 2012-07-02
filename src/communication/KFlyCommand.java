package communication;

public enum KFlyCommand {
	Ping(1),
	DebugMessage(2),
	GetRunningMode(3),
	WriteFirmware(4),
	ReadFirmware(5),
	GetBootloaderVersion(6),
	GetFirmwareVersion(7),
	SaveToFlash(8),
	GetRegulatorData(9),
	SetRegulatorData(10),
	GetChannelMix(11),
	SetChannelMi(12),
	StartRCCalibration(13),
	StopRCCalibration(14),
	CalibrateRCCenters(15),
	GetRCCalibration(16),
	SetRCCalibration(17),
	GetRCValues(18);
	
	private int value;
	
	private KFlyCommand(int i) {
		this.value = i;
	}
	
	public int value() {
		return value;
	}
}
