package Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;

abstract class Game {
    int score = 0;
    static final Integer STARTTIME = 300;
    Timeline timeline = new Timeline();
    int timeSeconds;

    void getTime(Label timer) {
        timeSeconds = STARTTIME;
        timer.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeSeconds--;
                            timer.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
                            if (timeSeconds <= 0) {
                                timeline.stop();
                                end();
                            }
                        }));
        timeline.playFromStart();
    }
    abstract void end();
    public void stopTimeline(){
        this.timeline.getKeyFrames().clear();
    }
    protected void setScore(){
        score +=10;
    }
}