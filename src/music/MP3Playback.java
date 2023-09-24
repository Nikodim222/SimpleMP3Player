/**
 * MP3Playback
 * *************************
 * This class is responsible for playing music through
 * a file such as an MP3 one. It uses JavaFX. Thus it
 * requires Java 8+.
 * 
 * @author A. V. Efremov, 2023/09/22
 */

package music;

import java.io.File;
import java.nio.file.Paths;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class MP3Playback {
    private String audioFileURI;
    private MediaPlayer mp3Player;

    public MP3Playback(final String audioFile) {
        this.doSetFile(audioFile);
    }

    private void play() {
        // com.sun.javafx.application.PlatformImpl.startup(() -> {}); // https://www.baeldung.com/java-play-sound
        try {
            Platform.startup(() -> {});
        }
        catch (Exception e) {}
        Media media = new Media(this.audioFileURI);
        mp3Player = new MediaPlayer(media);
        System.out.println("Playing \"" + audioFileURI + "\"...");
        mp3Player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        });
        mp3Player.play();
    }

    public void stop() {
        if (mp3Player != null) {
            mp3Player.stop();
        }
    }

    public void setFile(final String audioFile) {
        doSetFile(audioFile);
    }

    private void doSetFile(final String audioFile) {
        audioFileURI = Paths.get(audioFile).toUri().toString();
        File file = new File(audioFile);
        if (file.exists() && file.canRead()) {
            if (mp3Player != null) {
                stop();
                mp3Player = null;
            }
            play();
        }
    }

    public void waitEndOfPlayback() throws InterruptedException {
        if (mp3Player != null) {
            System.out.println("Awaiting the end of the playback...");
            Status st = mp3Player.getStatus();
            if (st == MediaPlayer.Status.UNKNOWN || st == MediaPlayer.Status.PLAYING || st == MediaPlayer.Status.READY) {
                while (st != MediaPlayer.Status.STOPPED) {
                    st = mp3Player.getStatus();
                    Thread.sleep(1 * 1000);
                }
            }
            // com.sun.javafx.application.PlatformImpl.exit();
            // Platform.exit();
            System.out.println("The playback has stopped.");
        }
    }

    public long getDuration() throws InterruptedException {
        Thread.sleep(1 * 1000);
        if (mp3Player != null) {
            return (mp3Player.getStatus() != MediaPlayer.Status.PLAYING) ? 0 : (long) mp3Player.getTotalDuration().toMillis();
        } else {
            return 0;
        }
    }

}
