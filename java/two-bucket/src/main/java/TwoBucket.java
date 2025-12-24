class TwoBucket {
    int bucketFromCap;
    int bucketToCap;
    int desiredLiters;
    String startBucket;

    TwoBucket(int bucketOneCap, int bucketTwoCap, int desiredLiters, String startBucket) {
        this.desiredLiters = desiredLiters;
        this.startBucket = startBucket;
        this.bucketFromCap = startBucket.equals("one") ? bucketOneCap : bucketTwoCap;
        this.bucketToCap = startBucket.equals("one") ? bucketTwoCap : bucketOneCap;
        if (desiredLiters % gcd(bucketFromCap, bucketToCap) != 0 || (bucketFromCap < desiredLiters && bucketToCap < desiredLiters))
            throw new UnreachableGoalException();
    }

    Result getResult() {
        int fromBucket = bucketFromCap;
        int toBucket = 0;
        int steps = 1;
        if (bucketToCap == desiredLiters) {
            toBucket = bucketToCap;
            steps++;
        }

        while (fromBucket != desiredLiters && toBucket != desiredLiters) {
            int maxSpace = Math.min(fromBucket, bucketToCap - toBucket);
            toBucket += maxSpace;
            fromBucket -= maxSpace;
            steps++;
            if (fromBucket == desiredLiters || toBucket == desiredLiters)
                break;
            if (fromBucket == 0) {
                fromBucket = bucketFromCap;
                steps++;
            }
            if (toBucket == bucketToCap) {
                steps++;
                toBucket = 0;
            }
        }

        String finalBucket = fromBucket == desiredLiters && startBucket.equals("one") ? "one"
                : fromBucket == desiredLiters && startBucket.equals("two") ? "two"
                : startBucket.equals("one") ? "two" : "one";

        int otherBucket = fromBucket == desiredLiters ? toBucket : fromBucket;
        return new Result(steps, finalBucket, otherBucket);
    }

    int gcd(int bucketFromCap, int bucketToCap) {
        if (bucketToCap == 0)
            return bucketFromCap;
        else
            return gcd(bucketToCap, bucketFromCap % bucketToCap);
    }

}