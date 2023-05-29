package packet;

import javax.swing.*;
import java.awt.*;

public class Tile extends JLabel {
    boolean hasApple;
    boolean hasSnake;
    String direction;

    Map map;

    public Tile(Map map, int i, int j){
        this.map = map;

        setText(String.valueOf(i)+String.valueOf(j));
    }
}
