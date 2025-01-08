package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;

public class InstructionF2 extends Node{

    private int op1;
    private int op2;
    private boolean enOperand;


    //za rr ali rn
    public InstructionF2(Mnemonic mnemonic, int op1, int op2) {
        super(mnemonic);
        this.op1 = op1;
        this.op2 = op2;
        this.enOperand = false;
    }
    //za stevilski(n) ali registerski operand(r)
    public InstructionF2(Mnemonic mnemonic, int op1) {
        super(mnemonic);
        this.op1 = op1;
        this.enOperand = true;
    }

    @Override
    public int length(){
        return 2;
    }
    @Override
    public String toString(){
        return ((this.getLabel().isEmpty() ? "      " : this.getLabel()) + "\t" + mnemonic.toString() + "\t" + Integer.toString(op1) + "\t" + (this.enOperand ? "" : Integer.toString(op2))+"\t\t"+comment);
    }
    @Override
    public String emitCode(byte[] buffer, int offset) {
        if(enOperand){
            buffer[offset] = (byte) (mnemonic.opcode);
            buffer[offset + 1] = (byte) ((op1 & 0x0F) << 4);
        }
        else{
            buffer[offset] = (byte) (mnemonic.opcode);
            buffer[offset + 1] = (byte) ((op1 & 0x0F) << 4 | (op2 &  0x0F));
        }
        String hex = String.format(
                "%02X%02X",
                buffer[offset] & 0xFF,
                buffer[offset + 1] & 0xFF
        );
        return hex;
    }
}
