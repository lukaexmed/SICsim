package sic.simulator.UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SIM extends javax.swing.JFrame {
    RegisterView registerView;
    MemoryView memoryView;
    private SIMEventListener listener;
    public SIM() {
        setTitle("SIC-SIM-LUKA");
        setSize(920,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);


        registerView = new RegisterView();
        registerView.setBounds(20, 20, 300, 400);
        memoryView = new MemoryView();
        memoryView.setBounds(350, 20, 550, 500);
        JPanel controlPanel = controlPanel();
        controlPanel.setBounds(20, 450, 300, 100);
        add(controlPanel);
        add(registerView);
        add(memoryView);

    }

    public RegisterView getRegisterView(){
        return registerView;
    }
    public MemoryView getMemoryView(){
        return memoryView;
    }

    public void addSIMEventListener(SIMEventListener listener){
        this.listener = listener;
    }
    private JPanel controlPanel(){
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton resetButton = new JButton("Reset");
        JButton stepButton = new JButton("Step");

        startButton.addActionListener(e -> {listener.onStart();});
        stopButton.addActionListener(e -> {listener.onStop();});
        resetButton.addActionListener(e -> {listener.onReset();});
        stepButton.addActionListener(e -> {listener.onStep();});

        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(resetButton);
        controlPanel.add(stepButton);
        return controlPanel;
    }

}
