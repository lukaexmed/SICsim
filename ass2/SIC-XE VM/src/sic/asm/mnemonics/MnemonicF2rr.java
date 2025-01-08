package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.InstructionF2;
import sic.asm.code.Node;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

public class MnemonicF2rr extends Mnemonic {

    public MnemonicF2rr(String mnemonic, int opcode, String hint, String desc) {
        super(mnemonic, opcode, hint, desc);
    }


    @Override
    public Node parse(Parser parser) throws SyntaxError {
        int reg = parser.parseRegister();
        parser.parseComma();
        return new InstructionF2(this,reg, parser.parseRegister());
    }


}
