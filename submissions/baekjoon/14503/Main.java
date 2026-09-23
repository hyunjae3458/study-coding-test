import java.util.*;
class Solution {
    /**
     * @param n 방의 세로 크기 N (입력값 그대로)
     * @param m 방의 가로 크기 M (입력값 그대로)
     * @param r 로봇 청소기의 시작 행 좌표 (0-based, 입력값 그대로)
     * @param c 로봇 청소기의 시작 열 좌표 (0-based, 입력값 그대로)
     * @param d 로봇 청소기의 시작 방향 (0: 북쪽, 1: 동쪽, 2: 남쪽, 3: 서쪽)
     * @param room N×M 방의 상태 (0: 청소되지 않은 칸, 1: 벽, 입력값 그대로)
     * @return 로봇 청소기가 청소한 칸의 개수
     */
    class Node{
        int x,y,dir;
        public Node(int x, int y, int dir){
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }
    int[] dxs = {-1,0,1,0};
    int[] dys = {0,1,0,-1};
    public int solution(int n, int m, int r, int c, int d, int[][] room) {
        int answer = 1;
        // (0: 북쪽, 1: 동쪽, 2: 남쪽, 3: 서쪽) (dir + 3) % 4

        int x = r;
        int y  = c;
        int dir = d;
        while(true){
            room[x][y] = 2;
            boolean moved = false;
            for(int i = 0; i < 4; i++){
                dir = (dir + 3) % 4;
                int nx = x + dxs[dir];
                int ny = y + dys[dir];
                if(room[nx][ny] == 0){
                    x = nx;
                    y = ny;
                    answer += 1;
                    moved = true;
                    break;
                }
            }
            if(moved) continue;
            int backDir = (dir + 2) % 4;
            int backX = x + dxs[backDir];
            int backY = y + dys[backDir];
            if(room[backX][backY] == 1){
                break;
            } else{
                x = backX;
                y = backY;
            }
        }

        return answer;
    }

}

public class Main {
    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int r = scanner.nextInt();
        int c = scanner.nextInt();
        int d = scanner.nextInt();
        int[][] room = new int[n][m];
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < m; column++) {
                room[row][column] = scanner.nextInt();
            }
        }
        System.out.println(new Solution().solution(n, m, r, c, d, room));
    }

    private static class FastScanner {
        private final java.io.InputStream input = new java.io.BufferedInputStream(System.in);

        int nextInt() throws java.io.IOException {
            int ch;
            do {
                ch = input.read();
                if (ch == -1) throw new java.io.EOFException();
            } while (ch <= ' ');
            int value = 0;
            while (ch >= '0' && ch <= '9') {
                value = value * 10 + ch - '0';
                ch = input.read();
            }
            return value;
        }
    }
}
