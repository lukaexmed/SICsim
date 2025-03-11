package sic.simulator;

import sic.simulator.UI.SIM;
import sic.simulator.UI.SIMEventListener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Main{
    public static void main(String[] args) throws FileNotFoundException, IOException {
        SIM sim = new SIM();
        sim.setVisible(true);
        sim.getRegisterView().resetRegs();

        Reader r = new FileReader(args[0]);
        Machine m = new Machine();
        Clock ura = new Clock(1, m);
        if(Utils.loadSection(m,r)){
            System.out.println("Load complete.");
        }
        sim.getMemoryView().updateMemory(m.mem.memory);

        //registrira se event listneer object, kjer overrajdamo metode
        sim.addSIMEventListener(new SIMEventListener() {
            @Override public void onStart() {
                ura.start();
                while(ura.isRunning())
                sim.getRegisterView().updateOp(m.curOp);
                sim.getRegisterView().updateReg("A", m.regs.getAs());
                sim.getRegisterView().updateReg("B", m.regs.getBs());
                sim.getRegisterView().updateReg("X", m.regs.getXs());
                sim.getRegisterView().updateReg("L", m.regs.getLs());
                sim.getRegisterView().updateReg("S", m.regs.getSs());
                sim.getRegisterView().updateReg("T", m.regs.getTs());
                sim.getRegisterView().updateReg("F", "000000000000");
                sim.getRegisterView().updateReg("PC", m.regs.getPCs());
                sim.getRegisterView().updateReg("SW", m.regs.getSWs());
                sim.getMemoryView().updateMemory(m.mem.memory);
            }
            @Override public void onStep() {
                m.execute();
                sim.getRegisterView().updateOp(m.curOp);
                sim.getRegisterView().updateReg("A", m.regs.getAs());
                sim.getRegisterView().updateReg("B", m.regs.getBs());
                sim.getRegisterView().updateReg("X", m.regs.getXs());
                sim.getRegisterView().updateReg("L", m.regs.getLs());
                sim.getRegisterView().updateReg("S", m.regs.getSs());
                sim.getRegisterView().updateReg("T", m.regs.getTs());
                sim.getRegisterView().updateReg("F", "000000000000");
                sim.getRegisterView().updateReg("PC", m.regs.getPCs());
                sim.getRegisterView().updateReg("SW", m.regs.getSWs());
                sim.getMemoryView().updateMemory(m.mem.memory);
            }
            @Override public void onStop() {
                sim.getRegisterView().updateOp(m.curOp);
                sim.getRegisterView().updateReg("A", m.regs.getAs());
                sim.getRegisterView().updateReg("B", m.regs.getBs());
                sim.getRegisterView().updateReg("X", m.regs.getXs());
                sim.getRegisterView().updateReg("L", m.regs.getLs());
                sim.getRegisterView().updateReg("S", m.regs.getSs());
                sim.getRegisterView().updateReg("T", m.regs.getTs());
                sim.getRegisterView().updateReg("F", "000000000000");
                sim.getRegisterView().updateReg("PC", m.regs.getPCs());
                sim.getRegisterView().updateReg("SW", m.regs.getSWs());
                sim.getMemoryView().updateMemory(m.mem.memory);
                ura.stop();
            }
            @Override public void onReset() {
                ura.stop();
                m.reset();
                FileReader fr = null;
                sim.getRegisterView().resetRegs();
                try {
                    fr = new FileReader(args[0]);
                } catch (IOException e) {
                    System.out.println("Error reset.");
                }
                sim.getRegisterView().resetOp();
                Utils.loadSection(m, fr);
                sim.getMemoryView().updateMemory(m.mem.memory);
            }
        });

//        Scanner sc = new Scanner(System.in);
//        while(true) {
//            System.out.println("Izvajam execute: ");
//            m.execute();
//            sc.nextLine();
//            sim.getRegisterView().updateOp(m.curOp);
//            sim.getRegisterView().updateReg("A", m.regs.getAs());
//        }
    }


}
