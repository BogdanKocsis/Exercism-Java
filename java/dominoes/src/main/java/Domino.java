import java.util.Objects;


// This class can't be changed by the solution.
//
class Domino {
    private final int left;
    private final int right;
    private final int hash;
    Domino(int left, int right) {
        if (left < 0 || left > 9 || right < 0 || right > 9 ) {
            throw new IllegalArgumentException("Domino tiles must have a number between 0 and 9 on each side");
        } 
        this.left = left;
        this.right = right;
        this.hash = Integer.min(left,right) + Integer.max(left, right) * 10;
    }
    
    int getLeft() {
        return this.left;
    }
    
    int getRight() {
        return this.right;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Domino otherDomino) ) {
            return false;
        }
        return this.hash == otherDomino.hash;
    }
    
    @Override
    public int hashCode() {
        return hash;
    }
}
