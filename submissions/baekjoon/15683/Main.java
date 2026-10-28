import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens = new StringTokenizer(reader.readLine());
        int n = Integer.parseInt(tokens.nextToken());
        int m = Integer.parseInt(tokens.nextToken());
        int[][] office = new int[n][m];
        for (int row = 0; row < n; row++) {
            tokens = new StringTokenizer(reader.readLine());
            for (int column = 0; column < m; column++) {
                office[row][column] = Integer.parseInt(tokens.nextToken());
            }
        }
        System.out.println(new Solution().solution(n, m, office));
    }
}

class Solution {
    /**
     * @param n 사무실의 세로 크기 N (입력값 그대로)
     * @param m 사무실의 가로 크기 M (입력값 그대로)
     * @param office N×M 사무실 상태, 입력 행·열 순서와 값 그대로.
     *               0은 빈칸, 1~5는 CCTV 종류, 6은 벽.
     *               배열 인덱스는 0-based이며 office[0][0]은 첫 행 첫 열.
     * @return 사각지대의 최소 크기
     */
    List<int[]> lst = new ArrayList<>();
    int N;
    int M;
    int[] dxs = {0,-1,0,1};
    int[] dys = {1,0,-1,0};
    int[][] cameras = {{0}, {0,2}, {0,1}, {0,1,2}, {0,1,2,3}};
    int answer = Integer.MAX_VALUE;
    public int solution(int n, int m, int[][] office) {
        N = n;
        M = m;
        // 카메라 위치를 담음
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(office[i][j] > 0 && office[i][j] < 6){
                    lst.add(new int[]{i,j});
                }
            }
        }
        backtrack(lst.size(), 0, office);

        return answer;
    }

    private void backtrack(int target, int curr, int[][] office){
        if(target == curr){
            answer = Math.min(answer, counting(office));
            return;
        }

        int x = lst.get(curr)[0];
        int y = lst.get(curr)[1];

        // 카메라의 4방향 모두 체크
        for(int i = 0; i < 4; i++){
            int[][] copy = office.clone();
            for(int j = 0; j < copy.length; j++){
                copy[j] = office[j].clone();
            }
            checkCamera(office[x][y], i, x,y, copy);
            backtrack(target, curr + 1, copy);
        }
    }

    // 카메라 번호, 회전 방향, 현재위치, 배열
    private void checkCamera(int camera, int dir, int x, int y, int[][] office){
        // 카메라의 감시 위치
        int[] nextDir = cameras[camera - 1];
        // 카메라의 모든 방향 검사
        for(int i = 0; i < nextDir.length; i++){
            // 현재 검사해야할 카메라의 위치와 돌아간 방향을 합친 감시 위치
            int checkDir = (dir  + nextDir[i]) % 4;
            // 다음 위치
            int nx = x + dxs[checkDir];
            int ny = y + dys[checkDir];
            while(inRange(nx,ny)){
                // 벽 만나면 종료
                if(office[nx][ny] == 6){
                    break;
                }
                // 빈칸이면 감시영역으로 표시
                if(office[nx][ny] == 0){
                    office[nx][ny] = 7;
                }
                nx += dxs[checkDir];
                ny += dys[checkDir];
            }

        }
    }

    private int counting(int[][] office){
        int noSight = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(office[i][j] == 0){
                    noSight += 1;
                }
            }
        }
        return noSight;
    }
    private boolean inRange(int nx, int ny){
        return nx >= 0 && nx < N && ny >= 0 && ny < M;
    }
}
