/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * This class represent some kind of old style terminal. It is capable of
 * displaying individual characters in a rectangular grid. It is intended to be
 * used as is. Just like a library class.
 *
 * @author evenal
 */
public class CharacterDisplay extends JPanel {

    /**
     * The size of the terminal
     */
    public static final int HEIGHT = 20;
    public static final int WIDTH = 40;

    /**
     * Holds the data for the grid of characters
     */
    TableModel tableModel;
    JTextField messageArea;
    int cursorRow, cursorCol;

    public CharacterDisplay() {
        tableModel = new DisplayTableModel();
        cursorCol = cursorRow = 0;
        JTable table = createTable();
        table.setFocusable(true);
        table.setRowSelectionAllowed(false);
        messageArea = new JTextField();
        messageArea.setEditable(false);
        LayoutManager layout = new BorderLayout();
        setLayout(layout);
        add(table, BorderLayout.CENTER);
        add(messageArea, BorderLayout.SOUTH);
    }

    private JTable createTable() {
        JTable table = new JTable(tableModel);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(15);

        TableColumnModel colModel = table.getColumnModel();
        for (int col = 0; col < WIDTH; col++) {
            colModel.getColumn(col).setMaxWidth(8);
            colModel.getColumn(col).setMinWidth(8);
        }
        return table;
    }

    /**
     * Display the character c, in position row,col in the grid. Use this method
     * to display characters. To erase a character, call this method with a
     * blank/space as c.
     */
    public void displayChar(char c, int row, int col) {
        String s = String.format("%c", c);
        tableModel.setValueAt(s, row, col);
        repaint();
    }

    private class DisplayTableModel extends AbstractTableModel {

        private String[][] data;

        public DisplayTableModel() {
            this.data = new String[HEIGHT][WIDTH];
        }

        @Override
        public int getRowCount() {
            return HEIGHT;
        }

        @Override
        public int getColumnCount() {
            return WIDTH;
        }

        @Override
        public Object getValueAt(int row, int col) throws IndexOutOfBoundsException {
            if (row >= HEIGHT)
                throw new IndexOutOfBoundsException("Line index too large!");
            if (col >= WIDTH)
                throw new IndexOutOfBoundsException("Column index too large!");
            return data[row][col];
        }

        @Override
        public void setValueAt(Object o, int row, int col)
                throws IndexOutOfBoundsException {

            data[row][col] = (String) o;
        }

        @Override
        public boolean isCellEditable(int r, int c) throws IndexOutOfBoundsException {
            return false;
        }
    }
}
