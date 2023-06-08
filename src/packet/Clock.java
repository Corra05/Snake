package packet;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.util.Random;

public class Clock extends JFrame implements Runnable {
    boolean loop = false;
    boolean gameOver = false;
    Random rand = new Random();

    String str;
    String[] coordinates;
    PlayerListener player;

    public Clock(PlayerListener player) {
        this.player = player;

        //Timer é utile per l'orologio del gioco e per aggiungere le mele
        Thread timer = new Thread(new Timer(this));
        timer.start();
    }

    @Override
    public void run() {
        while (true) {
            //Mostro/cambio le icone del serpente
            SnakeIcons();

            //Ripulisco la mappa dalle precedenti posizioni occupate dal serpente
            for (int i = 1; i < player.map.dimension - 1; i++) {
                for (int j = 1; j < player.map.dimension - 1; j++) {
                    player.map.tile[i][j].hasSnake = false;
                }
            }

            //Imposto sulla mappa le nuove posizioni occupate dal serpente
            for (int i = 0; i < player.map.snakeCoords.size(); i++) {
                str = player.map.snakeCoords.elementAt(i);

                String[] coordinates = str.split(" ");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);

                player.map.tile[x][y].hasSnake = true;
            }

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //Fa avanzare il serpente nella direzione della testa
            moveSnake();
        }
    }

    public void SnakeIcons() {
        String[] coordinates;

        int x;
        int y;

        //Imposto l'icona della testa
        str = player.map.snakeCoords.elementAt(0);

        coordinates = str.split(" ");
        x = Integer.parseInt(coordinates[0]);
        y = Integer.parseInt(coordinates[1]);

        if (player.map.tile[x][y].direction.equals("Right")) {
            player.map.tile[x][y].setIcon(player.map.snakeHeadRight);
        } else if (player.map.tile[x][y].direction.equals("Left")) {
            player.map.tile[x][y].setIcon(player.map.snakeHeadLeft);
        } else if (player.map.tile[x][y].direction.equals("Top")) {
            player.map.tile[x][y].setIcon(player.map.snakeHeadTop);
        } else if (player.map.tile[x][y].direction.equals("Bottom")) {
            player.map.tile[x][y].setIcon(player.map.snakeHeadBottom);
        }

        //Imposto l'icone della coda
        str = player.map.snakeCoords.elementAt(player.map.snakeCoords.size() - 1);

        coordinates = str.split(" ");
        x = Integer.parseInt(coordinates[0]);
        y = Integer.parseInt(coordinates[1]);

        if (player.map.tile[x][y].direction.equals("Right")) {
            player.map.tile[x][y].setIcon(player.map.snakeTailRight);
        } else if (player.map.tile[x][y].direction.equals("Left")) {
            player.map.tile[x][y].setIcon(player.map.snakeTailLeft);
        } else if (player.map.tile[x][y].direction.equals("Top")) {
            player.map.tile[x][y].setIcon(player.map.snakeTailTop);
        } else if (player.map.tile[x][y].direction.equals("Bottom")) {
            player.map.tile[x][y].setIcon(player.map.snakeTailBottom);
        }

        //Imposto le icone del resto del body
        for (int i = 1; i < player.map.snakeCoords.size() - 1; i++) {
            str = player.map.snakeCoords.elementAt(i);

            coordinates = str.split(" ");
            x = Integer.parseInt(coordinates[0]);
            y = Integer.parseInt(coordinates[1]);

            if (player.map.tile[x][y].direction.equals("Right") || player.map.tile[x][y].direction.equals("Left")) {
                player.map.tile[x][y].setIcon(player.map.snakeBodyHorizontal);
            } else if (player.map.tile[x][y].direction.equals("Top") || player.map.tile[x][y].direction.equals("Bottom")) {
                player.map.tile[x][y].setIcon(player.map.snakeBodyVertical);
            }
        }

        //Imposto le icone degli angoli
        for (int i = 1; i < player.map.snakeCoords.size() - 1; i++) {
            str = player.map.snakeCoords.elementAt(i);

            coordinates = str.split(" ");
            x = Integer.parseInt(coordinates[0]);
            y = Integer.parseInt(coordinates[1]);

            str = player.map.snakeCoords.elementAt(i - 1);

            coordinates = str.split(" ");
            int xPrevious = Integer.parseInt(coordinates[0]);
            int yPrevious = Integer.parseInt(coordinates[1]);

            str = player.map.snakeCoords.elementAt(i + 1);

            coordinates = str.split(" ");
            int xAfter = Integer.parseInt(coordinates[0]);
            int yAfter = Integer.parseInt(coordinates[1]);

            //Eseguo un controllo su dove sono presenti le caselle vicine aventi serpente
            if((yPrevious < y && xAfter < x) || (xPrevious < x && yAfter < y)){
                player.map.tile[x][y].setIcon(player.map.snakeCornerBottomRight);
            }else if((yPrevious > y && xAfter < x) || (xPrevious < x && yAfter > y)){
                player.map.tile[x][y].setIcon(player.map.snakeCornerBottomLeft);
            }else if((yPrevious < y && xAfter > x) || (xPrevious > x && yAfter < y)){
                player.map.tile[x][y].setIcon(player.map.snakeCornerTopRight);
            }else if((yPrevious > y && xAfter > x) || (xPrevious > x && yAfter > y)){
                player.map.tile[x][y].setIcon(player.map.snakeCornerTopLeft);
            }
        }
    }

    public void moveSnake() {
        //Inserisco in un array le direzioni delle caselle del serpente, così da poterle poi dare
        //alle altre caselle dopo il movimento
        String[] directions = new String[player.map.snakeCoords.size()];

        for (int i = 0; i < player.map.snakeCoords.size(); i++) {
            //Coordinate della testa del serpente
            str = player.map.snakeCoords.elementAt(i);

            coordinates = str.split(" ");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);

            directions[i] = player.map.tile[x][y].direction;
        }

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

                //Elimino l'icona della coda
                player.map.tile[xMax][yMax].setIcon(null);

                //Controllo se la casella successiva ha una mela
                if (player.map.tile[x0][y0 + 1].hasApple) {
                    player.map.snakeCoords.addElement(String.valueOf(xMax) + " " + String.valueOf(yMax));
                    player.map.appleSpawned = false;
                    player.map.tile[x0][y0 + 1].hasApple = false;
                    player.map.score.setText(String.valueOf(player.map.snakeCoords.size() - 3));

                    //Effetto audio mela mangiata
                    try {
                        File file = new File("appleEaten.wav"); // Inserire il percorso del file audio clic
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(file));
                        clip.start();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
                //Controllo se la casella successiva ha una parte del serpente
                else if (player.map.tile[x0][y0 + 1].hasSnake) {
                    gameOver = true;
                    JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over", JOptionPane.ERROR_MESSAGE);

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

                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);

                    //Imposto le coordinate della cella successiva
                    player.map.snakeCoords.set(i + 1, String.valueOf(x) + " " + String.valueOf(y));
                    player.map.tile[x][y].direction = directions[i];
                }

                //Imposto il nuovo valore della posizione della testa
                player.map.snakeCoords.set(0, String.valueOf(x0) + " " + String.valueOf(y0 + 1));

            } catch (Exception e) {
                gameOver = true;
                JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over", JOptionPane.ERROR_MESSAGE);

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

                //Elimino l'icona della coda
                player.map.tile[xMax][yMax].setIcon(null);

                //Controllo se la casella successiva ha una mela
                if (player.map.tile[x0][y0 - 1].hasApple) {
                    player.map.snakeCoords.addElement(String.valueOf(xMax) + " " + String.valueOf(yMax));
                    player.map.appleSpawned = false;
                    player.map.tile[x0][y0 - 1].hasApple = false;
                    player.map.score.setText(String.valueOf(player.map.snakeCoords.size() - 3));

                    //Effetto audio mela mangiata
                    try {
                        File file = new File("appleEaten.wav"); // Inserire il percorso del file audio clic
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(file));
                        clip.start();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
                //Controllo se la casella successiva ha una parte del serpente
                else if (player.map.tile[x0][y0 - 1].hasSnake) {
                    gameOver = true;
                    JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over", JOptionPane.ERROR_MESSAGE);

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

                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);

                    //Imposto le coordinate della cella successiva
                    player.map.snakeCoords.set(i + 1, String.valueOf(x) + " " + String.valueOf(y));
                    player.map.tile[x][y].direction = directions[i];
                }

                //Imposto il nuovo valore della posizione della testa
                player.map.snakeCoords.set(0, String.valueOf(x0) + " " + String.valueOf(y0 - 1));

            } catch (Exception e) {
                gameOver = true;
                JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over", JOptionPane.ERROR_MESSAGE);

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

                //Elimino l'icona della coda
                player.map.tile[xMax][yMax].setIcon(null);

                //Controllo se la casella successiva ha una mela
                if (player.map.tile[x0 - 1][y0].hasApple) {
                    player.map.snakeCoords.addElement(String.valueOf(xMax) + " " + String.valueOf(yMax));
                    player.map.appleSpawned = false;
                    player.map.tile[x0 - 1][y0].hasApple = false;
                    player.map.score.setText(String.valueOf(player.map.snakeCoords.size() - 3));

                    //Effetto audio mela mangiata
                    try {
                        File file = new File("appleEaten.wav"); // Inserire il percorso del file audio clic
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(file));
                        clip.start();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
                //Controllo se la casella successiva ha una parte del serpente
                else if (player.map.tile[x0 - 1][y0].hasSnake) {
                    gameOver = true;
                    JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over", JOptionPane.ERROR_MESSAGE);

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

                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);

                    //Imposto le coordinate della cella successiva
                    player.map.snakeCoords.set(i + 1, String.valueOf(x) + " " + String.valueOf(y));
                    player.map.tile[x][y].direction = directions[i];
                }

                //Imposto il nuovo valore della posizione della testa
                player.map.snakeCoords.set(0, String.valueOf(x0 - 1) + " " + String.valueOf(y0));

            } catch (Exception e) {
                gameOver = true;
                JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over", JOptionPane.ERROR_MESSAGE);

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

                //Elimino l'icona della coda
                player.map.tile[xMax][yMax].setIcon(null);

                //Controllo se la casella successiva ha una mela
                if (player.map.tile[x0 + 1][y0].hasApple) {
                    player.map.snakeCoords.addElement(String.valueOf(xMax) + " " + String.valueOf(yMax));
                    player.map.appleSpawned = false;
                    player.map.tile[x0 + 1][y0].hasApple = false;
                    player.map.score.setText(String.valueOf(player.map.snakeCoords.size() - 3));

                    //Effetto audio mela mangiata
                    try {
                        File file = new File("appleEaten.wav"); // Inserire il percorso del file audio clic
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(file));
                        clip.start();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
                //Controllo se la casella successiva ha una parte del serpente
                else if (player.map.tile[x0 + 1][y0].hasSnake) {
                    gameOver = true;
                    JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over", JOptionPane.ERROR_MESSAGE);

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

                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);

                    //Imposto le coordinate della cella successiva
                    player.map.snakeCoords.set(i + 1, String.valueOf(x) + " " + String.valueOf(y));
                    player.map.tile[x][y].direction = directions[i];
                }

                //Imposto il nuovo valore della posizione della testa
                player.map.snakeCoords.set(0, String.valueOf(x0 + 1) + " " + String.valueOf(y0));

            } catch (Exception e) {
                gameOver = true;
                JOptionPane.showMessageDialog(this, "Hai perso L", "Game Over", JOptionPane.ERROR_MESSAGE);

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
