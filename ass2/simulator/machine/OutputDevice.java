import java.io.IOException;
import java.io.OutputStream;

public class OutputDevice extends Device{

    private OutputStream output;


    public OutputDevice(OutputStream output){
        this.output = output;
    }
    @Override
    public void write(int data) throws IOException{
        output.write(data);
    }

}