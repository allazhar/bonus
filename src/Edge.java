/**
 * Represents a weighted directed edge in a graph.
 */
public class Edge {
    public int destination;
    public int weight;

    public Edge(int destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }
}