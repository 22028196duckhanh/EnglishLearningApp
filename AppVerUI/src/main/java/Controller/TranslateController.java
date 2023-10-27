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
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class TranslateController {

    @FXML
    private TextArea text;
    @FXML
    private Button sound;
    @FXML
    private TextArea translated;
    @FXML
    private Button translateBtn;
    private TranslatorAPI translatorAPI = new TranslatorAPI();
    private boolean isListening = false;
    private SpeechRecognitionService recognitionService;

    public void initialize() {
        translated.setEditable(false);
        text.setWrapText(true);
        translated.setWrapText(true);
        recognitionService = new SpeechRecognitionService();

        sound.setOnAction((ActionEvent actionEvent) -> {
            isListening = !isListening;
            System.out.println(isListening);

            if (isListening) {
                recognitionService.restart();
            } else {
                recognitionService.cancel();
            }
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
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                Thread thread = new Thread(translatorAPI);
                thread.start();
                TranslatorAPI.setText(t1);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        translated.setText(TranslatorAPI.getTranslated());
                    }
                }, 1500);
            }
        });
    }
}

class SpeechRecognitionService extends Service<String> {
    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                return SpeechToText.speechToText();
            }
        };
    }
}