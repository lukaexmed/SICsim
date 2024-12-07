package simulator.UI;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class MemoryView {
    private int rows = 16;
    private int cols = 20;

    public JScrollPane createPanel() {
        JTable memoryTable = new JTable(new MemoryTableModel(rows, cols));
        memoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        memoryTable.setRowSelectionAllowed(false);
        memoryTable.setCellSelectionEnabled(false);
        memoryTable.setFillsViewportHeight(true);

        memoryTable.setRowHeight(30);
        for(int i = 0; i < cols; i++){
            memoryTable.getColumnModel().getColumn(i).setPreferredWidth(50);
        }
        return new JScrollPane(memoryTable);
    }
    class MemoryTableModel extends AbstractTableModel {
        private int rows;
        private int cols;
        private String[][] data;

        public MemoryTableModel(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
            this.data = new String[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    this.data[i][j] = "00";
                }
            }
        }
        @Override
        public int getRowCount(){
            return rows;
        }
        @Override
        public int getColumnCount(){
            return cols;
        }
        @Override
        public Object getValueAt(int row, int col){
            return data[row][col];
        }
        @Override
        public void setValueAt(Object value, int row, int col){
            if(value instanceof String){
                String val = (String)value;
                if(val.matches("[0-9A-Fa-f]{2}")){
                    data[row][col] = val.toUpperCase();
                    fireTableCellUpdated(row, col);
                }
            }
        }
        @Override
        public boolean isCellEditable(int row, int col){
            return true;
        }
    }

}
