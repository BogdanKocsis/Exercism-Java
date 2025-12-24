import java.util.ArrayList;
import java.util.List;

class BowlingGame {
    private final List<Integer> rolls = new ArrayList<>();

    public void roll(int pins) {
        if (pins < 0) throw new IllegalStateException("Negative roll is invalid");
        if (pins > 10 || rolls.size() < 20 && rolls.size() % 2 == 1 && getRoll(rolls.size() - 1) < 10 && getRoll(rolls.size() - 1) + pins > 10)
            throw new IllegalStateException("Pin count exceeds pins on the lane");
        if (rolls.size() >= 19 && isStrike(18)) {
            int firstBonus = getRoll(19);
            if (rolls.size() == 20) {
                if (firstBonus != 10 && (firstBonus + pins > 10)) {
                    throw new IllegalStateException("Pin count exceeds pins on the lane");
                }
                if (firstBonus != 10 && pins == 10) {
                    throw new IllegalStateException("Pin count exceeds pins on the lane");
                }
            }
        }
        if (isGameOver()) throw new IllegalStateException("Cannot roll after game is over");
        rolls.add(pins);
    }

    public int score() {
        if (!isGameComplete()) throw new IllegalStateException("Score cannot be taken until the end of the game");

        int score = 0;
        int rollIndex = 0;
        for (int frame = 0; frame < 10; frame++) {
            if (isStrike(rollIndex)) {
                score += 10 + getRoll(rollIndex + 1) + getRoll(rollIndex + 2);
                rollIndex++;
            } else if (isSpare(rollIndex)) {
                score += 10 + getRoll(rollIndex + 2);
                rollIndex += 2;
            } else {
                score += getRoll(rollIndex) + getRoll(rollIndex + 1);
                rollIndex += 2;
            }
        }
        return score;
    }

    private boolean isStrike(int rollIndex) {
        return getRoll(rollIndex) == 10;
    }

    private boolean isSpare(int rollIndex) {
        return rolls.size() > rollIndex + 1 && getRoll(rollIndex) + getRoll(rollIndex + 1) == 10;
    }

    private int getRoll(int index) {
        if (index < rolls.size()) {
            return rolls.get(index);
        }
        return 0;
    }

    private boolean isGameOver() {
        return isGameComplete() && rolls.size() >= getExpectedRollCount();
    }

    private boolean isGameComplete() {
        if (rolls.size() < 12) {
            return false;
        }

        int frame = 0;
        int rollIndex = 0;
        for (int i = 0; i < 10; i++) {
            if (isStrike(rollIndex)) {
                rollIndex++;
                frame++;
            } else if (rolls.size() > rollIndex + 1) {
                rollIndex += 2;
                frame++;
            } else {
                return false; // Not enough rolls for a complete game
            }
            if (frame == 9 && !isStrike(rollIndex -1) && isSpare(rollIndex - 2)) {
                return rolls.size() == rollIndex + 1;
            }
        }

        if (frame == 10) {
            if (isStrike(rollIndex - 1)) {
                return rolls.size() == rollIndex + 2;
            } else if (isSpare(rollIndex - 2)) {
                return rolls.size() == rollIndex + 1;
            } else {
                return rolls.size() == rollIndex;
            }
        }
        return false;
    }

    private int getExpectedRollCount() {
        int strikes = 0;
        for (int roll : rolls) {
            if (roll == 10) {
                strikes++;
            }
        }
        return 20 - strikes;
    }
}