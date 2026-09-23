import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseCount = Integer.parseInt(br.readLine());
        StringBuilder output = new StringBuilder();

        for (int testCase = 1; testCase <= testCaseCount; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int size = Integer.parseInt(st.nextToken());
            int maxCutDepth = Integer.parseInt(st.nextToken());
            int[][] map = new int[size][size];

            for (int row = 0; row < size; row++) {
                st = new StringTokenizer(br.readLine());
                for (int column = 0; column < size; column++) {
                    map[row][column] = Integer.parseInt(st.nextToken());
                }
            }

            Solution solver = new Solution();
            int answer = solver.solution(size, maxCutDepth, map);
            output.append('#').append(testCase).append(' ').append(answer).append('\n');
        }

        System.out.print(output);
    }

    /**
     * @param size 지도의 한 변의 길이 N (입력값 그대로)
     * @param maxCutDepth 최대 공사 가능 깊이 K (입력값 그대로)
     * @param map N×N 지형 높이 지도 (입력 순서와 값 그대로)
     * @return 만들 수 있는 가장 긴 등산로의 길이
     */
    int[][][] dp;
    int[] dxs = {1,0,-1,0};
    int[] dys = {0,1,0,-1};
    int N;
    boolean[][] visited;
    int answer = 0;
    public int solution(int size, int maxCutDepth, int[][] map) {
        visited = new boolean[size][size];
        N = size;
        dp = new int[N][N][maxCutDepth + 1];
        int highest = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                highest = Math.max(highest, map[i][j]);
            }
        }

        // q초기화
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(highest == map[i][j]){
                    visited[i][j] = true;
                    dfs(i,j,1,false,map[i][j],map,maxCutDepth);
                    visited[i][j] = false;
                }
            }
        }

        return answer;
    }

    private void dfs(int x, int y, int length, boolean used, int currHeight, int[][] map, int K) {
        answer = Math.max(answer, length);

        for (int d = 0; d < 4; d++) {
            int nx = x + dxs[d];
            int ny = y + dys[d];

            if (!inRange(nx, ny) || visited[nx][ny]) {
                continue;
            }

            // 공사 없이 이동 가능
            if (map[nx][ny] < currHeight) {
                visited[nx][ny] = true;
                dfs(nx, ny, length + 1, used, map[nx][ny], map, K);
                visited[nx][ny] = false;
            }

            // 아직 공사를 안 했고, 깎으면 이동 가능
            else if (!used && map[nx][ny] - K < currHeight) {
                visited[nx][ny] = true;

                // 필요한 만큼만 깎아서 현재 높이보다 1 낮게 설정
                dfs(nx, ny, length + 1, true, currHeight - 1, map,K);

                visited[nx][ny] = false;
            }
        }
    }

    private boolean inRange(int nx, int ny){
        return nx >= 0 && nx < N && ny >= 0 && ny < N;
    }
}
