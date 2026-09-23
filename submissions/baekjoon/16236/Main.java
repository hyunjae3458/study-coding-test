import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] space = new int[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) space[i][j] = Integer.parseInt(st.nextToken());
        }
        System.out.print(new Solution().solution(n, space));
    }
}

class Solution {
    int N;
    int[] dxs = {1,0,-1,0};
    int[] dys = {0,1,0,-1};
    int size = 2;
    class Node{
        int x;
        int y;
        int sx;
        int sy;
        int dis;
        public Node(int x, int y,int sx, int sy, int dis){
            this.x = x;
            this.y = y;
            this.sx = sx;
            this.sy = sy;
            this.dis = dis;
        }
    }
    // 먹을 수 있는 물고기 후보군 (거리 -> x -> y 오름차순)
    PriorityQueue<Node> pq = new PriorityQueue<>((a,b) -> {
        if(a.dis != b.dis) return Integer.compare(a.dis, b.dis);
        if(a.x != b.x) return Integer.compare(a.x, b.x);
        return Integer.compare(a.y, b.y);
    });
    int answer = 0;
    public int solution(int n, int[][] space) {

        // 기본적인 상어 움직임은 상하좌우 1
        // 먹을 수 잇는 물고기 모음 pq를 이용해서 Node(x,y,dis)로 dis - x-y 각각 오름차순으로 조건
        // 무한루프를 하지만 pq가 빌때까지
        // 상어의 크기를 지속적으로 기록하고 크기가 바뀔때마다 pq 최신화 (조건의 크기가 작기 때문에 완탐가능)
        // 최초에 상어 크기 2인상태로 물고기를 넣어본다.
        // 정답인 cnt는 지속적으로 체크
        // while 마다 다음 목표로 이동 move메서드는 목표 x,y값을 파라미터로 상어가 이동해보고 boolean을 반환하여 갈 수 있는 지 없는지를 판단
        // 만약 여기서 false가 나오면 못먹는 물고기
        N = n;

        int startX = 0;
        int startY = 0;
        // 최초 물고기 위치 확인 및 먹을 수 있는 생선 등록
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(space[i][j] == 9){
                    startX = i;
                    startY = j;
                    getFish(startX, startY,space);
                }
            }
        }

        int cnt = 0;
        // 본격적 진행
        while(!pq.isEmpty()){
            // 먹을 물고기
            Node curr = pq.poll();

            // 이미 먹은 위치/ 시작점이 다른 지점은 스킵
            if(space[curr.x][curr.y] == 0 || !(curr.sx == startX && curr.sy == startY)) continue;
            cnt += 1;
            space[startX][startY] = 0;
            startY = curr.y;
            startX = curr.x;
            answer += curr.dis;
            if(cnt == size){
                size += 1;
                cnt = 0;
            }
            // 현재 위치에서 다시 먹을 물고기 후보 먹기
            getFish(startX, startY,space);

        }

        return answer;
    }
    private void getFish(int x, int y, int[][] space){
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];
        int[][] disInfo = new int[N][N];
        visited[x][y] = true;
        q.add(new int[]{x,y});

        while(!q.isEmpty()){
            int[] curr = q.poll();
            int currX = curr[0];
            int currY = curr[1];
            int dis = disInfo[currX][currY];
            if(space[currX][currY] != 0 && space[currX][currY] < size && dis > 0){
                pq.add(new Node(currX, currY, x, y, dis));
            }

            for(int i = 0; i < 4; i++){
                int nx = currX + dxs[i];
                int ny = currY + dys[i];
                if(inRange(nx,ny) && !visited[nx][ny] && space[nx][ny] <= size){
                    q.add(new int[]{nx,ny});
                    disInfo[nx][ny] = dis + 1;
                    visited[nx][ny] = true;
                }
            }
        }
    }

    private int calDis(int x1, int y1, int x2, int y2){
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private boolean inRange(int nx, int ny){
        return nx >= 0 && nx < N && ny >= 0 && ny < N;
    }
}
