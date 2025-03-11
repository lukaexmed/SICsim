package sic.simulator;


import java.io.FileNotFoundException;

public class Machine{

    private static final int MAX_ADDR = 1 << 20; //1Mb
    private static final int MAX_DEVICES = 256;


    private Device[] devices;
    public Register regs;
    public Memory mem;
    public int curOp;

    //konstruktor
    public Machine(){
        devices = new Device[MAX_DEVICES];

        devices[0] = new InputDevice(System.in);
        devices[1] = new OutputDevice(System.out);
        devices[2] = new OutputDevice(System.err);
        //zafila bin z vsemi devajsi
//        for(int i = 3; i < MAX_DEVICES; i++){
//            try {
//                devices[i] = new FileDevice(Integer.toHexString(i) + ".dev", "rw");
//            } catch (FileNotFoundException e) {
//                System.err.println("Error writing file");
//            }
//        }

        regs = new Register();
        //dodaj še FileDevice?
        //mogoce ob getDevice vecjemu od 2?

        mem = new Memory(MAX_ADDR);
        mem.reset();
    }
    public void reset(){
        regs = new Register();
        mem.reset();
    }

    public Device getDevice(int dev){
        if(dev < 0 || dev >= MAX_DEVICES){
            throw new IllegalArgumentException("Neveljavna številka naprave.");
        }
        return devices[dev];
    }

    public void setDevice(int dev, Device device){
        if(dev < 0 || dev >= MAX_DEVICES){
            throw new IllegalArgumentException("Neveljavna številka naprave.");
        }
        devices[dev] = device;
    }

    // Odpravljanje težav
    public void notImplemented(String mnemonic){
        System.err.printf("Ni implementirana mnemonika: %s!\n", mnemonic);
    }

    public void invalidOpcode(int opcode){
        System.err.printf("Izvajalnik je naletel na neveljavno operacijsko kodo ' %d ' ukaza.\n", opcode);
    }

    public void invalidAddressing(){
        System.err.printf("Neveljavni način naslavljanja");
    }

    //Izvajalnik
    public int fetch(){

        int fetchan = regs.getPC();
        int command = mem.getByte(fetchan);
        fetchan++;
        regs.setPC(fetchan);
        return command;
    }

    public void execute(){
//        System.out.println(regs.toString());
        int opcode = fetch();
        if(execF1(opcode))
            return;
        int operand1 = fetch();
        if(execF2(opcode, operand1))
            return;
        Opcode flags = new Opcode(opcode, operand1);
        int operand2;
        if(flags.isSic()){
            // x1234567_89-----
            //odstranit mormo x, premaknit za 8, in dodat novi fetch
            operand2 = (operand1 & 0xFE) << 8 | fetch() & 0xFF;
        }
        else if(flags.isF4()){
            //xbpe mormo rezat iz drugega fetcha, pol še dva štuklamo
            operand2 = ((operand1 & 0x0F) << 8 | fetch() & 0xFF) << 8 | fetch() & 0xFF;
            if(flags.isRel())
                invalidAddressing(); // ker je 20 bitni addres, je to absolutni address, nikoli rel na PC ali bazni reg
        }
        else { // je F3  tukaj pa je lahko relativno naslavljanje
            operand2 = (operand1 & 0x0F) << 8 | fetch() & 0xFF;
            if(flags.isPcRel()){
                //12 bitni offset  -2048 do +2047,  pa se od 2048 do 4095 zaradi dvojiškega komplementa tretiraj kot negativne torej -2048 do -1
                operand2 = (operand2 >= 2048) ? operand2 - 4096 + regs.getPC(): operand2 + regs.getPC();
            }
            else if(flags.isBaseRel()){
                operand2 += regs.getB();
            }
            else if (flags.isDirect()) {
                //p = 0, b = 0  -> celih 12 bitov se uporabi za absolutni naslov, če to ne velja sta oba settana in je invalid addressing
            }
            else
                invalidAddressing();
        }
        // preverit moramo samo še če je indeksirano naslavljanje
        if(flags.isIndeksirano()){
            if(flags.isPreprosto())
                operand2 += regs.getX();
            else
                invalidAddressing();
        }
        if(execSICF3F4(opcode & 0xFC, flags, operand2))
            return;
        else
            invalidOpcode(opcode);
        System.out.printf("Nic se ne izvede\n");
    }

    public boolean execF1(int opcode){
        switch(opcode){
            case Opcode.FIX:
                regs.setA((int) regs.getF());
                break;
            case Opcode.FLOAT:
                regs.setF((float) regs.getA());
                break;
            case Opcode.HIO:
                notImplemented("HIO");
                break;
            case Opcode.TIO:
                notImplemented("TIO");
                break;
            case Opcode.NORM:
                notImplemented("NORM");
                break;
            case Opcode.SIO:
                notImplemented("SIO");
                break;
            default: return false;
        }
        String s = String.format("0x%02x", opcode);
        curOp = opcode;
//        System.out.printf("Izvajam operacijo: %s - %s\n", s, Opcode.getMnemonic(opcode & 0xFF));
        return true;
    }
    public boolean execF2(int opcode, int operand){
        int r1 = operand >> 4; // 0x1234 -> 0x12
        int r2 = operand & 0xF; //0x1234 -> 0x34

        switch(opcode){
            case Opcode.ADDR:
                regs.setReg(r2, ((int)regs.getReg(r1) + (int)regs.getReg(r2)));
                break;
            case Opcode.CLEAR:
                regs.setReg(r1, 0);
                break;
            case Opcode.COMPR: //0x0, 0x40 in 0x80 manjše, enako, večje
                if((int)regs.getReg(r1) < (int)regs.getReg(r2))
                    regs.setSW(0x00);
                else if((int)regs.getReg(r1) > (int)regs.getReg(r2))
                    regs.setSW(0x80);
                else regs.setSW(0x40);
                break;
            case Opcode.DIVR:
                regs.setReg(r2, ((int)regs.getReg(r2) / (int)regs.getReg(r1)));
                break;
            case Opcode.MULR:
                regs.setReg(r2, ((int)regs.getReg(r1) * (int)regs.getReg(r2)));
                break;
            case Opcode.RMO:
                regs.setReg(r2, regs.getReg(r1));
                break;
            case Opcode.SHIFTL:
                regs.setReg(r1, (int)regs.getReg(r1) << r2 | (int)regs.getReg(r1) >> (24 - r2));
                //r2 = 3                 0x12345678-> 0x45678000             0x12345678 -> 0x00000123   = 0x45678123 circular shift
                break;
            case Opcode.SHIFTR:
                int skrajnoLevi = ((int)regs.getReg(r1) >> 23) & 1;
                int premakjeno = (int)regs.getReg(r1) >>> r2; // >>> jih zafila z nič
                int mask = (skrajnoLevi == 1) ? ~((1 << (24-r2)) -1) :0;
                regs.setReg(r1, premakjeno | mask);
                break;
            case Opcode.SUBR:
                regs.setReg(r2, ((int)regs.getReg(r2) - (int)regs.getReg(r1)));
                break;
            case Opcode.SVC:
                notImplemented("SVC");
                break;
            case Opcode.TIXR:
                regs.setX(regs.getX() + 1);
                if(regs.getX() < r1)
                    regs.setSW(0x00);
                else if(regs.getX() > r1)
                    regs.setSW(0x80);
                else regs.setSW(0x40);
                break;
            default: return false;
        }
        String s = String.format("0x%02x", opcode);
        curOp = opcode;
//        System.out.printf("Izvajam operacijo: %s - %s\n", s, Opcode.getMnemonic(opcode & 0xFF));
        return true;
    }
    public int naslavljanje(Opcode flags, int operand){
        if(flags.isPosredno()){
            return mem.getWord(mem.getWord(operand));
        }
        else if(flags.isPreprosto()){
            return mem.getWord(operand);
        }
//        else if(flags.isTakojsnje())
        //takojšnje
            return operand;
    }
    public int naslavljanjeDevices(Opcode flags, int operand){
        if(flags.isPosredno()){
            return mem.getByte(mem.getWord(operand));
        }
        else if(flags.isPreprosto()){
            return mem.getByte(operand);
        }
        return operand;
    }


    public boolean execSICF3F4(int opcode, Opcode flags, int operand){
        switch (opcode){
            case Opcode.ADD:
                regs.setA(regs.getA() + naslavljanje(flags, operand));
                break;
            case Opcode.ADDF:
                notImplemented("ADDF");
                break;
            case Opcode.AND:
                regs.setA(regs.getA() & naslavljanje(flags, operand));
                break;
            case Opcode.COMP:
                if(regs.getA() < naslavljanje(flags, operand))
                    regs.setSW(0x00);
                else if(regs.getA() > naslavljanje(flags, operand))
                    regs.setSW(0x80);
                else regs.setSW(0x40);
                break;
            case Opcode.COMPF:
                notImplemented("COMPF");
                break;
            case Opcode.DIV:
                regs.setA(regs.getA() / naslavljanje(flags, operand));
                break;
            case Opcode.DIVF:
                notImplemented("DIVF");
                break;
            case Opcode.J:
                //00111111_001011111_1111101
                //opcodeni_xbpe
                regs.setPC(operand);
                break;
            case Opcode.JEQ:
                if(regs.getSW() == 0x40) regs.setPC(flags.isPosredno() ? mem.getWord(operand) : operand);
                break;
            case Opcode.JGT:
                if(regs.getSW() == 0x80) regs.setPC(flags.isPosredno() ? mem.getWord(operand) : operand);
                break;
            case Opcode.JLT:
                if(regs.getSW() == 0x00) regs.setPC(flags.isPosredno() ? mem.getWord(operand) : operand);
                break;
            case Opcode.JSUB:
                regs.setL(regs.getPC());
                regs.setPC(flags.isPosredno() ? mem.getWord(operand) : operand);
                break;
                //LOADS
            case Opcode.LDA:
                regs.setA(naslavljanje(flags, operand));
                break;
            case Opcode.LDB:
                regs.setB(naslavljanje(flags, operand));
                break;
            case Opcode.LDCH:
                regs.setA(mem.getByte(operand)); //samo en bajt, skrajno desni
//                System.out.print("LDCH ");
//                System.out.print("Naslov: "+ operand);
//                System.out.print("Vrednost na naslovu;" + (mem.getByte(operand)));
//                if(flags.isPreprosto())System.out.print("Preprosto");
                break;
            case Opcode.LDF:
                notImplemented("LDF");
                break;
            case Opcode.LDL:
                regs.setL(naslavljanje(flags, operand));
                break;
            case Opcode.LDS:
                regs.setS(naslavljanje(flags, operand));
                break;
            case Opcode.LDT:
                regs.setT(naslavljanje(flags, operand));
                break;
            case Opcode.LDX:
                regs.setX(naslavljanje(flags, operand));
                break;
            case Opcode.LPS:
                notImplemented("LPS");
                break;

            case Opcode.MUL:
                regs.setA(regs.getA() * naslavljanje(flags, operand));
                break;
            case Opcode.MULF:
                notImplemented("MULF");
                break;
            case Opcode.OR:
                regs.setA(naslavljanje(flags, operand) | regs.getA());
                break;
            case Opcode.RSUB:
                regs.setPC(regs.getL());
                break;
            case Opcode.SSK:
                notImplemented("SSK");
                break;
            case Opcode.STA:
                mem.setWord(flags.isPosredno() ? mem.getWord(operand) : operand, regs.getA());
                break;
            case Opcode.STB:
                mem.setWord(flags.isPosredno() ? mem.getWord(operand) : operand, regs.getB());
                break;
            case Opcode.STCH:
                mem.setByte(flags.isPosredno() ? mem.getWord(operand) : operand, regs.getA() & 0xFF);
                break;
            case Opcode.STF:
                notImplemented("STF");
                break;
            case Opcode.STI:
                notImplemented("STI");
                break;
            case Opcode.STL:
                mem.setWord(flags.isPosredno() ? mem.getWord(operand) : operand, regs.getL());
                break;
            case Opcode.STS:
                mem.setWord(flags.isPosredno() ? mem.getWord(operand) : operand, regs.getS());
                break;
            case Opcode.STSW:
                mem.setWord(flags.isPosredno() ? mem.getWord(operand) : operand, regs.getSW());
                break;
            case Opcode.STT:
                mem.setWord(flags.isPosredno() ? mem.getWord(operand) : operand, regs.getT());
                break;
            case Opcode.STX:
                mem.setWord(flags.isPosredno() ? mem.getWord(operand) : operand, regs.getX());
                break;
            case Opcode.SUB:
                regs.setA(regs.getA() - naslavljanje(flags, operand));
                break;
            case Opcode.SUBF:
                notImplemented("SUBF");
                break;
            case Opcode.TD:
                regs.setSW(devices[naslavljanjeDevices(flags,operand)].test() ? 0 : 0x40);
                break;
            case Opcode.TIX:
                regs.setX(regs.getX() +1);
                if(regs.getX() < naslavljanje(flags, operand))
                    regs.setSW(0x00);
                else if(regs.getX() > naslavljanje(flags, operand))
                    regs.setSW(0x80);
                else regs.setSW(0x40);
                break;
            case Opcode.WD:
                devices[naslavljanjeDevices(flags,operand)].write((byte)(regs.getA() & 0xFF));
                break;
            case Opcode.RD:
                byte data = devices[naslavljanjeDevices(flags,operand)].read();
                regs.setA(data);
                break;
            default: return false;
        }
        String s = String.format("0x%02x", opcode);
        curOp = opcode;
//        System.out.printf("Izvajam operacijo: %s - %s\n", s, Opcode.getMnemonic(opcode & 0xFF));
        return true;
    }

}