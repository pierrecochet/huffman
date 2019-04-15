import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Character> chartab = new ArrayList<Character>();
        ArrayList<Letter> lettertab = new ArrayList<Letter>();
        ArrayList<Node> nodetab = new ArrayList<Node>();
        Huffman huff = new Huffman();
        chartab = huff.Readfile();
        lettertab = huff.createLetters(chartab);
        nodetab = huff.initiateNodes(lettertab);
        huff.sortNodes(nodetab);


    }
}
