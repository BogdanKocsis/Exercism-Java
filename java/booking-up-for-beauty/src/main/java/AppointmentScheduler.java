import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class AppointmentScheduler {
    public LocalDateTime schedule(String appointmentDateDescription) {
        var formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return LocalDateTime.parse(appointmentDateDescription, formatter);
    }

    public boolean hasPassed(LocalDateTime appointmentDate) {
        var now = LocalDateTime.now();
        return appointmentDate.isBefore(now);
    }

    public boolean isAfternoonAppointment(LocalDateTime appointmentDate) {
        int hour = appointmentDate.getHour();
        return hour >= 12 && hour < 18;
    }

    private String capitalize(String text) {
        return text.charAt(0) + text.substring(1).toLowerCase();
    }

    public String getDescription(LocalDateTime appointmentDate) {
        int day = appointmentDate.getDayOfMonth();
        String dayOfWeek = capitalize(appointmentDate.getDayOfWeek().toString());
        String month = capitalize(appointmentDate.getMonth().toString());
        int year = appointmentDate.getYear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        String time = appointmentDate.format(formatter);

        return "You have an appointment on " + dayOfWeek + ", " + month + " " + day + ", " + year + ", at " + time + ".";
    }

    public LocalDate getAnniversaryDate() {
        final int month = 9;
        final int day = 15;
        int year = LocalDate.now().getYear();

        return LocalDate.of(year, month, day);
    }
}
