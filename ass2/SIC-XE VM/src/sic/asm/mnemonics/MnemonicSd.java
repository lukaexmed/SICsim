package sic.asm.mnemonics;

import sic.asm.code.Node;
import sic.asm.code.Storage;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

//BYTE, WORD
public class MnemonicSd extends Mnemonic {

    public MnemonicSd(String mnemonic, int opcode, String hint, String desc) {
        super(mnemonic, opcode, hint, desc);
    }

    @Override
    public Node parse(Parser parser) throws SyntaxError {
        return new Storage(this, parser.parseData());
    }


}
