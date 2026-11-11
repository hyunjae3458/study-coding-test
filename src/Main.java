import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.*;

public class Main {
    static class Node{
        int node;
        int cost;
        Node(int node, int cost){
            this.node = node;
            this.cost = cost;
        }
    }
    static int N;
    static int E;
    static boolean possilbe = true;
    static List<Node>[] graph;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        graph = new ArrayList[N+1];
        for(int i = 0; i < N + 1; i++){
            graph[i] = new ArrayList<>();
        }

        // 그래프 생성
        for(int i = 0; i < E; i++){
            st = new StringTokenizer(bf.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph[start].add(new Node(end, cost));
            graph[end].add(new Node(start, cost));
        }
        st = new StringTokenizer(bf.readLine());
        int v1 = Integer.parseInt(st.nextToken());
        int v2 = Integer.parseInt(st.nextToken());

        // 중간지점끼리 가장 가까운 길이 구하기
        int vDist = dijkstra(v1, v2);
        // 출발지에서 종착지까지 출발지 - 중간지점 - 중간지점 - 도착지 순서에 따른 다른 값구하기
        int dist1 = dijkstra(1, v1) + vDist + dijkstra(v2, N);
        int dist2 = dijkstra(1, v2) + vDist + dijkstra(v1, N);

        if(!possilbe) System.out.println(-1);
        else System.out.println(Math.min(dist1, dist2));

    }

    static int dijkstra(int start, int end){
        PriorityQueue<Node> pq = new PriorityQueue<>((a,b) -> Integer.compare(a.cost, b.cost));
        int[] dp = new int[N + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);

        dp[start] = 0;
        pq.add(new Node(start, 0));

        while(!pq.isEmpty()){
            Node curr = pq.poll();
            int node = curr.node;
            int cost = curr.cost;
            if(dp[node] < cost) continue;

            for(int i = 0; i < graph[node].size(); i++){
                Node nextNode = graph[node].get(i);
                int next = nextNode.node;
                int nextCost = nextNode.cost;
                if(dp[next] > cost + nextCost){
                    dp[next] = cost + nextCost;
                    pq.add(new Node(next, dp[next]));
                }
            }
        }
        if(dp[end] == Integer.MAX_VALUE) possilbe = false;
        return dp[end];
    }
}
