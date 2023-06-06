package packet;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Clock extends JFrame implements Runnable {
    boolean loop = false;
    Random rand = new Random();

    String str;
    String[] coordinates;
    PlayerListener player;

    public Clock(PlayerListener player) {
        this.player = player;
        Thread timer = new Thread(new Timer(this));
        timer.start();
    }

    @Override
    public void run() {
        while (true) {
            SnakeIcons();

            for (int i = 1; i < player.map.dimension - 1; i++) {
                for (int j = 1; j < player.map.dimension - 1; j++) {
                    player.map.tile[i][j].hasSnake = false;
                }
            }

            for (int i = 0; i < player.map.snakeCoords.size(); i++) {
                str = player.map.snakeCoords.elementAt(i);

                String[] coordinates = str.split(" ");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);

                player.map.tile[x][y].hasSnake = true;
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            moveSnake();
        }
    }

    public void SnakeIcons() {
        for (int i = 0; i < player.map.snakeCoords.size(); i++) {
            str = player.map.snakeCoords.elementAt(i);

            String[] coordinates = str.split(" ");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);

            if (player.map.tile[x][y].direction.equals("Right")) {
                if (i == 0) {
                    player.map.tile[x][y].setIcon(player.map.snakeHeadRight);
                } else if (i == player.map.snakeCoords.size() - 1) {
                    player.map.tile[x][y].setIcon(player.map.snakeTailRight);
                } else {
                    player.map.tile[x][y].setIcon(player.map.snakeBodyHorizontal);
                }
            } else if (player.map.tile[x][y].direction.equals("Left")) {
                if (i == 0) {
                    player.map.tile[x][y].setIcon(player.map.snakeHeadLeft);
                } else if (i == player.map.snakeCoords.size() - 1) {
                    player.map.tile[x][y].setIcon(player.map.snakeTailLeft);
                } else {
                    player.map.tile[x][y].setIcon(player.map.snakeBodyHorizontal);
                }
            } else if (player.map.tile[x][y].direction.equals("Top")) {
                if (i == 0) {
                    player.map.tile[x][y].setIcon(player.map.snakeHeadTop);
                } else if (i == player.map.snakeCoords.size() - 1) {
                    player.map.tile[x][y].setIcon(player.map.snakeTailTop);
                } else {
                    player.map.tile[x][y].setIcon(player.map.snakeBodyVertical);
                }
            } else if (player.map.tile[x][y].direction.equals("Bottom")) {
                if (i == 0) {
                    player.map.tile[x][y].setIcon(player.map.snakeHeadBottom);
                } else if (i == player.map.snakeCoords.size() - 1) {
                    player.map.tile[x][y].setIcon(player.map.snakeTailBottom);
                } else {
                    player.map.tile[x][y].setIcon(player.map.snakeBodyVertical);
                }
            }

        }
    }

    public void moveSnake() {
        //Coordinate della testa del serpente
        str = player.map.snakeCoords.elementAt(0);

        coordinates = str.split(" ");
        int x0 = Integer.parseInt(coordinates[0]);
        int y0 = Integer.parseInt(coordinates[1]);

        //Metto le coordinate di ogni posizione in un array
        String[] coordinates1 = new String[player.map.snakeCoords.size()];
        for (int i = 0; i < player.map.snakeCoords.size(); i++) {
            coordinates1[i] = player.map.snakeCoords.elementAt(i);
        }

        //Spostamento del serpente in caso la testa sia rivolta a destra
        if (player.map.tile[x0][y0].direction.equals("Right")) {
            try {
                //Faccio avanzare la testa di una posizione
                player.map.tile[x0][y0].direction = null;
                player.map.tile[x0][y0 + 1].direction = "Right";

                //Coordinate della posizione della coda
                str = player.map.snakeCoords.elementAt(player.map.snakeCoords.size() - 1);

                coordinates = str.split(" ");
                int xMax = Integer.parseInt(coordinates[0]);
                int yMax = Integer.parseInt(coordinates[1]);

                //Elimino la coda
                player.map.tile[xMax][yMax].setIcon(null);

                if(player.map.tile[x0][y0 + 1].hasApple){
                    player.map.snakeCoords.addElement(String.valueOf(xMax) + " " + String.valueOf(yMax));
                    player.map.appleSpawned = false;
                    player.map.apples += 1;
                    player.map.score.setText(String.valueOf(player.map.apples));
                }
                else if(player.map.tile[x0][y0 + 1].hasSnake){
                    JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over",JOptionPane.ERROR_MESSAGE);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }

                    System.exit(0);
                }

                //Ciclo nel quale grazie all'array di prima posso far prendere a ogni casella del serpente
                //il valore che aveva quella successiva prima di avanzare di una posizione
                for (int i = 0; i < player.map.snakeCoords.size() - 1; i++) {
                    //Prendo le coordinate di ogni posizione dell'array
                    coordinates = coordinates1[i].split(" ");

                    int x1 = Integer.parseInt(coordinates[0]);
                    int y1 = Integer.parseInt(coordinates[1]);

                    //Imposto le coordinate della cella successiva
                    player.map.snakeCoords.set(i + 1, String.valueOf(x1) + " " + String.valueOf(y1));
                    player.map.tile[x1][y1].direction = "Right";
                }

                //Imposto il nuovo valore della posizione della testa
                player.map.snakeCoords.set(0, String.valueOf(x0) + " " + String.valueOf(y0 + 1));

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over",JOptionPane.ERROR_MESSAGE);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    throw new RuntimeException(e1);
                }

                System.exit(0);
            }
        }

        //Spostamento del serpente in caso la testa sia rivolta a sinistra
        else if (player.map.tile[x0][y0].direction.equals("Left")) {
            try {
                //Faccio avanzare la testa di una posizione
                player.map.tile[x0][y0].direction = null;
                player.map.tile[x0][y0 - 1].direction = "Left";

                //Coordinate della posizione della coda
                str = player.map.snakeCoords.elementAt(player.map.snakeCoords.size() - 1);

                coordinates = str.split(" ");
                int xMax = Integer.parseInt(coordinates[0]);
                int yMax = Integer.parseInt(coordinates[1]);

                //Elimino la coda
                player.map.tile[xMax][yMax].setIcon(null);

                if(player.map.tile[x0][y0 - 1].hasApple){
                    player.map.snakeCoords.addElement(String.valueOf(xMax) + " " + String.valueOf(yMax));
                    player.map.appleSpawned = false;
                    player.map.apples += 1;
                    player.map.score.setText(String.valueOf(player.map.apples));
                }
                else if(player.map.tile[x0][y0 - 1].hasSnake){
                    JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over",JOptionPane.ERROR_MESSAGE);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }

                    System.exit(0);
                }

                //Ciclo nel quale grazie all'array di prima posso far prendere a ogni casella del serpente
                //il valore che aveva quella successiva prima di avanzare di una posizione
                for (int i = 0; i < player.map.snakeCoords.size() - 1; i++) {
                    //Prendo le coordinate di ogni posizione dell'array
                    coordinates = coordinates1[i].split(" ");

                    int x1 = Integer.parseInt(coordinates[0]);
                    int y1 = Integer.parseInt(coordinates[1]);

                    //Imposto le coordinate della cella successiva
                    player.map.snakeCoords.set(i + 1, String.valueOf(x1) + " " + String.valueOf(y1));
                    player.map.tile[x1][y1].direction = "Left";
                }

                //Imposto il nuovo valore della posizione della testa
                player.map.snakeCoords.set(0, String.valueOf(x0) + " " + String.valueOf(y0 - 1));

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over",JOptionPane.ERROR_MESSAGE);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    throw new RuntimeException(e1);
                }

                System.exit(0);
            }
        }

        //Spostamento del serpente in caso la testa sia rivolta in alto
        else if (player.map.tile[x0][y0].direction.equals("Top")) {
            try {
                //Faccio avanzare la testa di una posizione
                player.map.tile[x0][y0].direction = null;
                player.map.tile[x0 - 1][y0].direction = "Top";

                //Coordinate della posizione della coda
                str = player.map.snakeCoords.elementAt(player.map.snakeCoords.size() - 1);

                coordinates = str.split(" ");
                int xMax = Integer.parseInt(coordinates[0]);
                int yMax = Integer.parseInt(coordinates[1]);

                //Elimino la coda
                player.map.tile[xMax][yMax].setIcon(null);

                if(player.map.tile[x0 - 1][y0].hasApple){
                    player.map.snakeCoords.addElement(String.valueOf(xMax) + " " + String.valueOf(yMax));
                    player.map.appleSpawned = false;
                    player.map.apples += 1;
                    player.map.score.setText(String.valueOf(player.map.apples));
                }
                else if(player.map.tile[x0 - 1][y0].hasSnake){
                    JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over",JOptionPane.ERROR_MESSAGE);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }

                    System.exit(0);
                }

                //Ciclo nel quale grazie all'array di prima posso far prendere a ogni casella del serpente
                //il valore che aveva quella successiva prima di avanzare di una posizione
                for (int i = 0; i < player.map.snakeCoords.size() - 1; i++) {
                    //Prendo le coordinate di ogni posizione dell'array
                    coordinates = coordinates1[i].split(" ");

                    int x1 = Integer.parseInt(coordinates[0]);
                    int y1 = Integer.parseInt(coordinates[1]);

                    //Imposto le coordinate della cella successiva
                    player.map.snakeCoords.set(i + 1, String.valueOf(x1) + " " + String.valueOf(y1));
                    player.map.tile[x1][y1].direction = "Top";
                }

                //Imposto il nuovo valore della posizione della testa
                player.map.snakeCoords.set(0, String.valueOf(x0 - 1) + " " + String.valueOf(y0));

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over",JOptionPane.ERROR_MESSAGE);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    throw new RuntimeException(e1);
                }

                System.exit(0);
            }
        }

        //Spostamento del serpente in caso la testa sia rivolta in basso
        else if (player.map.tile[x0][y0].direction.equals("Bottom")) {
            try {
                //Faccio avanzare la testa di una posizione
                player.map.tile[x0][y0].direction = null;
                player.map.tile[x0 + 1][y0].direction = "Bottom";

                //Coordinate della posizione della coda
                str = player.map.snakeCoords.elementAt(player.map.snakeCoords.size() - 1);

                coordinates = str.split(" ");
                int xMax = Integer.parseInt(coordinates[0]);
                int yMax = Integer.parseInt(coordinates[1]);

                //Elimino la coda
                player.map.tile[xMax][yMax].setIcon(null);

                if(player.map.tile[x0 + 1][y0].hasApple){
                    player.map.snakeCoords.addElement(String.valueOf(xMax) + " " + String.valueOf(yMax));
                    player.map.appleSpawned = false;
                    player.map.apples += 1;
                    player.map.score.setText(String.valueOf(player.map.apples));
                }
                else if(player.map.tile[x0 + 1][y0].hasSnake){
                    JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over",JOptionPane.ERROR_MESSAGE);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }

                    System.exit(0);
                }

                //Ciclo nel quale grazie all'array di prima posso far prendere a ogni casella del serpente
                //il valore che aveva quella successiva prima di avanzare di una posizione
                for (int i = 0; i < player.map.snakeCoords.size() - 1; i++) {
                    //Prendo le coordinate di ogni posizione dell'array
                    coordinates = coordinates1[i].split(" ");

                    int x1 = Integer.parseInt(coordinates[0]);
                    int y1 = Integer.parseInt(coordinates[1]);

                    //Imposto le coordinate della cella successiva
                    player.map.snakeCoords.set(i + 1, String.valueOf(x1) + " " + String.valueOf(y1));
                    player.map.tile[x1][y1].direction = "Bottom";
                }

                //Imposto il nuovo valore della posizione della testa
                player.map.snakeCoords.set(0, String.valueOf(x0 + 1) + " " + String.valueOf(y0));

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over",JOptionPane.ERROR_MESSAGE);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    throw new RuntimeException(e1);
                }

                System.exit(0);
            }
        }
    }
}
