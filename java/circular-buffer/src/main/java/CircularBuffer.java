import java.util.ArrayList;

class CircularBuffer<T> {
    private final int size;
    private int oldestHead = 0;
    private int newestHead = 0;
    private final ArrayList<T> buffer;
    private final ArrayList<Boolean> used;


    CircularBuffer(final int size) {
        this.size = size;
        this.oldestHead = 0;
        this.newestHead = 0;
        buffer = new ArrayList<>(size);
        used = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            buffer.add(null);
            used.add(false);
        }
    }


    T read() throws BufferIOException {
        int nextUsedIndex = getNextUsedIndex();
        if (nextUsedIndex < 0) {
            throw new BufferIOException("Tried to read from empty buffer");
        }
        T saved = buffer.get(nextUsedIndex);
        used.set(nextUsedIndex, false);
        oldestHead = (nextUsedIndex + 1) % size;
        return saved;
    }


    void write(T data) throws BufferIOException {
        if (used.get(newestHead)) {
            throw new BufferIOException("Tried to write to full buffer");
        }
        if (data == null) {
            throw new BufferIOException("Cannot write null data");
        }
        buffer.set(newestHead, data);
        used.set(newestHead, true);
        newestHead = (newestHead + 1) % size;
        oldestHead = newestHead;
    }


    void overwrite(T data) {
        buffer.set(newestHead, data);
        used.set(newestHead, true);
        newestHead = (newestHead + 1) % size;
        oldestHead = newestHead;
    }


    void clear() {
        for (int i = 0; i < size; i++) {
            buffer.set(i, null);
            used.set(i, false);
        }
    }


    private int getNextUsedIndex() {
        for (int i = oldestHead; i < oldestHead + size; i = i + 1) {
            if (used.get(i % size)) {
                return i % size;
            }
        }
        return -1;
    }
}