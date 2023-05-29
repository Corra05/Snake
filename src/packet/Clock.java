package packet;

public class Clock implements Runnable{
    int sec = 0;
    int min = 0;
    String str;

    PlayerListener player;

    public Clock(PlayerListener player){
        this.player = player;
    }

    @Override
    public void run() {
        while(true){
            SnakeIcons();
            moveSnake();

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
                    player.map.timer.setText("0" + min + ":0" + sec);
                }else{
                    player.map.timer.setText(min + ":0" + sec);
                }
            }else{
                if(min < 10){
                    player.map.timer.setText("0" + min + ":" + sec);
                }else{
                    player.map.timer.setText(min + ":" + sec);
                }
            }
        }
    }

    public void moveSnake(){

    }

    public void SnakeIcons(){
        for(int i = 0; i < player.map.snakeCoords.size(); i++){
            str =  player.map.snakeCoords.elementAt(i);

            int x = str.charAt(0) - 48;
            int y = str.charAt(2) - 48;

            if(player.map.tile[x][y].direction.equals("Right")){
                if(i == 0){
                    player.map.tile[x][y].setIcon(player.map.snakeHeadRight);
                }
                else if(i == player.map.snakeCoords.size() - 1){
                    player.map.tile[x][y].setIcon(player.map.snakeTailRight);
                }else{
                    player.map.tile[x][y].setIcon(player.map.snakeBodyHorizontal);
                }
            }
            else if(player.map.tile[x][y].direction.equals("Left")){
                if(i == 0){
                    player.map.tile[x][y].setIcon(player.map.snakeHeadLeft);
                }
                else if(i == player.map.snakeCoords.size() - 1){
                    player.map.tile[x][y].setIcon(player.map.snakeTailLeft);
                }else{
                    player.map.tile[x][y].setIcon(player.map.snakeBodyHorizontal);
                }
            }
            else if(player.map.tile[x][y].direction.equals("Top")){
                if(i == 0){
                    player.map.tile[x][y].setIcon(player.map.snakeHeadTop);
                }
                else if(i == player.map.snakeCoords.size() - 1){
                    player.map.tile[x][y].setIcon(player.map.snakeTailTop);
                }else{
                    player.map.tile[x][y].setIcon(player.map.snakeBodyVertical);
                }
            }
            else if(player.map.tile[x][y].direction.equals("Bottom")){
                if(i == 0){
                    player.map.tile[x][y].setIcon(player.map.snakeHeadBottom);
                }
                else if(i == player.map.snakeCoords.size() - 1){
                    player.map.tile[x][y].setIcon(player.map.snakeTailBottom);
                }else{
                    player.map.tile[x][y].setIcon(player.map.snakeBodyVertical);
                }
            }

        }
    }
}
