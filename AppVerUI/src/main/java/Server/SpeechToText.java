package Server;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SpeechToText {
    public static String speechToText(String language) {
        try {
            String pythonScript = "python";
            String scriptPath = "C:\\Users\\Admin\\Downloads\\speech.py";

            ProcessBuilder processBuilder = new ProcessBuilder(pythonScript, scriptPath);

            Process process = processBuilder.start();

            OutputStream outputStream = process.getOutputStream();
            outputStream.write(language.getBytes());
            outputStream.flush();
            outputStream.close();

            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            StringBuilder recognizedText = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                recognizedText.append(line);
            }

            byte[] decodedBytes = Base64.getDecoder().decode(recognizedText.toString());

            return new String(decodedBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}