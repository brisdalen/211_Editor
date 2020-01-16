/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import editor.display.CharacterDisplay;

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

    public Document(CharacterDisplay display) {
        this.display = display;
        data = new char[CharacterDisplay.HEIGHT][CharacterDisplay.WIDTH];
        cursorCol = cursorRow = 0;
        display.displayChar('_', cursorRow, cursorCol);
    }

    public void insertChar(char c) {
        data[cursorRow][cursorCol] = c;
        display.displayChar(c, cursorRow, cursorCol);
        increaseCol();
        display.displayChar('_', cursorRow, cursorCol);
    }

    public void deleteLastChar() {
        decreaseCol();
        data[cursorRow][cursorCol] = ' ';
        display.displayChar('_', cursorRow, cursorCol);
        increaseCol();
        display.displayChar(' ', cursorRow, cursorCol);
        decreaseCol();
    }

    public void moveCursorLeft() {

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

    public int getCursorRow() {
        return cursorRow;
    }

    public int getCursorCol() {
        return cursorCol;
    }
}
