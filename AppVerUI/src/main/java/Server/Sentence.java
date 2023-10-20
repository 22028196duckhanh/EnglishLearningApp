package Server;

public class Sentence {
    private String[] cmp;
    private int size;
    private String answer;

    public Sentence() {

    }

    public String[] getCmp() {
        return cmp;
    }

    public void setCmp(String[] cmp) {
        this.cmp = cmp;
        this.size = cmp.length;
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
