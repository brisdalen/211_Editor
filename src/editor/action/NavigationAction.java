package editor.action;

import editor.Document;
import editor.Editor;

import java.awt.event.ActionEvent;

public class NavigationAction extends EditorAction {

    Editor editor;
    String direction;

    public NavigationAction(String name, String direction, Editor editor) {
        super(name);
        this.direction = direction;
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Document doc = editor.getDocument();
        switch(direction) {

            case "LEFT": doc.moveCursorLeft();
                break;

            case "RIGHT": doc.moveCursorRight();
                break;

            case "UP": doc.moveCursorUp();
                break;

            case "DOWN": doc.moveCursorDown();
                break;

            default: System.out.println("Unknown direction");
                break;
        }
    }
}
