import java.math.BigInteger;

class Grains {
    final int MAX_SQUARE = 64;
    final int MIN_SQUARE = 1;

    BigInteger grainsOnSquare(final int square) {
        if (square < MIN_SQUARE || square > MAX_SQUARE) {
            throw new IllegalArgumentException("square must be between 1 and 64");
        } else {
            return BigInteger.TWO.pow(square - 1);
        }
    }

    BigInteger grainsOnBoard() {
        return BigInteger.TWO.pow(MAX_SQUARE).subtract(BigInteger.ONE);

    }

}
