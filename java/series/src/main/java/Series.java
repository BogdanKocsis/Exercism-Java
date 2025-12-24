import java.util.List;
import java.util.stream.IntStream;

class Series {
    public final String string;

    Series(String string) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("series cannot be empty");
        }
        this.string = string;
    }

    List<String> slices(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("slice length cannot be negative or zero");
        } else if (num > string.length()) {
            throw new IllegalArgumentException("slice length cannot be greater than series length");
        }

        return IntStream.range(0, string.length() - num + 1)
                .mapToObj(index -> string.substring(index, index + num))
                .toList();
    }
}
