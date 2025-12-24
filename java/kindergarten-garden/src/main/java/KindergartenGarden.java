import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class KindergartenGarden {
    private final String garden;
    private final List<String> students = Arrays.asList(
            "Alice", "Bob", "Charlie", "David", "Eve", "Fred",
            "Ginny", "Harriet", "Ileana", "Joseph", "Kincaid", "Larry"
    );


    KindergartenGarden(String garden) {
        this.garden = garden;
    }

    List<Plant> getPlantsOfStudent(String student) {
        int index = students.indexOf(student);
        List<Plant> plants = new ArrayList<>();
        String[] rows = garden.split("\n");

        for (String row : rows) {
            plants.add(Plant.getPlant(row.charAt(index * 2)));
            plants.add(Plant.getPlant(row.charAt(index * 2 + 1)));
        }
        return plants;
    }

}
