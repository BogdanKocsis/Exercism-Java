import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class React {

    public static class Cell<T> {
        T value;
        List<ComputeCell<T>> observers = new ArrayList<>();
        boolean isUpdated = true;

        public void addObserver(ComputeCell<T> observer) {
            observers.add(observer);
        }

        public void applyChanges() {
            resetUpdates(this);

            for (ComputeCell<T> observer : observers) {
                observer.computeCell();
            }
        }

        //Alle Zelle, die diese Änderung beeinflusst, werden als nicht geänderte markiert.
        private void resetUpdates(Cell<T> cell) {
            for (ComputeCell<T> observer : cell.observers) {
                observer.isUpdated = false;

                resetUpdates(observer);
            }
        }

        public T getValue() {
            return value;
        }

        public void setValue(T newValue) {
            if (!newValue.equals(this.value)) {
                isUpdated = true;
                this.value = newValue;

                applyChanges();
            }
        }
    }

    public static class InputCell<T>
            extends Cell<T> {
    }

    public static class ComputeCell<T>
            extends Cell<T> {
        List<Consumer<T>> callbacks = new ArrayList<>();
        List<Cell<T>> inputs = new ArrayList<>();
        Function<List<T>, T> function;

        ComputeCell(List<Cell<T>> cells, Function<List<T>, T> function) {
            this.inputs.addAll(cells);
            this.function = function;

            for (Cell<T> cell : cells) {
                cell.addObserver(this);
            }

            this.value = function.apply(extractValues(inputs));
        }

        public void computeCell() {
            T oldValue = this.value;
            T newValue = function.apply(extractValues(inputs));
            isUpdated = true;

            if (!newValue.equals(oldValue)) {
                this.value = newValue;

                for (Consumer<T> callback : callbacks) {
                    callback.accept(newValue);
                }
            }

            if (checkInputs(this)) {
                applyChanges();
            }
        }

        //Überprüft, ob alle Zellen, von denen der Observer abhängt, bereits geändert wurden.
        private boolean checkInputs(Cell<T> cell) {
            for (ComputeCell<T> observer : cell.observers) {
                for (Cell<T> input : observer.inputs) {
                    if (!input.isUpdated) {
                        return false;
                    }
                }
            }

            return true;
        }

        public void addCallback(Consumer<T> callback) {
            callbacks.add(callback);
        }

        public void removeCallback(Consumer<T> callback) {
            callbacks.remove(callback);
        }
    }

    public static <T> InputCell<T> inputCell(T initialValue) {
        InputCell<T> cell = new InputCell<>();
        cell.setValue(initialValue);
        return cell;
    }

    public static <T> ComputeCell<T> computeCell(Function<List<T>, T> function, List<Cell<T>> cells) {
        return new ComputeCell<>(cells, function);
    }

    private static <T> List<T> extractValues(List<Cell<T>> cells) {
        return cells.stream().map(Cell::getValue).toList();
    }
}