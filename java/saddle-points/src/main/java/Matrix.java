import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

record Matrix(List<List<Integer>> matrix) {

    Set<MatrixCoordinate> getSaddlePoints() {

        return IntStream.range(0, matrix.size()).boxed().flatMap(row ->
                        IntStream.range(0, matrix.get(row).size()).boxed()
                                .filter(col -> matrix.get(row).stream().allMatch(x -> x <= matrix.get(row).get(col))
                                        && matrix.stream().allMatch(x -> x.get(col) >= matrix.get(row).get(col)))
                                .map(col -> new MatrixCoordinate(row + 1, col + 1)))
                .collect(Collectors.toSet());
    }

}
