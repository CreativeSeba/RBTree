package pack;

public class Node {

    Node(int key, int value){
        this.key = key;
        this.value = value;
        this.color=Color.BLACK;
        this.left=null;
        this.right=null;
        this.parent=null;
    }
    Node(int key){
        this.key = key;
    }
    enum Color { RED, BLACK }
    int key;
    int value;
    Color color;
    Node left, right, parent;

    @Override
    public String toString() {
        return ""+key+"";
    }
}