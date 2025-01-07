package asm.code;

@SuppressWarnings("serial")
public class SemanticError extends Exception {

    /**
     *
     */
    public SemanticError(String msg) {
        super(msg + ".");
    }

}
