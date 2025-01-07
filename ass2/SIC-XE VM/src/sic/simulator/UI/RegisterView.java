package sic.simulator.UI;

import sic.simulator.Opcode;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RegisterView extends JPanel {
    private JLabel opcode;
    private Map<String, JTextField> regs;

    public RegisterView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Registri"));

        opcode = new JLabel("Ukaz: ");
        add(opcode);

        regs = new HashMap<>();
        JPanel gridRegs = new JPanel(new GridLayout(9,2,5,1));

        String[] regsName = {"A", "X", "L", "B", "S", "T", "F", "PC", "SW"};
        for(String regName : regsName) {
            JLabel reg = new JLabel(regName);
            JTextField regVal = new JTextField(6);
            regVal.setEditable(false);
            regs.put(regName, regVal);
            gridRegs.add(reg);
            gridRegs.add(regVal);
        }

        add(gridRegs);
    }
    public void updateOp(int op){
        opcode.setText("Ukaz: " + String.format("0x%02x", op) + " : " + Opcode.getMnemonic(op));
    }
    public void resetOp(){
        opcode.setText("Ukaz: ");
    }
    public void updateReg(String regName, String regVal){
        if(regs.containsKey(regName)){
            regs.get(regName).setText(regVal);
        }
    }
    public void resetRegs(){
        for(JTextField reg : regs.values()){
            reg.setText("000000");
        }
    }
}
