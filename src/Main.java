import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N;
    static int M;
    static int[] dxs = {1,0,-1,0};
    static int[] dys = {0,1,0,-1};
    static class Node{
        int x;
        int y;
        int used;
        int cnt;

        Node(int x, int y, int used, int cnt){
            this.x = x;
            this.y = y;
            this.used = used;
            this.cnt = cnt;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int[][] grid = new int[N][M];
        for(int i = 0; i < N; i++){
            String[] arr = bf.readLine().split("");
            for(int j = 0; j < M; j++){
                grid[i][j] = Integer.parseInt(arr[j]);
            }
        }

        Queue<Node> q = new LinkedList<>();
        int[][][] dp = new int[N][M][2];
        dp[0][0][0] = 1;

        q.add(new Node(0,0,0,1));

        int answer = -1;
        while(!q.isEmpty()){
            Node curr = q.poll();
            int x = curr.x;
            int y = curr.y;
            int cnt = curr.cnt;
            int used = curr.used;
            if(x == N -1 && y == M - 1) {
                answer = cnt;
                break;
            };

            // 아직 벽을 안부섰을때
            if(used == 0){
                for(int i = 0; i < 4; i++){
                    int nx = x + dxs[i];
                    int ny = y + dys[i];
                    if(!inRange(nx,ny)) continue;
                    // 다음 칸이 벽이면
                    if(grid[nx][ny] == 1){
                        if(dp[nx][ny][1] == 0){
                            dp[nx][ny][1] = cnt + 1;
                            q.add(new Node(nx,ny,1, cnt + 1));
                        }
                    }
                    else{
                        if(dp[nx][ny][used] == 0){
                            dp[nx][ny][0] = cnt + 1;
                            q.add(new Node(nx,ny,0, cnt + 1));
                        }
                    }
                }
            }
            // 이미 벽을 부섰으면
            else{
                for(int i = 0; i < 4; i++){
                    int nx = x + dxs[i];
                    int ny = y + dys[i];
                    if(!inRange(nx,ny) || grid[nx][ny] == 1) continue;
                    if(dp[nx][ny][1] == 0){
                        dp[nx][ny][1] = cnt + 1;
                        q.add(new Node(nx,ny,1,cnt + 1));
                    }
                }
            }
        }

        System.out.println(answer);
    }

    static boolean inRange(int nx, int ny){
        return nx >= 0 && nx < N && ny >= 0 && ny < M;
    }
}
