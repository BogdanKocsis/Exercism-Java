import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class SwiftScheduling {
    public static LocalDateTime convertToDeliveryDate(LocalDateTime meetingStart, String description) {
        return switch (description) {
            case "NOW" -> meetingStart.plusHours(2);
            case "ASAP" -> meetingStart.getHour() < 13 ? meetingStart.withHour(17).withMinute(0) :
                    meetingStart.plusDays(1).withHour(13).withMinute(0);
            case "EOW" -> {
                int dayOfWeek = meetingStart.getDayOfWeek().get(ChronoField.DAY_OF_WEEK);
                yield dayOfWeek < 4 ? meetingStart.plusDays(5 - dayOfWeek).withHour(17).withMinute(0) :
                        meetingStart.plusDays(7 - dayOfWeek).withHour(20).withMinute(0);
            }
            default -> {
                if (description.endsWith("M")) {
                    int month = Integer.parseInt(description.replace("M", ""));
                    LocalDateTime date = meetingStart.withMonth(month).withDayOfMonth(1).withHour(8).withMinute(0);
                    if (month <= meetingStart.getMonthValue()) {
                        date = date.plusYears(1);
                    }
                    if (date.getDayOfWeek().get(ChronoField.DAY_OF_WEEK) > 5) {
                        date = date.plusDays(8 - date.getDayOfWeek().get(ChronoField.DAY_OF_WEEK));
                    }
                    yield date;
                } else if (description.startsWith("Q")) {
                    int quarter = Integer.parseInt(description.replace("Q", ""));
                    LocalDateTime date = meetingStart.withMonth(quarter * 3).withDayOfMonth(1).withHour(8).withMinute(0);
                    if (quarter * 3 <= meetingStart.getMonthValue()) {
                        date = date.plusYears(1);
                    }
                    date = date.plusMonths(1).plusDays(-1);
                    if (date.getDayOfWeek().get(ChronoField.DAY_OF_WEEK) > 5) {
                        date = date.plusDays(5 - date.getDayOfWeek().get(ChronoField.DAY_OF_WEEK));
                    }
                    yield date;
                }
                yield meetingStart;
            }
        };
    }
}