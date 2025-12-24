public class Hamming {
    private final String leftStrand;
    private final String rightStrand;
    private final int hammingDistance;

    public Hamming(String leftStrand, String rightStrand) {

        if (leftStrand.length() != rightStrand.length()) {
            throw new IllegalArgumentException("strands must be of equal length");
        }

        this.leftStrand = leftStrand;
        this.rightStrand = rightStrand;

        int count = 0;
        for (int i = 0; i < rightStrand.length(); i++) {
            if (leftStrand.charAt(i) != rightStrand.charAt(i)) {
                count += 1;
            }
        }
        this.hammingDistance = count;
    }

    public int getHammingDistance() {
        return hammingDistance;
    }
}
