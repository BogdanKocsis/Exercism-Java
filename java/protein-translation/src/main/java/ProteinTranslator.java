import java.util.ArrayList;
import java.util.List;

class ProteinTranslator {

    private static int findStopIndex(String rnaSequence) {
        for (int i = 0; i + 2 < rnaSequence.length(); i += 3) {
            String codon = rnaSequence.substring(i, i + 3);
            if (codon.equals("UAA") || codon.equals("UAG") || codon.equals("UGA")) {
                return i;
            }
        }
        return -1;
    }

    List<String> translate(String rnaSequence) {
        List<String> proteins = new ArrayList<>();
        if (rnaSequence == null || rnaSequence.isBlank()) return proteins;

        int stopIndex = findStopIndex(rnaSequence);

        int effectiveLength = (stopIndex == -1) ? rnaSequence.length() : stopIndex;

        if (effectiveLength % 3 != 0) {
            throw new IllegalArgumentException("Invalid codon");
        }

        for (int i = 0; i + 2 < rnaSequence.length(); i += 3) {
            String codon = rnaSequence.substring(i, i + 3);

            switch (codon) {
                case "AUG" -> proteins.add("Methionine");
                case "UUU", "UUC" -> proteins.add("Phenylalanine");
                case "UUA", "UUG" -> proteins.add("Leucine");
                case "UCU", "UCC", "UCA", "UCG" -> proteins.add("Serine");
                case "UAU", "UAC" -> proteins.add("Tyrosine");
                case "UGU", "UGC" -> proteins.add("Cysteine");
                case "UGG" -> proteins.add("Tryptophan");
                case "UAA", "UAG", "UGA" -> {
                    return proteins;
                }
                default -> throw new IllegalArgumentException("Invalid codon");
            }
        }
        return proteins;
    }
}
