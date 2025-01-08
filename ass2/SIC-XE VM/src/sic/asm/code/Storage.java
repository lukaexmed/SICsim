package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.simulator.Opcode;

import java.util.Arrays;

public class Storage extends Node{

    private byte[] data;
    private int val;
    private String symbol;

    public Storage(Mnemonic mnemonic, byte[] data) {
        super(mnemonic);
        this.data = data;
    }

    public Storage(Mnemonic mnemonic, int val) {
        super(mnemonic);
        this.val = val;
    }
    public Storage(Mnemonic mnemonic, String symbol) {
        super(mnemonic);
        this.symbol = symbol;
    }
    @Override
    public String toString(){
        boolean leading = true;
        String output = ((this.getLabel().isEmpty() ? "" : String.format("%-5s",this.getLabel())) + "\t" + mnemonic.toString() + "\t");
        if(data == null){
            return output + (symbol == null ? Integer.toString(val) : symbol) + "\t\t"+comment;
        }
        else{
            for(byte b : data){
                if(b != 0){
                    leading = false;
                }
                if(!leading)
                    output += Byte.toString(b);
            }
            return output + "\t\t"+comment;
        }
    }

    @Override
    public void resolve(Code code){

    }
    @Override
    public boolean greVObj(){
        return (mnemonic.opcode == Opcode.BYTE) || (mnemonic.opcode == Opcode.WORD);
    }

    @Override
    public int objLen(){
        return this.length();
    }
    @Override
    public String objPrint(){
        StringBuilder sb = new StringBuilder();
        for(byte b : data){
            sb.append(String.format("%02X", b));
        }
        return sb.toString();

    }
    @Override
    public int length() {
        if(mnemonic.opcode == Opcode.BYTE){
            return data.length;
        }
        if(mnemonic.opcode == Opcode.WORD){
            return 3;
        }
        if(mnemonic.opcode == Opcode.RESB){
            return val;
        }
        if(mnemonic.opcode == Opcode.RESW){
            return val*3;
        }
        return 0;
    }
    @Override
    public String emitCode(byte[] buffer, int offset){
        if(mnemonic.opcode == Opcode.BYTE){
            return objPrint();
        }
        if(mnemonic.opcode == Opcode.WORD){
            return objPrint();
        }
        else if(mnemonic.opcode == Opcode.RESW){
            return "000000";
        }
        else{
            return "00";
        }

    }

}

