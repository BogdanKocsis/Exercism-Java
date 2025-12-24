import java.util.ArrayList;
import java.util.List;

class PrimeFactorsCalculator {

    boolean isPrime(long number) {
        if (number < 2) {
            return false;
        }
        for (long i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    List<Long> calculatePrimeFactorsOf(long number) {
        List<Long> primeFactors = new ArrayList<>();
        for (long i = 2; i <= number; i++) {
            while (number % i == 0 && isPrime(i)) {
                primeFactors.add(i);
                number /= i;
            }
        }
        return primeFactors;
    }

}