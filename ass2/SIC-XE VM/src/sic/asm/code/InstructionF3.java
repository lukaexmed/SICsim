package sic.asm.code;

import sic.asm.mnemonics.*;
import sic.simulator.Opcode;

public class InstructionF3 extends Node {

    private int op1;
    private boolean brezOperandov;
    private Opcode flags;
    private String symbol;

    //brez operandov
    public InstructionF3(Mnemonic mnemonic) {
        super(mnemonic);
    }

    //en operand (3m)
    public InstructionF3(Mnemonic mnemonic, int op1, Opcode flags, String symbol) {
        super(mnemonic);
        this.op1 = op1;
        this.brezOperandov = false;
        this.flags = flags;
        this.symbol = symbol;
    }

    @Override
    public int length(){
        return 3;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(flags.isTakojsnje())
            sb.append("#");
        if(flags.isPosredno())
            sb.append("@");
        sb.append(symbol == null ? Integer.toString(op1) : symbol);
        return ((this.getLabel().isEmpty() ? "      " : this.getLabel()) + "\t" + mnemonic.toString() + "\t" + (this.brezOperandov ? "" : sb) + "\t\t"+comment);

    }
    @Override
    public String resolvedToString(){
        return (mnemonic.toString() + "\t" + (this.brezOperandov ? "" : (Integer.toString(op1))) + "\n");

    }
    @Override
    public void resolve(Code code) throws SemanticError {
        op1 = (symbol == null ? op1 : code.resolveSymbol(symbol));
        if(flags.isTakojsnje())
            return;
        if(code.isPCrelative(op1)){
            flags.setP();
            op1 -= code.getNextLocCtr();
            //[-2048. 2047]
            //short je javin cast v 16 bitni dvojiški komplement, in pol odrežemo sao 12 spodnjih bitov
            op1 = ((short) op1) & 0xFFF;
        }
        else if(code.isBaseRelative(op1)){
            flags.setB();
            op1 -= code.getBaseReg();
            //moramo spravit na interval [0, 4095]
            op1 = (op1 < 0 ? op1 + 4096 : op1) & 0xFFF;
        }
//        else if(true){ //implementacija za direktno
//            return;
//        }
        else{
            throw new SemanticError("Nepravilno naslavljanje" + this);
        }
    }

    @Override
    public String emitCode(byte[] buffer, int offset) {
        if(brezOperandov){
            //dodaj za RSUB
        }
        //0x000000ni_xbpe0000
        int ni = ((flags.getN() ? 1 : 0) << 1 | (flags.getI() ? 1 : 0)) & 0xFF;
        int xbpe = ((flags.getX() ? 1 : 0) << 7 | (flags.getB() ? 1 : 0) << 6 | (flags.getP() ? 1 : 0) << 5 | (flags.getE() ? 1 : 0) << 4) & 0xFF;
        buffer[offset] = (byte) (this.mnemonic.opcode & 0xFC | ni);
        buffer[offset + 1] = (byte) (xbpe | (op1 >> 8) & 0x0F);
        buffer[offset + 2] = (byte) (op1 & 0xFF);

        String hex = String.format(
                "%02X%02X%02X",
                buffer[offset] & 0xFF,
                buffer[offset + 1] & 0xFF,
                buffer[offset + 2] & 0xFF
        );
        return hex;
    }
}
