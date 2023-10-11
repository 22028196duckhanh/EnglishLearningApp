import java.util.*;

public class Trie {
    private final String curWord;
    private boolean wordEnd = false;
    private final Map <Character, Trie> children;

    public Trie() {
        this(null);
    }

    private Trie(String curWord) {
        this.curWord = curWord;
        children = new HashMap<Character, Trie>();
    }

    private void addNode(char c) {
        String tmp;
        if (this.curWord == null) tmp = Character.toString(c);
        else tmp = this.curWord + c;
        children.put(c, new Trie(tmp));
    }

    public void addWord(String wordTarget) {
        Trie node = this;
        for (char c : wordTarget.toCharArray()) {
            if (!node.children.containsKey(c)) node.addNode(c);
            node = node.children.get(c);
        }
        node.wordEnd = true;
    }

    public void deleteWord(String wordTarget) {
        Trie node = this;
        for (char c : wordTarget.toCharArray()) {
            node = node.children.get(c);
        }
        node.wordEnd = false;
    }

    public List<String> autoComplete(String prefix) {
        Trie node = this;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) return null;
            node = node.children.get(c);
        }
        return node.allPrefixes();
    }

    public List<String> allPrefixes() {
        List<String> searchPrefixes = new ArrayList<String>();
        if (this.wordEnd) searchPrefixes.add(this.curWord);
        for (Map.Entry<Character, Trie> entry : children.entrySet()) {
            Trie child = entry.getValue();
            List <String> searchChild = child.allPrefixes();
            searchPrefixes.addAll(searchChild);
        }
        return searchPrefixes;
    }
}

