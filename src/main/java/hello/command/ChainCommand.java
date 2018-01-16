package hello.command;

import jssc.SerialPort;
import jssc.SerialPortException;

public abstract class ChainCommand {

    private String commandName;
    int arg = 1;
    int camera = 0x06;


    protected byte head = (byte) 0x88;
    protected byte tail = (byte) 0xFF;

    public ChainCommand(String commandName)
    {
        this.commandName = commandName;
    }


    public abstract byte[] getCommand();

    public void execute(String command, SerialPort serialPort) throws NotKnownCommand, SerialPortException
    {
        if(command.equals(commandName))
            serialPort.writeBytes(getCommand());
    }

    public String getCommandName() {
        return commandName;
    }

    public void setArg(int arg) {
        this.arg = arg;
    }

    public void setCamera(int camera) {
        this.camera = camera;
    }
}
