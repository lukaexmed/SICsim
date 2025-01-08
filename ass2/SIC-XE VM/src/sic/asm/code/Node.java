package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.simulator.Opcode;

public abstract class Node {

    protected String label; // ime vrstice na začetku
    protected Mnemonic mnemonic; //tu not je opcode pa
    protected String comment; // inline comment

    public Node(Mnemonic mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getLabel() {
        return label == null ? "" : label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Return comment as a string.
     */
    public String getComment() {
        return comment == null ? "" : comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Return string representation of the node.
     * Label and comment are not included.
     */
    @Override
    public String toString() {
        return mnemonic.toString() + " " + operandToString()+"\t\t"+comment;
    }

    public String operandToString() {
        return mnemonic.operandToString(this);
    }

    public String resolvedToString(){
        return "nekaj";
    }
    public boolean greVObj(){
        return false;
    }
    public int objLen(){
        return 0;
    }
    public String objPrint(){
        return "";
    }
    public int length(){
        return 0;
    }
    //vstop v dan node
    public void enter(Code code){
        code.setLocCtr(code.getNextLocCtr());
        code.setNextLocCtr(code.getLocCtr() + this.length());
    }
    //izspot iz danega noda
    public void leave(Code code){
    }

    //v symtab shranjujemo labele in ukaze
    public void activate(Code code){
        if(this.getLabel() != null && !this.getLabel().isEmpty()){
            code.defineSymbol(this.getLabel(), code.getLocCtr());
        }
    }
    //razreševanje simbolov
    public void resolve(Code code) throws SemanticError{
    }

    public String emitCode(byte[] buffer, int offset){
        return "      ";
    }
}

