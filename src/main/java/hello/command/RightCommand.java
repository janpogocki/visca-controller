package hello.command;

public class RightCommand extends ChainCommand {

    public RightCommand()
    {
        super("right");
    }

    @Override
    public byte[] getCommand() {
        return new byte[]{(byte)0x81,(byte)0x01,(byte)0x06,(byte)0x01,(byte)this.arg,(byte)0x00,(byte)0x02,(byte)0x03,tail};
    }
}
