package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.Node;
import sic.asm.code.Storage;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;
//RESW, RESB
public class MnemonicSn extends Mnemonic {

    public MnemonicSn(String mnemonic, int opcode, String hint, String desc) {
        super(mnemonic, opcode, hint, desc);
    }

    @Override
    public Node parse(Parser parser) throws SyntaxError {
        if(Character.isLetter(parser.lexer.peek())){
            return new Storage(this, parser.parseSymbol());
        }
        else if(Character.isDigit(parser.lexer.peek())){
            return new Storage(this, parser.parseNumber(0, Code.MAX_WORD));
        }
        return null;
    }

}
