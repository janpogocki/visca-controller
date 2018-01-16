package hello.command;

public class UpCommand extends ChainCommand {


    public UpCommand() {
        super("up");
    }

    @Override
    public byte[] getCommand() {
        System.out.println(this.arg);
        return new byte[]{(byte) 0x81, (byte) 0x01, (byte) 0x06, (byte) 0x01, (byte) arg, (byte) this.arg, (byte) 0x03, (byte) 0x01, tail};
    }

}
