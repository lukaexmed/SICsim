package sic.simulator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class FileDevice extends Device{
    private RandomAccessFile file;

    public FileDevice (String file, String mode) throws FileNotFoundException{
        this.file = new RandomAccessFile(file, mode);
    }

    @Override
    public byte read(){ //implementiraj custom exception
            try {
                return (byte) this.file.readByte();
            } catch (IOException e) {
                System.err.println("Error reading file");
            }
            return 0;
    }

    @Override
    public void write(byte data){ //implementiraj custom exception
        try{
            this.file.writeByte(data);
        } catch (IOException e){
            System.err.println("Error writing file");
        }
    }

//    public void close(){ //implementiraj custom exception
//        file.close();
//    }
}
