import java.util.*;

public class FordFulkersonMaxFlow{
    public static void main(String[] args){
        int n = 11;
        int s = n - 2;
        int t = n - 1;
        FordFulkerson graph = new FordFulkerson(n, s, t);

        graph.addEdge(s, 0, 10);
        graph.addEdge(s, 1, 5);
        graph.addEdge(s, 2, 10);

        graph.addEdge(0, 3, 10);
        graph.addEdge(1, 2, 10);
        graph.addEdge(2, 5, 15);
        graph.addEdge(3, 1, 20);
        graph.addEdge(3, 6, 15);
        graph.addEdge(4, 3, 3);
        graph.addEdge(4, 1, 15);
        graph.addEdge(5, 4, 4);
        graph.addEdge(5, 8, 10);
        graph.addEdge(6, 7, 10);
        graph.addEdge(7, 4, 10);
        graph.addEdge(7, 5, 7);
        
        graph.addEdge(6, t, 15);
        graph.addEdge(8, t, 10);

        System.out.println("Max Flow is : "+ graph.execute());
        System.out.println(graph);

    }
}

class FordFulkerson{
    List<List<Edge>> graph;
    int n;
    int[] visited;
    int visitedToken;
    int source, sink;
    int maxFlow;
    private static final int INFINITY = Short.MAX_VALUE;
    FordFulkerson(int n, int source, int sink){
        this.n = n;
        this.source = source;
        this.sink = sink;
        init();
    }

    private void init(){
        this.graph = new ArrayList<>();
        for(int i = 0; i < n; i++){
            this.graph.add(new ArrayList<>());
        }

        this.visited = new int[n];
        this.visitedToken = 1;
        
    }

    public void addEdge(int from, int to, int capacity){
        Edge e1 = new Edge(from, to, capacity);
        Edge e2 = new Edge(to, from, 0);
        e1.residual = e2;
        e2.residual = e1;
        this.graph.get(from).add(e1);
        this.graph.get(to).add(e2);
    }

    public int execute(){
        int f = 0;
        maxFlow = 0;
        do{
            f = dfs(source, INFINITY);
            visitedToken++;
            maxFlow += f;
            System.out.println(visitedToken+" "+ f);
        }while(f != 0);

        return maxFlow;
    }

    private int dfs(int at, int flow){
        if( at == sink) return flow;
        visited[at] = visitedToken;
        for(Edge edge: graph.get(at)){
            if(visited[edge.to] != visitedToken && edge.remainingCapacity() > 0){
                int bottleneck = dfs(edge.to, Math.min(flow, edge.remainingCapacity()));
                if(bottleneck > 0){
                    edge.augment(bottleneck);
                    return bottleneck;
                }
            }
        }
        return 0;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder()
        .append(String.format("MaxFlow: %3d", maxFlow))
        .append("\n Edges: \n");
        for(List<Edge> edges: graph){
            for(Edge edge: edges){
                if(edge.capacity > 0)
                    sb.append(edge.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}

class Edge{
    int from, to, flow, capacity;
    Edge residual;
    Edge(int from, int to, int capacity){
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = 0;
    }

    public int remainingCapacity(){
        return this.capacity - this.flow;
    }

    public void augment(int bottleneck){
        this.flow += bottleneck;
        this.residual.flow -= bottleneck;
    }

    public String toString(){
        return new StringBuilder()
        .append(String.format("From: %3d |", from))
        .append(String.format("To: %3d |", to))
        .append(String.format("Flow: %3d |", flow))
        .append(String.format("Capacity: %3d",capacity))
        .toString();
    }
}