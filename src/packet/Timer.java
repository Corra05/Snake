package packet;

public class Timer implements Runnable {
    int sec = 0;
    int min = 0;
    Clock clock;
    public Timer(Clock clock){
        this.clock = clock;
    }

    @Override
    public void run() {
        while(true){
            sec++;

            if (!(sec < 60)) {
                sec = 0;
                min++;
            }

            if (sec < 10) {
                if (min < 10) {
                    clock.player.map.timer.setText("0" + min + ":0" + sec);
                } else {
                    clock.player.map.timer.setText(min + ":0" + sec);
                }
            } else {
                if (min < 10) {
                    clock.player.map.timer.setText("0" + min + ":" + sec);
                } else {
                    clock.player.map.timer.setText(min + ":" + sec);
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(!clock.player.map.appleSpawned && !clock.loop){
                clock.loop = true;
                SpawnApple();
            }
        }
    }

    private void SpawnApple() {
        int X;
        int Y;

        do{
            X = clock.rand.nextInt(1,clock.player.map.dimension - 1);
            Y = clock.rand.nextInt(1,clock.player.map.dimension - 1);
        }while (clock.player.map.tile[X][Y].hasSnake);

        clock.player.map.appleSpawned = true;

        clock.player.map.tile[X][Y].hasApple = true;
        clock.player.map.tile[X][Y].setIcon(clock.player.map.apple);

        clock.loop = false;
    }
}
