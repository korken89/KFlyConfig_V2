package communication;

import java.util.*;
import crcgenerator.*;

public class StateMachine {
	public enum State {
		WaitingForSYNC,
		WaitingForSYNCorCMD,
		ReceivingCommand,
		ReceivingSize,
		ReceivingCRC8,
		ReceivingData,
		ReceivingCRC16
	}
	
	private State _currentState = State.WaitingForSYNC; 
	private List<Byte> _recievedData = new ArrayList<Byte>();
	private int _dataLength = 0;
	private State _savedState = null;
	
	public void SerialManager(byte data) {
		int inData = (int)(data) & 0xff;
		
		if (data == KFlyCommand.SYNC) {
			if ((_currentState != State.WaitingForSYNC) && (_currentState != State.WaitingForSYNCorCMD) && (_currentState != State.ReceivingCommand)) {
				_savedState = _currentState;
				_currentState = State.WaitingForSYNCorCMD;
			}
		}
		
		switch (_currentState)
		{
		case WaitingForSYNC:
			_currentState = WaitingForSyncManager(inData);
			break;
			
		case WaitingForSYNCorCMD:
			_currentState = WaitingForSYNCorCMDManager(inData);
			break;
			
		case ReceivingCommand:
			_currentState = ReveivingCommandManager(inData);
			break;
			
		case ReceivingSize:
			_currentState = ReveivingSizeManager(inData);
			break;
				
		case ReceivingCRC8:
			_currentState = ReveivingCRC8Manager(inData);
			break;
				
		case ReceivingData:
			_currentState = ReveivingDataManager(inData);
			break;
				
		case ReceivingCRC16:
			_currentState = ReveivingCRC16Manager(inData);
			break;
				
		default:
			break;	

		}
	}

	private State WaitingForSyncManager(int data) {
		_recievedData.clear();
		
		if (data == ((int)(KFlyCommand.SYNC) & 0xff)) {
			_recievedData.add((byte)data);
			return State.ReceivingCommand;
		}
		else
			return State.WaitingForSYNC;
	}
	
	private State WaitingForSYNCorCMDManager(int data) {
		State returnState = null;
		
		if ((byte)data == KFlyCommand.SYNC) {
			switch (_savedState)
			{
			case ReceivingCommand:
				returnState = ReveivingCommandManager(data);
				break;
				
			case ReceivingSize:
				returnState = ReveivingSizeManager(data);
				break;
					
			case ReceivingCRC8:
				returnState = ReveivingCRC8Manager(data);
				break;
					
			case ReceivingData:
				returnState = ReveivingDataManager(data);
				break;
					
			case ReceivingCRC16:
				returnState = ReveivingCRC16Manager(data);
				break;
					
			default:
				break;	
			}
		}
		else {
			_recievedData.clear();
			_recievedData.add(KFlyCommand.SYNC);
			returnState = ReveivingCommandManager(data);
		}
		
		return returnState;
	}
	
	private State ReveivingCommandManager(int data) {
		State returnState = null;
		int mask = (data & (((int)KFlyCommand.ACK_MASK) & 0xff));
		
		if ((byte)data == KFlyCommand.SYNC) {
			returnState = State.ReceivingCommand;
		}
		else if ((mask < KFlyCommand.Command.NoCommand.ordinal()) && (mask != 0)) {
			_recievedData.add((byte)data);
			returnState = State.ReceivingSize;
		}
		else {
			returnState = State.WaitingForSYNC;
		}
		
		return returnState;
	}
	
	private State ReveivingSizeManager(int data) {
		State returnState = null;
		int index = (int)(_recievedData.get(1)) & 0xff;
		
		if ((data == KFlyCommand.Command.values()[index].length()) || (KFlyCommand.Command.values()[index].length() == 255)) {
			_recievedData.add((byte)data);
			returnState = State.ReceivingCRC8;
			_dataLength = data;
		}
		else {
			returnState = State.WaitingForSYNC;
		}
		
		return returnState;
	}
	
	private State ReveivingCRC8Manager(int data) {
		State returnState = null;
		
		if (CRC8.GenerateCRC(_recievedData) == (byte)data) {
			_recievedData.add((byte) data);
			
			if ((_recievedData.get(1) & KFlyCommand.ACK_BIT) != 0) {
				SendACK();
			}
			
			if (_dataLength == 0) {
				returnState = State.WaitingForSYNC;
				Parser(_recievedData);
			}
			else {
				returnState = State.ReceivingData;
			}
		}
		else {
			returnState = State.WaitingForSYNC;
		}

		return returnState;
	}

	private State ReveivingDataManager(int data) {
		State returnState = null;
		
		_recievedData.add((byte) data);
		
		if (_recievedData.size() < (_dataLength + 4))
			returnState = State.ReceivingData;
		else
			returnState = State.ReceivingCRC16;
			
		return returnState;
	}
	
	private State ReveivingCRC16Manager(int data) {
		State returnState = null;
		
		_recievedData.add((byte) data);
		
		if (_recievedData.size() < (_dataLength + 6))
			returnState = State.ReceivingCRC16;
		else {
			returnState = State.WaitingForSYNC;
			List<Byte> crc = CRC_CCITT.GenerateCRC(_recievedData.subList(0, _recievedData.size() - 3));
			
			/* Check so CRC is correct */
			if ((crc.get(0) == _recievedData.get(_recievedData.size() - 2)) && (crc.get(1) == _recievedData.get(_recievedData.size() - 1))) {
				if ((_recievedData.get(1) & KFlyCommand.ACK_BIT) != 0) {
					SendACK();
				}
				
				Parser(_recievedData);
			}
		}
		
		return returnState;
	}
	
	private void SendACK() {
		
	}
	
	private void Parser(List<Byte> message) {
		System.out.println("Arrived at Parser! :D");
		System.out.print("Command: ");
		System.out.println(KFlyCommand.Command.values()[(int)(message.get(1)) & 0xff]);
		System.out.print("Message: ");
		for (byte b: message)
			System.out.print(SerialCom.IntToHex((int)b & 0xff) + " ");
	}
}
