package simulator.UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SIM extends javax.swing.JFrame {
    RegisterView registerView;
    private List<SIMEventListener> listeners = new ArrayList<SIMEventListener>();
    public SIM() {
        setTitle("SIM");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        registerView = new RegisterView();
//        add(memPanel(), BorderLayout.EAST);
        add(controlPanel(), BorderLayout.WEST);
        add(registerView, BorderLayout.NORTH);

    }
//    private JScrollPane memPanel(){
//        return new MemoryView().createPanel();
//    }

//    private JPanel regPanel(){
//        return new RegisterView().;
//    }

    public RegisterView getRegisterView(){
        return registerView;
    }

    public void addSIMEventListener(SIMEventListener listener){
        listeners.add(listener);
    }
    private JPanel controlPanel(){
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton resetButton = new JButton("Reset");
        JButton stepButton = new JButton("Step");


        startButton.addActionListener(e -> {listeners.forEach(SIMEventListener::onStart);});
        stopButton.addActionListener(e -> {listeners.forEach(SIMEventListener::onStop);});
        resetButton.addActionListener(e -> {listeners.forEach(SIMEventListener::onReset);});
        stepButton.addActionListener(e -> {listeners.forEach(SIMEventListener::onStep);});

        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(resetButton);
        controlPanel.add(stepButton);
        return controlPanel;
    }

}
