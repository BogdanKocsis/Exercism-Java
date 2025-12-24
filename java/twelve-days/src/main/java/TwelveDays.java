class TwelveDays {
    public String convertNumberToOrdinal(int number) {
        return switch (number) {
            case 1 -> "first";
            case 2 -> "second";
            case 3 -> "third";
            case 4 -> "fourth";
            case 5 -> "fifth";
            case 6 -> "sixth";
            case 7 -> "seventh";
            case 8 -> "eighth";
            case 9 -> "ninth";
            case 10 -> "tenth";
            case 11 -> "eleventh";
            case 12 -> "twelfth";
            default -> throw new IllegalArgumentException("Number out of range");
        };
    }

    String verse(int verseNumber) {
        StringBuilder verse = new StringBuilder();
        verse.append("On the ").append(convertNumberToOrdinal(verseNumber)).append(" day of Christmas my true love gave to me: ");
        switch (verseNumber) {
            case 12:
                verse.append("twelve Drummers Drumming, ");
            case 11:
                verse.append("eleven Pipers Piping, ");
            case 10:
                verse.append("ten Lords-a-Leaping, ");
            case 9:
                verse.append("nine Ladies Dancing, ");
            case 8:
                verse.append("eight Maids-a-Milking, ");
            case 7:
                verse.append("seven Swans-a-Swimming, ");
            case 6:
                verse.append("six Geese-a-Laying, ");
            case 5:
                verse.append("five Gold Rings, ");
            case 4:
                verse.append("four Calling Birds, ");
            case 3:
                verse.append("three French Hens, ");
            case 2:
                verse.append("two Turtle Doves, and ");
            case 1:
                verse.append("a Partridge in a Pear Tree.\n");
                break;
            default:
                throw new IllegalArgumentException("Verse number out of range");
        }
        return verse.toString();
    }

    String verses(int startVerse, int endVerse) {
        StringBuilder verses = new StringBuilder();
        for (int i = startVerse; i <= endVerse; i++) {
            verses.append(verse(i));
            if (i < endVerse) {
                verses.append("\n");
            }
        }
        return verses.toString();
    }

    String sing() {
        return verses(1, 12);
    }
}
