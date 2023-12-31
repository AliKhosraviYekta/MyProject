package com.example.monitoring;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class HelloController {

    @FXML
    private AnchorPane ancp;

    @FXML
    private LineChart<Number, Number> chartline;

    @FXML
    private Button cpubtn;

    @FXML
    private Label labelOne;
    @FXML
    private Button rambtn;

    @FXML
    private Button ssdbtn;
    private AnimationTimer cpuTimer;
    private AnimationTimer ramTimer;
    private AnimationTimer ssdAnimationTimer;
    private XYChart.Series<Number,Number> cpuSeries;
    private static double cpuFrequencyMultiplier = 1e-6;
    private final double ramFrequencyMultiplier = 1e-6;
    private XYChart.Series<Number, Number> ramSeries;
    private Ssdinfo ssdinfo = new Ssdinfo();
    private XYChart.Series<Number, Number> ssdReadsSeries;
    private XYChart.Series<Number, Number> ssdWritesSeries;

    private Timeline ssdTimeline;
    @FXML
    void btnCPU(ActionEvent event) {
        chartline.getData().clear();
        chartline.getYAxis().setLabel("مگاهرتز");
        labelOne.setText("");
        labelOne.setText(new Cpuinfo().showInfo());
        cpuSeries = new XYChart.Series<>();
        cpuSeries.setName("Cpu Frequency");
        chartline.getData().add(cpuSeries);
        Cpuinfo cpuinfo = new Cpuinfo();
        long startTime = System.currentTimeMillis();
        cpuTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double cpuFrequency = cpuinfo.cpuFreqeuncyShow();
                double currentTime = System.currentTimeMillis();
                double timeElapsed = currentTime - startTime;
                cpuSeries.getData().add(new XYChart.Data<>(System.currentTimeMillis(), cpuFrequency));
            }
        };
        cpuTimer.start();
        Timeline timeline = new Timeline();
        Duration duration = Duration.millis(1000);
        KeyFrame keyFrame = new KeyFrame(duration, e -> {
            double cpuFrequency = cpuinfo.showFrequency();
            double currentTime = System.currentTimeMillis();
            double timeElapsed = currentTime - startTime;  // محاسبه زمان گذرده
            cpuSeries.getData().add(new XYChart.Data<>(System.currentTimeMillis(), cpuFrequency));
        });
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
    @FXML
    void btnRAM(ActionEvent event) {
        chartline.getData().clear();
        labelOne.setText("");
        labelOne.setText(new Raminfo().showInfo());
        ramSeries = new XYChart.Series<>();
        ramSeries.setName("Ram Frequency");
        chartline.getData().add(ramSeries);
        ramTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double cpuFrequency = new Raminfo().showFrequency();
                ramSeries.getData().add(new XYChart.Data<>(System.currentTimeMillis(),
                        cpuFrequency * ramFrequencyMultiplier));
            }
        };
        ramTimer.start();
        Timeline timeline = new Timeline();
        Duration duration = Duration.millis(1000);
        KeyFrame keyFrame = new KeyFrame(duration, e -> {
            double ramFrequency = new Raminfo().showFrequency();
            ramSeries.getData().add(new XYChart.Data<>(System.currentTimeMillis(), ramFrequency));
        });
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }



    @FXML
    void btnSSD(ActionEvent event) {
        chartline.getData().clear();
        chartline.getYAxis().setLabel("مگابایت");
        labelOne.setText("");
        Ssdinfo ssdinfo = new Ssdinfo();
        labelOne.setText(ssdinfo.showInfo());
        ssdReadsSeries = new XYChart.Series<>();
        ssdReadsSeries.setName("SSD Reads");
        chartline.getData().add(ssdReadsSeries);
        ssdWritesSeries = new XYChart.Series<>();
        ssdWritesSeries.setName("SSD Writes");
        chartline.getData().add(ssdWritesSeries);
        ssdAnimationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long ssdReads = ssdinfo.getSsdReads();
                ssdReadsSeries.getData().add(new XYChart.Data<>(System.currentTimeMillis(), ssdReads));
                long ssdWrites = ssdinfo.getSsdWrites();
                ssdWritesSeries.getData().add(new XYChart.Data<>(System.currentTimeMillis(), ssdWrites));
                long currentTime = System.currentTimeMillis();
                for (XYChart.Data<Number, Number> data : ssdReadsSeries.getData()) {
                    if (currentTime - data.getXValue().longValue() > 1000) {
                        ssdReadsSeries.getData().remove(data);
                        break;
                    }
                }
                for (XYChart.Data<Number, Number> data : ssdWritesSeries.getData()) {
                    if (currentTime - data.getXValue().longValue() > 1000) {
                        ssdWritesSeries.getData().remove(data);
                        break;
                    }
                }
            }
        };
        ssdAnimationTimer.start();
        Timeline ssdTimeline = new Timeline();
        Duration duration = Duration.seconds(1);
        KeyFrame keyFrame = new KeyFrame(duration, e -> {
            long ssdReads = ssdinfo.getSsdReads();
            ssdReadsSeries.getData().add(new XYChart.Data<>(System.currentTimeMillis(), ssdReads));
            long ssdWrites = ssdinfo.getSsdWrites();
            ssdWritesSeries.getData().add(new XYChart.Data<>(System.currentTimeMillis(), ssdWrites));
            long currentTime = System.currentTimeMillis();
            for (XYChart.Data<Number, Number> data : ssdReadsSeries.getData()) {
                if (currentTime - data.getXValue().longValue() > 1000) {
                    ssdReadsSeries.getData().remove(data);
                    break;
                }
            }
            for (XYChart.Data<Number, Number> data : ssdWritesSeries.getData()) {
                if (currentTime - data.getXValue().longValue() > 1000) {
                    ssdWritesSeries.getData().remove(data);
                    break;
                }
            }
        });
        ssdTimeline.getKeyFrames().add(keyFrame);
        ssdTimeline.setCycleCount(Animation.INDEFINITE);
        ssdTimeline.play();
    }
    @FXML
    void infoCpu(MouseEvent event) {

    }

    @FXML
    void infoRam(MouseEvent event) {

    }

    @FXML
    void infoSsd(MouseEvent event) {

    }

}