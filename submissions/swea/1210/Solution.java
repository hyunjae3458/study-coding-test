import java.io.*;
import java.util.*;

public class Solution {
    private static final int TEST_CASE_COUNT = 10;
    private static final int LADDER_SIZE = 100;

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder output = new StringBuilder();

        for (int count = 0; count < TEST_CASE_COUNT; count++) {
            int testCase = fs.nextInt();
            int[][] ladder = new int[LADDER_SIZE][LADDER_SIZE];

            for (int row = 0; row < LADDER_SIZE; row++) {
                for (int column = 0; column < LADDER_SIZE; column++) {
                    ladder[row][column] = fs.nextInt();
                }
            }

            Solution solver = new Solution();
            int result = solver.solution(testCase, ladder);
            output.append('#').append(testCase).append(' ').append(result).append('\n');
        }

        System.out.print(output);
    }

    /**
     * @param testCase 테스트 케이스 번호 (입력값 그대로)
     * @param ladder 100×100 사다리 배열 (0: 길 없음, 1: 길, 2: 도착점)
     * @return 도착점에 대응하는 출발점의 X 좌표 (0-based)
     */
    int answer = 0;
    int[] dxs = {1,0,-1,0};
    int[] dys = {0,1,0,-1};
    public int solution(int testCase, int[][] ladder) {
        for(int i = 0; i < ladder[0].length; i ++){
            boolean[][] visited = new boolean[100][100];
            if(ladder[0][i] == 1){
                dfs(i, 0,i,0,ladder,visited);
            }
        }
        return answer;
    }

    private void dfs(int start, int x, int y, int dir, int[][] ladder, boolean[][] visited){
        if(!inRange(x,y)) return;
        if(ladder[x][y] == 2){
            answer = start;
            return;
        }
        visited[x][y] = true;
        if(dir == 0){
            int leftDir = (dir + 3) % 4;
            int rightDir = (dir + 1) % 4;

            // 오른쪽 사다리
            if(inRange(x+dxs[rightDir], y + dys[rightDir]) && ladder[x+dxs[rightDir]][y + dys[rightDir]] == 1 && !visited[x+dxs[rightDir]][y + dys[rightDir]]){
                dfs(start, x + dxs[rightDir], y + dys[rightDir], rightDir,ladder,visited);
            }
            // 왼쪽 사다리
            else if(inRange(x+dxs[leftDir], y + dys[leftDir]) && ladder[x+dxs[leftDir]][y + dys[leftDir]] == 1 && !visited[x+dxs[leftDir]][y + dys[leftDir]]){
                dfs(start, x + dxs[leftDir], y + dys[leftDir], leftDir,ladder,visited);
            }
            else{
                dfs(start, x + dxs[dir], y + dys[dir], dir, ladder,visited);
            }
        }
        else if(dir == 1){
            int nextDir = (dir + 3) % 4;
            // 아래로 이동
            if(inRange(x+dxs[nextDir], y + dys[nextDir]) && ladder[x+dxs[nextDir]][y + dys[nextDir]] == 1 && !visited[x+dxs[nextDir]][y + dys[nextDir]]){
                dfs(start, x + dxs[nextDir], y + dys[nextDir], nextDir,ladder,visited);
            }
            // 그대로 이동
            else{
                dfs(start, x + dxs[dir], y + dys[dir], dir, ladder,visited);
            }
        }
        else{
            int nextDir = (dir + 1) % 4;
            // 아래로 이동
            if(inRange(x+dxs[nextDir], y + dys[nextDir]) && ladder[x+dxs[nextDir]][y + dys[nextDir]] == 1 && !visited[x+dxs[nextDir]][y + dys[nextDir]]){
                dfs(start, x + dxs[nextDir], y + dys[nextDir], nextDir,ladder,visited);
            }
            else{
                // 그대로 이동
                dfs(start, x + dxs[dir], y + dys[dir], dir, ladder,visited);
            }
        }



    }

    private boolean inRange(int nx, int ny){
        return nx >= 0 && nx < 100 && ny >= 0 && ny < 100;
    }

    private static class FastScanner {
        private final InputStream input;
        private final byte[] buffer = new byte[1 << 16];
        private int pointer;
        private int length;

        FastScanner(InputStream input) {
            this.input = input;
        }

        int nextInt() throws IOException {
            int value = 0;
            int character;

            do {
                character = read();
            } while (character <= ' ');

            while (character > ' ') {
                value = value * 10 + character - '0';
                character = read();
            }

            return value;
        }

        private int read() throws IOException {
            if (pointer >= length) {
                length = input.read(buffer);
                pointer = 0;
                if (length <= 0) {
                    return -1;
                }
            }
            return buffer[pointer++];
        }
    }
}
