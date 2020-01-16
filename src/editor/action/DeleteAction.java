package editor.action;

import editor.Document;
import editor.Editor;

import java.awt.event.ActionEvent;

public class DeleteAction extends EditorAction {

    Editor editor;

    public DeleteAction(String name, Editor editor) {
        super(name);
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Document doc = editor.getDocument();
        doc.deleteLastChar();
    }
}
