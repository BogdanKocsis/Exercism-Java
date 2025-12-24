import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class SplitSecondStopwatch {
    private enum State {
        READY, RUNNING, STOPPED
    }

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
    private State state = State.READY;
    private final List<String> laps = new LinkedList<>();
    private LocalTime lap = LocalTime.MIDNIGHT;

    public SplitSecondStopwatch() {}

    public void start() {
        if (state == State.RUNNING) throw new IllegalStateException("cannot start an already running stopwatch");

        this.state = State.RUNNING;
    }

    public void stop() {
        if (state != State.RUNNING) throw new IllegalStateException("cannot stop a stopwatch that is not running");

        this.state = State.STOPPED;
    }

    public void reset() {
        if (state != State.STOPPED) throw new IllegalStateException("cannot reset a stopwatch that is not stopped");

        this.laps.clear();
        this.lap = LocalTime.MIDNIGHT;
        this.state = State.READY;
    }

    public void lap() {
        if (state != State.RUNNING) throw new IllegalStateException("cannot lap a stopwatch that is not running");

        laps.add(this.lap.format(fmt));

        this.lap = LocalTime.MIDNIGHT;
    }

    public String state() {
        return switch(this.state) {
            case READY -> "ready";
            case RUNNING -> "running";
            case STOPPED -> "stopped";
        };
    }

    public String currentLap() {
        return this.lap.format(fmt);
    }

    public String total() {
        LocalTime totalSeconds = this.lap;

        for (String lap : this.previousLaps()) {
            totalSeconds = totalSeconds.plusSeconds(LocalTime.parse(lap).toSecondOfDay());
        }

        return totalSeconds.format(fmt);
    }

    public List<String> previousLaps() {
        return this.laps;
    }

    public void advanceTime(String timeString) {
        if (state == State.RUNNING) {
            this.lap = this.lap.plusSeconds(LocalTime.parse(timeString).toSecondOfDay());
        }
    }
}