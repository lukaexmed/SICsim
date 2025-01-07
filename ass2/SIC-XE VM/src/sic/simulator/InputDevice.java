package sic.simulator;

import java.io.IOException;
import java.io.InputStream;

public class InputDevice extends Device{

    private InputStream input;


    public InputDevice(InputStream input){
        this.input = input;
    }
    @Override
    public byte read(){ //implementiraj custom exception
        try {
            return (byte) ((this.input.read()) & 0xFF); //reži
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }
        return 0;
    }

    @Override
    public boolean test(){
        try {
            if(this.input.available() > 0)
                return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
