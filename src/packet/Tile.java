package packet;

import javax.swing.*;
import java.awt.*;

public class Tile extends JLabel {
    int i;
    int j;

    boolean hasApple;
    boolean hasSnake;
    boolean isCorner;
    String cornerType;      //BottomRight, BottomLeft, TopRight, TopLeft

    String direction;
    String prevDirection;

    public Tile(int i, int j){
        this.i = i;
        this.j = j;
        setText(String.valueOf(i)+String.valueOf(j));
    }
}
