

public class Machine{

    private static final int MAX_ADDR = 1 << 20; //1Mb
    private static final int MAX_DEVICES = 256;


    private Device[] devices;


    //konstruktor
    public Machine(){
        devices = new Device[MAX_DEVICES];

        devices[0] = new InputDevice(System.in);
        devices[1] = new OutputDevice(System.out);
        devices[2] = new OutputDevice(System.err);

        //dodaj še FileDevice?
        //mogoce ob getDevice vecjemu od 2?
    }

    public Device getDevice(int dev){
        if(dev < 0 || dev >= MAX_DEVICES){
            throw new IllegalArgumentException("Neveljavna številka naprave.");
        }
        return devices[dev];
    }

    public void setDevice(int dev, Device device){
        if(dev < 0 || dev >= MAX_DEVICES){
            throw new IllegalArgumentException("Neveljavna številka naprave.");
        }
        devices[dev] = device;
    }

    // za ukaze
    void notImplemented(String mnemonic){

    }

    void invalidOpcode(int opcode){

    }

    void invalidAddressing(){
        
    }

}