package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.simulator.Opcode;

//START, END, ORG, BASE, EQU
public class DirectiveDn extends Node {

    private String symbol;
    private int number;

    public DirectiveDn(Mnemonic mnemonic, int number){
        super(mnemonic);
        this.number = number;
    }
    public DirectiveDn(Mnemonic mnemonic, String symbol){
        super(mnemonic);
        this.symbol = symbol;
    }


    @Override
    public String toString(){
        return ((this.getLabel().isEmpty() ? "   " : this.getLabel()) + "\t" + mnemonic.toString() + "\t" + (this.symbol == null ? Integer.toString(number): this.symbol));
    }

    @Override
    public void resolve(Code code) throws SemanticError {
        if(this.mnemonic.opcode == Opcode.START) {
            code.setStart(symbol == null ? number : code.resolveSymbol(symbol));
            code.setName(label);
        }
        else if(this.mnemonic.opcode == Opcode.END) {
            code.setEnd(code.getLocCtr());
        }
        else if(this.mnemonic.opcode == Opcode.BASE) {
            code.setBaseReg(symbol == null ? number : code.resolveSymbol(symbol));
        }
        else if(this.mnemonic.opcode == Opcode.ORG){
            code.setLocCtr(symbol == null ? number : code.resolveSymbol(symbol));
            code.setNextLocCtr(symbol == null ? number : code.resolveSymbol(symbol));
        }
        else if(this.mnemonic.opcode == Opcode.EQU){
            code.defineSymbol(this.label, symbol == null ? number : code.resolveSymbol(symbol));
        }
    }

}
