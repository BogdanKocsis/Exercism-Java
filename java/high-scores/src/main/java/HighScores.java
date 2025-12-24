import java.util.List;

class HighScores {
    final List<Integer> highScores;

    public HighScores(List<Integer> highScores) {
        this.highScores = highScores;
    }

    List<Integer> scores() {
        return highScores;
    }

    Integer latest() {
        return highScores.getLast();
    }

    Integer personalBest() {
        return highScores.stream().max(Integer::compareTo).orElse(0);
    }

    List<Integer> personalTopThree() {

        return highScores.stream()
                .sorted((a, b) -> b - a)
                .limit(3)
                .toList();
    }

}
