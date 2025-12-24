public class CarsAssemble {

    final int CARS_EACH_HOUR = 221;
    final int MINUTES_PER_HOUR = 60;

    public double productionRatePerHour(int speed) {
        final double SUCCESS_RATE = switch (speed) {
            case 1, 2, 3, 4 -> 1;
            case 5, 6, 7, 8 -> 0.9;
            case 9 -> 0.8;
            case 10 -> 0.77;
            default -> 0;
        };
        return speed * CARS_EACH_HOUR * SUCCESS_RATE;
    }

    public int workingItemsPerMinute(int speed) {
        return (int) productionRatePerHour(speed) / MINUTES_PER_HOUR;
    }
}
