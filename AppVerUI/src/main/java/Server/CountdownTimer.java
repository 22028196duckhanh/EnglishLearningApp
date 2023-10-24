package Server;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CountdownTimer {

    private static final Integer STARTTIME = 300;
    private static int timeSeconds = STARTTIME;
    private static Timeline timeline;

    public static void gettime(Label timer) {
        timer.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));

        // Countdown logic
        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeSeconds--;
                            // update timerLabel
                            timer.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
                            if (timeSeconds <= 0) {
                                timeline.stop();
                            }
                        }));
        timeline.playFromStart();

    }

    public static void resetTime() {
        timeSeconds = STARTTIME;
    }
}
