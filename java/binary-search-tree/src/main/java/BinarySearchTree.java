import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;

class BinarySearchTree<T extends Comparable<T>> {
    private Node<T> root;
    TreeMap<T, Integer> map = new TreeMap<>();

    void insert(T value) {
        map.put(value, map.getOrDefault(value, 0) + 1);
        root = insertRec(root, value);
    }

    private Node<T> insertRec(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }

        if (value.compareTo(node.data) <= 0) {
            node.left = insertRec(node.left, value);
        } else if (value.compareTo(node.data) > 0) {
            node.right = insertRec(node.right, value);
        }

        return node;
    }

    List<T> getAsSortedList() {
        List<T> result = new ArrayList<>();

        for (T key : map.keySet()) {
            int count = map.get(key);
            for (int i = 0; i < count; i++) {
                result.add(key);
            }
        }

        return result;


    }

    List<T> getAsLevelOrderList() {
        List<T> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();

            int count = map.get(current.data);
            for (int i = 0; i < count; i++) {
                result.add(current.data);
            }

            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }

        return result;
    }

    Node<T> getRoot() {
        return root;
    }

    static class Node<T> {
        private final T data;
        private Node<T> left;
        private Node<T> right;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        Node<T> getLeft() {
            return left;
        }

        Node<T> getRight() {
            return right;
        }

        T getData() {
            return data;
        }
    }
}