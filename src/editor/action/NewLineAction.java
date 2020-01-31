package editor.action;

import editor.Document;
import editor.Editor;

import java.awt.event.ActionEvent;

public class NewLineAction extends EditorAction {

    Editor editor;

    public NewLineAction(String name, Editor editor) {
        super(name);
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Document doc = editor.getDocument();
        doc.insertNewLine();
    }
}
