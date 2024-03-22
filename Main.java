package pack;

public class Main{
    public static void main(String[] args) {
        RBTree tree = new RBTree();
        tree.add(4, 6);
        tree.add(17, 6);
        tree.add(1, 6);
        tree.add(24, 6);
        tree.add(15, 6);
        tree.add(2, 6);
        tree.remove(4);
        tree.showTree();
        tree.get(2);
        tree.height();
    }
}