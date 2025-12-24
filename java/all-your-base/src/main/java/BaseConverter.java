import java.util.LinkedList;

class BaseConverter {

    int convertBase10;

    BaseConverter(int originalBase, int[] originalDigits) {
        if (originalBase < 2) throw new IllegalArgumentException("Bases must be at least 2.");
        for (int i = 0; i < originalDigits.length; i++) {
            if (originalDigits[i] < 0) throw new IllegalArgumentException("Digits may not be negative.");
            if (originalDigits[i] >= originalBase)
                throw new IllegalArgumentException("All digits must be strictly less than the base.");
            this.convertBase10 += (int) (originalDigits[i] * Math.pow(originalBase, originalDigits.length - 1 - i));
        }
    }

    int[] convertToBase(int newBase) {
        if (newBase < 2) throw new IllegalArgumentException("Bases must be at least 2.");
        if (convertBase10 == 0) return new int[]{0};
        LinkedList<Integer> newDigitsList = new LinkedList<>();
        for (int i = convertBase10; i > 0; i /= newBase) newDigitsList.addFirst(i % newBase);
        return newDigitsList.stream().mapToInt(Integer::intValue).toArray();
    }
}