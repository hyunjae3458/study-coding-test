import java.util.*;
class Solution {
    /**
     * @param gears 4×8 톱니바퀴 상태. 행 0~3은 입력 순서대로 1~4번 톱니바퀴.
     *              각 행은 12시 방향부터 시계 방향 순서이며, 0은 N극, 1은 S극.
     * @param k 회전 횟수 K (입력값 그대로)
     * @param rotations K×2 회전 명령. 각 행은 {톱니바퀴 번호, 방향}.
     *                  번호는 1-based(1~4), 방향은 1(시계), -1(반시계) 그대로 전달.
     * @return 모든 회전 후 네 톱니바퀴의 점수 합
     */
    public int solution(int[][] gears, int k, int[][] rotations) {
        for(int i = 0; i < k; i++){
            int[] status = new int[4];
            int weal = rotations[i][0];
            int dir = rotations[i][1];
            check(weal, dir, status, gears);
            for(int s = 0; s < 4; s++){
                move(status[s], s, gears);
            }
        }

        int answer = 0;
        for(int i = 0; i < 4; i++){

            if(gears[i][0] == 1){
                answer += (int)Math.pow(2, i);
            }
        }
        return answer;
    }

    private void check(int weal, int dir, int[] status , int[][] gears){
        status[weal-1] = dir;
        for(int i = weal - 1; i > 0; i--){
            // 왼쪽 검사
            if(status[i] != 0 && gears[i][6] != gears[i -1][2]){
                status[i-1] = -status[i];
            } else{
                status[i-1] = 0;
            }
        }
        // 오른쪽 검사
        for(int i = weal - 1; i < 3; i++){
            if(status[i] != 0 && gears[i][2] != gears[i+1][6]){
                status[i+1] = -status[i];;
            } else{
                status[i + 1] = 0;
            }
        }

    }

    private void move(int status, int weal, int[][] gears){
        if(status == -1){
            int temp1 = gears[weal][7];
            int temp2 = 0;
            for(int i = 7; i > 0; i--){
                temp2 = gears[weal][i-1];
                gears[weal][i - 1] = temp1;
                temp1 = temp2;
            }
            gears[weal][7] = temp1;
        }
        if(status == 1){
            int temp1 = gears[weal][0];
            int temp2 = 0;
            for(int i = 0; i < 7; i++){
                temp2 = gears[weal][i+1];
                gears[weal][i+1] = temp1;
                temp1 = temp2;
            }
            gears[weal][0] = temp1;
        }
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(System.in));
        int[][] gears = new int[4][8];
        for (int row = 0; row < 4; row++) {
            String state = reader.readLine().trim();
            for (int column = 0; column < 8; column++) {
                gears[row][column] = state.charAt(column) - '0';
            }
        }
        int k = Integer.parseInt(reader.readLine().trim());
        int[][] rotations = new int[k][2];
        for (int i = 0; i < k; i++) {
            StringTokenizer tokens = new StringTokenizer(reader.readLine());
            rotations[i][0] = Integer.parseInt(tokens.nextToken());
            rotations[i][1] = Integer.parseInt(tokens.nextToken());
        }
        System.out.println(new Solution().solution(gears, k, rotations));
    }
}
