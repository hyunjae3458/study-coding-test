import java.util.*;
public class Solution {
    /**
     * @param r 방의 행 수 R (입력값 그대로)
     * @param c 방의 열 수 C (입력값 그대로)
     * @param t 경과 시간 T (입력값 그대로)
     * @param room R×C 방의 상태, 입력 행·열 순서 그대로.
     *             -1은 공기청정기, 0 이상은 미세먼지 양.
     *             배열 인덱스는 0-based이며 room[0][0]은 문제의 1행 1열.
     * @return T초 후 방에 남은 미세먼지의 총량
     */
    int[] dxs = {1,0,-1,0};
    int[] dys = {0,1,0,-1};
    int R;
    int C;
    int airX1;
    int airX2;
    public int solution(int r, int c, int t, int[][] room) {
        R = r;
        C = c;
        Queue<int[]> q = new LinkedList<>();
        // 공기 청정기 위치 구하기
        for(int i = 0; i < R; i++) {
            if(room[i][0] == -1){
                airX1 = i;
                break;
            }
        }
        airX2 = airX1 + 1;



        while(t>0){
            // 확산 시작될 미세먼지 위치 기록
            for(int i = 0; i < R; i++){
                for(int j = 0; j < C; j++){
                    if(room[i][j] > 0){
                        q.add(new int[]{i,j});
                    }
                }
            }

            int[][] dp = new int[R][C];
            // 확산
            while(!q.isEmpty()){
                int[] curr = q.poll();
                int x = curr[0];
                int y = curr[1];
                // 확산된 영역의 개수
                int count = 0;
                int amount = room[x][y] / 5;
                for(int i = 0; i < 4; i++){
                    int nx = x + dxs[i];
                    int ny = y + dys[i];
                    if(inRange(nx,ny) && room[nx][ny] != -1){
                        count += 1;
                        dp[nx][ny] += amount;
                    }
                }
                // 확산된 만큼 그 자리는 감소
                room[x][y] -= count * amount;
            }

            // 현재 상태에서 확산된 미세먼지 값을 갱신
            for(int i = 0; i < R; i++){
                for(int j = 0; j < C; j++){
                    if(dp[i][j] > 0){
                        room[i][j] += dp[i][j];
                    }
                }
            }

            // 공기청정기 작동
            airCleaner(room, 0);
            airCleaner(room, 1);
            t -= 1;
        }

        int answer = 0;
        for(int i = 0; i < R; i++){
            for(int j = 0; j < C; j++){
                if(room[i][j] > 0){
                    answer += room[i][j];
                }
            }
        }

        return answer;
    }

    private void airCleaner(int[][] room, int pos){
        int dir = 1;
        // 청정기 위쪽
        if(pos == 0){
            int temp1 = 0;
            int x = airX1;
            int y = 1;

            while(true){
                if(x == airX1 && y == 0){
                    break;
                }
                int temp2 = room[x][y];
                room[x][y] = temp1;
                temp1 = temp2;

                int nx = x + dxs[dir];
                int ny = y + dys[dir];
                if(!inRange(nx,ny)){
                    dir = (dir + 1) % 4;
                    nx = x + dxs[dir];
                    ny = y + dys[dir];
                }
                x = nx;
                y = ny;
            }
        }
        // 청정기 아래쪽
        if(pos == 1){
            int temp1 = 0;
            int x = airX2;
            int y = 1;

            while(true){
                if(x == airX2 && y == 0){
                    break;
                }
                int temp2 = room[x][y];
                room[x][y] = temp1;
                temp1 = temp2;

                int nx = x + dxs[dir];
                int ny = y + dys[dir];
                if(!inRange(nx,ny)){
                    dir = (dir + 3) % 4;
                    nx = x + dxs[dir];
                    ny = y + dys[dir];
                }
                x = nx;
                y = ny;
            }
        }
    }

    private boolean inRange(int nx, int ny){
        return nx >= 0 && nx < R && ny >= 0 && ny < C;
    }
}
