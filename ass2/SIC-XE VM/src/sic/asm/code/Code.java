package sic.asm.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Code {
    public static final int MAX_WORD = 0xFFFFFF;
    public static final int MAX_ADDR = (1 << 20) - 1;
    public static int locCtr;
    public static int nextLocCtr; // PC
    public static int start;
    public static int end;
    public static int baseReg;
    public String name;


    public Map<String, Integer> symtab = new HashMap<>();
    public List<Node> program = new ArrayList<>();

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

    public void append(Node node){
        program.add(node);
    }
    public void print(){
        for(Node node : program){
            System.out.println(node.toString());
        }
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setLocCtr(int i){
        locCtr = i;
    }
    public int getLocCtr(){
        return locCtr;
    }
    public void setNextLocCtr(int i){
        nextLocCtr = i;
    }
    public int getNextLocCtr(){
        return nextLocCtr;
    }
    public void setStart(int i){
        start = i;
    }
    public int getStart(){
        return start;
    }
    public void setEnd(int i){
        end = i;
    }
    public int getEnd(){
        return end;
    }
    public void setBaseReg(int i){
        baseReg = i;
    }
    public int getBaseReg(){
        return baseReg;
    }

    public void begin(){
        locCtr = start;
        nextLocCtr = start;
        baseReg = -1;
    }

    public void end(){
        locCtr = end;
        nextLocCtr = end;
        baseReg = -1;
    }
    // TODO
    public void resolve() throws SemanticError {
        for(Node node : program){
            node.enter(this);
//            System.out.printf("LocCtr %d ", locCtr);
            node.resolve(this);
//            System.out.print(node.resolvedToString());
            node.leave(this);
        }
    }

    public boolean isPCrelative(int operand){
        return ((operand - nextLocCtr) >= -(2048)) && ((operand - nextLocCtr) <= (2048) -1);
    }
    public boolean isBaseRelative(int operand){
        return (baseReg >= 0) && ((operand - nextLocCtr) >= 0) && ((operand - nextLocCtr) <= 4095);
    }

    public byte[] emitCode(){
        byte[] buffer = new byte[4096];
        for(Node node : program){
            node.enter(this);
            node.emitCode(buffer, getLocCtr());
            node.leave(this);
        }
        return buffer;
    }
    public String obj(byte[] buffer){
        StringBuilder sb = new StringBuilder();
        StringBuilder vrstica = new StringBuilder();
        sb.append("H");
        sb.append(String.format("%-6s",this.getName()));
        sb.append(String.format("%06X",this.getStart()));
        sb.append(String.format("%06X",(this.getEnd())));
        sb.append("\n");
        int odmik = 0;
        for(Node node : program){
            node.enter(this);
            if(!(node instanceof DirectiveD) && !(node instanceof DirectiveDn) && !(node instanceof Comment)) {
                if(!(node instanceof Storage)) {
                    vrstica.append((node.emitCode(buffer, getLocCtr())));
                }
                else if(node.greVObj()){
                    vrstica.append((node.objPrint()));
                }
            }
            if(vrstica.length() > (60-2) || ((node instanceof Storage) && !(node.greVObj()))){//60 je lahko v vrstici znakov, pa en 2 dolgi se lahko not gre
                if(!vrstica.isEmpty()) {
                    sb.append(String.format("T%06X%02X", odmik, vrstica.length() / 2));
                    sb.append(vrstica.toString() + "\n");
//                    vrstica.delete(0, vrstica.length());
                    vrstica.setLength(0);
                }
                odmik = getLocCtr()+node.length();
            }
            node.leave(this);
        }
        if(!vrstica.isEmpty()){
            sb.append(String.format("T%06X%02X", odmik, vrstica.length() / 2));
            sb.append(vrstica + "\n");
        }
        sb.append(String.format("E%06X\n", this.getStart()));

        return sb.toString();
    }
    public String lst(byte[] buffer){
        StringBuilder sb = new StringBuilder();
        for(Node node : program){
            node.enter(this);
            if(!(node instanceof Comment))
                sb.append(String.format("%05X", getLocCtr()));
            else
                sb.append("     ");
            sb.append("  ");
            sb.append(node.emitCode(buffer, getLocCtr()));
            for(int i = 0; i < 6-node.length(); i++){
                sb.append(" ");
            }
            sb.append(node);
            sb.append("\n");
            node.leave(this);
        }
        return sb.toString();
    }
    public void prviPrehod(){
        for(Node node : program){
            node.enter(this);
            node.activate(this);
            node.leave(this);
        }
    }
}
