import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.*;
import java.lang.invoke.StringConcatFactory;
import java.lang.reflect.Array;
import java.util.ArrayList; // import the ArrayList class
import java.util.Collections;
import java.util.Comparator;
import java.io.File;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Huffman implements Comparator<Letter> {

    /**
     * Main function to zip that calls all the function in the right order
     * @param file : the unzip file
     */
    public void zip(File file){
        ArrayList<Character> chartab = new ArrayList<Character>();
        ArrayList<Letter> lettertab = new ArrayList<Letter>();
        ArrayList<Node> nodetab = new ArrayList<Node>();
        ArrayList<Node> tree = new ArrayList<Node>();
        chartab = Readfile(file);
        lettertab = createLetters(chartab);
        freqFile(lettertab);
        nodetab = initiateNodes(lettertab);
        sortNodes(nodetab);
        tree = createTree(nodetab);
        parcoursTree(tree.get(tree.size()-1),null);

        zipFile(chartab,lettertab);
    }

    //Snippet to read files char by char from :
    //http://www.java2s.com/Code/Java/File-Input-Output/Readfilecharacterbycharacter.htm

    /**
     * Read a file char by char and split them in a ArrayList
     * @return ArrayList<Character>
     */
    public ArrayList Readfile(File file){
        ArrayList<Character> chartab = new ArrayList<Character>();
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
    //checker le char parcourir le Arraylist<Lettre> checker si label = char (il y a une fonction pour ça) prendre le code
    //huffman puis le mettre dans le fichier

    /**
     * Create a binary string by replacing every letter of the text in the file by his corresponding huffman code
     * send it to the function huffToZip to receive the zip version of the orginal text
     * And then print it in a new ZipFile.
     * @param ArrayList<Character> c : The list of every char in the text (chartab)
     * @param ArrayList<Letter> l
     * @return
     */
    public void zipFile(ArrayList<Character> c, ArrayList<Letter> l){
        String bitsStr="";
        PrintWriter writer = null;
        try {
            File f = new File("src/data/compressedFile.txt");
            writer = new PrintWriter(f, "ISO-8859-1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for(int i=0; i<c.size();i++) {
            if(checklist(l,c.get(i))!=-1){//get the index of the letter corresponding to the char
                bitsStr+=l.get(checklist(l,c.get(i))).getCodeH();//get his huffman code
            }
        }
        writer.print(huffToZip(bitsStr));
        writer.close();
    }

    /**
     * It takes as a parameter the binary string created in the function zipFile.
     * It verifies if the lenght of the string can be divided by 8. If not it adds "0" to the end of the string
     * Then it splits the string in bytes and translate every bytes in his corresponding ASCII character
     * @param str
     * @return the zip version of the text in a string
     */
    public String huffToZip(String str){
        String s ="";
        int size = str.length();
        while(str.length()%8!=0){
            str+=0;
        }//Je prends les bits 8 par 8 et je les convertis en decimal puis de décimale à ASCII en castant en (char)
        for(int index = 0; index < str.length(); index+=8) {
            String temp = str.substring(index, index+8);
            int num = Integer.parseInt(temp,2);
            char letter = (char) num;
            s = s+letter;
        }
        return s;
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
                //System.out.println("char : "+chartab.get(i)+" ");
            }else{
                listletters.get(token).updateFreq();
                //System.out.println("i :"+i);
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
                return j;
            }
        }
        return -1;
    }

    /**
     *Initate the nodes with the given list of Letters by putting them in a list of Nodes
     * @param ArrayList<Letter> l
     * @return The list of nodes : nodeList
     */
    public ArrayList<Node> initiateNodes(ArrayList<Letter> l){
        ArrayList<Node> nodeList = new ArrayList<Node>();
        for(int i=0; i<l.size();i++){
            nodeList.add(new Node(l.get(i),null,null));
        }
        return nodeList;
    }


//snippet from : https://howtodoinjava.com/sort/collections-sort/
    /**
     *Sort the letters in ascii order then write them in a file with their frequency
     * like this :
     * a 3
     * b 2
     * c 45
     * ...
     *
     * @param ArrayList<Letter> l
     * @return nothing it jsut creats the freq file
     */
    public void freqFile(ArrayList<Letter> l){
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
    }

    /** Sort nodes by frequency first, then in ASCII order in the cas where differents nodes have the same frequency
     * @param ArrayList<Node> n
     * @return nothing it just sorts the list
     */
    public void sortNodes(ArrayList<Node> n){
        n.sort(Comparator.comparing((Node n1) -> n1.getLetter().getFreq()).thenComparing(n1 -> n1.getLetter().getLabel()));
    }

    /**
     * Fonction pour créer un l'arbre en liant les noeuds entre au travers des attributs "fils droit" "fils gauche"
     * @param ArrayList<Node> n
     * @return
     */
    public ArrayList<Node> createTree(ArrayList<Node> n){
        ArrayList<Node> templist = (ArrayList) n.clone();
        ArrayList<Node> tree = new ArrayList<Node>();
        int min;
        int minI1;
        int minI2;
        int cmpt = 0;
        Node rm2;
        Node rm1;
        while(templist.size()>1){
            cmpt++;
            min=1000000000;
            minI1=-1;
            minI2=-1;
            rm2=null;
            rm1=null;
            for(int i = 0;i<templist.size();i++){
                if(templist.get(i).getLetter().getFreq()<min){
                    min=templist.get(i).getLetter().getFreq();
                    minI1=i;
                }
            }
            min=1000000000;
            for(int i = 0;i<templist.size();i++){
                if(templist.get(i).getLetter().getFreq()<min && i!=minI1){
                    min=templist.get(i).getLetter().getFreq();
                    minI2=i;
                }
            }

            tree.add(templist.get(minI1));
            tree.add(templist.get(minI2));
            rm1 = templist.get(minI1);
            rm2 = templist.get(minI2);
            templist.add(0,new Node(new Letter(rm1.getLetter().getFreq()+rm2.getLetter().getFreq(),null),rm1,rm2));
            templist.remove(rm1);
            templist.remove(rm2);


        }
        tree.add(templist.get(0));
        return tree;
    }

    /**
     * Takes the root Node of the tree and read it through the left and right child of every node and generate the huffman code
     * for each letter associate to the leaf nodes.
     * Everytime the path to reach the leaves goes to the right, the path string gets plus one "1" and everytime it goes to the left
     * it gets plus one "0"
     * @param ArrayList<Node> n
     * @param nothing it just updates the huffman codes of every letters
     */
    public void parcoursTree(Node n, String path){
        if(n.getRight() == null && n.getLeft()==null){
            n.getLetter().setCodeH(path);
            System.out.println("label : "+n.getLetter().getLabel()+" path : "+n.getLetter().getCodeH());
            return;
        }
        if(path==null){
            parcoursTree(n.getRight(),"1");
            parcoursTree(n.getLeft(),"0");
        }else{
            parcoursTree(n.getRight(),path+"1");
            parcoursTree(n.getLeft(),path+"0");
        }

    }

    /*---------------------------- UNZIP ------------------------------------*/

    /**
     * Main function to unZip that calls all the function in the right order
     * @param f : the zip file
     * @return
     * @throws IOException
     */
    public void unzipFile(File f, File freqFile)throws IOException{
        ArrayList<Letter> lettertab = new ArrayList<Letter>();
        ArrayList<Node> nodetab = new ArrayList<Node>();
        ArrayList<Node> tree = new ArrayList<Node>();
        lettertab=createLetters2(freqFile);
        nodetab = initiateNodes(lettertab);
        sortNodes(nodetab);
        tree = createTree(nodetab);
        parcoursTree(tree.get(tree.size()-1),null);
        String bits = byteToBinary(f);
        String unZipText=huffToChar(bits,lettertab);
        System.out.println("unzip : "+unZipText);
        unZipFile(unZipText);
    }

    /**
     * Write the unzip string in a new file
     * @param str : the unzip text
     */
    public void unZipFile(String str){
        PrintWriter writer = null;
        try {
            File f = new File("src/data/unzipFile.txt");
            writer = new PrintWriter(f, "ISO-8859-1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.print(str);
        writer.close();
    }

    /**
     * Convert the binary string into the original text by converting every huffman code found in the binary string into
     * the corresponding character
     * @param str : binary string of the zip text
     * @param listletters
     * @return newstr : original text in a string
     */
    public String huffToChar(String str, ArrayList<Letter> listletters){
        String templist="";
        String newstr="";
        int size = calculFreq(listletters); //get the number of char of the orginal file
        int tempSize=0;
        for(int i = 0; i < str.length(); i++)//lis la string de bits
        {
            templist+=str.charAt(i);
            for (int j = 0; j < listletters.size(); j++) {
                //check if templist is = to an huffman code
                //check for the spare "0" at the end of the string by comparing the actual number of char to the supposed number = size
                if(templist.equals(listletters.get(j).getCodeH())&&tempSize<size){
                    str.substring(0,templist.length()-1);//remove the already used huffman code
                    newstr+=listletters.get(j).getLabel();
                    templist="";
                    tempSize++;
                }
            }
        }
        return newstr;
    }

    /**
     * Calcul the the total of all the frequencies additionned of the freq file
     * @param ArrayList<Letter>l
     * @return size : the total of all the frequencies additionned
     */
    public int calculFreq(ArrayList<Letter>l){
        int size=0;
        for (int i = 0; i < l.size(); i++) {
            size+=l.get(i).getFreq();
        }
        return size;
    }

    /**
     * Convert the ascii char of the zip file in a string of the corresponding bits.
     * @param f : the zip File
     * @return str : the binary string
     * @throws IOException
     */
    public String byteToBinary(File f) throws IOException{
        byte[] bFile = Files.readAllBytes(f.toPath());
        StringBuilder binary = new StringBuilder();
        String str;
        for (byte b : bFile)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        str = binary.toString();
        return str;
    }

    /**
     * unlike the other createLetter, this function creats the letters from the freq file
     * reads the char gets the frequency -> creats the letter -> add it to a list
     * @return listletters
     */
    public ArrayList createLetters2(File freqFile){
        ArrayList<Letter> listletters = new ArrayList<Letter>();
        BufferedReader reader;
        if (!freqFile.exists()) {
            System.out.println("src/data/freq.txt" + " does not exist.");
            return null;
        }
        if (!(freqFile.isFile() && freqFile.canRead())) {
            System.out.println(freqFile.getName() + " cannot be read from.");
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(freqFile);
            char current;
            char before='~';
            String str;
            boolean reg;
            boolean reg1;
            boolean reg2;
            boolean token= true;
            boolean token1 = true;
            boolean token2 = false;
            boolean declancheur = false;
            char templabel = '~';
            String freq ="";
            while (fis.available() > 0) {
                current = (char) fis.read();
                str = String.valueOf(current);
                reg = Pattern.matches("( )", str);//True or false if space
                reg1 = Pattern.matches("\\n", String.valueOf(before));
                reg2 = Pattern.matches("\\r", String.valueOf(before));
                if (declancheur){//attend la premier itération du while
                    if(token2){//lis char by char la freq d'un character
                        if(Pattern.matches("[0-9]+", String.valueOf(current))){
                            freq+=current;//construit la string de freq
                        }else{

                            listletters.add(new Letter(Integer.parseInt(freq),templabel));
                            token2=false;
                            freq="";
                        }
                    }
                    if(!reg1 && !reg2 && reg && !token2){//si l'on est au niveau d'un espace entre un char et une fréquence
                        templabel=before;
                        token2=true;
                    }
                    if(token){//Si le character \n n'a pas encore été lu
                        if(reg && reg1 && !token2) {
                            templabel=before;
                            token2 = true;
                            token = false;
                        }
                    }
                    if(token1){//Si le character \r n'a pas encore été lu
                        if(reg && reg2 && !token2) {
                            templabel=before;
                            token2 = true;
                            token1=false;
                        }
                    }
                }
                before = current;
                declancheur = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listletters;
    }

    /*---------------------------- UNZIP ------------------------------------*/

    @Override
    public int compare(Letter o1, Letter o2) {
        return 0;
    }
}
