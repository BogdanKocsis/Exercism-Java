class LargestSeriesProductCalculator {
    private final String inputNumber;

    LargestSeriesProductCalculator(String inputNumber) {
        if (inputNumber == null) {
            throw new IllegalArgumentException("String to search may only contain digits.");
        }
        if (!inputNumber.matches("\\d*")) {
            throw new IllegalArgumentException("String to search may only contain digits.");
        }
        this.inputNumber = inputNumber;
    }

    long calculateLargestProductForSeriesLength(int numberOfDigits) {
        if (numberOfDigits < 0) {
            throw new IllegalArgumentException("Series length must be non-negative.");
        }
        if (numberOfDigits > inputNumber.length()) {
            throw new IllegalArgumentException("Series length must be less than or equal to the length of the string to search.");
        }

        long largestProduct = 0;

        for (int i = 0; i <= inputNumber.length() - numberOfDigits; i++) {
            String series = inputNumber.substring(i, i + numberOfDigits);
            long product = 1;
            for (char digitChar : series.toCharArray()) {
                int digit = Character.getNumericValue(digitChar);
                product *= digit;
            }
            if (product > largestProduct) {
                largestProduct = product;
            }
        }

        return largestProduct;
    }
}
