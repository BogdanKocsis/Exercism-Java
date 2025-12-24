import java.util.ArrayList;
import java.util.List;

class Dominoes {

    public List<Domino> formChain(List<Domino> inputDominoes) throws ChainNotFoundException {
        if (inputDominoes.isEmpty()) {
            return new ArrayList<>();
        }

        boolean[] used = new boolean[inputDominoes.size()];
        List<Domino> currentChain = new ArrayList<>();

        if (attemptChain(inputDominoes, used, currentChain, inputDominoes.getFirst(), 0)) {
            return currentChain;
        }

        Domino first = inputDominoes.getFirst();
        if (attemptChain(inputDominoes, used, currentChain, new Domino(first.getRight(), first.getLeft()), 0)) {
            return currentChain;
        }

        throw new ChainNotFoundException("No domino chain found.");
    }

    private boolean backtrack(List<Domino> inputDominoes, boolean[] used, List<Domino> currentChain) {
        if (currentChain.size() == inputDominoes.size()) {
            Domino first = currentChain.getFirst();
            Domino last = currentChain.getLast();
            return first.getLeft() == last.getRight();
        }

        int targetValue = currentChain.getLast().getRight();

        for (int i = 0; i < inputDominoes.size(); i++) {
            if (!used[i]) {
                Domino candidate = inputDominoes.get(i);

                if (candidate.getLeft() == targetValue) {
                    if (attemptChain(inputDominoes, used, currentChain, candidate, i)) {
                        return true;
                    }
                }
                else if (candidate.getRight() == targetValue) {
                    Domino flipped = new Domino(candidate.getRight(), candidate.getLeft());
                    if (attemptChain(inputDominoes, used, currentChain, flipped, i)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean attemptChain(List<Domino> input, boolean[] used, List<Domino> chain, Domino dominoToAdd, int index) {
        used[index] = true;
        chain.add(dominoToAdd);

        if (backtrack(input, used, chain)) {
            return true;
        }

        // Backtrack: If this path failed, remove the domino and mark as unused
        chain.removeLast();
        used[index] = false;
        return false;
    }
}