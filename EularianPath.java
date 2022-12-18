import java.util.*;

public class EularianPath{
    static List<List<Edge>> adjList = new ArrayList<>();
    static int n;
    static int e;

    static int[] in;
    static int[] out;
    static LinkedList<Integer> path = new LinkedList<>();
    
    public static void main(String[] args){
        input();
        init();
        path = findEularianPath();
        output();
    }

    private static void init(){
        in = new int[n];
        out = new int[n];
    }

    private static LinkedList<Integer> findEularianPath(){
        countInOutDegrees();
        //If Graph does not have any Eularian Path
        if( ! graphHasEularianPath()){
            return null;
        }

        int start = findStartNode();
        dfs(start);

        if(path.size() == e + 1) return path;
        else return null;
    }

    private static void countInOutDegrees(){
        for(List<Edge> edges : adjList){
            for(Edge edge: edges){
                in[edge.to]++;
                out[edge.from]++;
            }
        }
    }

    private static boolean graphHasEularianPath(){
        int startNodes = 0;
        int endNodes = 0;

        for(int i = 0 ; i < n; i++){
            if(in[i] - out[i] > 1 || out[i] - in[i] > 1){
                return false;
            } else if(out[i] - in[i] == 1){
                endNodes++;
            } else if(in[i] - out[i] == 1){
                startNodes++;
            }
        }
        return  ( startNodes == 0 && endNodes == 0 ) ||
                ( startNodes == 1 && endNodes == 1 );

    }

    private static int findStartNode(){
        int start = 0;
        for(int i = 0; i < n; i++){
            if(out[i] - in[i] == 1) return i;
            if(out[i] > 0) start = i;
        }
        return start;
    }

    private static void dfs(int at){
        while(out[at] > 0){
            Edge e = adjList.get(at).get(--out[at]);
            dfs(e.to);
        }
        path.addFirst(at);
    }
    
    private static void input(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter no. of Vertices");
        n = sc.nextInt();
        for(int i = 0 ; i < n; i++){
            adjList.add(new ArrayList<>());
        }

        System.out.println("Enter No. of Edges");
        e = sc.nextInt();
        
        System.out.println("Enter Source Destination");
        for(int i = 0 ; i < e; i++){
            int source = sc.nextInt();
            int dest = sc.nextInt();
            adjList.get(source).add(new Edge(source, dest));
        }
        sc.close();
    }

    private static void output(){
        System.out.println(path);
    }
}

class Edge{
    int from, to;
    Edge(int from, int to){
        this.from = from;
        this.to = to;
    }
}