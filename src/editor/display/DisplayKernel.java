package editor.display;

import java.util.List;
import java.util.ListIterator;

public class DisplayKernel {
    public static final int WIDTH = CharacterDisplay.WIDTH;
    public static final int HEIGHT = CharacterDisplay.HEIGHT;

    private List data;
    private ListIterator iterator;

    public DisplayKernel(List data) {
        this.data = data;
        this.iterator = data.listIterator();
    }

    public void moveUp() {
        //TODO: implement
    }

    public void moveDown() {
        //TODO: implement
    }
}
