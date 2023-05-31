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
        str = map.snakeCoords.elementAt(0);

        String[] coordinates = str.split(" ");
        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);

        if(!map.tile[x][y].direction.equals("Left") && ((e.getKeyChar() == 'd') || (e.getKeyChar() == 'D'))){
            if(start){
                clock = new Thread(new Clock(this));
                clock.start();

                start = false;
            }else{
                map.tile[x][y].direction = "Right";
            }
        }
        else if(!map.tile[x][y].direction.equals("Right") && ((e.getKeyChar() == 'a') || (e.getKeyChar() == 'A'))){
            if(!start){
                map.tile[x][y].direction = "Left";
            }
        }
        else if(!map.tile[x][y].direction.equals("Bottom") && ((e.getKeyChar() == 'w') || (e.getKeyChar() == 'W'))){
            if(!start){
                map.tile[x][y].direction = "Top";
            }
        }
        else if(!map.tile[x][y].direction.equals("Top") && ((e.getKeyChar() == 's') || (e.getKeyChar() == 'S'))){
            if(!start){
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
