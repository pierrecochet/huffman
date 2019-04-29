public class Node {
    Node left;
    Node right;
    Letter letter;

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }


    public Node(Letter letter,Node left, Node right){
        this.left = left;
        this.right  = right;
        this.letter = letter;
    }

}
