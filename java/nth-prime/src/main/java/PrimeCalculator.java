class PrimeCalculator {

    boolean isPrime(int number) {
        if (number < 2) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    int nth(int nth) {
        if (nth < 1) {
            throw new IllegalArgumentException("There is no zeroth prime");
        }
        int count = 1;
        int number = 2;
        while (count < nth) {
            if (isPrime(++number)) {
                count++;
            }
        }
        return number;
    }

}
