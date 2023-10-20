package Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SortData {
    private static List<Sentence> data = new ArrayList<>();
    private static final String path1 = "src/main/resources/Utils/data/sapxepcau.txt";
    private static final String path2 = "src/main/resources/Utils/data/key.txt";
    public void getData() {
        try {
            FileReader fileReader1 = new FileReader(path1);
            BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
            FileReader fileReader2 = new FileReader(path2);
            BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
            String line;
            while ((line = bufferedReader1.readLine()) != null) {
                String[] tmp = line.split("/");
                for (int i = 0 ;i < tmp.length; i++) {
                    tmp[i] = tmp[i].trim();
                }
                Sentence sentence = new Sentence();
                sentence.setCmp(tmp);
                data.add(sentence);
            }
            int i = 0;
            while ((line = bufferedReader2.readLine()) != null) {
                data.get(i).setAnswer(line);
                i++;
            }
            bufferedReader1.close();
            bufferedReader2.close();
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
        return data.get(new Random().nextInt(81));
    }
}
