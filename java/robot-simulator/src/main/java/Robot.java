import java.util.List;
import java.util.Map;

class Robot {
    private Orientation orientation;
    private GridPosition gridPosition;

    Robot(GridPosition gridPosition, Orientation orientation) {
        this.orientation = orientation;
        this.gridPosition = gridPosition;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public GridPosition getGridPosition() {
        return gridPosition;
    }

    public void turnRight() {
        orientation = Orientation.values()[(orientation.ordinal() + 1) % Orientation.values().length];
    }

    public void turnLeft() {
        orientation = Orientation.values()[(orientation.ordinal() + 3) % 4];
    }

    public void advance() {
        int currentX = gridPosition.x;
        int currentY = gridPosition.y;
        switch (getOrientation()) {
            case NORTH -> gridPosition = new GridPosition(currentX, currentY + 1);
            case SOUTH -> gridPosition = new GridPosition(currentX, currentY - 1);
            case EAST -> gridPosition = new GridPosition(currentX + 1, currentY);
            case WEST -> gridPosition = new GridPosition(currentX - 1, currentY);

        }
    }

    public void simulate(String tasks) {
        List<String> ListOfTasks = List.of(tasks.split(""));
        Map<String, Runnable> taskActions = Map.of(
                "R", this::turnRight,
                "L", this::turnLeft,
                "A", this::advance
        );
        for (String task : ListOfTasks) {
            taskActions.getOrDefault(task, () -> {
            }).run();
        }
    }
}