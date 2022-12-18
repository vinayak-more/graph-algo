import java.util.*;

//Tarjans strongly Connected components
public class TarjanSCC {
    static List<List<Integer>> adjList = new ArrayList<>();
    static int n; //number of nodes

    public static void main(String[] args){
        input();
        init();
        findScc();
        output();
    }

    static int id = 0;
    static int sccCount = 0;
    static int[] ids, low;
    static boolean[] onStack;
    static Stack<Integer> stack;
    static final int UNVISITED = -1;

    private static void init(){
        ids = new int[n];
        Arrays.fill(ids, UNVISITED);

        low = new int[n];
        Arrays.fill(low, UNVISITED);

        onStack = new boolean[n];

        stack = new Stack<Integer>();

    }

    private static void findScc(){
        for(int i = 0 ; i < n ; i++){
            if(ids[i] == UNVISITED){
                dfs(i);
            }
        }
    }

    private static void dfs(int at){
        stack.push(at);
        onStack[at] = true;
        ids[at] = low[at] = id++;

        for(int to : adjList.get(at)){
            if(ids[to] == UNVISITED){
                dfs(to);
            }
            if(onStack[to]){
                low[at] = Math.min(low[to],low[at]);
            }
        }

        if(low[at] == ids[at]){
            for(int node = stack.pop(); ; node = stack.pop()){
                onStack[node] = false;
                if(node == at){
                    break;
                }
            }
            sccCount++;
        }
    }

    private static void input(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter no. of Vertices");
        n = sc.nextInt();

        for(int i = 0 ; i < n; i++){
            adjList.add(new ArrayList<>());
        }

        System.out.println("Enter No. of Edges");
        int e = sc.nextInt();

        for(int i = 0 ; i < e; i++){
            int source = sc.nextInt();
            int dest = sc.nextInt();
            adjList.get(source).add(dest);
        }
        System.out.println(adjList);
        sc.close();
    }

    private static void output(){
        System.out.println("IDs:");
        System.out.println(Arrays.toString(ids));
        System.out.println("Low link values:");
        System.out.println(Arrays.toString(low));
        System.out.println("SCC Count:" + sccCount);
    }

}