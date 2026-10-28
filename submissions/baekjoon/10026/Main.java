import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N;
    static int[] dxs= {1,0,-1,0};
    static int[] dys= {0,1,0,-1};
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(bf.readLine());

        String[][] normalGrid = new String[N][N];
        String[][] notNormalGrid = new String[N][N];
        for(int i = 0; i < N; i++){

            String[] arr = bf.readLine().split("");
            for(int j = 0; j < arr.length; j++){
                normalGrid[i][j] = arr[j];
                if(arr[j].equals("G")){
                    notNormalGrid[i][j] = "R";
                }
                else {
                    notNormalGrid[i][j] = arr[j];
                }
            }
        }

        // 정상
        int normal = 0;
        boolean[][] visited = new boolean[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(!visited[i][j]){
                    bfs(i,j,normalGrid[i][j], normalGrid,visited);
                    normal += 1;
                }
            }
        }

        // 비정상
        int noNormal = 0;
        visited = new boolean[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(!visited[i][j]){
                    bfs(i,j,notNormalGrid[i][j], notNormalGrid,visited);
                    noNormal += 1;
                }
            }
        }

        System.out.println(normal + " " + noNormal);
    }

    static void bfs(int x, int y, String color, String[][] grid, boolean[][] visited){
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{x,y});
        visited[x][y] = true;


        while (!q.isEmpty()){
            int[] curr = q.poll();
            int cx = curr[0];
            int cy = curr[1];

            for(int i = 0; i < 4; i++){
                int nx = cx + dxs[i];
                int ny = cy + dys[i];
                if(inRange(nx,ny) && !visited[nx][ny] && grid[nx][ny].equals(color)){
                    visited[nx][ny] = true;
                    q.add(new int[]{nx,ny});
                }
            }
        }
    }

    static boolean inRange(int nx, int ny){
        return nx >=0 && nx < N && ny >=0 && ny < N;
    }
}
