class DoublyLinkedList<T> {
    private Element<T> head;

    void push(T value) {
        if (head == null) {
            head = new Element<>(value, null, null);
        } else {
            Element<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Element<>(value, current, null);
        }
    }

    T pop() {
        if (head == null) {
            throw new UnsupportedOperationException("Please implement the DoublyLinkedList.pop() method.");
        } else {
            Element<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            T value = current.value;
            if (current.prev != null) {
                current.prev.next = null;
            } else {
                head = null;
            }
            return value;
        }
    }

    void unshift(T value) {
        if (head == null) {
            head = new Element<>(value, null, null);
        } else {
            Element<T> newHead = new Element<>(value, null, head);
            head.prev = newHead;
            head = newHead;
        }
    }

    T shift() {
        if (head == null) {
            throw new UnsupportedOperationException("Please implement the DoublyLinkedList.shift() method.");
        } else {
            T value = head.value;
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
            return value;
        }
    }

    private static final class Element<T> {
        private final T value;
        private Element<T> prev;
        private Element<T> next;

        Element(T value, Element<T> prev, Element<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
