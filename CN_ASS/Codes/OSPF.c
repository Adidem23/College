#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <limits.h>

#define MAX_VERTICES 5

struct Edge {
    int dest;
    int weight;
    struct Edge* next;  // Added member to represent the next edge in the adjacency list.
};


struct OSPF {
    int V;
    struct Edge* adj[MAX_VERTICES];
    int dist[MAX_VERTICES];
    int predecessor[MAX_VERTICES];
    bool visited[MAX_VERTICES];
    struct {
        int first, second;
    } pq[MAX_VERTICES];
    int pq_size;
};

void exploreNeighbors(struct OSPF* ospf, int u);

void init(struct OSPF* ospf, int vertices) {
    ospf->V = vertices;
    for (int i = 0; i < MAX_VERTICES; i++) {
        ospf->adj[i] = NULL;
        ospf->dist[i] = INT_MAX;
        ospf->predecessor[i] = -1;
        ospf->visited[i] = false;
    }
    ospf->pq_size = 0;
}

void addEdge(struct OSPF* ospf, int src, int dest, int weight) {
    struct Edge* edge = (struct Edge*)malloc(sizeof(struct Edge));
    edge->dest = dest;
    edge->weight = weight;
    edge->next = ospf->adj[src];
    ospf->adj[src] = edge;

    edge = (struct Edge*)malloc(sizeof(struct Edge));
    edge->dest = src;
    edge->weight = weight;
    edge->next = ospf->adj[dest];
    ospf->adj[dest] = edge;
}

void dijkstra(struct OSPF* ospf, int src) {
    ospf->dist[src] = 0;
    ospf->pq[ospf->pq_size].first = 0;
    ospf->pq[ospf->pq_size].second = src;
    ospf->pq_size++;

    while (ospf->pq_size > 0) {
        int u = ospf->pq[0].second;
        ospf->pq[0] = ospf->pq[ospf->pq_size - 1];
        ospf->pq_size--;

        if (!ospf->visited[u]) {
            ospf->visited[u] = true;
            exploreNeighbors(ospf, u);
        }
    }
}

void exploreNeighbors(struct OSPF* ospf, int u) {
    for (struct Edge* edge = ospf->adj[u]; edge != NULL; edge = edge->next) {
        int v = edge->dest;
        int d = edge->weight;
        int nd = ospf->dist[u] + d;

        if (nd < ospf->dist[v]) {
            ospf->dist[v] = nd;
            ospf->predecessor[v] = u;
            ospf->pq[ospf->pq_size].first = ospf->dist[v];
            ospf->pq[ospf->pq_size].second = v;
            ospf->pq_size++;
        }
    }
}

void printPath(struct OSPF* ospf, int source, int destination) {
    printf("####Shortest path from Router %d to Router %d\n\t", source, destination);
    int path[MAX_VERTICES];
    int current = destination;
    int path_size = 0;

    while (current != -1) {
        path[path_size] = current;
        path_size++;
        current = ospf->predecessor[current];
    }

    for (int i = path_size - 1; i >= 0; i--) {
        printf("Router %d", path[i]);
        if (i > 0) {
            printf(" -> ");
        }
    }
    printf("\n\tThe path cost is %d\n\n", ospf->dist[destination]);
}

int main() {
    struct OSPF routers;
    init(&routers, MAX_VERTICES);

    addEdge(&routers, 0, 1, 2);
    addEdge(&routers, 0, 2, 1);
    addEdge(&routers, 0, 3, 6);
    addEdge(&routers, 0, 4, 8);
    addEdge(&routers, 1, 4, 4);
    addEdge(&routers, 1, 3, 3);
    addEdge(&routers, 2, 3, 5);
    addEdge(&routers, 2, 1, 1);
    addEdge(&routers, 2, 4, 2);
    dijkstra(&routers, 3);

    for (int i = 0; i < MAX_VERTICES; i++)
        printPath(&routers, 3, i);

    return 0;
}
