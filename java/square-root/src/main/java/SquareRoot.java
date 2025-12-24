public class SquareRoot {
    public int squareRoot(int radicand) {
        if (radicand < 0) {
            throw new IllegalArgumentException("Radicand must be non-negative");
        }
        return (int) Math.sqrt(radicand);
    }
}
