package simulator;

import java.io.IOException;
import java.io.OutputStream;

public class OutputDevice extends Device{

    private OutputStream output;


    public OutputDevice(OutputStream output){
        this.output = output;
    }
    @Override
    public void write(byte data){ //implementiraj custom exception
        try {
            this.output.write(data);
            this.output.flush();
        } catch (IOException e) {
            System.out.println("Error writing to output stream");
        }
    }

}