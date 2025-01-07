package asm.mnemonics;

import asm.code.DirectiveD;
import asm.code.Node;
import asm.parsing.Parser;
import asm.parsing.SyntaxError;


/**
 * Directive without operands.
 * Podporni razred za predmet Sistemska programska oprema.
 * @author jure
 */
public class MnemonicD extends Mnemonic {

    public MnemonicD(String mnemonic, int opcode, String hint, String desc) {
        super(mnemonic, opcode, hint, desc);
    }

    @Override
    public Node parse(Parser parser) throws SyntaxError {
        return new DirectiveD(this, 0);
    }

}
