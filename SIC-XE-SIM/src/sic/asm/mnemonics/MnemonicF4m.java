package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.InstructionF3;
import sic.asm.code.InstructionF4;
import sic.asm.code.Node;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;
import sic.simulator.Opcode;

public class MnemonicF4m extends Mnemonic {

    public MnemonicF4m(String mnemonic, int opcode, String hint, String desc) {
        super(mnemonic, opcode, hint, desc);
    }

    @Override
    public Node parse(Parser parser) throws SyntaxError {
        String symbol = null;
        int val = 0;
        Opcode flags = new Opcode();
        if(parser.lexer.advanceIf('#')){ //immidiate
            flags.setI();
        }
        else if(parser.lexer.advanceIf('@')){ //indirect
            flags.setN();
        }
        else{
            flags.setI();
            flags.setN();
        }

        char next = parser.lexer.peek();
        if(Character.isLetter(next)){
            symbol = parser.parseSymbol();
        }
        else{
            val = parser.parseNumber(0, Code.MAX_WORD);
        }
        //preverimo še če je indexirano
        parser.lexer.skipWhitespace();
        if(parser.lexer.advanceIf(',')){
            if(parser.parseIndexed()){
                flags.setX();
            }
            else{
                throw new SyntaxError("Pricakovan X", parser.lexer.row, parser.lexer.col);
            }
        }
        return new InstructionF4(this, val, flags, symbol);
    }


}
