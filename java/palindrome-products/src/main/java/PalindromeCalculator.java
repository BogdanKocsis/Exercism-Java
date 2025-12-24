import java.util.List;
import java.util.*;

class PalindromeCalculator {

    public static boolean isPalindrome(long number) {
        String str = Long.toString(number);
        return str.contentEquals(new StringBuilder(str).reverse());
    }

    public static SortedMap<Long, List<List<Integer>>> getPalindromeProductsWithFactors(int minFactor, int maxFactor) {
        if (minFactor > maxFactor)
            throw new IllegalArgumentException("invalid input: min must be <= max");
        SortedMap<Long, List<List<Integer>>> palindromeMap = new TreeMap<>();

        for (int i = minFactor; i <= maxFactor; i++) {
            for (int j = i; j <= maxFactor; j++) { // avoid duplicate pairs
                long product = (long) i * j;
                if (isPalindrome(product)) {
                    palindromeMap.computeIfAbsent(product, k -> new ArrayList<>())
                            .add(Arrays.asList(i, j));
                }
            }
        }

        return palindromeMap;
    }


}