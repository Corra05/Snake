package packet;

import javax.swing.*;
import java.awt.*;

public class Tile extends JLabel {
    boolean hasApple;
    boolean hasSnake;

    //Immagini utilizzate
    ImageIcon apple = new ImageIcon(new ImageIcon("images/apple.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    ImageIcon snakeHead = new ImageIcon(new ImageIcon("images/snakeHead.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    ImageIcon snakeBody = new ImageIcon(new ImageIcon("images/snakeBody.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    ImageIcon snakeTail = new ImageIcon(new ImageIcon("images/snakeTail.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

    public Tile(Map map, int i, int j){

    }
}
