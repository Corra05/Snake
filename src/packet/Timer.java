package packet;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Timer implements Runnable {
    int var;
    int sec = 0;
    int min = 0;
    Clock clock;

    public Timer(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void run() {
        while (true) {
            //Effetto audio movimento
            if(!clock.gameOver){
                try {
                    File file = new File("movingSound.wav"); // Inserire il percorso del file audio clic
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(file));
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

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

                for (int i = 1; i < clock.player.map.dimension - 1; i++) {
                    var = 0;
                    for (int j = 1; j < clock.player.map.dimension - 1; j++) {
                        if (clock.player.map.tile[i][j].hasApple) {
                            var++;
                        }
                    }
                }

                if (var > 0) {
                    clock.loop = true;
                } else {
                    clock.loop = false;
                }

                if (!clock.player.map.appleSpawned && !clock.loop) {
                    SpawnApple();
                }
            }
        }
    }

    private void SpawnApple() {
        int X;
        int Y;

        do {
            X = clock.rand.nextInt(1, clock.player.map.dimension - 1);
            Y = clock.rand.nextInt(1, clock.player.map.dimension - 1);
        } while (clock.player.map.tile[X][Y].hasSnake);

        clock.player.map.appleSpawned = true;

        clock.player.map.tile[X][Y].hasApple = true;
        clock.player.map.tile[X][Y].setIcon(clock.player.map.apple);
    }
}
