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
            System.out.println("c");
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

            moveSnake();
            System.out.println("Ciclo completato");
        }
    }

    public void SnakeIcons(){
        for(int i = 0; i < player.map.snakeCoords.size(); i++){
            str =  player.map.snakeCoords.elementAt(i);

            String[] coordinates = str.split(" ");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);

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

    public void moveSnake(){
        for(int i = 0; i < player.map.snakeCoords.size(); i++){
            str =  player.map.snakeCoords.elementAt(i);

            String[] coordinates = str.split(" ");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);

            if(player.map.tile[x][y].direction.equals("Right")){
                try{
                    player.map.tile[x][y].direction = null;
                    player.map.tile[x][y + 1].direction = "Right";

                    player.map.snakeCoords.set(i, String.valueOf(x) + " " + String.valueOf(y + 1));
                }catch (Exception e){
                    System.out.println("Partita persa 1");
                    System.exit(0);
                }

                if(i == player.map.snakeCoords.size() - 1){
                    player.map.tile[x][y].setIcon(null);
                }
            }
            else if(player.map.tile[x][y].direction.equals("Left")){
                try{
                    player.map.tile[x][y].direction = null;
                    player.map.tile[x][y - 1].direction = "Left";

                    player.map.snakeCoords.set(i, String.valueOf(x) + " " + String.valueOf(y - 1));

                    System.out.println(player.map.snakeCoords.elementAt(i));
                }catch (Exception e){
                    System.out.println("Partita persa 2");
                    System.exit(0);
                }

                if(i == player.map.snakeCoords.size()){
                    player.map.tile[x][y].setIcon(null);
                }
            }
            else if(player.map.tile[x][y].direction.equals("Top")){
                try{
                    player.map.tile[x][y].direction = null;
                    player.map.tile[x - 1][y].direction = "Top";

                    player.map.snakeCoords.set(i, String.valueOf(x - 1) + " " + String.valueOf(y));

                    if(player.map.tile[x][y].prevDirection.equals("Right")){
                        player.map.tile[x][y - 1].direction = null;
                        player.map.tile[x][y - 1].prevDirection = null;

                        player.map.tile[x + 1][y].direction = "Top";
                    }

                    str =  player.map.snakeCoords.elementAt(i + 1);

                    coordinates = str.split(" ");
                    int x1 = Integer.parseInt(coordinates[0]);
                    int y1 = Integer.parseInt(coordinates[1]);

                    player.map.tile[x1][y1 + 1].direction = "Top";

                    player.map.tile[x][y].prevDirection = null;
                }catch (Exception e){
                    System.out.println(e);
                }

                if(i == player.map.snakeCoords.size()){
                    player.map.tile[x][y].setIcon(null);
                }
            }
            else if(player.map.tile[x][y].direction.equals("Bottom")){
                try{
                    player.map.tile[x][y].direction = null;
                    player.map.tile[x + 1][y].direction = "Bottom";

                    player.map.snakeCoords.set(i, String.valueOf(x + 1) + " " + String.valueOf(y));
                }catch (Exception e){
                    System.out.println("Partita persa 4");
                    System.exit(0);
                }

                if(i == player.map.snakeCoords.size()){
                    player.map.tile[x][y].setIcon(null);
                }
            }
        }
    }
}
