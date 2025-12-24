import java.lang.reflect.Array;
import java.util.NoSuchElementException;

class SimpleLinkedList<T> {

    private static class Node<T> {
        final T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> head;
    private int size;

    SimpleLinkedList() {
        this.head = null;
        this.size = 0;
    }


    SimpleLinkedList(T[] values) {
        this();
        if (values != null) {
            for (T value : values) {
                push(value);
            }
        }
    }

    void push(T value) {
        Node<T> newNode = new Node<>(value);
        newNode.next = this.head;
        this.head = newNode;
        this.size++;
    }

    T pop() {
        if (head == null) {
            throw new NoSuchElementException("Cannot pop from an empty list.");
        }
        T value = head.data;
        this.head = head.next;
        this.size--;
        return value;
    }

    void reverse() {
        Node<T> prev = null;
        Node<T> current = this.head;
        Node<T> nextTemp;

        while (current != null) {
            nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }
        this.head = prev;
    }

    T[] asArray(Class<T> clazz) {
        if (size == 0) {
            return (T[]) Array.newInstance(clazz, 0);
        }

        T[] array = (T[]) Array.newInstance(clazz, size);
        Node<T> current = head;
        int index = 0;

        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }
        return array;
    }

    int size() {
        return this.size;
    }
}