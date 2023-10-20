package Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sentence {
    private List<String> cmp = new ArrayList<>();
    private int size;
    private String answer;

    public Sentence() {

    }

    public List<String> getCmp() {
        return this.cmp;
    }

    public void setCmp(List<String> cmp) {
        Collections.shuffle(cmp);
        this.cmp.addAll(cmp);
        this.size = cmp.size();
    }

    public int getSize() {
        return size;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean check(String check) {
        System.out.println(answer);
        return check.equals(answer);
    }

    public void showSentence() {
        String ques = String.join(" | ", cmp);
        System.out.println(ques);
        System.out.println(answer);
    }
}
