package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.simulator.Opcode;

public class InstructionF4 extends Node {

    private int op1;
    private Opcode flags;
    private String symbol;


    //en operand (4m)
    public InstructionF4(Mnemonic mnemonic, int op1, Opcode flags, String symbol) {
        super(mnemonic);
        this.op1 = op1;
        this.flags = flags;
        this.symbol = symbol;
    }

    @Override
    public int length(){
        return 4;
    }
    @Override
    public String toString(){
        return ((this.getLabel().isEmpty() ? "      " : this.getLabel()) + "\t" + mnemonic.toString() + "\t" + (symbol == null ? Integer.toString(op1) : symbol)+"\t\t"+comment);

    }

    @Override
    public void resolve(Code code) throws SemanticError {
        op1 = (symbol == null ? op1 : code.resolveSymbol(symbol));
        flags.setE();
    }

    @Override
    public String emitCode(byte[] buffer, int offset) {
        //0x000000ni_xbpe0000
        int ni = ((flags.getN() ? 1 : 0) << 1 | (flags.getI() ? 1 : 0)) & 0xFF;
        int xbpe = ((flags.getX() ? 1 : 0) << 7 | (flags.getB() ? 1 : 0) << 6 | (flags.getP() ? 1 : 0) << 5 | (flags.getE() ? 1 : 0) << 4) & 0xFF;
        buffer[offset] = (byte) (this.mnemonic.opcode & 0xFC | ni);
        buffer[offset + 1] = (byte) (xbpe | (op1 >> 16) & 0x0F);
        buffer[offset + 2] = (byte) (op1 >> 8 & 0xFF);
        buffer[offset + 3] = (byte) (op1 & 0xFF);

        String hex = String.format(
                "%02X%02X%02X%02X",
                buffer[offset] & 0xFF,
                buffer[offset + 1] & 0xFF,
                buffer[offset + 2] & 0xFF,
                buffer[offset + 3] & 0xFF
        );

        return hex;
    }
}
