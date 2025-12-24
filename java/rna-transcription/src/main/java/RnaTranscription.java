class RnaTranscription {

    char toRNANucleotide(char nucleotide) {
        return switch (nucleotide) {
            case 'G' -> 'C';
            case 'C' -> 'G';
            case 'T' -> 'A';
            case 'A' -> 'U';
            default -> throw new IllegalArgumentException("Invalid nucleotide: " + nucleotide);
        };
    }

    String transcribe(String dnaStrand) {
        var rnaStrand = new StringBuilder();
        for (char nucleotide : dnaStrand.toCharArray()) {
            rnaStrand.append(toRNANucleotide(nucleotide));
        }
        return rnaStrand.toString();
    }

}
