import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Weighted directed graph using an adjacency list.
 * Supports Dijkstra's shortest-path algorithm.
 */
public class Graph {

    private final int vertices;
    private final List<List<Edge>> adjacencyList;

    /**
     * Constructs a graph with the given number of vertices.
     *
     * @param vertices total number of vertices (0-indexed)
     */
    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    /**
     * Adds a directed weighted edge from {@code src} to {@code dest}.
     *
     * @param src    source vertex
     * @param dest   destination vertex
     * @param weight non-negative edge weight
     */
    public void addEdge(int src, int dest, int weight) {
        adjacencyList.get(src).add(new Edge(dest, weight));
    }

    /**
     * Runs Dijkstra's algorithm from the given starting vertex and prints
     * the shortest distance from {@code start} to every other vertex.
     *
     * <p>Time complexity: O(V²) — uses a simple array scan instead of a
     * priority queue, which is sufficient for dense or small graphs.
     *
     * @param start the source vertex (0-indexed)
     */
    public void dijkstra(int start) {
        int[] dist    = new int[vertices];   // shortest known distance from start
        boolean[] visited = new boolean[vertices]; // whether vertex is finalized

        // Initialize all distances to "infinity"
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        for (int iteration = 0; iteration < vertices; iteration++) {

            // 1. Pick the unvisited vertex with the smallest tentative distance
            int u = -1;
            for (int v = 0; v < vertices; v++) {
                if (!visited[v] && (u == -1 || dist[v] < dist[u])) {
                    u = v;
                }
            }

            // If the smallest distance is still infinity, remaining vertices
            // are unreachable — stop early.
            if (dist[u] == Integer.MAX_VALUE) break;

            visited[u] = true;

            // 2. Relax all edges leaving u
            for (Edge edge : adjacencyList.get(u)) {
                int newDist = dist[u] + edge.weight;
                if (newDist < dist[edge.destination]) {
                    dist[edge.destination] = newDist;
                }
            }
        }

        // 3. Print results
        printResults(start, dist);
    }

    /**
     * Prints the shortest-path distances in a formatted table.
     */
    private void printResults(int start, int[] dist) {
        System.out.println("Dijkstra's Shortest Path from vertex " + start);
        System.out.println("------------------------------------------");
        System.out.printf("%-10s %-15s%n", "Vertex", "Distance");
        System.out.println("------------------------------------------");
        for (int v = 0; v < vertices; v++) {
            String distance = (dist[v] == Integer.MAX_VALUE) ? "Unreachable" : String.valueOf(dist[v]);
            System.out.printf("%-10d %-15s%n", v, distance);
        }
        System.out.println("------------------------------------------");
    }

    // ---------------------------------------------------------------
    // Quick demo / manual test
    // ---------------------------------------------------------------
    public static void main(String[] args) {
        /*
         * Example graph (6 vertices):
         *
         *   0 --1--> 1 --4--> 3
         *   |        |        |
         *   4        2        1
         *   |        v        v
         *   2 --3--> 3 <--1-- 4
         *            |
         *            5
         *            v
         *            5
         *
         * Expected shortest distances from vertex 0:
         *   0 -> 0 : 0
         *   0 -> 1 : 1
         *   0 -> 2 : 4
         *   0 -> 3 : 3   (0→1→3 via weight 1+2=3)
         *   0 -> 4 : 5   (0→1→2→4 or 0→1→3→4)
         *   0 -> 5 : 8   (0→1→3→5)
         */
        Graph g = new Graph(6);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 4);
        g.addEdge(1, 2, 3);
        g.addEdge(1, 3, 2);
        g.addEdge(1, 4, 6);
        g.addEdge(2, 3, 1);
        g.addEdge(3, 4, 2);
        g.addEdge(3, 5, 5);
        g.addEdge(4, 5, 3);

        g.dijkstra(0);
    }
}