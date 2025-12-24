import java.time.LocalDate;
import java.time.LocalDateTime;

public class Gigasecond {
    private LocalDateTime moment;

    private static final long GIGA_SECOND = 1_000_000_000L;

    public Gigasecond(LocalDate moment) {
        this.moment = moment.atStartOfDay();
    }

    public Gigasecond(LocalDateTime moment) {
        this.moment = moment;
    }

    public LocalDateTime getDateTime() {
        return (moment != null) ? moment.plusSeconds(GIGA_SECOND) : null;
    }

}
