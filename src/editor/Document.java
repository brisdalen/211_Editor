/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import com.sun.org.apache.xml.internal.utils.ListingErrorHandler;
import editor.display.CharacterDisplay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * This class represents the document being edited. Using a 2d array to hold the
 * document content is probably not a very good choice. Fixing this class is the
 * main focus of the exercise. In addition to designing a better data model, you
 * must add methods to do at least basic editing: write and delete text, and
 * moving the cursor
 *
 * @author evenal
 */
public class Document {

    private CharacterDisplay display;
    private int cursorRow;
    private int cursorCol;
    //private char[][] data;
    private LinkedList list;
    private ArrayList<String> lines;

    public Document(CharacterDisplay display) {
        this.display = display;
        //data = new char[CharacterDisplay.HEIGHT][CharacterDisplay.WIDTH];
        list = new LinkedList();
        cursorCol = cursorRow = 0;
        displayCursor();
    }

    public void insertChar(char c) {
        if(validPosition()) {
            list.add(c);
            displayChar(c);
            moveCursorRight();
        }
    }

    public void insertNewLine() {
        //TODO: legge til listen selv om man ikke ser det? Samme i insertChar?
        if(validPosition()) {
            list.add('\n');
        }
        moveCursorDownAndReset();
    }

    public void setAndDisplayChar(char c) {
        setCurrentChar(c);
        displayChar(c);
    }

    public void setCurrentChar(char c) {
        if(validPosition()) {
            int index = calculateIndex(cursorRow, cursorCol);
            if(index < list.size()) {
                list.set(index, c);
            }
            //data[cursorRow][cursorCol] = c;
        }
    }

    public void displayChar(char c) {
        if(validPosition()) {
            display.displayChar(c, cursorRow, cursorCol);
        }
    }

    public void displayChar(char c, int row, int col) {
        display.displayChar(c, row, col);
    }

    public void displayCursor() {
        display.displayCursor(' ', cursorRow, cursorCol);
    }

    public void displayCurrentChar() {
        if(validPosition()) {
            displayChar((char)list.get(calculateIndex(cursorRow, cursorCol)));
        }
    }

    public void deleteLastChar() {
        decreaseCol();
        deleteCurrentChar();
        displayCursor();
    }

    public void deleteCurrentChar() {

        //data[cursorRow][cursorCol] = ' ';
        display.displayChar(' ', cursorRow, cursorCol);
    }

    public char getCurrentChar() {
        return (char)list.get(calculateIndex(cursorRow, cursorCol));
        //return data[cursorRow][cursorCol];
    }

    public void moveCursorLeft() {
        decreaseCol();
        displayCursor();
    }

    public void moveCursorRight() {
        //TODO: Flytt pekeren nedover hvis du har kommet til slutten av linja
        if(validPosition()) {
            increaseCol();
            displayCursor();
        }
    }

    public void moveCursorUp() {
        decreaseRow();
        displayCursor();
    }

    public void moveCursorDown() {
        //TODO: Flytt pekeren nedover kun om det er noe å flytte til
        increaseRow();
        displayCursor();
    }

    // New line
    public void moveCursorDownAndReset() {
        increaseRow();
        cursorCol = 0;
        displayCursor();
    }

    public boolean increaseCol() {
        cursorCol++;
        if (cursorCol >= CharacterDisplay.WIDTH) {
            if(cursorRow < CharacterDisplay.HEIGHT-1) {
                System.out.println("new line");
                increaseRow();
                cursorCol = 0;
                //TODO: shift lines
                return true;
            } else {
                //System.out.println("no new line");
                //cursorCol = CharacterDisplay.WIDTH -1;
                System.out.println("end of display");
                cursorCol = 0;
                scrollDisplayDown();
                return false;
            }
        }
        return true;
    }

    public void decreaseCol() {
        cursorCol--;
        if(cursorCol < 0) {
            if(cursorRow > 0) {
                cursorCol = CharacterDisplay.WIDTH -1;
                decreaseRow();
            } else {
                cursorCol = 0;
                System.out.println("Cursor column can't go lower.");
            }
        }
    }

    public void increaseRow() {
        cursorRow++;
        if (cursorRow >= CharacterDisplay.HEIGHT) {
            //cursorRow--;
            // Flytt "skjermen" nedover
            scrollDisplayDown();
        }
    }

    public void scrollDisplayDown() {
        printList();
        int width = CharacterDisplay.WIDTH;
        int height = CharacterDisplay.HEIGHT;
        // Kopier de n-1 nederste linjene, og lim dem inn 1 linje ovenfor
        ListIterator iterator = list.listIterator(width);
        for(int row = 0; row < height; row++) {
            for (int i = width; i < (width * height); i++) {
                displayChar((char)list.get(i), row, i%10);
            }
        }
        // Lag en ny tom linje i nederste rad
        for(int i = 0; i < 10; i++) {
            displayChar(' ', 4, i);
        }
    }

    public void decreaseRow() {
        cursorRow--;
        if (cursorRow < 0) {
            cursorRow = 0;
            System.out.println("Cursor row can't go lower.");
        }
    }

    private boolean validPosition() {
        boolean valid = (cursorRow < CharacterDisplay.HEIGHT && cursorCol < CharacterDisplay.WIDTH);
        //System.out.println("valid = " + valid);
        return valid;
    }

    /**
     * Calculates the index of a 2D index for a one-dimensional array
     * @param row row position of the two-dimensional array
     * @param column column position of the two-dimensional array
     * @return index of a one-dimensional array based on the row and column of a two-dimensional array
     */
    public int calculateIndex(int row, int column) {
        int result = column + (CharacterDisplay.WIDTH * row);
        //System.out.println("result = " + result);
        return result;
    }

    public int getCursorRow() {
        return cursorRow;
    }

    public int getCursorCol() {
        return cursorCol;
    }

    public void printList() {
        System.out.println();
        for(Object o : list) {
            System.out.print((char) o);
        }
    }

}
