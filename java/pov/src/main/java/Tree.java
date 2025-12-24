import java.util.*;

class Tree {
    private final String label;
    private final List<Tree> children;

    public Tree(String label) {
        this(label, new ArrayList<>());
    }

    public Tree(String label, List<Tree> children) {
        this.label = label;
        this.children = children;
    }

    public static Tree of(String label) {
        return new Tree(label);
    }

    public static Tree of(String label, List<Tree> children) {
        return new Tree(label, children);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree tree = (Tree) o;
        return label.equals(tree.label)
                && children.size() == tree.children.size()
                && children.containsAll(tree.children)
                && tree.children.containsAll(children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, children);
    }

    @Override
    public String toString() {
        return "Tree{" + label +
                ", " + children +
                "}";
    }

    public Tree fromPov(String fromNode) {
        ArrayList<Tree> path = findPath(fromNode);
        if (path == null || path.isEmpty()) {
            throw new UnsupportedOperationException("Tree could not be reoriented");
        }
        int pathSize = path.size();
        for (int i = 1; i < pathSize; ++i) {
            Tree t0 = path.get(i - 1), t1 = path.get(i);

            List<Tree> children0 = t0.children.stream()
                    .filter(child -> !child.equals(t1))
                    .toList();
            Tree newTree0 = new Tree(t0.label, children0);
            path.set(i - 1, newTree0);

            ArrayList<Tree> newChildren1 = new ArrayList<>(t1.children);
            newChildren1.add(newTree0);
            newChildren1.sort(Comparator.comparing(t2 -> t2.label));
            Tree newTree1 = new Tree(t1.label, newChildren1);
            path.set(i, newTree1);
        }
        return path.get(pathSize - 1);
    }

    public List<String> pathTo(String fromNode, String toNode) {
        ArrayList<Tree> pathFrom = findPath(fromNode);
        if (pathFrom == null || pathFrom.isEmpty()) {
            throw new UnsupportedOperationException("No path found");
        }
        Tree fromTree = fromPov(fromNode);
        ArrayList<Tree> path = fromTree.findPath(toNode);
        if (path == null || path.isEmpty()) {
            throw new UnsupportedOperationException("No path found");
        }
        return path.stream()
                .map(tree -> tree.label)
                .toList();
    }

    private ArrayList<Tree> findPath(String fromNode) {
        if (label.equals(fromNode)) {
            return new ArrayList<Tree>(List.of(this));
        }
        for (Tree child : children) {
            List<Tree> path = child.findPath(fromNode);
            if (path != null) {
                ArrayList<Tree> result = new ArrayList<>();
                result.add(this);
                result.addAll(path);
                return result;
            }
        }
        return null;
    }
}