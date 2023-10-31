package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SpeechToText {
    public static String speechToText() {
        try {
            // Specify the Python script and its arguments (if any)
            String pythonScript = "python";
            String scriptPath = "src/main/resources/Utils/sound/speech.py";

            ProcessBuilder processBuilder = new ProcessBuilder(pythonScript, scriptPath);

            Process process = processBuilder.start();

            // Capture and print the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder recognizedText = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                recognizedText.append(line);
            }

            // Wait for the process to finish
            //int exitCode = process.waitFor();
            //System.out.println("Exited with code " + exitCode);

            // Get the recognized text from the Python script
            return recognizedText.toString();
        } catch (IOException e/*| InterruptedException e*/) {
            e.printStackTrace();
        }
        return "";
    }
}