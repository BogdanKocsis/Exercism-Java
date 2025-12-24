import java.time.Duration;
import java.time.LocalTime;

class Clock {

    private LocalTime timeInFormatHoursMinutes;

    Clock(int hours, int minutes) {
        convertStringToLocalTime(hours, minutes);
    }

    void add(int minutes) {
        timeInFormatHoursMinutes = timeInFormatHoursMinutes.plusMinutes(minutes);
    }

    @Override
    public String toString() {
        return this.timeInFormatHoursMinutes.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.equals(timeInFormatHoursMinutes);
    }


    private void convertStringToLocalTime(int hours, int minutes) {
        if (hours == 24) {
            hours = 00;
        }
        if (hours > 24 || minutes > 59 || hours < 0 || minutes < 0) {
            int howMuchMinutes = (hours * 60) + minutes;
            String timeFromMinutes = LocalTime.MIN.plus(Duration.ofMinutes(howMuchMinutes)).toString();
            hours = Integer.parseInt(timeFromMinutes.substring(0, 2));
            minutes = Integer.parseInt(timeFromMinutes.substring(3));
        }
        this.timeInFormatHoursMinutes = LocalTime.of(hours, minutes);
    }

}