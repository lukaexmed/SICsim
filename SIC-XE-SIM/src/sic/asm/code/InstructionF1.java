package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;

public class InstructionF1 extends Node {
    // nima operanda
    public InstructionF1(Mnemonic mnemonic) {
        super(mnemonic);
    }

//    @Override
//    public String toString(){
//        return
//    }
    @Override
    public int length(){
        return 1;
    }
    @Override
    public String emitCode(byte[] buffer, int offset) {
        buffer[offset] = (byte) (mnemonic.opcode);
        String hex = String.format(
                "%02X",
                buffer[offset] & 0xFF
        );
        return hex;
//        System.out.println("F1 " + hex);
    }


}
