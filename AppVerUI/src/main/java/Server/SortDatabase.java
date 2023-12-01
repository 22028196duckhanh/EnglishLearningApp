package Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SortDatabase {
    private static final List<Sentence> data = new ArrayList<>();
    private static final String path = "src/main/resources/Utils/data/sortdata.txt";

    private static final int NUMS_OF_QUES = 285;
    public static void getData() {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    line = line.substring(0, 1).toUpperCase() + line.substring(1);
                }
                String[] tmp = line.split("/");
                Sentence sentence = new Sentence();
                List<String> cmp = new ArrayList<>(List.of(tmp));
                sentence.setCmp(cmp);
                sentence.setAnswer(line);
                data.add(sentence);
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("An error occur with file: " + e);
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public void showData() {
        for (int i = 0; i < data.size() ; i++) {
            System.out.print(i + ". ");
            data.get(i).showSentence();
        }
    }

    public static Sentence getRdSentence() {
        return data.get(new Random().nextInt(NUMS_OF_QUES));
    }
}
