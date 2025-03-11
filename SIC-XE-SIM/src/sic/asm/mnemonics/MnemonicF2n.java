package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.InstructionF2;
import sic.asm.code.Node;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

public class MnemonicF2n extends Mnemonic {

    public MnemonicF2n(String mnemonic, int opcode, String hint, String desc) {
        super(mnemonic, opcode, hint, desc);
    }


    @Override
    public Node parse(Parser parser) throws SyntaxError {
        return new InstructionF2(this, parser.parseNumber(0, Code.MAX_WORD));
    }


}
