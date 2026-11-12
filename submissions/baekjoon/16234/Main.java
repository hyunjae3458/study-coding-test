import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dxs = {1,0,-1,0};
    static int[] dys = {0,1,0,-1};
    static Map<Integer, Node> map;
    static int[][] dp;
    static int[][] grid;
    static int N;
    static int L;
    static int R;
    static class Node{
        int size;
        int sum;
        Node(int size, int sum){
            this.size = size;
            this.sum = sum;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        dp = new int[N][N];
        grid = new int[N][N];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(bf.readLine());
            for(int j = 0; j < N; j++){
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;
         while(true){
             map = new HashMap<>();
             dp = new int[N][N];
             int cnt = 1;
             for(int i = 0; i < N; i++){
                 for(int j = 0; j < N; j++){
                     if(dp[i][j] == 0){
                         bfs(i,j,cnt);
                         cnt += 1;
                     }

                 }
             }

             if(map.isEmpty()) break;
             for(int i = 0; i < N; i++){
                 for(int j = 0; j < N; j++){
                     if(map.containsKey(dp[i][j])){
                         grid[i][j] = map.get(dp[i][j]).sum / map.get(dp[i][j]).size;
                     }
                 }
             }
             answer += 1;

         }

        System.out.println(answer);
    }

    static void bfs(int x, int y, int cnt){
        Queue<int[]> q = new LinkedList<>();
        dp[x][y] = cnt;
        q.add(new int[]{x,y});

        while(!q.isEmpty()){
            int[] curr = q.poll();
            int currX = curr[0];
            int currY = curr[1];

            for(int i = 0; i < 4; i++){
                int nx = currX + dxs[i];
                int ny = currY + dys[i];
                if(inRange(nx,ny) && dp[nx][ny] == 0 && check(grid[currX][currY], grid[nx][ny])){
                    dp[nx][ny] = cnt;
                    q.add(new int[]{nx,ny});
                    if(!map.containsKey(cnt)){
                        map.put(cnt, new Node(1,grid[x][y]));
                    }
                    map.get(cnt).size += 1;
                    map.get(cnt).sum += grid[nx][ny];
                }
            }

        }
    }

    static boolean check(int c, int n){
        return Math.abs(c-n) >= L && Math.abs(c-n) <= R;
    }

    static boolean inRange(int nx, int ny){
        return nx >= 0 && nx < N && ny >= 0 && ny < N;
    }
}
