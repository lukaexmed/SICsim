package asm.code;

import asm.mnemonics.Mnemonic;

public abstract class Node {

    protected String label;
    protected Mnemonic mnemonic;
    protected String comment;

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
        return mnemonic.toString() + " " + operandToString();
    }

    public String operandToString() {
        return mnemonic.operandToString(this);
    }

}

