import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class FileDevice extends Device{
    private RandomAccessFile file;

    public FileDevice (String file, String mode) throws FileNotFoundException{
        this.file = new RandomAccessFile(file, mode);
    }

    @Override
    public int read() throws IOException{
        return file.read();
    }

    @Override
    public void write(int data) throws IOException{
        file.write(data);
    }

    public void close() throws IOException{
        file.close();
    }
}