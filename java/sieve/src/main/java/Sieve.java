import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Sieve {
    private final Map<Integer, Boolean> sieveMap = new HashMap<>();

    Sieve(int maxPrime) {
        for (int i = 2; i <= maxPrime; i++) {
            sieveMap.put(i, true);
        }
        for (Integer key : sieveMap.keySet()) {
            if (checkPrime(key)) {
                for (int multiple = 2 * key; multiple <= maxPrime; multiple += key) {
                    sieveMap.put(multiple, false);
                }
            }
        }
    }

    List<Integer> getPrimes() {
        return sieveMap.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();
    }

    boolean checkPrime(int number) {
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }
}
