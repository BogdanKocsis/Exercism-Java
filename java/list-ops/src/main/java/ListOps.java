import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

class ListOps {

    static <T> List<T> append(List<T> list1, List<T> list2) {

        ArrayList<T> mutable = new ArrayList<>(list1);

        mutable.addAll(list2);

        return List.copyOf(mutable);
    }

    static <T> List<T> concat(List<List<T>> listOfLists) {
        ArrayList<T> mutable = new ArrayList<>();

        for (List<T> list : listOfLists) {
            mutable.addAll(list);
        }

        return List.copyOf(mutable);
    }

    static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        ArrayList<T> mutable = new ArrayList<>();

        for (T t : list) {
            if (predicate.test(t)) mutable.add(t);
        }

        return List.copyOf(mutable);
    }

    static <T> int size(List<T> list) {
        int counter = 0;

        for (T _ : list) {
            counter++;
        }

        return counter;
    }

    static <T, U> List<U> map(List<T> list, Function<T, U> transform) {
        ArrayList<U> mutable = new ArrayList<>();

        list.forEach((p) -> mutable.add(transform.apply(p)));

        return List.copyOf(mutable);
    }


    static <T, U> U foldLeft(List<T> list, U initial, BiFunction<U, T, U> f) {

        for (T t : list) {
            initial = f.apply(initial, t);
        }

        return initial;
    }

    static <T, U> U foldRight(List<T> list, U initial, BiFunction<T, U, U> f) {

        for (int i = size(list) - 1; i >= 0; i--) {
            initial = f.apply(list.get(i), initial);
        }

        return initial;
    }

    private ListOps() {
        // No instances.
    }

    static <T> List<T> reverse(List<T> list) {
        ArrayList<T> arr = new ArrayList<>();

        for (int i = size(list) - 1; i >= 0; i--) {
            arr.add(list.get(i));
        }

        return List.copyOf(arr);
    }
}