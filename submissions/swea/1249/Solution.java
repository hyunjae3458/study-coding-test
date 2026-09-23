import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseCount = Integer.parseInt(br.readLine());
        StringBuilder output = new StringBuilder();

        for (int testCase = 1; testCase <= testCaseCount; testCase++) {
            int size = Integer.parseInt(br.readLine());
            int[][] map = new int[size][size];

            for (int row = 0; row < size; row++) {
                String line = br.readLine();
                for (int column = 0; column < size; column++) {
                    map[row][column] = line.charAt(column) - '0';
                }
            }

            Solution solver = new Solution();
            int answer = solver.solution(size, map);
            output.append('#').append(testCase).append(' ').append(answer).append('\n');
        }

        System.out.print(output);
    }

    /**
     * @param size 지도의 크기 N (입력값 그대로)
     * @param map 각 칸의 복구 시간인 N×N 지도 (입력 숫자 그대로)
     * @return (0, 0)에서 (N-1, N-1)까지 이동할 때 필요한 최소 복구 시간
     */
    class Node{
        int x;
        int y;
        int cost;
        public Node(int x, int y, int cost){
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
    int N;
    int[] dxs = {1,0,-1,0};
    int[] dys = {0,1,0,-1};
    public int solution(int size, int[][] map) {
        N = size;
        PriorityQueue<Node> q = new PriorityQueue<>((a,b) -> Integer.compare(a.cost, b.cost));
        int[][] dp = new int[N][N];

        for(int[] d : dp){
            Arrays.fill(d, Integer.MAX_VALUE);
        }

        dp[0][0] = 0;
        q.add(new Node(0,0,0));

        while(!q.isEmpty()){
            Node curr = q.poll();
            int x = curr.x;
            int y = curr.y;
            int cost = curr.cost;
            if(dp[x][y] < cost) continue;

            for(int i = 0; i < 4; i++){
                int nx = x + dxs[i];
                int ny = y + dys[i];
                if(inRange(nx,ny)){
                    if(dp[nx][ny] > cost + map[nx][ny]){
                        dp[nx][ny] = cost + map[nx][ny];
                        q.add(new Node(nx,ny,dp[nx][ny]));
                    }
                }
            }
        }
        return dp[N-1][N-1];
    }

    private boolean inRange(int nx ,int ny){
        return nx >= 0 && nx < N && ny >= 0 && ny < N;
    }
}
