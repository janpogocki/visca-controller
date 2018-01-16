package hello;

import jssc.SerialPort;
import jssc.SerialPortException;
import hello.command.*;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.HashMap;

public class ViscaCtrl {
	private SerialPort serialPort = null;
	private String definingMacro = "";
	private HashMap<String, String> macroMap = new HashMap<>();

	public void init() throws SerialPortException {
		serialPort = new SerialPort("com1");

		serialPort.openPort();
		serialPort.setParams(9600, 8, 1, 0);

	}

	public void closeSerial() throws SerialPortException {
		serialPort.closePort();
	}

	private ChainCommand stringToCommand(String commandString) {
		ChainCommand command = null;
		switch (commandString.trim()) {
		case "clear":
			command = new ClearCommand();
			break;
		case "down":
			command = new DownCommand();
			break;
		case "home":
			command = new HomeCommand();
			break;
		case "left":
			command = new LeftCommand();
			break;
		case "right":
			command = new RightCommand();
			break;
		case "set":
			command = new SetAddressCommand();
			break;
		case "stop":
			command = new StopCommand();
			break;
		case "up":
			command = new UpCommand();
			break;
		}
		return command;
	}

	private void createMacro(String commandString){
		// def nowemakro
		// left 10,wait 30

		String macroName = commandString.split(" ")[1];

		if (macroMap.containsKey(macroName))
			System.out.println("Macro already exists");
		else {
			definingMacro = macroName;
			System.out.print("> ");
		}
	}

	private void finalizeCreatingMacro(String commandString){
		macroMap.put(definingMacro, commandString);
		System.out.println("Macro " + definingMacro + " has been saved");
		definingMacro = "";
	}

	private void removeMacro(String commandString){
		// remove nowemakro

		String macroName = commandString.split(" ")[1];

		if (macroMap.remove(macroName) != null)
			System.out.println("Macro " + macroName + " has been deleted");
		else
			System.out.println("Macro " + macroName + " not found");
	}

	public String executeCommand(String commandString) throws SerialPortException {
		String response = "";
		boolean runCommand = true;

		if (commandString.startsWith("def")){
			createMacro(commandString);
			runCommand = false;
		}
		else if (commandString.startsWith("remove")){
			removeMacro(commandString);
			runCommand = false;
		}
		else if (definingMacro != ""){
			finalizeCreatingMacro(commandString);
			runCommand = false;
		}
		else if (macroMap.containsKey(commandString)){
			commandString = macroMap.get(commandString);
		}

		// RUN COMMAND
		if (runCommand) {
			String [] commands = commandString.split(",");

			for (String command : commands) {
				System.out.println(command);
				if (command.trim().startsWith("wait")) {
					try {
						Thread.sleep(Long.valueOf(command.trim().substring(4).trim()));
						response += "ok " + command;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					String[] parts = command.split(" ");
					String com = parts[0];

					System.out.println(com);
					ChainCommand chainCommand = stringToCommand(com.trim());
					if (parts.length == 2) {
						int arg = Integer.valueOf(parts[1]);
						chainCommand.setArg(arg);
					}
					if (chainCommand != null) {
						response += executeCommand(chainCommand) + "\n";
					} else {
						response += "not found " + command + "\n";
					}
				}
			}
		}

		return response;
	}

	public String executeCommand(ChainCommand command)
			throws SerialPortException {
		serialPort.writeBytes(command.getCommand());

		try {
			Thread.sleep(500);
			String response = serialPort.readHexString();
			System.out.println(response);
			if (response.compareToIgnoreCase("90 42 FF 90 52 FF") == 0) {
				return "ok " + command.getCommandName();
			}
			serialPort.readHexString();
		} catch (InterruptedException e) {
				e.printStackTrace();
		}

		return "not ok " + command.getCommandName();
	}
}
