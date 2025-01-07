package asm.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Code {
    public static final int MAX_WORD = 6;
    public static final int MAX_ADDR = (1 << 20) - 1;
    public static int locCtr = 0;


    public Map<String, Integer> symtab = new HashMap<>();

    public void defineSymbol(String sym, int val){
        symtab.put(sym, val);
    }

    public int resolveSymbol(String symbol)throws SemanticError{
        Integer val = symtab.get(symbol);
        if(val == null){
            throw new SemanticError("Symbol " + symbol + " not found");
        }
        return val;
    }



    // TODO
}
