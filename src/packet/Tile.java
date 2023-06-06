package packet;

import javax.swing.*;
import java.awt.*;

public class Tile extends JLabel {
    boolean hasApple;
    boolean hasSnake;
    boolean isCorner;
    String cornerType;      //BottomRight, BottomLeft, TopRight, TopLeft

    String direction;
}
