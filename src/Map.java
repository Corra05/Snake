import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Map extends JFrame {
    public Map(){
        //Vettore contenente le coordinate del serpente
        Vector<String> snakeCoords = new Vector();

        //Immagini utilizzate
        ImageIcon grassBackground = new ImageIcon(new ImageIcon("images/grassBackground.png").getImage().getScaledInstance(700, 700, Image.SCALE_SMOOTH));
        ImageIcon apple = new ImageIcon(new ImageIcon("images/apple.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        ImageIcon snakeHead = new ImageIcon(new ImageIcon("images/snakeHead.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        ImageIcon snakeBody = new ImageIcon(new ImageIcon("images/snakeBody.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        ImageIcon snakeTail = new ImageIcon(new ImageIcon("images/snakeTail.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

        //Oggetti legati all'interfaccia
        Container cont = new Container();

        JPanel upperBar = new JPanel();
        JLabel timer = new JLabel();
        JPanel upperBarDx = new JPanel();
        JLabel appleIcon = new JLabel();
        JLabel score = new JLabel();

        JLabel background = new JLabel();

        JPanel grid = new JPanel();

    }
}
