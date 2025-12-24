import java.util.Arrays;

class BirdWatcher {
    private final int[] birdsPerDay;
    final int BIRDS_IN_A_BUSY_DAY = 5;

    public BirdWatcher(int[] birdsPerDay) {
        this.birdsPerDay = birdsPerDay.clone();
    }

    public int[] getLastWeek() {
        return birdsPerDay;
    }

    public int getToday() {
        return birdsPerDay[birdsPerDay.length - 1];
    }

    public void incrementTodaysCount() {
        birdsPerDay[birdsPerDay.length - 1]++;
    }

    public boolean hasDayWithoutBirds() {
        Arrays.sort(birdsPerDay);
        return Arrays.binarySearch(birdsPerDay, 0) >= 0;
    }

    public int getCountForFirstDays(int numberOfDays) {
        if (numberOfDays > birdsPerDay.length) numberOfDays = birdsPerDay.length;

        int count = 0;
        for (int i = 0; i < numberOfDays; i++) {
            count += birdsPerDay[i];
        }
        return count;
    }

    public int getBusyDays() {
        int count = 0;
        for (int birds : birdsPerDay) {
            if (birds >= BIRDS_IN_A_BUSY_DAY) count++;
        }
        return count;
    }
}
