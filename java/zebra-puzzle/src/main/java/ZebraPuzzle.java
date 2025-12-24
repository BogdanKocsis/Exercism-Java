import java.util.*;

class ZebraPuzzle {
    final int numberOfHouses = 5;

    enum COLOR {RED, YELLOW, IVORY, GREEN, BLUE}

    enum NATIONALITY {ENGLISHMAN, SPANIARD, UKRAINIAN, NORWEGIAN, JAPANESE}

    enum PET {DOG, SNAIL, FOX, HORSE, ZEBRA}

    enum DRINKS {WATER, COFFEE, MILK, ORANGE_JUICE, TEA}

    enum HOBBY {CHESS, FOOTBALL, DANCING, READING, PAINTING}

    static class House {
        COLOR color;
        NATIONALITY nationality;
        PET pet;
        DRINKS drinks;
        HOBBY hobby;
    }

    private final House[] houses = new House[numberOfHouses];

    ZebraPuzzle() {
        solve();
    }

    private void solve() {

        for (int i = 0; i < numberOfHouses; i++) {
            houses[i] = new House();
        }

        houses[0].nationality = NATIONALITY.NORWEGIAN;
        houses[2].drinks = DRINKS.MILK;

        houses[1].color = COLOR.BLUE;
        houses[2].color = COLOR.RED;
        houses[3].color = COLOR.IVORY;
        houses[4].color = COLOR.GREEN;

        houses[4].drinks = DRINKS.COFFEE; // Green house drinks coffee
        houses[1].pet = PET.HORSE; // Painter's house is next to horse

        houses[3].nationality = NATIONALITY.UKRAINIAN; // Ukrainian drinks tea
        houses[3].drinks = DRINKS.TEA;

        houses[4].nationality = NATIONALITY.JAPANESE; // Japanese plays chess
        houses[4].hobby = HOBBY.CHESS;

        houses[3].hobby = HOBBY.FOOTBALL; // Football player drinks orange juice
        houses[3].drinks = DRINKS.ORANGE_JUICE;

        houses[0].color = COLOR.YELLOW; // Painter lives in yellow house
        houses[0].hobby = HOBBY.PAINTING;

        houses[2].nationality = NATIONALITY.ENGLISHMAN; // Englishman in red house

        houses[1].nationality = NATIONALITY.SPANIARD; // Spaniard owns dog
        houses[1].pet = PET.DOG;

        houses[0].pet = PET.FOX; // Reader lives next to fox
        houses[1].hobby = HOBBY.READING;

        houses[2].pet = PET.SNAIL; // Snail owner likes dancing
        houses[2].hobby = HOBBY.DANCING;

        houses[0].drinks = DRINKS.WATER; // By elimination, water is in house 1
        houses[4].pet = PET.ZEBRA; // By elimination, zebra is in house 5
    }

    private String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    String getWaterDrinker() {
        for (House house : houses) {
            if (house.drinks == DRINKS.WATER) {
                return capitalizeFirstLetter(house.nationality.name());
            }
        }
        return null;
    }

    String getZebraOwner() {
        for (House house : houses) {
            if (house.pet == PET.ZEBRA) {
                return capitalizeFirstLetter(house.nationality.name());
            }
        }
        return null;

    }
}
