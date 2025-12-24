import java.util.ArrayList;
import java.util.List;

public class KillerSudokuHelper {
    List<Integer> exclude = List.of(0);
    int cageSize;

    List<List<Integer>> combinationsInCage(Integer cageSum, Integer cageSize, List<Integer> exclude) {
        this.exclude = exclude;
        return combinationsInCage(cageSum, cageSize);
    }

    List<List<Integer>> combinationsInCage(Integer cageSum, Integer cageSize) {
        List<List<Integer>> combinations = new ArrayList<>();
        this.cageSize = cageSize;
        findCombinations(combinations, new Integer[cageSize], cageSum, cageSize, 1);
        return combinations;
    }

    void findCombinations(List<List<Integer>> combinations, Integer[] combination, Integer sum, Integer size, int next) {
        int index = cageSize - size;
        size--;
        for (int i = next; i <= 9; i++) {
            int tempSum = sum - i;
            if (tempSum < 0) break;
            if (exclude.contains(i)) continue;
            combination[index] = i;
            if (size > 0) findCombinations(combinations, combination, tempSum, size, i + 1);
            else if (tempSum == 0) combinations.add(List.of(combination));
        }
    }
}
