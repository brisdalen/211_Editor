/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import com.sun.org.apache.xml.internal.utils.ListingErrorHandler;
import editor.display.CharacterDisplay;

import java.util.ArrayList;
import java.util.Iterator;
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
    private int width = CharacterDisplay.WIDTH;
    private int cursorRow;
    private int cursorCol;
    private int cursorIndex;
    //private char[][] data;
    private LinkedList<Character> list;
    private Iterator<Character> iterator;

    public Document(CharacterDisplay display) {
        this.display = display;
        //data = new char[CharacterDisplay.HEIGHT][CharacterDisplay.WIDTH];
        list = new LinkedList();
        cursorCol = cursorRow = 0;
        cursorIndex = 0;
        displayCursor();
    }

    public void insertChar(char c) {
        if(validPosition()) {
            // Bruk addFirst eller addLast om mulig, da denne operasjonen er O(1)
            if(cursorIndex == list.size()) {
                System.out.println("insert last");
                list.addLast(c);
            } else if(cursorIndex == 0) {
                System.out.println("insert first");
                list.addFirst(c);
                iterator = list.listIterator(0);
                handleInsert(iterator);
            } else {
                System.out.println("insert@:" + cursorIndex);
                list.add(cursorIndex, c);
                iterator = list.listIterator(cursorIndex);
                handleInsert(iterator);
            }
            displayChar(c);
            moveCursorRight();
        }
    }

    private void handleInsert(Iterator<Character> iterator) {
        if(list.size() > 1) {
            //TODO: Ikke slett, men flytt data (Handler kanksje mer om displayet)
            iterator.next();
            iterator.remove();
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

    public void increaseCol() {
        System.out.println("[increaseCol]cursorIndex before = " + cursorIndex);
        System.out.println("[increaseCol]list.size() = " + list.size());
        // Pass på å ikke inkrementere cursoren mer enn listas lengde
        if(cursorIndex < list.size()) {
            cursorIndex++;
        } else {
            //TODO: Stopper riktig hvis du kun skriver på en linje.

            // Ellers avbrytes resten av metoden
            System.out.println("[increaseCol]cursor column can't go higher");
            return;
        }
        System.out.println("[increaseCol]cursorIndex after = " + cursorIndex);

        cursorCol++;
        if (cursorCol >= CharacterDisplay.WIDTH) {
            if(cursorRow < CharacterDisplay.HEIGHT-1) {
                System.out.println("new line");
                increaseRow();
                cursorCol = 0;
                //TODO: shift lines
            } else {
                //System.out.println("no new line");
                //cursorCol = CharacterDisplay.WIDTH -1;
                System.out.println("end of display");
                cursorCol = 0;
                scrollDisplayDown();
            }
        }
    }

    public void decreaseCol() {
        // Pass på å ikke dekrementere liste-index mindre enn 0
        if(cursorIndex > 0) {
            cursorIndex--;
        } else {
            // Ellers avbrytes resten av metoden
            System.out.println("[decreaseCol]cursorCol can't go lower");
            return;
        }

        cursorCol--;
        if(cursorCol < 0) {
            // Hvis du ikke er på øverste linje, og er helt til venstre i dokumentet, settes cursor helt til høyre på forrige linje
            if(cursorRow > 0) {
                cursorCol = CharacterDisplay.WIDTH -1;
                decreaseRow();
            } else {
                // Ellers settes cursor til 0 på skjermen (hvis du er på øverste linje og helt til venstre i dokumentet)
                cursorCol = 0;
                System.out.println("Cursor column can't go lower.");
            }
        }
    }

    public void increaseRow() {
        printList();
        // Hvis det er mer enn skjermens lengde (n) igjen i lista, hopper vi n plasser frem
        if(cursorIndex < list.size()-CharacterDisplay.WIDTH) {
            cursorIndex += CharacterDisplay.WIDTH;
        } else {
            // Ellers settes liste-index til listas endepunkt, og resten av metoden avbrytes
            cursorIndex = list.size();
        }
        cursorRow++;
        if (cursorRow >= CharacterDisplay.HEIGHT) {
            cursorRow--;
            // Flytt "skjermen" nedover
            scrollDisplayDown();
        }
    }

    public void scrollDisplayDown() {
        //TODO: Oppfører seg rart etter man sletter ting. på grunn av ingen ordentlig sletting i deleteChar I guess
        int loopWidth = CharacterDisplay.WIDTH;
        printList();
        int height = CharacterDisplay.HEIGHT;
        // Kopier de n-1 nederste linjene, og lim dem inn 1 linje ovenfor

        for(int row = 1; row < height; row++) {
            for(int i = width; i < (width + loopWidth); i++) {

                System.out.println("i = " + (i + ((row-1) * width)));
                char c = list.get(i + ((row-1) * loopWidth));
                displayChar((c), row-1, i-width);
            }
        }
        // Lag en ny tom linje i nederste rad
        for(int i = 0; i < loopWidth; i++) {
            displayChar(' ', height-1, i);
        }

        width += loopWidth;
    }

    public void decreaseRow() {
        // Hvis det er mer enn skjermens lengde (n) igjen til starten av lista, hopper vi n plasser tilbake
        if(cursorIndex > CharacterDisplay.WIDTH) {
            cursorIndex -= CharacterDisplay.WIDTH;
        } else {
            // Ellers setter vi indexen til listas første plass
            cursorIndex = 0;
        }

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
