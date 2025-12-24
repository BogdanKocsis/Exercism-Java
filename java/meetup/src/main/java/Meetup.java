import java.time.DayOfWeek;
import java.time.LocalDate;

class Meetup {
    private final int month;
    private final int year;
    private final int lastDay;

    Meetup(int monthOfYear, int year) {
        if (monthOfYear < 1 || monthOfYear > 12)
            throw new IllegalArgumentException();
        if (year < 1900 || year > 2099)
            throw new IllegalArgumentException();

        this.month = monthOfYear;
        this.year = year;
        int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isLeap(year))
            days[2]++;
        this.lastDay = days[monthOfYear];
    }

    private boolean isLeap(int year) {
        if (year % 4 == 0) {
            return year % 100 != 0 || (year % 400 == 0);
        }
        return false;
    }

    LocalDate day(DayOfWeek dayOfWeek, MeetupSchedule schedule) {
        int d = switch (schedule) {
            case MeetupSchedule.FIRST -> findDay(1, 7, dayOfWeek);
            case MeetupSchedule.SECOND -> findDay(8, 14, dayOfWeek);
            case MeetupSchedule.THIRD -> findDay(15, 21, dayOfWeek);
            case MeetupSchedule.FOURTH -> findDay(22, 28, dayOfWeek);
            case MeetupSchedule.LAST -> findDay(this.lastDay - 6, this.lastDay, dayOfWeek);
            case MeetupSchedule.TEENTH -> findDay(13, 19, dayOfWeek);
        };
        return LocalDate.of(this.year, this.month, d);
    }

    private int findDay(int from, int end, DayOfWeek dayOfWeek) {
        for (int i = from; i <= end; i++) {
            if (LocalDate.of(this.year, this.month, i).getDayOfWeek().equals(dayOfWeek))
                return i;
        }
        throw new IllegalArgumentException();
    }

}