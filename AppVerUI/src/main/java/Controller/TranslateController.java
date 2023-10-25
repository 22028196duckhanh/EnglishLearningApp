package Controller;

import Server.SpeechToText;
import Server.TextToSpeech;
import Server.TranslatorAPI;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class TranslateController {

    @FXML
    private TextArea text;
    @FXML
    private Button sound;
    @FXML
    private TextArea translated;
    @FXML
    private Button translateBtn;

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
        translateBtn.setOnAction(ActionEvent ->{
            try {
                translated.setText(TranslatorAPI.translate("en","vi",text.getText()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        recognitionService.setOnSucceeded(event -> {
            String recognizedText = recognitionService.getValue();
            if (recognizedText != null) {
                /*if (recognizedText.equals(".....")) {
                    return;
                }*/
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