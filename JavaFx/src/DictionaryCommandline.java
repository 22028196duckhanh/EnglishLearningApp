public class DictionaryCommandline {

    public void showAllWord(Dictionary dictionary) {
        dictionary.sort(new SortByWord());
        System.out.printf("%-5s| %-15s| %s\n","No","English","Vietnamese");
        int idx = 1;
        for (Word word : dictionary) {
            System.out.printf("%-5d| %-15s| %s\n", idx, word.getWordTarget(), word.getWordExplain());
            idx++;
        }
    }
}
