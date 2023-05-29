package packet;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerListener implements KeyListener {
    boolean start = true;
    Map map;
    Thread clock;

    public PlayerListener(Map map){
        this.map = map;
        System.out.println("a");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if((e.getKeyCode() == KeyEvent.VK_RIGHT) || (e.getKeyChar() == 'd') || (e.getKeyChar() == 'D')){
            if(start){
                clock = new Thread(new Clock(this));
                clock.start();

                start = false;
            }else{

            }
        }
        else if((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyChar() == 'a') || (e.getKeyChar() == 'A')){
            if(!start){

            }
        }
        else if((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyChar() == 'w') || (e.getKeyChar() == 'W')){
            if(start){

            }
        }
        else if((e.getKeyCode() == KeyEvent.VK_DOWN) || (e.getKeyChar() == 'S') || (e.getKeyChar() == 'S')){
            if(!start){

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
