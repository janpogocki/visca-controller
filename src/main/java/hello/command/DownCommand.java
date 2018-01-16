package hello.command;

public class DownCommand extends ChainCommand {


    public DownCommand() {
        super("down");
    }

    @Override
    public byte[] getCommand() {
        return new byte[]{(byte) 0x81, (byte) 0x01, (byte)  0x06, (byte) 0x01, (byte) arg,
                (byte) arg, (byte) 0x03, (byte) 0x02, (byte) 0xFF};
    }
}