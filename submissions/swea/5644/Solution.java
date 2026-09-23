/////////////////////////////////////////////////////////////////////////////////////////////
// 기본 제공코드는 임의 수정해도 관계 없습니다. 단, 입출력 포맷 주의
/////////////////////////////////////////////////////////////////////////////////////////////
import java.util.Scanner;
import java.io.FileInputStream;
import java.util.*;

/*
   사용하는 클래스명이 Solution 이어야 하므로, 가급적 Solution.java 를 사용할 것을 권장합니다.
   이러한 상황에서도 동일하게 java Solution 명령으로 프로그램을 수행해볼 수 있습니다.
 */
class Solution
{
    /**
     * @param moveTime 총 이동 시간 M (입력값 그대로)
     * @param movesA 사용자 A의 M개 이동 방향 (0: 이동 없음, 1: 상, 2: 우, 3: 하, 4: 좌)
     * @param movesB 사용자 B의 M개 이동 방향 (0: 이동 없음, 1: 상, 2: 우, 3: 하, 4: 좌)
     * @param chargers 각 BC의 X, Y, 충전 범위 C, 성능 P (좌표와 입력 순서 그대로)
     * @return 두 사용자가 충전한 양의 합의 최댓값
     */

    String[][] map = new String[10][10];
    int[] dxs = {0,-1,0,1,0};
    int[] dys = {0,0,1,0,-1};
    int[] answerSum;
    int[] chargerPowers;
    boolean[] using;

    public static void main(String args[]) throws Exception
    {
        //System.setIn(new FileInputStream("res/input.txt"));

        Scanner sc = new Scanner(System.in);
        int T;
        T=sc.nextInt();
        StringBuilder output = new StringBuilder();

        for(int test_case = 1; test_case <= T; test_case++)
        {
            /////////////////////////////////////////////////////////////////////////////////////////////
            /*
                 이 부분에 입력 처리와 알고리즘 호출이 들어갑니다.
             */
            /////////////////////////////////////////////////////////////////////////////////////////////
            int moveTime = sc.nextInt();
            int chargerCount = sc.nextInt();
            int[] movesA = new int[moveTime];
            int[] movesB = new int[moveTime];
            int[][] chargers = new int[chargerCount][4];

            for(int i = 0; i < moveTime; i++) {
                movesA[i] = sc.nextInt();
            }

            for(int i = 0; i < moveTime; i++) {
                movesB[i] = sc.nextInt();
            }

            for(int i = 0; i < chargerCount; i++) {
                for(int j = 0; j < 4; j++) {
                    chargers[i][j] = sc.nextInt();
                }
            }

            int answer = new Solution().solution(moveTime, movesA, movesB, chargers);
            output.append('#').append(test_case).append(' ').append(answer).append('\n');
        }

        System.out.print(output);
    }

    public int solution(int moveTime, int[] movesA, int[] movesB, int[][] chargers) {
        answerSum = new int[moveTime + 1];
        chargerPowers = new int[chargers.length + 1];
        using = new boolean[chargers.length + 1];
        for(int i = 0; i < 10; i ++){
            for(int j = 0; j < 10; j++){
                map[i][j] = "";
            }
        }
        // 충전기 정보 초기화
        for(int i = 0; i < chargers.length; i++){
            int x = chargers[i][1] - 1;
            int y = chargers[i][0] - 1;
            int c = chargers[i][2];
            int p = chargers[i][3];
            // 충전기 파워 기록
            chargerPowers[i + 1] = p;
            // 충전기 범위 그룹화로 기록
            makeGroup(c, String.valueOf(i+1), x,y);
        }


        // 시간에 따른 움직임으로 답 기록
        int ax = 0;
        int ay = 0;
        int bx = 9;
        int by = 9;
        getAnswer(ax,ay,bx,by,0);

        for(int i = 0; i < moveTime; i++){
            ax += dxs[movesA[i]];
            ay += dys[movesA[i]];
            bx += dxs[movesB[i]];
            by += dys[movesB[i]];
            getAnswer(ax,ay,bx,by, i + 1);
        }
        int answer = 0;
        for(int i = 0; i < moveTime + 1; i++){
            answer += answerSum[i];
        }

        return answer;
    }

    private void makeGroup(int r, String c, int x, int y){
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[10][10];
        visited[x][y] = true;
        q.add(new int[]{x,y});

        while(!q.isEmpty()){
            int[] curr = q.poll();
            int currX = curr[0];
            int currY = curr[1];
            map[currX][currY] += c;

            for(int i = 1; i < 5; i++){
                int nx = currX + dxs[i];
                int ny = currY + dys[i];
                if(inRange(nx,ny) && !visited[nx][ny]){
                    if(inGroupRange(r,x,y,nx,ny)){
                        visited[nx][ny] = true;
                        q.add(new int[]{nx,ny});
                    }
                }
            }
        }
    }

    private void getAnswer(int ax, int ay, int bx, int by, int time){
        String[] arrA = map[ax][ay].split("");
        String[] arrB = map[bx][by].split("");
        Arrays.sort(arrA, (a,b) -> chargerPowers[Integer.parseInt(a)] - chargerPowers[Integer.parseInt(b)]);
        Arrays.sort(arrB, (a,b) -> chargerPowers[Integer.parseInt(a)] - chargerPowers[Integer.parseInt(b)]);
        int max = Integer.MIN_VALUE;
        int ap = 0;
        int bp = 0;

        for(int i = 0; i < arrA.length; i++){
            for(int j = 0; j < arrB.length; j++){
                if(!arrA[i].isEmpty()){
                    ap = Integer.parseInt(arrA[i]);
                }
                if(!arrB[j].isEmpty()){
                    bp = Integer.parseInt(arrB[j]);
                }
                if(ap == bp){
                    max = Math.max(max, chargerPowers[ap] / 2 + chargerPowers[bp] / 2);
                } else{
                    max = Math.max(max, chargerPowers[ap] + chargerPowers[bp]);
                }
            }
        }

        answerSum[time] = max;
    }

    private boolean inRange(int nx, int ny){
        return nx >= 0 && nx < 10 && ny >= 0 && ny < 10;
    }

    private boolean inGroupRange(int r, int x, int y, int nx, int ny){
        return Math.abs(x - nx) + Math.abs(y - ny) <= r;
    }
}
