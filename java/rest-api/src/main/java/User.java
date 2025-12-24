import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO representing a User in the database.
 */
public record User(String name, List<Iou> owes, List<Iou> owedBy) {
    public User(String name, List<Iou> owes, List<Iou> owedBy) {
        this.name = name;
        this.owes = new ArrayList<>(owes);
        this.owedBy = new ArrayList<>(owedBy);
    }

    /**
     * IOUs this user owes to other users.
     */
    @Override
    public List<Iou> owes() {
        return unmodifiableList(owes);
    }

    /**
     * IOUs other users owe to this user.
     */
    @Override
    public List<Iou> owedBy() {
        return unmodifiableList(owedBy);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private final List<Iou> owes = new ArrayList<>();
        private final List<Iou> owedBy = new ArrayList<>();

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder owes(String name, double amount) {
            owes.add(new Iou(name, amount));
            return this;
        }

        public Builder owedBy(String name, double amount) {
            owedBy.add(new Iou(name, amount));
            return this;
        }

        public User build() {
            return new User(name, owes, owedBy);
        }
    }
}