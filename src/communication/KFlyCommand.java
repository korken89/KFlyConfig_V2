package communication;

public enum KFlyCommand {
	Ping(1),
	DebugMessage(2),
	WriteFirmware(3),
	ReadFirmware(4),
	SaveToFlash(5),
	GetRegulatorData(6),
	SetRegulatorData(7),
	GetChannelMix(8),
	SetChannelMi(9),
	StartRCCalibration(10),
	StopRCCalibration(11),
	CalibrateRCCenters(12),
	GetRCCalibration(13),
	SetRCCalibration(14),
	GetRCValues(15);
	
	private int value;
	
	private KFlyCommand(int i) {
		this.value = i;
	}
	
	public int getValue() {
		return value;
	}
}
