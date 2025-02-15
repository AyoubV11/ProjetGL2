package com.menu;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Chrono {
    private int seconds;
    private Timeline timeline;
    private Label timeLabel;

    public Chrono(Label label) {
        this.timeLabel = label;
        this.seconds = 0;
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            seconds++;
            timeLabel.setText("TEMPS : " + formatTime(seconds));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }


    public void start() {
        timeline.play();
    }


    public void stop() {
        timeline.stop();
    }


    public void reset() {
        timeline.stop();
        seconds = 0;
        timeLabel.setText("TEMPS : 00:00:00");
        timeline.play();
    }


    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}
