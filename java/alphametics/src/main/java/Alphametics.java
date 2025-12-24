import java.util.*;

record Alphametics(String puzzle) {

    Alphametics(String puzzle) {
        this.puzzle = puzzle.replaceAll("\\s+", ""); // remove espaços
    }

    Map<Character, Integer> solve() throws UnsolvablePuzzleException {
        String[] sides = puzzle.split("==");
        if (sides.length != 2) {
            throw new UnsolvablePuzzleException();
        }

        String left = sides[0];
        String right = sides[1];

        Set<Character> lettersSet = new LinkedHashSet<>();
        for (char c : puzzle.toCharArray()) {
            if (Character.isLetter(c)) lettersSet.add(c);
        }

        if (lettersSet.size() > 10) throw new UnsolvablePuzzleException();

        List<Character> letters = new ArrayList<>(lettersSet);
        int[] digits = new int[letters.size()];
        boolean[] used = new boolean[10];

        if (!backtrack(letters, digits, used, 0, left.split("\\+"), right)) {
            throw new UnsolvablePuzzleException();
        }

        Map<Character, Integer> solution = new HashMap<>();
        for (int i = 0; i < letters.size(); i++) solution.put(letters.get(i), digits[i]);
        return solution;
    }

    private boolean backtrack(List<Character> letters, int[] digits, boolean[] used, int index, String[] leftWords, String rightWord) {
        if (index == letters.size()) {
            return isValid(letters, digits, leftWords, rightWord);
        }
        for (int d = 0; d <= 9; d++) {
            if (!used[d]) {
                digits[index] = d;
                used[d] = true;
                if (backtrack(letters, digits, used, index + 1, leftWords, rightWord)) return true;
                used[d] = false;
            }
        }
        return false;
    }

    private boolean isValid(List<Character> letters, int[] digits, String[] leftWords, String rightWord) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < letters.size(); i++) map.put(letters.get(i), digits[i]);

        for (String w : leftWords) if (map.get(w.charAt(0)) == 0) return false;
        if (map.get(rightWord.charAt(0)) == 0) return false;

        long leftSum = 0;
        for (String w : leftWords) leftSum += toNumber(w, map);

        return leftSum == toNumber(rightWord, map);
    }

    private long toNumber(String word, Map<Character, Integer> map) {
        long num = 0;
        for (char c : word.toCharArray()) num = num * 10 + map.get(c);
        return num;
    }
}