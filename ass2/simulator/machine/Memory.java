import java.util.Arrays;

public class Memory{
    public byte[] memory;

    public Memory(int velikost){ //maks velikost definirana v top razredu (Machine?)
        this.memory = new byte[velikost];
    }

    public int getByte(int addr){
        int unsigned = memory[addr] & 0xFF;
        return unsigned; //ker ga castamo v int ga mormo se obrezat
    }

    public void setByte(int addr, int val){
        byte unsigned = (byte)(val & 0xFF);
        memory[addr] = unsigned;
    }

    public void reset(){
        Arrays.fill(memory, (byte)0x00);
    }

    public int getWord(int addr){
        int prvi,drugi,tretji;
        prvi = getByte(addr);   //0x12
        prvi = prvi << 16; // 2*byte 0x120000
        drugi = getByte(addr+1);//nasledni bajt //0x34
        drugi = drugi << 8; // 0x3400
        tretji = getByte(addr+2);//nasledni bajt 0x56

        //0x123456
        return  prvi | drugi | tretji;
    }

    public void setWord(int addr, int val){
        memory[addr+2] = (byte)(val & 0xFF); // 0x123456 -> 0x56
        val = val >> 8; // 0x001234
        memory[addr+1] = (byte)(val & 0xFF); //0x34
        val = val >> 8; // 0x000012
        memory[addr] = (byte)(val & 0xFF); // 0x12
    }
}