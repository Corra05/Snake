package packet;

public class Clock implements Runnable{
    int sec = 0;
    int min = 0;

    Map map;
    public Clock(Map map){
        this.map = map;
    }

    @Override
    public void run() {
        while(true){
            SnakeIcons();

            sec++;

            if(!(sec < 60)){
                sec = 0;
                min++;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(sec < 10){
                if(min < 10){
                    map.timer.setText("0" + min + ":0" + sec);
                }else{
                    map.timer.setText(min + ":0" + sec);
                }
            }else{
                if(min < 10){
                    map.timer.setText("0" + min + ":" + sec);
                }else{
                    map.timer.setText(min + ":" + sec);
                }
            }
        }
    }

    public void SnakeIcons(){
        if(map.direction.equals("Right")){
            map.snakeCoords
        } else if (map.direction.equals("Left")) {

        }else if (map.direction.equals("Up")) {

        }else if (map.direction.equals("Down")) {

        }
    }
}
