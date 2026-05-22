import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Graph {

    private final int vertices;
    private final List<List<Edge>> adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int src, int dest, int weight) {
        adjacencyList.get(src).add(new Edge(dest, weight));
    }

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
