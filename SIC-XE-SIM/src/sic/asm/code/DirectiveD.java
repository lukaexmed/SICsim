package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.simulator.Opcode;

//NOBASE
public class DirectiveD extends Node {


    public DirectiveD(Mnemonic mnemonic){
        super(mnemonic);
    }

    @Override
    public String toString(){
        return ((this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString() + "\t");
    }

    @Override
    public void resolve(Code code){
        if(this.mnemonic.opcode == Opcode.NOBASE) {
            code.setBaseReg(-1);
        }
    }
}
