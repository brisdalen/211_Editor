package editor.action;

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
        switch(direction) {

            case "LEFT": editor.getDocument().decreaseCol();
                break;

            case "RIGHT": editor.getDocument().increaseCol();
                break;

            case "UP": editor.getDocument().decreaseRow();
                break;

            case "DOWN": editor.getDocument().increaseRow();
                break;

            default: System.out.println("Unknown direction");
                break;
        }
    }
}
