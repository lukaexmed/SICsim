package sic.simulator.UI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MemoryView extends JPanel {
    private JTextArea memoryArea;

    public MemoryView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Pomnilnik"));

        memoryArea = new JTextArea(20, 60);
        memoryArea.setEditable(false);
        memoryArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(memoryArea);
        add(scrollPane);
    }
    public void updateMemory(byte[] memory) {
        memoryArea.setText(toHexDump(memory));
        //da se nea fokusa na zadni bit po vsakem updejtu
        memoryArea.setCaretPosition(0);
    }
    private String toHexDump(byte[] data) {
        StringBuilder sb = new StringBuilder();
        final int cols = 16;

        for (int i = 0; i < 1000; i += cols) {
            //začetn naslov vrstice
            sb.append(String.format("%06X: ", i));

            StringBuilder hexPart = new StringBuilder();
            StringBuilder asciiPart = new StringBuilder();

            for (int j = 0; j < cols; j++) {
                byte b = data[i + j];
                hexPart.append(String.format("%02X ", b));
                //to so sao printable ascii
                if (b >= 32 && b < 127) {
                    asciiPart.append((char) b);
                } else {
                    asciiPart.append('.');
                }
            }
            sb.append(hexPart)
                    .append("  ")
                    .append(asciiPart)
                    .append("\n");
        }

        return sb.toString();
    }
}
