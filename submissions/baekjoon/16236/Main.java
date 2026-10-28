import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine().trim());
        int[][] space = new int[n][n];
        for (int row = 0; row < n; row++) {
            StringTokenizer tokens = new StringTokenizer(reader.readLine());
            for (int column = 0; column < n; column++) {
                space[row][column] = Integer.parseInt(tokens.nextToken());
            }
        }
        System.out.println(new Solution().solution(n, space));
    }
}

class Solution {
    /**
     * @param n 공간의 크기 N (입력값 그대로)
     * @param space N×N 공간, 입력 행·열 순서와 값 그대로.
     *              0은 빈칸, 1~6은 물고기 크기, 9는 아기 상어의 위치.
     *              배열 인덱스는 0-based이며 space[0][0]은 첫 행 첫 열.
     * @return 아기 상어가 물고기를 잡아먹을 수 있는 총 시간
     */
    class Node{
        int x;
        int y;
        int dis;
        public Node(int x, int y, int dis){
            this.x = x;
            this.y= y;
            this.dis =dis;
        }
    }
    int[] dxs = {1,0,-1,0};
    int[] dys = {0,1,0,-1};
    int size = 2;
    int x;
    int y;
    public int solution(int n, int[][] space) {
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(space[i][j] == 9){
                    x = i;
                    y = j;
                }
            }
        }
        int cnt = 0;
        int answer = 0;
        while(true){
            PriorityQueue<Node> pq = new PriorityQueue<>((a,b) -> {
                if(a.dis != b.dis) return a.dis - b.dis;
                else if(a.x != b.x) return a.x - b.x;
                else return a.y - b.y;
            });

            move(pq, space);

            if(pq.isEmpty()) break;
            Node next = pq.poll();
            space[x][y] = 0;
            x = next.x;
            y = next.y;
            answer += next.dis;
            space[x][y] = 9;
            cnt += 1;
            if(cnt == size) {
                cnt = 0;
                size += 1;
            }
        }
        return answer;
    }

    private void move(PriorityQueue<Node> pq, int[][] space){
        boolean[][] visited = new boolean[space.length][space.length];
        Queue<Node> q = new LinkedList<>();
        int best = Integer.MAX_VALUE;
        q.add(new Node(x,y,0));
        visited[x][y] = true;

        while(!q.isEmpty()){
            Node curr = q.poll();
            int currX = curr.x;
            int currY = curr.y;
            int dis = curr.dis;
            int fishSize = space[currX][currY];
            if(best < dis) break;
            if(fishSize != 0 && fishSize != 9 && fishSize < size){
                pq.add(new Node(currX, currY, dis));
                best = dis;
            }

            for(int i = 0; i < 4; i++){
                int nx = currX + dxs[i];
                int ny = currY + dys[i];
                if(inRange(nx,ny,space.length) && !visited[nx][ny] && space[nx][ny] <= size){

                    q.add(new Node(nx,ny,dis + 1));
                    visited[nx][ny] = true;
                }
            }
        }
    }

    private boolean inRange(int nx, int ny, int n){
        return nx >= 0 && nx < n && ny >= 0 && ny < n;
    }
}
