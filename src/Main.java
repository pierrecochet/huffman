import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args)throws IOException {
        Huffman huff = new Huffman();
        //zip
        File file = new File("src/data/test.txt");
        huff.zip(file);
        //unzip
        File f1 = new File("src/data/compressedFile.txt");
        File freqFile = new File("src/data/freq.txt");

        huff.unzipFile(f1,freqFile);




    }
}
