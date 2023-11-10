package Server;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TranslatorAPI implements Runnable{
    private static TextArea translated;
    private static String text = "";
    private static String from = "en";
    private static String to = "vi";
    private static final String apiURL = "https://script.google.com/macros/s/AKfycbwuoarMpriRLadcHsMXF7W5EPE5TqzW9d5FaU6CHTZdQNPSXCdEhv_Unk_S9pWzPgT-jA/exec";
    public static void translate(String langFrom, String langTo) throws IOException {
        // INSERT YOU URL HERE
        String s = "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
                "&target=" + langTo +
                "&source=" + langFrom;
        String urlStr = apiURL + s;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        translated.setText(response.toString());
        in.close();
    }

    public static void changeLanguage() {
        String tmp = from;
        from = to;
        to = tmp;
    }
    public static void setTextArea(TextArea translated){
        TranslatorAPI.translated = translated;
    }

    public static void setText(String text) {
        TranslatorAPI.text = text;
    }

    public static String getTranslated() {
        return TranslatorAPI.translated.getText();
    }

    @Override
    public void run() {
        
        try {
            TranslatorAPI.translate(from, to);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
