package asm.code;

import asm.mnemonics.Mnemonic;

//START, END, ORG, BASE
public class DirectiveDn extends Node {

    private String symbol;
    private int value;

    public DirectiveDn(Mnemonic mnemonic, int value){
        super(mnemonic);
        this.value = value;
    }
    public DirectiveDn(Mnemonic mnemonic, String symbol){
        super(mnemonic);
        this.symbol = symbol;
    }

//    @Override
//    public String toString(){
//        return "";
//    }

}
