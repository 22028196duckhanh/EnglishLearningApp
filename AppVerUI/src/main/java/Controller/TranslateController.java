package Controller;

import Server.SpeechToText;
import Server.TextToSpeech;
import Server.TranslatorAPI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TranslateController {

    @FXML
    private TextArea text;
    @FXML
    private Button sound;
    @FXML
    private TextArea translated;
    @FXML
    private Button changeLanguage;
    @FXML
    private Label fromLanguage;
    @FXML
    private Label toLanguage;
    private TranslatorAPI translatorAPI = new TranslatorAPI();
    private boolean isListening = false;
    private SpeechRecognitionService recognitionService;

    private Future<?> taskFuture = null;
    @FXML
    private Image speakerImage = new Image("file:src/main/resources/Utils/images/micro.png");
    @FXML
    private Image convertImage = new Image("file:src/main/resources/Utils/images/convert.png");
    @FXML
    private ImageView speakerIcon = new ImageView(speakerImage);
    @FXML
    private ImageView convertIcon = new ImageView(convertImage);

    public void initialize() {
        TranslatorAPI.setTextArea(translated);
        translated.setEditable(false);
        text.setWrapText(true);
        text.setPromptText("Type here...");
        translated.setWrapText(true);
        recognitionService = new SpeechRecognitionService(fromLanguage);

        sound.setGraphic(speakerIcon);
        changeLanguage.setGraphic(convertIcon);

        sound.setOnAction((ActionEvent actionEvent) -> {
            isListening = !isListening;
            System.out.println(isListening);

            if (isListening) {
                recognitionService.restart();
            } else {
                recognitionService.cancel();
            }
        });

        changeLanguage.setOnAction((ActionEvent actionEvent) -> {
            if (fromLanguage.getText().equals("English")) {
                fromLanguage.setText("Vietnamese");
                toLanguage.setText("English");
                text.setPromptText("Nhập ở đây...");

            } else {
                fromLanguage.setText("English");
                toLanguage.setText("Vietnamese");
                text.setPromptText("Type here...");
            }
            TranslatorAPI.changeLanguage();
        });

        recognitionService.setOnSucceeded(event -> {
            String recognizedText = recognitionService.getValue();
            if (recognizedText != null) {
                if (text.getText().isEmpty()) {
                    text.setText(recognizedText);
                } else {
                    text.setText(text.getText() + " " + recognizedText);
                }
            }
            if (isListening) {
                recognitionService.restart();
            }
        });

        text.textProperty().addListener(new ChangeListener<String>() {
            final ExecutorService threadPool = Executors.newFixedThreadPool(1);

            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                TranslatorAPI.setText(t1);

                if (taskFuture != null && !taskFuture.isDone()) {
                    taskFuture.cancel(true);
                }

                taskFuture = threadPool.submit(translatorAPI);
            }

        });
    }
}

class SpeechRecognitionService extends Service<String> {

    Label fromLanguage;

    SpeechRecognitionService(Label fromLanguage){
        this.fromLanguage = fromLanguage;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                return SpeechToText.speechToText(fromLanguage.getText().substring(0,2).toLowerCase() + '-' + fromLanguage.getText().substring(0,2).toUpperCase());
            }
        };
    }
}