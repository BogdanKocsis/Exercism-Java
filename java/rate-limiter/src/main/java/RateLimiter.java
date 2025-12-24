import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
public class RateLimiter<K> {
    private final TimeSource timeSource;
    private final int limit;
    private final Duration windowSize;
    private final Map<K, Instant> clientWindows = new HashMap<>();
    private final Map<K, Integer> clientCountInWindow = new HashMap<>();

    public RateLimiter(int limit, Duration windowSize, TimeSource timeSource) {
        this.timeSource = timeSource;
        this.limit = limit;
        this.windowSize = windowSize;
    }

    public boolean allow(K clientId) {
        if (clientWindows.containsKey(clientId)) {

            if (clientCountInWindow.getOrDefault(clientId, 1) < limit) {
                clientCountInWindow.put(clientId, clientCountInWindow.getOrDefault(clientId, 1) + 1);
                return true;
            }
            if (timeSource.now().isBefore(clientWindows.get(clientId).plus(windowSize))) return false;

            clientWindows.put(clientId, timeSource.now());
            clientCountInWindow.put(clientId, 1);
        } else {
            clientWindows.put(clientId, timeSource.now());
        }
        return true;
    }
}
