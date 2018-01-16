package hello.command;

public class SetAddressCommand  extends ChainCommand{


    public SetAddressCommand() {
        super("set");
    }

    @Override
    public byte[] getCommand() {
        return new byte[] {head,(byte)0x30 ,(byte)0x01 ,tail};
    }


}
