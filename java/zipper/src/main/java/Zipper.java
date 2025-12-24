
public class Zipper {


    public Zipper up;

    public Zipper left;

    public Zipper right;

    private int value;


    public Zipper(int val) {
        value = val;
    }

    public BinaryTree toTree() {
        return new BinaryTree(getRoot());
    }


    public int getValue() {
        return value;
    }


    public void setLeft(Zipper leftChild) {
        if (leftChild != null) {
            leftChild.up = this;
        }
        left = leftChild;
    }


    public void setRight(Zipper rightChild) {
        if (rightChild != null) {
            rightChild.up = this;
        }
        right = rightChild;
    }

    public void setValue(int val) {
        value = val;
    }

    private Zipper getRoot() {
        Zipper highest = this;
        while (highest.up != null) {
            highest = highest.up;
        }
        return highest;
    }
}


record BinaryTree(Zipper root) {

    public BinaryTree(int value) {
        this(new Zipper(value));
    }

    public String printTree() {
        return "value: " + root.getValue() +
                ", left: " +
                (root.left == null ? "null" : "{ " + new BinaryTree(root.left).printTree() + " }") +
                ", right: " +
                (root.right == null ? "null" : "{ " + new BinaryTree(root.right).printTree() + " }");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryTree(Zipper root1)) {
            return this.root.equals(root1);
        }
        return false;
    }
}