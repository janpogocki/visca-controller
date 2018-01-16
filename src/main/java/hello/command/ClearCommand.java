package hello.command;

public class ClearCommand extends ChainCommand {

    public ClearCommand()
    {
        super("cancel");
    }

    @Override
    public byte[] getCommand() {
        return new byte[]{(byte)0x81,(byte)0x21,(byte)0xFF};
    }
}
