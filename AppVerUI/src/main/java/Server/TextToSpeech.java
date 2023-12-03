package Server;

import javazoom.jl.player.Player;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TextToSpeech {
    public static void playSoundGoogleTranslate(String text, String language) {
        try {
            if(text.isEmpty()) {
                return;
            }
            String api = "https://translate.google.com/translate_tts?ie=UTF-8&tl="+language+"&client=tw-ob&q="
                            + URLEncoder.encode(text, StandardCharsets.UTF_8);
            URL url = new URL(api);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream audio = con.getInputStream();
            Player player = new Player(audio);
            player.play();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in getting voices");
        }
    }
}
