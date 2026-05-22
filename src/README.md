# Bonus Task – Dijkstra's Shortest Path Algorithm

## Overview

This bonus task extends the existing weighted graph implementation with
**Dijkstra's Algorithm**, which computes the shortest path from a chosen
starting vertex to **every other vertex** in the graph.

---

## Files Added / Modified

| File | Change |
|------|--------|
| `Edge.java` | Added `weight` field and updated constructor |
| `Graph.java` | Added adjacency-list with weights; implemented `dijkstra(int start)` |

---

## How It Works

### 1. Data Structures

```
int[]     dist[]    – shortest known distance from start to each vertex
                      (initialised to Integer.MAX_VALUE = "infinity")
boolean[] visited[] – marks vertices whose shortest path is finalised
```

### 2. Algorithm Steps (O(V²) array-scan variant)

```
1. Set dist[start] = 0; all others = ∞
2. Repeat V times:
   a. Pick the unvisited vertex u with the smallest dist[u]
   b. Mark u as visited (finalised)
   c. For every edge (u → v, weight w):
        if dist[u] + w < dist[v]:
            dist[v] = dist[u] + w   // "relax" the edge
3. Print dist[] — these are the shortest distances
```

No priority queue is used; a simple linear scan finds the minimum each
iteration, keeping the implementation straightforward.

### 3. Edge Weights

The `Edge` class now carries a `weight` field:

```java
public class Edge {
    public int destination;
    public int weight;

    public Edge(int destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }
}
```

Edges are added to the graph with:

```java
graph.addEdge(src, dest, weight);
```

### 4. Output

Running `dijkstra(0)` on the sample graph prints:

```
Dijkstra's Shortest Path from vertex 0
------------------------------------------
Vertex     Distance
------------------------------------------
0          0
1          1
2          4
3          3
4          5
5          8
------------------------------------------
```

---

## Sample Graph Used for Testing

```
0 --1--> 1 --2--> 3 --5--> 5
|        |        |         ^
4        3        2         |
|        v        v         |
2 -------> 3 --> 4 ---3-----+
```

Edges:
```
0→1 (1),  0→2 (4)
1→2 (3),  1→3 (2),  1→4 (6)
2→3 (1)
3→4 (2),  3→5 (5)
4→5 (3)
```

---

## Limitations & Possible Extensions

- The current implementation handles **directed** graphs with
  **non-negative** weights (a requirement of Dijkstra's algorithm).
- For **undirected** graphs, call `addEdge` in both directions.
- Replacing the linear-scan minimum with a `PriorityQueue` would improve
  performance to **O((V + E) log V)** for large sparse graphs.
- Negative weights require **Bellman-Ford** instead.
