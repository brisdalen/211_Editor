/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import editor.display.CharacterDisplay;
import java.util.LinkedList;

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
    private char[][] data;
    private char lastCharHorizontal;
    private char lastCharVertical;
    private LinkedList list;

    public Document(CharacterDisplay display) {
        this.display = display;
        data = new char[CharacterDisplay.HEIGHT][CharacterDisplay.WIDTH];
        list = new LinkedList();
        cursorCol = cursorRow = 0;
        displayChar('_');
    }

    public void insertChar(char c) {
        list.add(c);
        setAndDisplayChar(c);
        moveCursorRight();
    }

    public void setAndDisplayChar(char c) {
        setChar(c);
        displayChar(c);
    }

    public void setChar(char c) {
        data[cursorRow][cursorCol] = c;
    }

    public void displayChar(char c) {
        display.displayChar(c, cursorRow, cursorCol);
    }

    public void deleteLastChar() {
        decreaseCol();
        deleteCurrentChar();
        increaseCol();
        moveCursorLeft();
    }

    public void deleteCurrentChar() {
        data[cursorRow][cursorCol] = ' ';
        display.displayChar(' ', cursorRow, cursorCol);
        lastCharHorizontal = ' ';
        list.remove(calculateIndex(cursorRow, cursorCol));
    }

    public char getCurrentChar() {
        return data[cursorRow][cursorCol];
    }

    public void moveCursorLeft() {
        decreaseCol();
        displayChar('_');
        increaseCol();
        displayChar(data[cursorRow][cursorCol]);
        decreaseCol();
    }

    public void moveCursorRight() {
        increaseCol();
        displayChar('_');
        decreaseCol();
        displayChar(data[cursorRow][cursorCol]);
        increaseCol();
    }

    public void moveCursorUp() {
        displayChar(data[cursorRow][cursorCol]);
        decreaseRow();
        displayChar('_');
    }

    public void moveCursorDown() {
        displayChar(data[cursorRow][cursorCol]);
        increaseRow();
        displayChar('_');

    }

    public void increaseCol() {
        cursorCol++;
        if (cursorCol >= CharacterDisplay.WIDTH) {
            cursorCol = 0;
            cursorRow++;
        }
    }

    public void decreaseCol() {
        cursorCol--;
        if(cursorCol < 0) {
            if(cursorRow > 0) {
                cursorCol = CharacterDisplay.WIDTH -1;
                cursorRow--;
            } else {
                cursorCol = 0;
                System.out.println("Cursor column can't go lower.");
            }
        }
    }

    public void increaseRow() {
        System.out.println(list.size());
        cursorRow++;
        if (cursorRow >= CharacterDisplay.HEIGHT) {
            cursorRow--;
        }
    }

    public void decreaseRow() {
        cursorRow--;
        if (cursorRow < 0) {
            cursorRow = 0;
        }
    }

    /**
     * Calculates the index of a 2D index for a one-dimensional array
     * @param row row position of the two-dimensional array
     * @param column column position of the two-dimensional array
     * @return index of a one-dimensional array based on the row and column of a two-dimensional array
     */
    public int calculateIndex(int row, int column) {
        return column + (CharacterDisplay.WIDTH * row);
    }

    public int getCursorRow() {
        return cursorRow;
    }

    public int getCursorCol() {
        return cursorCol;
    }

}
