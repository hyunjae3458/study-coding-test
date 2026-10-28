import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dxs = {1,0,-1,0};
    static int[] dys = {0,1,0,-1};
    static int N;
    static int M;
    static int answer = Integer.MIN_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int[][] grid = new int[N][M];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(bf.readLine());
            for(int j = 0; j < M; j++){
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(3, 0, grid,0);
        System.out.println(answer);

    }

    static void dfs(int target, int curr, int[][] grid, int pos){
        if(curr == target){
            Queue<int[]> q = new LinkedList<>();
            initQueue(q, grid);
            answer = Math.max(answer, bfs(grid, q));
            return;
        }

        for(int start = pos; start < N*M; start++){
            int i = start / M;
            int j = start % M;
            if(grid[i][j] == 0){
                grid[i][j] = 1;
                dfs(target, curr + 1, grid, start + 1);
                grid[i][j] = 0;
            }
        }
    }

    static void initQueue(Queue<int[]> q, int[][] grid){
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(grid[i][j] == 2){
                    q.add(new int[]{i,j});
                }
            }
        }
    }

    static boolean inRange(int nx, int ny){
        return nx >= 0 && nx < N && ny >= 0 && ny < M;
    }

    static int bfs(int[][] grid, Queue<int[]> q){
        int[][] copy = new int[N][M];
        for(int i = 0; i < N; i++){
            copy[i] = grid[i].clone();
        }

        while(!q.isEmpty()){
            int[] curr = q.poll();
            int x = curr[0];
            int y = curr[1];

            for(int i = 0; i < 4; i++){
                int nx = x + dxs[i];
                int ny = y + dys[i];
                if(inRange(nx,ny) && copy[nx][ny] == 0){
                    copy[nx][ny] = 2;
                    q.add(new int[]{nx,ny});
                }
            }
        }
        int cnt = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(copy[i][j] == 0) cnt += 1;
            }
        }

        return cnt;
    }
}
