package Server;

// com.sun.speech.freetts.Voice;
// com.sun.speech.freetts.VoiceManager;
import javazoom.jl.player.Player;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TextToSpeech {
//    public static void playKevinSpeech(String text) {
//        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
//        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
//        if (voice != null) {
//            voice.allocate();
//            voice.speak(text);
//            voice.deallocate();
//        } else {
//            System.out.println("Something went wrong");
//        }
//    }

    public static void playSoundGoogleTranslate(String text) {
        try {
            String api = "https://translate.google.com/translate_tts?ie=UTF-8&tl=en&client=tw-ob&q="
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
