class NaturalNumber {
    private final int number;

    NaturalNumber(int number) {
        if (number <= 0)
            throw new IllegalArgumentException("You must supply a natural number (positive integer)");
        this.number = number;
    }

    Classification getClassification() {
        int sumOfDivisors = 0;
        for (int i = 1; i <= number / 2; i++) {
            if (number % i == 0)
                sumOfDivisors += i;
        }
        if (sumOfDivisors == number) return Classification.PERFECT;
        else if (sumOfDivisors > number) return Classification.ABUNDANT;
        else return Classification.DEFICIENT;
    }
}
