package asm.code;

import asm.mnemonics.Mnemonic;

//NOBASE
public class DirectiveD extends Node {

    public DirectiveD(Mnemonic mnemonic, int value){
        super(mnemonic);
    }

//    @Override
//    public String toString(){
//        return (this.getLabel().isEmpty() == true ? "" : this.getLabel()) + "\t" + mnemonic.toString() + "\t" + ((symbol == null) ? Integer.toString(value) : symbol);
//    }

}
