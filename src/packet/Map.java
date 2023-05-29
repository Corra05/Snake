package packet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class Map extends JFrame{
    final int dimension = 18;
    final int sizes = 40;

    //Immagini utilizzate
    ImageIcon apple = new ImageIcon(new ImageIcon("images/apple.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

    ImageIcon snakeBodyHorizontal = new ImageIcon(new ImageIcon("images/SnakeBodyHorizontal.png").getImage().getScaledInstance(sizes, sizes, Image.SCALE_SMOOTH));
    ImageIcon snakeBodyVertical = new ImageIcon(new ImageIcon("images/SnakeBodyVertical.png").getImage().getScaledInstance(sizes, sizes, Image.SCALE_SMOOTH));

    ImageIcon snakeHeadRight = new ImageIcon(new ImageIcon("images/Right/SnakeHeadRight.png").getImage().getScaledInstance(sizes, sizes, Image.SCALE_SMOOTH));
    ImageIcon snakeTailRight = new ImageIcon(new ImageIcon("images/Right/SnakeTailRight.png").getImage().getScaledInstance(sizes, sizes, Image.SCALE_SMOOTH));

    ImageIcon snakeHeadLeft = new ImageIcon(new ImageIcon("images/Left/SnakeHeadLeft.png").getImage().getScaledInstance(sizes, sizes, Image.SCALE_SMOOTH));
    ImageIcon snakeTailLeft = new ImageIcon(new ImageIcon("images/Left/SnakeTailLeft.png").getImage().getScaledInstance(sizes, sizes, Image.SCALE_SMOOTH));

    ImageIcon snakeHeadTop = new ImageIcon(new ImageIcon("images/Top/SnakeHeadBottom.png").getImage().getScaledInstance(sizes, sizes, Image.SCALE_SMOOTH));
    ImageIcon snakeTailTop = new ImageIcon(new ImageIcon("images/Top/SnakeTailBottom.png").getImage().getScaledInstance(sizes, sizes, Image.SCALE_SMOOTH));

    ImageIcon snakeHeadBottom = new ImageIcon(new ImageIcon("images/Bottom/SnakeHeadBottom.png").getImage().getScaledInstance(sizes, sizes, Image.SCALE_SMOOTH));
    ImageIcon snakeTailBottom = new ImageIcon(new ImageIcon("images/Bottom/SnakeTailBottom.png").getImage().getScaledInstance(sizes, sizes, Image.SCALE_SMOOTH));

    //Vettore contenente le coordinate del serpente
    Vector<String> snakeCoords = new Vector<String>();

    //Oggetti legati all'interfaccia
    Container cont = this.getContentPane();

    JPanel upperBar = new JPanel();
    JLabel timer = new JLabel();
    JPanel upperBarDx = new JPanel();
    JLabel appleIcon = new JLabel();
    JLabel score = new JLabel();

    JPanel grid = new JPanel();
    Tile[][] tile = new Tile[dimension - 1][dimension - 1];

    //Oggetti legati all'estetica
    Color lightGreen = new Color(45, 163, 199);
    Color darkGreen = new Color(43, 133, 222);
    Color mapBorders = new Color(11, 15, 103);

    PlayerListener keyboard;

    public Map(){
        setTitle("GameMap");

        cont.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));

        //Aggiunta barra superiore
        upperBar.setLayout(new GridLayout(1,2));
        timer.setText("00:00");
        timer.setBorder(new EmptyBorder(10,30,10,0));

        upperBarDx.setLayout(new FlowLayout(FlowLayout.RIGHT));
        appleIcon.setIcon(apple);
        score.setText("0");
        score.setBorder(new EmptyBorder(10,0,10,30));
        upperBarDx.add(appleIcon);
        upperBarDx.add(score);

        upperBar.add(timer);
        upperBar.add(upperBarDx);

        cont.add(upperBar);

        grid.setLayout(new GridLayout(dimension,dimension));

        //Creazione della griglia, da inserire in un panel
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                //Aggiunta di un bordo a tutta la mappa
                if ((i == 0 && j == 0) || (i == 0 && j != 0) || (i != 0 && j == 0) || ((i == dimension - 1) && j != 0) || (i != dimension && (j == dimension - 1))) {
                    JLabel border = new JLabel();
                    border.setBackground(mapBorders);
                    border.setOpaque(true);
                    grid.add(border);
                }
                //Aggiunta delle caselle della mappa
                else {
                    tile[i][j] = new Tile(this, i, j);      //Popolamento matrice
                    tile[i][j].setPreferredSize(new Dimension(sizes,sizes));
                    tile[i][j].setOpaque(true);

                    //Aggiunta di un colore alle caselle
                    if(j%2 == 0){
                        if(i%2 == 0){
                            tile[i][j].setBackground(lightGreen);
                        }else{
                            tile[i][j].setBackground(darkGreen);
                        }
                    }else{
                        if(i%2 == 0){
                            tile[i][j].setBackground(darkGreen);
                        }else{
                            tile[i][j].setBackground(lightGreen);
                        }
                    }

                    grid.add(tile[i][j]);   //Aggiunta della casella alla griglia
                }
            }
        }

        initialSetup();

        cont.add(grid);

        keyboard = new PlayerListener(this);
        this.addKeyListener(keyboard);

        //Impostazioni di visualizzazione
        this.pack();
        this.setLocation((ScreenSize.getWidth() / 2 - this.getWidth() / 2), (ScreenSize.getHeight() / 2 - this.getHeight() / 2));
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public void initialSetup(){
        tile[8][4].direction = "Right";
        tile[8][3].direction = "Right";
        tile[8][2].direction = "Right";

        snakeCoords.addElement("8 4");
        snakeCoords.addElement("8 3");
        snakeCoords.addElement("8 2");
    }
}
