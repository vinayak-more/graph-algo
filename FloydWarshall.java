import java.util.*;

public class FloydWarshall{
	static List<List<Edge>> adjList = new ArrayList<>();
	
	static double[][] dp;
	static double[][] next;
	static int n;
	static final double POSITIVE_INFINITY = Double.POSITIVE_INFINITY;
	static final double NEGATIVE_INFINITY = Double.NEGATIVE_INFINITY;
	public static void main(String[] args){
		input();
		setup();
		floydWarshall();
		propogateNegativeCycle();
		
		System.out.println("DP Matric");
		for(int i = 0; i < n; i++){
			System.out.println(Arrays.toString(dp[i]));
		}
		
		System.out.println("Next Matrix");
		for(int i = 0; i < n; i++){
			System.out.println(Arrays.toString(next[i]));
		}
	}
	
	private static void floydWarshall(){
		for(int k = 0 ; k < n; k++){
			for(int i = 0 ; i < n ; i++){
				for(int j = 0; j < n; j++){
					if(dp[i][k] + dp[k][j] < dp[i][j]){
						dp[i][j] = dp[i][k] + dp[k][j];
						next[i][j] = next[i][k];
					}
				}
			}
		}
	}
	
	private static void propogateNegativeCycle(){
		for(int k = 0 ; k < n; k++){
			for(int i = 0 ; i < n ; i++){
				for(int j = 0; j < n; j++){
					if(dp[i][k] + dp[k][j] < dp[i][j]){
						dp[i][j] = NEGATIVE_INFINITY;
						next[i][j] = -1;
					}
				}
			}
		}
	}
	
	private static void setup(){
		dp = new double[n][n];
		next = new double[n][n];
		
		//Initialize with positive infinity
		for(int i = 0; i < n; i++){
			Arrays.fill(dp[i], POSITIVE_INFINITY);
			dp[i][i] = 0;
		}
		
		//fillup dp matrix with edge cost
		for(int i = 0; i < n; i++){
			for(Edge edge : adjList.get(i)){
				dp[edge.from][edge.to] = edge.cost;
				next[edge.from][edge.to] = edge.to;
			}
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
            //System.out.println("Source Destination Weight");
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
