import java.util.ArrayList;
import java.util.List;


class Flattener {
    List<Object> flatten(List<?> list) {
        List<Object> result = new ArrayList<>();
        for (Object item : list) {
            if (item instanceof List<?>) {
                result.addAll(flatten((List<?>) item));
            } else if (item != null) {
                result.add(item);
            }
        }
        return result;
    }
}