import java.util.HashSet;
import java.util.Set;

class SumOfMultiples {
    private final int number;
    private final int[] set;

    SumOfMultiples(int number, int[] set) {
        this.number = number;
        this.set = set;
    }

    int getSum() {
        Set<Integer> points = new HashSet<>();
        for (int factor : set) {
            for (int multiple = factor; multiple < number && multiple != 0; multiple += factor) {
                points.add(multiple);
            }
        }
        return points.stream().mapToInt(Integer::intValue).sum();
    }
}
