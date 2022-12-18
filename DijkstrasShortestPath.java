import java.util.*;

public class DijkstrasShortestPath {
    static List<List<Edge>> adjList = new ArrayList<>();

    public static void main(String[] args){
        input();
        
        int[][] result = dijkstras(0, adjList.size() - 1);
        
        System.out.println(Arrays.toString(result[0]));
        System.out.println(Arrays.toString(result[1]));
    }

    private static int[][] dijkstras(int start, int end){
        boolean[] visited = new boolean[adjList.size()];
        int[] dist = new int[adjList.size()];
        int[] path = new int[adjList.size()];
        Arrays.fill(dist, Short.MAX_VALUE);
        dist[start] = 0;
        Queue<Pair> pq = new PriorityQueue<>();
        pq.offer(new Pair(start, 0));

        while(!pq.isEmpty()){
            Pair pair = pq.poll();
            visited[pair.index] = true;

            if(dist[pair.index] < pair.dist) continue;

            for(Edge e: adjList.get(pair.index)){
                if(visited[e.to]) continue;
                int newDist = dist[pair.index] + e.weight;
                if(newDist < dist[e.to]){
                    dist[e.to] = newDist;
                    path[e.to] = pair.index;
                    pq.offer(new Pair(e.to, newDist));
                }
            }
        }

        return new int[][]{dist,path};
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
            adjList.get(source).add(new Edge(dest, wt));
        }
        sc.close();
    }
}
class Edge{
    int to;
    int weight;
    Edge(int to, int weight){
        this.to = to;
        this.weight = weight;
    }
}

class Pair implements Comparable<Pair>{
    int index;
    int dist;

    Pair(int index, int dist){
        this.index = index;
        this.dist = dist;
    }

    public int compareTo(Pair p){
        return this.dist - p.dist;
    }
}