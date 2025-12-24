import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class CustomSet<T> {

    private final Set<T> elements;

    CustomSet() {
        this.elements = new HashSet<>();
    }

    CustomSet(Collection<T> data) {
        this.elements = new HashSet<>(data);
    }

    boolean isEmpty() {
        return elements.isEmpty();
    }

    boolean contains(T element) {
        for (T item : elements) {
            if (item.equals(element))
                return true;
        }
        return false;
    }

    boolean isDisjoint(CustomSet<T> other) {

        for (T item : elements) {
            if (other.contains(item))
                return false;
        }
        return true;
    }

    void add(T element) {
        elements.add(element);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        CustomSet<?> other = (CustomSet<?>) obj;
        return this.elements.equals(other.elements);
    }

    CustomSet<T> getIntersection(CustomSet<T> other) {
        CustomSet<T> intersection = new CustomSet<>();
        for (T item : elements) {
            if (other.contains(item))
                intersection.add(item);
        }
        return intersection;
    }

    CustomSet<T> getUnion(CustomSet<T> other) {
        CustomSet<T> union = new CustomSet<>();
        for (T item : elements) {
            union.add(item);
        }
        for (T item : other.elements) {
            if (!union.contains(item))
                union.add(item);
        }
        return union;
    }

    CustomSet<T> getDifference(CustomSet<T> other) {
        CustomSet<T> difference = new CustomSet<>();
        for (T item : elements) {
            if (!other.contains(item))
                difference.add(item);
        }
        return difference;
    }

    boolean isSubset(CustomSet<T> other) {
        return elements.containsAll(other.elements);
    }
}