package packet;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerListener implements KeyListener {
    boolean start = true;
    String str;

    Map map;
    Thread clock;

    public PlayerListener(Map map){
        this.map = map;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if((e.getKeyCode() == KeyEvent.VK_RIGHT) || (e.getKeyChar() == 'd') || (e.getKeyChar() == 'D')){
            if(start){
                clock = new Thread(new Clock(this));
                clock.start();

                start = false;
            }else{
                str = map.snakeCoords.elementAt(0);

                String[] coordinates = str.split(" ");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);

                map.tile[x][y].direction = "Right";
            }
        }
        else if((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyChar() == 'a') || (e.getKeyChar() == 'A')){
            if(!start){
                str = map.snakeCoords.elementAt(0);

                String[] coordinates = str.split(" ");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);

                map.tile[x][y].direction = "Left";
            }
        }
        else if((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyChar() == 'w') || (e.getKeyChar() == 'W')){
            if(!start){
                str = map.snakeCoords.elementAt(0);

                String[] coordinates = str.split(" ");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);

                map.tile[x][y].prevDirection =  map.tile[x][y].direction;
                map.tile[x][y].direction = "Top";
            }
        }
        else if((e.getKeyCode() == KeyEvent.VK_DOWN) || (e.getKeyChar() == 's') || (e.getKeyChar() == 'S')){
            if(!start){
                str = map.snakeCoords.elementAt(0);

                String[] coordinates = str.split(" ");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);

                map.tile[x][y].direction = "Bottom";
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
