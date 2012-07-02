package communication;

public enum KFlyCommand {
	Ping(1),
	DebugMessage(2),
	WriteFirmware(3),
	ReadFirmware(4),
	GetBootloaderVersion(5),
	GetFirmwareVersion(6),
	SaveToFlash(7),
	GetRegulatorData(8),
	SetRegulatorData(9),
	GetChannelMix(10),
	SetChannelMi(11),
	StartRCCalibration(12),
	StopRCCalibration(13),
	CalibrateRCCenters(14),
	GetRCCalibration(15),
	SetRCCalibration(16),
	GetRCValues(17);
	
	private int value;
	
	private KFlyCommand(int i) {
		this.value = i;
	}
	
	public int value() {
		return value;
	}
}
