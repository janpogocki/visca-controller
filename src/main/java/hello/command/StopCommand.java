package hello.command;

public class StopCommand extends ChainCommand {

    public StopCommand()
    {
        super("stop");
    }

    @Override
    public byte[] getCommand() {
        return new byte[]{(byte)0x81,(byte)0x01,(byte)0x06,(byte)0x01,(byte)0x00,(byte)0x00,(byte)0x03,(byte)0x03,tail};
    }
}
