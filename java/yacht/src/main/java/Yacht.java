import java.util.Arrays;
import java.util.stream.IntStream;

record Yacht(int[] dice, YachtCategory yachtCategory) {

    int calculateNumericalScore(int value) {
        return Arrays.stream(dice).filter(number -> number == value).sum();
    }

    int calculateFullHouseScore() {

        int[] counts = new int[7];
        for (int number : dice) {
            counts[number]++;
        }
        boolean hasThree = Arrays.stream(counts).anyMatch(count -> count == 3);
        boolean hasTwo = Arrays.stream(counts).anyMatch(count -> count == 2);
        return (hasTwo && hasThree) ? Arrays.stream(dice).sum() : 0;

    }

    int calculateFourOfAKindScore() {
        int[] counts = new int[7];
        for (int number : dice) {
            counts[number]++;
        }
        return IntStream.rangeClosed(1, 6)
                .filter(number -> counts[number] >= 4)
                .map(number -> number * 4)
                .findFirst()
                .orElse(0);
    }

    int calculateLittleStraightScore() {
        int[] sortedDice = Arrays.copyOf(dice, dice.length);
        Arrays.sort(sortedDice);
        int[] littleStraight = {1, 2, 3, 4, 5};
        return Arrays.equals(sortedDice, littleStraight) ? 30 : 0;
    }

    int calculateBigStraightScore() {
        int[] sortedDice = Arrays.copyOf(dice, dice.length);
        Arrays.sort(sortedDice);
        int[] littleStraight = {2, 3, 4, 5, 6};
        return Arrays.equals(sortedDice, littleStraight) ? 30 : 0;
    }

    int calculateChoiceScore() {
        return Arrays.stream(dice).sum();
    }

    int calculateYachtScore() {
        int[] counts = new int[7];
        for (int number : dice) {
            counts[number]++;
        }
        return (Arrays.stream(counts).anyMatch(count -> count == 5) ? 50 : 0);
    }

    int score() {
        return switch (yachtCategory) {
            case YACHT -> calculateYachtScore();
            case ONES -> calculateNumericalScore(1);
            case TWOS -> calculateNumericalScore(2);
            case THREES -> calculateNumericalScore(3);
            case FOURS -> calculateNumericalScore(4);
            case FIVES -> calculateNumericalScore(5);
            case SIXES -> calculateNumericalScore(6);
            case FULL_HOUSE -> calculateFullHouseScore();
            case FOUR_OF_A_KIND -> calculateFourOfAKindScore();
            case LITTLE_STRAIGHT -> calculateLittleStraightScore();
            case BIG_STRAIGHT -> calculateBigStraightScore();
            case CHOICE -> calculateChoiceScore();
        };
    }

}
