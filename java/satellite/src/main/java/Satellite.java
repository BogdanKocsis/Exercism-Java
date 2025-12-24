import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Satellite {
    public Tree treeFromTraversals(List<Character> preorder, List<Character> inorder) {
        if (preorder == null || inorder == null || preorder.size() != inorder.size()) {
            throw new IllegalArgumentException("traversals must have the same length");
        }

        // Build index map for inorder positions and check for duplicates
        Map<Character, Integer> inIndex = getCharacterIntegerMap(preorder, inorder);

        Index pi = new Index();
        Node root = build(preorder, 0, inorder.size() - 1, inIndex, pi);
        return new Tree(root);
    }

    private static Map<Character, Integer> getCharacterIntegerMap(List<Character> preorder, List<Character> inorder) {
        Map<Character, Integer> inIndex = new HashMap<>();
        for (int i = 0; i < inorder.size(); i++) {
            if (inIndex.containsKey(inorder.get(i))) {
                throw new IllegalArgumentException("traversals must contain unique items");
            }
            inIndex.put(inorder.get(i), i);
        }

        // Verify that preorder and inorder contain the same elements
        for (Character ch : preorder) {
            if (!inIndex.containsKey(ch)) {
                throw new IllegalArgumentException("traversals must have the same elements");
            }
        }
        return inIndex;
    }

    private static class Index {
        int i = 0;
    }

    private Node build(List<Character> pre, int inL, int inR,
                       Map<Character, Integer> inIndex, Index pi) {
        if (inL > inR) return null;

        char val = pre.get(pi.i++);          // current root from preorder
        int mid = inIndex.get(val);          // root position in inorder
        Node node = new Node(val);

        // Build left subtree first (preorder: root, left, right)
        Node leftChild = build(pre, inL, mid - 1, inIndex, pi);
        Node rightChild = build(pre, mid + 1, inR, inIndex, pi);

        // Set children using direct field access
        node.left = leftChild;
        node.right = rightChild;

        return node;
    }
}