import java.util.*;

class DnDCharacter {
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private static final Random random = new Random();

    public DnDCharacter() {
        strength = ability(rollDice());
        dexterity = ability(rollDice());
        constitution = ability(rollDice());
        intelligence = ability(rollDice());
        wisdom = ability(rollDice());
        charisma = ability(rollDice());
    }

    int ability(List<Integer> scores) {
        List<Integer> scoresCopy = new ArrayList<>(scores);
        Collections.sort(scoresCopy);
        scoresCopy.removeFirst();
        int sum = 0;
        for (int score : scoresCopy) {
            sum += score;
        }
        return sum;
    }

    List<Integer> rollDice() {
        List<Integer> dice = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            dice.add(random.nextInt(6) + 1);
        }
        return dice;
    }

    int modifier(int input) {
        return (int) Math.floor((input - 10) / 2.0);
    }

    int getStrength() {
        return strength;
    }

    int getDexterity() {
        return dexterity;
    }

    int getConstitution() {
        return constitution;
    }

    int getIntelligence() {
        return intelligence;
    }

    int getWisdom() {
        return wisdom;
    }

    int getCharisma() {
        return charisma;
    }

    int getHitpoints() {
        return 10 + modifier(constitution);
    }
}
