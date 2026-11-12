import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Node{
        int start;
        int end;
        Node(int start, int end){
            this.start = start;
            this.end = end;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(bf.readLine());
        for(int t = 0; t < T; t++){
            StringTokenizer st = new StringTokenizer(bf.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            // 건물짓는 시간
            int[] times = new int[N + 1];

            st = new StringTokenizer(bf.readLine());
            for(int i = 0; i < N; i++){
                times[i + 1] = Integer.parseInt(st.nextToken());
            }

            List<Integer>[] graph = new ArrayList[N + 1];
            for(int i = 0; i < N + 1; i++){
                graph[i] = new ArrayList<>();
            }
            int[] indegree = new int[N + 1];
            for(int i = 0; i < K; i++){
                st = new StringTokenizer(bf.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());

                graph[start].add(end);
                indegree[end] += 1;
            }

            int W = Integer.parseInt(bf.readLine());

            Queue<Integer> q = new LinkedList<>();
            int[] dp = new int[N + 1];
            for(int i = 1; i < N + 1; i++){
                if (indegree[i] == 0) {
                    q.add(i);
                    dp[i] = times[i];
                }
            }

            while(!q.isEmpty()){
                int curr = q.poll();
                if(curr == W){
                    break;
                }
                for(int i = 0; i < graph[curr].size(); i++){
                    int next = graph[curr].get(i);
                    if(dp[next] < times[next] + dp[curr]){
                        dp[next] = times[next] + dp[curr];
                    }
                    indegree[next] -= 1;
                    if (indegree[next] == 0) {
                        q.add(next);
                    }
                }
            }
            System.out.println(dp[W]);
        }

    }
}
