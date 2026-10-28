import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens = new StringTokenizer(reader.readLine());
        int n = Integer.parseInt(tokens.nextToken());
        int k = Integer.parseInt(tokens.nextToken());
        int[][] board = new int[n][n];
        for (int row = 0; row < n; row++) {
            tokens = new StringTokenizer(reader.readLine());
            for (int column = 0; column < n; column++) {
                board[row][column] = Integer.parseInt(tokens.nextToken());
            }
        }
        int[][] pieces = new int[k][3];
        for (int piece = 0; piece < k; piece++) {
            tokens = new StringTokenizer(reader.readLine());
            for (int field = 0; field < 3; field++) {
                pieces[piece][field] = Integer.parseInt(tokens.nextToken());
            }
        }
        System.out.println(new Solution().solution(n, k, board, pieces));
    }
}

class Solution {
    /**
     * @param n 체스판 크기 N (입력값 그대로)
     * @param k 말의 개수 K (입력값 그대로)
     * @param board N×N 칸의 색: 0=흰색, 1=빨간색, 2=파란색.
     *              배열 인덱스는 0-based이며 board[0][0]은 1행 1열.
     * @param pieces K×3 말 정보. pieces[i]는 i+1번 말의 {행, 열, 방향}.
     *               행·열 값은 원본 그대로 1-based.
     *               방향도 원본 그대로 1=오른쪽, 2=왼쪽, 3=위, 4=아래.
     * @return 게임이 종료되는 턴 번호. 1000턴을 초과하거나 종료되지 않으면 -1.
     */
    class Node{
        int x;
        int y;
        int dir;
        Node(int x, int y, int dir){
            this.x =x;
            this.y = y;
            this.dir = dir;
        }
    }
    int[] dxs = {0,0,-1,1};
    int[] dys = {1,-1,0,0};
    Node[] nodes;
    List<Integer>[][] arr;
    public int solution(int n, int k, int[][] board, int[][] pieces) {
        arr = new ArrayList[n][n];
        nodes = new Node[k + 1];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                arr[i][j] = new ArrayList<>();
            }
        }

        // 초기화
        for(int i = 0; i < k; i++){
            int num = i + 1;
            int x = pieces[i][0] - 1;
            int y = pieces[i][1] - 1;
            int dir = pieces[i][2] - 1;
            arr[x][y].add(num);
            nodes[num] = new Node(x,y,dir);
        }

        int answer = 0;

        while(true){
            if(answer >= 1000) return -1;
            for(int i = 1; i < k + 1; i++) {
                int x = nodes[i].x;
                int y = nodes[i].y;
                int dir = nodes[i].dir;

                int nx = x + dxs[dir];
                int ny = y + dys[dir];
                if (!inRange(nx, ny, n) || board[nx][ny] == 2) {
                    dir = (5 - dir) % 4;
                    nodes[i].dir = dir;
                    nx = x + dxs[dir];
                    ny = y + dys[dir];
                    if (!inRange(nx, ny, n) || board[nx][ny] == 2) continue;
                    if (board[nx][ny] == 0) {
                        doWhite(i, x, y, nx, ny);
                        if(arr[nx][ny].size() >= 4){
                            return answer + 1;
                        }
                    } else {
                        doRed(i, x, y, nx, ny);
                        if(arr[nx][ny].size() >= 4){
                            return answer + 1;
                        }
                    }
                } else if (board[nx][ny] == 0) {
                    doWhite(i, x, y, nx, ny);
                    if(arr[nx][ny].size() >= 4){
                        return answer + 1;
                    }
                } else {
                    doRed(i, x, y, nx, ny);
                    if(arr[nx][ny].size() >= 4){
                        return answer + 1;
                    }
                }
            }
            answer += 1;
        }
    }

    private void doWhite(int num, int x, int y, int nx, int ny){
        int size = arr[x][y].size();
        int index = arr[x][y].indexOf(num);
        for(int i = index; i < size; i++){
            int number = arr[x][y].get(index);
            arr[nx][ny].add(number);
            arr[x][y].remove(index);
            nodes[number].x = nx;
            nodes[number].y = ny;
        }
    }

    private void doRed(int num, int x, int y, int nx, int ny){
        int size = arr[x][y].size();
        int index = arr[x][y].indexOf(num);
        for(int i = size - 1; i >= index; i--){
            int number = arr[x][y].get(i);
            arr[nx][ny].add(number);
            arr[x][y].remove(i);
            nodes[number].x = nx;
            nodes[number].y = ny;
        }
    }

    private boolean inRange(int nx, int ny, int n){
        return nx >= 0 && nx < n && ny >= 0 && ny < n;
    }
}
