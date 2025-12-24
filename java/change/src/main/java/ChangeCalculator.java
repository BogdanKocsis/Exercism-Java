import java.util.List;
import java.util.ArrayList;

class ChangeCalculator {
    List<Integer> currencyCoins;

    ChangeCalculator(List<Integer> currencyCoins) {
        this.currencyCoins = currencyCoins;
    }

    List<Integer> computeMostEfficientChange(int grandTotal) {
        if (grandTotal < 0)
            throw new IllegalArgumentException("Negative totals are not allowed.");
        if (grandTotal == 0)
            return new ArrayList<>();
        int length = 1;
        int maxLength = grandTotal / currencyCoins.getFirst() + 1;
        while (length < maxLength) {
            List<List<Integer>> combinations = new ArrayList<>();
            combine(combinations, new ArrayList<>(), length, 0);
            if (!combinations.isEmpty()) {
                for (List<Integer> combination : combinations) {
                    if (combination.stream().mapToInt(Integer::valueOf).sum() == grandTotal)
                        return combination;
                }
            }

            length++;
        }

        String exceptionMessage = String.format("The total %d cannot be represented in the given currency.", grandTotal);
        throw new IllegalArgumentException(exceptionMessage);
    }

    void combine(List<List<Integer>> combinations, List<Integer> combination, int length, int startIndex) {
        if (combination.size() == length) {
            combinations.add(new ArrayList<>(combination));
            return;
        }
        if (startIndex >= currencyCoins.size())
            return;
        combination.add(currencyCoins.get(startIndex));
        combine(combinations, combination, length, startIndex++);
        combination.removeLast();
        combine(combinations, combination, length, startIndex++);
    }

}