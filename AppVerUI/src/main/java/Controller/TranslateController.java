package Controller;

import Server.SpeechToText;
import Server.TextToSpeech;
import Server.TranslatorAPI;
import javafx.animation.PauseTransition;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;

import javafx.util.Duration;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TranslateController {

    @FXML
    private TextArea text;
    @FXML
    private Button sound;
    @FXML
    private Button speakFrom;
    @FXML
    private Button speakTo;
    @FXML
    private TextArea translated;
    @FXML
    private Button changeLanguage;
    @FXML
    private Label fromLanguage;
    @FXML
    private Label toLanguage;
    @FXML
    private Label characterCount;
    private TranslatorAPI translatorAPI = new TranslatorAPI();
    private boolean isListening = false;
    private SpeechRecognitionService recognitionService;

    private final int maxCharacters = 2500;

    private Future<?> taskFuture = null;

    PauseTransition pause = new PauseTransition(Duration.millis(500));
    private final ExecutorService threadPool = Executors.newSingleThreadExecutor();

    public void initialize() {
        TranslatorAPI.setTextArea(translated);
        translated.setEditable(false);
        text.setWrapText(true);
        text.setPromptText("Type here...");
        translated.setWrapText(true);
        recognitionService = new SpeechRecognitionService(fromLanguage);


        sound.setOnAction((ActionEvent actionEvent) -> {
            isListening = !isListening;
            if (isListening) {
                sound.setStyle("-fx-background-color: lightblue;");
            } else {
                sound.setStyle("-fx-background-color: transparent;");
            }
            if (isListening) {
                recognitionService.restart();
            } else {
                recognitionService.cancel();
            }
        });

        speakFrom.setOnAction(e -> {
            TextToSpeech.playSoundGoogleTranslate(text.getText(), fromLanguage.getText().substring(0, 2).toLowerCase());
        });

        speakTo.setOnAction(e -> {
            TextToSpeech.playSoundGoogleTranslate(translated.getText(), toLanguage.getText().substring(0, 2).toLowerCase());
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
            translated.setText("");
            TranslatorAPI.setText(text.getText());

            if (taskFuture != null && !taskFuture.isDone()) {
                taskFuture.cancel(true);
            }

            taskFuture = threadPool.submit(translatorAPI);
        });

        recognitionService.setOnSucceeded(event -> {
            String recognizedText = recognitionService.getValue();
            if (recognizedText != null) {
                if (text.getText().isEmpty()) {
                    text.setText(recognizedText);
                } else if (recognizedText.isEmpty()) {
                    text.setText(text.getText() + " " + recognizedText);
                }
            }
            if (isListening) {
                recognitionService.restart();
            }
        });

        text.textProperty().addListener((observable, oldValue, newValue) -> {
            TranslatorAPI.setText(newValue);
            pause.playFromStart();
            int count = newValue.length();
            characterCount.setText("Word Count: " + count + "/2500");
        });

        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= maxCharacters) {
                return change;
            }
            return null;
        });

        text.setTextFormatter(textFormatter);

        if (!MenuController.isLightMode) {
            fromLanguage.getStyleClass().clear();
            toLanguage.getStyleClass().clear();
            text.getStylesheets().removeAll();
            translated.getStylesheets().removeAll();
            changeLanguage.getStylesheets().removeAll();
            sound.getStylesheets().removeAll();
            characterCount.getStyleClass().clear();

            fromLanguage.getStyleClass().add("label-dark");
            toLanguage.getStyleClass().add("label-dark");
            characterCount.getStyleClass().add("label-dark");
            text.getStylesheets().add(Objects.requireNonNull(getClass().getResource
                    ("/Utils/css/darktranslate.css")).toExternalForm());
            translated.getStylesheets().add(Objects.requireNonNull(getClass().getResource
                    ("/Utils/css/darktranslate.css")).toExternalForm());
            changeLanguage.getStylesheets().add(Objects.requireNonNull(getClass().getResource
                    ("/Utils/css/darkbutton.css")).toExternalForm());
            sound.getStylesheets().add(Objects.requireNonNull(getClass().getResource
                    ("/Utils/css/darkbutton.css")).toExternalForm());
            speakFrom.getStylesheets().add(Objects.requireNonNull(getClass().getResource
                    ("/Utils/css/darkbutton.css")).toExternalForm());
            speakTo.getStylesheets().add(Objects.requireNonNull(getClass().getResource
                    ("/Utils/css/darkbutton.css")).toExternalForm());
        }

        pause.setOnFinished(e -> {
            taskFuture = threadPool.submit(translatorAPI);
        });
    }
}

class SpeechRecognitionService extends Service<String> {

    Label fromLanguage;

    SpeechRecognitionService(Label fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                return SpeechToText.speechToText(fromLanguage.getText().substring(0, 2).toLowerCase() + '-' + fromLanguage.getText().substring(0, 2).toUpperCase());
            }
        };
    }
}