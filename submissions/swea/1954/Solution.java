import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.*;
class Solution {
    static int[] dxs = {0,1,0,-1};
    static int[] dys = {1,0,-1,0};
    public static void main(String args[]) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(bf.readLine());

        for (int testCase = 1; testCase <= T; testCase++) {
            int N = Integer.parseInt(bf.readLine());
            int[][] grid = new int[N][N];
            grid[0][0] = 1;
            int number = 2;
            int remain = N*N - 1;
            int x = 0;
            int y = 0;
            int dir = 0;

            while(remain > 0){
                int nx = x + dxs[dir];
                int ny = y + dys[dir];
                if(!inRange(nx,ny,N) || grid[nx][ny] != 0){
                    dir = (dir + 1) % 4;
                    continue;
                }
                grid[nx][ny] = number;
                number += 1;
                x = nx;
                y = ny;
                remain -= 1;
            }

            System.out.println("#" + testCase);
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    System.out.print(grid[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    static boolean inRange(int nx, int ny, int N){
        return nx >= 0 && nx < N && ny >= 0 && ny < N;
    }
}
