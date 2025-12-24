import java.util.List;
import java.util.Map;

record RelativeDistance(Map<String, List<String>> familyTree) {

    int degreeOfSeparation(String personA, String personB) {
        int counter = 0;
        boolean keepGoing = true;
        while (keepGoing) {
            for (String person : familyTree.keySet()) {
                if (familyTree.get(person).contains(personA)) {
                    personA = person;
                    counter++;
                    break;
                }
            }
            for (String person : familyTree.keySet()) {
                if (familyTree.get(person).contains(personB)) {
                    personB = person;
                    counter++;
                    break;
                }
            }
            if (personA.equals(personB)) {
                keepGoing = false;
            }
            if (counter == 0) {
                return -1;
            }
        }
        if (familyTree.get(personA).size() > 1) {
            counter--;
        }
        return counter;
    }
}