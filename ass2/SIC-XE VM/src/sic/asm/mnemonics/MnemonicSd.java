package sic.asm.mnemonics;

import sic.asm.code.Node;
import sic.asm.code.Storage;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;
import sic.simulator.Opcode;

//BYTE, WORD
public class MnemonicSd extends Mnemonic {

    public MnemonicSd(String mnemonic, int opcode, String hint, String desc) {
        super(mnemonic, opcode, hint, desc);
    }

    @Override
    public Node parse(Parser parser) throws SyntaxError {
        if(this.opcode == Opcode.WORD)
            return new Storage(this, parser.parseData());
        return new Storage(this, parser.parseByte());
    }


}
