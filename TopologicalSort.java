import java.util.*;

public class TopologicalSort{

    static List<List<Edge>> adjList = new ArrayList<>();
    static int n;
    static int counter;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter no. of Vertices");
        n = sc.nextInt();
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
        int[] topoSort = topoSort();
        System.out.println(Arrays.toString(topoSort));
        sc.close();
    }

    static boolean[] visited;
    private static int[] topoSort(){
        int[] order = new int[adjList.size()];
        counter = n - 1;
        visited = new boolean[n];
        for(int i = 0 ; i < n; i++){
            if(visited[i] == false){
                dfs(i, order);
            }
        }
        return order;
    }

    private static void dfs(int at, int[] order){
        visited[at] = true;

        for(Edge edge : adjList.get(at)){
            if(visited[edge.to] == false){
                dfs(edge.to, order);
            }
        }

        order[counter--] = at;
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