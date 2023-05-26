package packet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class Map extends JFrame {
    final int dimension = 18;

    //Vettore contenente le coordinate del serpente
    Vector<String> snakeCoords = new Vector();

    //Oggetti legati all'interfaccia
    Container cont = this.getContentPane();

    JPanel upperBar = new JPanel();
    JLabel timer = new JLabel();
    JPanel upperBarDx = new JPanel();
    JLabel appleIcon = new JLabel();
    JLabel score = new JLabel();

    JPanel grid = new JPanel();
    Tile[][] tile = new Tile[dimension][dimension];

    //Oggetti legati all'estetica
    Color lightGreen = new Color(95, 247, 72);
    Color darkGreen = new Color(50, 190, 31);
    Color mapBorders = new Color(27, 110, 20);

    public Map(){
        setTitle("GameMap");

        cont.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));

        //Aggiunta barra superiore
        upperBar.setLayout(new GridLayout(1,2));
        timer.setText("00:00");
        timer.setBorder(new EmptyBorder(10,30,10,0));

        upperBarDx.setLayout(new FlowLayout(FlowLayout.RIGHT));
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
                    tile[i][j].setPreferredSize(new Dimension(40,40));
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

        cont.add(grid);

        //Impostazioni di visualizzazione
        this.pack();
        this.setLocation(ScreenSize.getWidth() / 2 - this.getWidth() / 2, ScreenSize.getHeight() / 2 - this.getHeight() / 2);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
}
