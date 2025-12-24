import java.util.ArrayList;
import java.util.List;

record PythagoreanTriplet(int a, int b, int c) {

    static TripletListBuilder makeTripletsList() {
        return new TripletListBuilder();
    }

    static class TripletListBuilder {
        private final List<PythagoreanTriplet> triplets = new ArrayList<>();
        private int max = 0;

        TripletListBuilder thatSumTo(int sum) {
            if (max == 0)
                max = sum;
            for (int i = 1; i < sum / 3; i++) {
                for (int j = i + 1; sum - i - j > j; j++) {
                    int k = sum - i - j;
                    if (k > max)
                        continue;
                    if (i * i + j * j == k * k)
                        triplets.add(new PythagoreanTriplet(i, j, k));
                }
            }
            return this;
        }

        TripletListBuilder withFactorsLessThanOrEqualTo(int maxFactor) {
            max = maxFactor;
            return this;
        }

        List<PythagoreanTriplet> build() {
            return this.triplets;
        }

    }

}