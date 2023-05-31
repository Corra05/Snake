package packet;

import javax.swing.*;
import java.awt.*;

public class Tile extends JLabel {
    int i;
    int j;
    boolean hasApple;
    boolean isCorner;
    String cornerType;      //BottomRight, BottomLeft, TopRight, TopLeft

    String direction;
    String prevDirection;

    Map map;

    public Tile(int i, int j){
        this.map = map;
        this.i = i;
        this.j = j;
        setText(String.valueOf(i)+String.valueOf(j));
    }
}
