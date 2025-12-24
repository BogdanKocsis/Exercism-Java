public class EliudsEggs {
    public int eggCount(int number) {
        /*
        CLASSIC WAY
        String binary = Integer.toBinaryString(number);
        int count = 0;
        for (char c : binary.toCharArray()) {
            if (c == '1') count += 1;
        }
        return count;

         */
        return Integer.bitCount(number);
    }
}
