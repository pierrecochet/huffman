import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList; // import the ArrayList class
import java.util.Collections;
import java.util.Comparator;

public class Huffman implements Comparator<Letter> {
    //Snippet to read files char by char from :
    //http://www.java2s.com/Code/Java/File-Input-Output/Readfilecharacterbycharacter.htm

    /**
     * Read a file char by char and split them in a ArrayList
     * @return ArrayList<Character>
     */
    public ArrayList Readfile(){
        ArrayList<Character> chartab = new ArrayList<Character>();
        File file = new File("src/data/test.txt");
        if (!file.exists()) {
            System.out.println("src/data/test.txt" + " does not exist.");
            return null;
        }
        if (!(file.isFile() && file.canRead())) {
            System.out.println(file.getName() + " cannot be read from.");
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            char current;
            while (fis.available() > 0) {
                current = (char) fis.read();
                chartab.add(current);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chartab;
    }

    /**
     * @param ArrayList<Character> chartab
     *
     * Go through the list of char created from the file earlier updated
     * Each new char met in the for loop a letter is created with the char as a label and a frequency set up to 1
     * That letter is then put in the list listLetters
     * Every time a char already contained in listLetters is met, the frequency of the letter concerned get incremented by 1
     * This works thanks to checklist
     *
     * @return a list of letters of objects Letter corresponding to the chars in the file and their frequency
     */
    public ArrayList createLetters(ArrayList<Character> chartab){
        ArrayList<Letter> listletters = new ArrayList<Letter>();
        int token;
        for (int i = 0; i < chartab.size(); i++) {
            token = checklist(listletters,chartab.get(i));
            if(token==-1) {
                listletters.add(new Letter(1, chartab.get(i)));
                System.out.println("char : "+chartab.get(i)+" ");
            }else{
                listletters.get(token).updateFreq();
                System.out.println("i :"+i);
            }

        }
        return listletters;
    }

    /**
     *
     * @param ArrayList<Letter> l
     * @param char c
     * check if the char is equal to the label of a letter of the list
     * if so it gets the index and return it
     * otherwise it return -1
     * @return -1 or j : index
     */
    public int checklist(ArrayList<Letter> l, char c){
        for(int j = 0; j<l.size();j++){
            if(l.get(j).label==c){
                System.out.println("label checklist = "+c+" --------- token : "+j);
                return j;
            }
        }
        return -1;
    }

    public ArrayList<Node> initiateNodes(ArrayList<Letter> l){
        ArrayList<Node> nodeList = new ArrayList<Node>();
        for(int i=0; i<l.size();i++){
            nodeList.add(new Node(l.get(i),null,null));
        }
        return nodeList;
    }

//snippet from : https://howtodoinjava.com/sort/collections-sort/

    /**
     *
     * @param l
     * @return
     */
    public File freqFile(ArrayList<Letter> l){
        Collections.sort(l, new Comparator<Letter>() {

            @Override
            public int compare(Letter object1, Letter object2) {
                return object1.getLabel().compareTo(object2.getLabel());
            }
        });


        PrintWriter writer = null;
        try {
            writer = new PrintWriter("src/data/freq.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for(int i=0; i<l.size();i++) {
            writer.println(l.get(i).getLabel()+" "+l.get(i).getFreq());
        }
        writer.close();
        return null;
    }

    /**
     *
     * @param n
     * @return
     */
    public ArrayList<Node> sortNodes(ArrayList<Node> n){
        Collections.sort(n, new Comparator<Node>() {

            private Node object1;
            private Node object2;
            private Node o1;
            private Node o2;

            @Override
            public int compare(Node object1, Node object2) {
                return Integer.compare(object1.getLetter().getFreq(),object2.getLetter().getFreq());
            }
        });
        for(int i=0;i<n.size();i++){
            System.out.println(n.get(i).getLetter().getLabel()+" "+n.get(i).getLetter().getFreq());
        }

        return null;
    }

    @Override
    public int compare(Letter o1, Letter o2) {
        return 0;
    }
}
