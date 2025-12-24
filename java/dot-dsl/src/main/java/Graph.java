import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class Graph {
    Map<String, String> attributes;
    Collection<Edge> edges = new ArrayList<>();
    Collection<Node> nodes = new ArrayList<>();

    public Graph() {
        this(Map.of());
    }

    public Graph(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Collection<Node> getNodes() {
        return nodes;
    }

    public Collection<Edge> getEdges() {
        return edges;
    }

    public Graph node(String name) {

        return this.node(name, Map.of());
    }

    public Graph node(String name, Map<String, String> attributes) {
        nodes.add(new Node(name, attributes));
        return this;
    }

    public Graph edge(String start, String end) {
        return this.edge(start, end, Map.of());
    }

    public Graph edge(String start, String end, Map<String, String> attributes) {
        edges.add(new Edge(start, end, attributes));
        return this;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}
