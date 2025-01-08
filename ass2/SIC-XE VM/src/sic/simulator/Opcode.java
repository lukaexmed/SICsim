package sic.simulator;

import java.util.HashMap;

public class Opcode{

    //load in store
    public static final int ADD = 0x18;
    public static final int ADDF = 0x58;
    public static final int ADDR = 0x90;
    public static final int AND = 0x40;
    public static final int CLEAR = 0xB4;
    public static final int COMP = 0x28;
    public static final int COMPF = 0x88;
    public static final int COMPR = 0xA0;
    public static final int DIV = 0x24;
    public static final int DIVF = 0x64;
    public static final int DIVR = 0x9C;
    public static final int FIX = 0xC4;
    public static final int FLOAT = 0xC0;
    public static final int HIO = 0xF4;
    public static final int J = 0x3C;
    public static final int JEQ = 0x30;
    public static final int JGT = 0x34;
    public static final int JLT = 0x38;
    public static final int JSUB = 0x48;
    public static final int LDA = 0x00;
    public static final int LDB = 0x68;
    public static final int LDCH = 0x50;
    public static final int LDF = 0x70;
    public static final int LDL = 0x08;
    public static final int LDS = 0x6C;
    public static final int LDT = 0x74;
    public static final int LDX = 0x04;
    public static final int LPS = 0xD0;
    public static final int MUL = 0x20;
    public static final int MULF = 0x60;
    public static final int MULR = 0x98;
    public static final int NORM = 0xC8;
    public static final int OR = 0x44;
    public static final int RD = 0xD8;
    public static final int RMO = 0xAC;
    public static final int RSUB = 0x4C;
    public static final int SHIFTL = 0xA4;
    public static final int SHIFTR = 0xA8;
    public static final int SIO = 0xF0;
    public static final int SSK = 0xEC;
    public static final int STA = 0x0C;
    public static final int STB = 0x78;
    public static final int STCH = 0x54;
    public static final int STF = 0x80;
    public static final int STI = 0xD4;
    public static final int STL = 0x14;
    public static final int STS = 0x7C;
    public static final int STSW = 0xE8;
    public static final int STT = 0x84;
    public static final int STX = 0x10;
    public static final int SUB = 0x1C;
    public static final int SUBF = 0x5C;
    public static final int SUBR = 0x94;
    public static final int SVC = 0xB0;
    public static final int TD = 0xE0;
    public static final int TIO = 0xF8;
    public static final int TIX = 0x2C;
    public static final int TIXR = 0xB8;
    public static final int WD = 0xDC;


    public static final int START       = 0;  //len 0
    public static final int END         = 1;  //len 0

    public static final int ORG         = 2;   //len 0
    public static final int LTORG       = 3;   //len 0

    public static final int BASE        = 4;    //len 0
    public static final int NOBASE      = 5;    //len 0

    public static final int EQU         = 6;    //len 0

    public static final int RESB        = 7;    // len 0
    public static final int RESW        = 8;    //len 0
    public static final int BYTE        = 9;    // len 1
    public static final int WORD        = 10;   //len 3


    //nixbpe biti v prihodnje
    private boolean n;
    private boolean i;
    private boolean x;
    private boolean b;
    private boolean p;
    private boolean e;

    public Opcode(int opcode1, int opcode2){
        //0x000000ni_xbpe0000
        this.n = (opcode1 & 0b00000010) != 0;
        this.i = (opcode1 & 0b00000001) != 0;
        this.x = (opcode2 & 0b10000000) != 0;
        this.b = (opcode2 & 0b01000000) != 0;
        this.p = (opcode2 & 0b00100000) != 0;
        this.e = (opcode2 & 0b00010000) != 0;
    }
    public Opcode(){
        this.n = false;
        this.i = false;
        this.x = false;
        this.b = false;
        this.p = false;
        this.e = false;
    }
    public void setN(){
        this.n = true;
    }
    public void setI(){
        this.i = true;
    }
    public void setX(){
        this.x = true;
    }
    public void setB(){
        this.b = true;
    }
    public void setP(){
        this.p = true;
    }
    public void setE(){
        this.e = true;
    }

    public boolean getN(){return this.n;}
    public boolean getI(){return this.i;}
    public boolean getX(){return this.x;}
    public boolean getB(){return this.b;}
    public boolean getP(){return this.p;}
    public boolean getE(){return this.e;}

    public boolean isSic(){
        return !n && !i;
    }
    public boolean isPreprosto(){ // v operandu je naslov do vrednosti
        return n && i;
    }
    public boolean isPosredno(){ // v operandu je naslov do naslova vrednosti
        return n && !i;
    } // @
    public boolean isTakojsnje(){ // direkt v operandu je vrednost
        return !n && i;
    } // #
    public boolean isIndeksirano(){
        return x;
    }
    public boolean isF4(){
        return e;
    }
    public boolean isBaseRel(){
        return b && !p;
    }
    public boolean isPcRel(){
        return p && !b;
    }
    public boolean isRel(){
        return p || b;
    }
    public boolean isDirect(){
        return !p && !b;
    }


    //opcode v mnemonice
    public static final HashMap<Integer, String> mnemonic = new HashMap<>();

    static{
        mnemonic.put(ADD, "ADD");
        mnemonic.put(ADDF, "ADDF");
        mnemonic.put(ADDR, "ADDR");
        mnemonic.put(AND, "AND");
        mnemonic.put(CLEAR, "CLEAR");
        mnemonic.put(COMP, "COMP");
        mnemonic.put(COMPF, "COMPF");
        mnemonic.put(COMPR, "COMPR");
        mnemonic.put(DIV, "DIV");
        mnemonic.put(DIVF, "DIVF");
        mnemonic.put(DIVR, "DIVR");
        mnemonic.put(FIX, "FIX");
        mnemonic.put(FLOAT, "FLOAT");
        mnemonic.put(HIO, "HIO");
        mnemonic.put(J, "J");
        mnemonic.put(JEQ, "JEQ");
        mnemonic.put(JGT, "JGT");
        mnemonic.put(JLT, "JLT");
        mnemonic.put(JSUB, "JSUB");
        mnemonic.put(LDA, "LDA");
        mnemonic.put(LDB, "LDB");
        mnemonic.put(LDCH, "LDCH");
        mnemonic.put(LDS, "LDS");
        mnemonic.put(LDT, "LDT");
        mnemonic.put(LDX, "LDX");
        mnemonic.put(LPS, "LPS");
        mnemonic.put(MUL, "MUL");
        mnemonic.put(MULF, "MULF");
        mnemonic.put(MULR, "MULR");
        mnemonic.put(NORM, "NORM");
        mnemonic.put(OR, "OR");
        mnemonic.put(RD, "RD");
        mnemonic.put(RMO, "RMO");
        mnemonic.put(RSUB, "RSUB");
        mnemonic.put(SHIFTL, "SHIFTL");
        mnemonic.put(SHIFTR, "SHIFTR");
        mnemonic.put(SIO, "SIO");
        mnemonic.put(SSK, "SSK");
        mnemonic.put(STA, "STA");
        mnemonic.put(STB, "STB");
        mnemonic.put(STCH, "STCH");
        mnemonic.put(STF, "STF");
        mnemonic.put(STI, "STI");
        mnemonic.put(STL, "STL");
        mnemonic.put(STS, "STS");
        mnemonic.put(STX, "STX");
        mnemonic.put(SUB, "SUB");
        mnemonic.put(SUBF, "SUBF");
        mnemonic.put(SUBR, "SUBR");
        mnemonic.put(SVC, "SVC");
        mnemonic.put(TD, "TD");
        mnemonic.put(TIX, "TIX");
        mnemonic.put(TIXR, "TIXR");
        mnemonic.put(WD, "WD");
        mnemonic.put(TIO, "TIO");
        mnemonic.put(STT,"STT");
        mnemonic.put(START,"START");
        mnemonic.put(END,"END");
        mnemonic.put(ORG,"ORG");
        mnemonic.put(LTORG,"LTORG");
        mnemonic.put(BASE,"BASE");
        mnemonic.put(NOBASE,"NOBASE");
        mnemonic.put(EQU,"EQU");
        mnemonic.put(RESB,"RESB");
        mnemonic.put(RESW,"RESW");
        mnemonic.put(BYTE,"BYTE");
        mnemonic.put(WORD,"WORD");
    }
    public static String getMnemonic(int opcode){
        return mnemonic.getOrDefault(opcode, "Neznan opcode");
    }



}
