import java.io.IOException;
import java.io.InputStream;

public class InputDevice extends Device{

    private InputStream input;


    public InputDevice(InputStream input){
        this.input = input;
    }
    @Override
    public int read() throws IOException{
        return input.read();
    }

}