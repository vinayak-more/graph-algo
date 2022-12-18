import java.util.*;


public class BellmanFord {
    static List<List<Edge>> adjList = new ArrayList<>();

    public static void main(String[] args){
        input();

        double[][] result = bellmanFord();
        System.out.println(Arrays.toString(result[0]));
        System.out.println(Arrays.toString(result[1]));

    }

    private static double[][] bellmanFord(){
        double[] dist = new double[adjList.size()];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[0] = 0d;
        double[] path = new double[adjList.size()];

        for(int i = 0; i < adjList.size(); i++){
            for(int j = 0 ; j < adjList.size(); j++){
                for(Edge edge: adjList.get(j)){
                    if(dist[edge.from] + edge.cost < dist[edge.to]){
                        dist[edge.to] = dist[edge.from] + edge.cost;
                        path[edge.to] = edge.from;
                    }
                }
            }
        }

        for(int i = 0; i < adjList.size(); i++){
            for(int j = 0 ; j < adjList.size(); j++){
                for(Edge edge: adjList.get(j)){
                    if(dist[edge.from] + edge.cost < dist[edge.to]){
                        dist[edge.to] = Double.NEGATIVE_INFINITY;
                    }
                }
            }
        }

        return new double[][] {dist, path};

    }
    private static void input(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter no. of Vertices");
        int n = sc.nextInt();
        for(int i = 0 ; i < n; i++){
            adjList.add(new ArrayList<>());
        }

        System.out.println("Enter No. of Edges");
        int e = sc.nextInt();

        for(int i = 0 ; i < e; i++){
            System.out.println("Source Destination Weight");
            int source = sc.nextInt();
            int dest = sc.nextInt();
            int wt = sc.nextInt();
            adjList.get(source).add(new Edge(source, dest, wt));
        }
        sc.close();
    }
}
class Edge{
    int from, to;
    int cost;
    Edge(int from, int to, int cost){
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}
