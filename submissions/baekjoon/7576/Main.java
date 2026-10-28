import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dxs = {1,0,-1,0};
    static int[] dys = {0,1,0,-1};
    static int N;
    static int M;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        int[][] grid = new int[N][M];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(bf.readLine());
            for(int j = 0; j < M; j++){
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Queue<int[]> q = new LinkedList<>();
        // 최초 위치 초기화
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(grid[i][j] == 1){
                    q.add(new int[]{i,j});
                }
            }
        }

        while(!q.isEmpty()){
            int[] curr = q.poll();
            int x = curr[0];
            int y = curr[1];

            for(int i = 0; i < 4; i++){
                int nx = x + dxs[i];
                int ny = y + dys[i];
                if(inRange(nx,ny) && grid[nx][ny] == 0){
                    grid[nx][ny] = grid[x][y] + 1;
                    q.add(new int[]{nx,ny});
                }
            }
        }

        int answer = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                answer = Math.max(answer, grid[i][j]);
                if(grid[i][j] == 0){
                    System.out.println(-1);
                    return;
                }
            }
        }
        System.out.println(answer -1);

    }

    private static boolean inRange(int nx, int ny){
        return nx >= 0 && nx < N && ny >= 0 && ny < M;
    }
}
