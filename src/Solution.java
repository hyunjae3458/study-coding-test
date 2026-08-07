import java.util.*;
public class Solution {
    /**
     * @param n 세로 크기 (입력값 그대로)
     * @param m 가로 크기 (입력값 그대로)
     * @param map 0은 바다, 1은 땅인 N x M 지도 (입력 순서와 값 그대로)
     * @return 모든 섬을 연결하는 다리 길이의 최솟값, 연결할 수 없으면 -1
     */
    class Bridge{
        int start;
        int end;
        int cost;

        public Bridge(int start, int end, int cost){
            this.start = start;
            this.end = end;
            this.cost = cost;
        }
    }

    int[] parents;
    int[] size;
    List<Bridge> bridges = new ArrayList<>();
    int[] dxs = {1,0,-1,0};
    int[] dys = {0,1,0,-1};
    Queue<int[]> q = new LinkedList<>();
    int N;
    int M;
    public int solution(int n, int m, int[][] map) {
        N = n;
        M = m;
        // 그룹화
        // 그룹 id는 2부터시작(1이 땅이기 때문)
        int groupId = 2;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(map[i][j] == 1){
                    makeGroup(map, i, j, groupId);
                    groupId += 1;
                }
            }
        }

        // 다리 기록
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                // 섬이라면
                if(map[i][j] != 0 && map[i][j] != 1){
                    // 섬의 한 지점에서 상하좌우를 봤을때 바다라면
                    for(int dir = 0; dir < 4; dir++){
                        int nextX = i + dxs[dir];
                        int nextY = j + dys[dir];
                        if(inRange(nextX, nextY) && map[nextX][nextY] == 0){
                            getBridge(map, nextX, nextY, dir, map[i][j]);
                        }
                    }
                }
            }
        }

        // parents와 size배열은 기록된 groupId 크기만큼 기록
        parents = new int[groupId];
        size = new int[groupId];
        for(int i = 1; i < groupId; i++){
            parents[i] = i;
            size[i] = 1;
        }
        // 다리 최소 값 구하기
        Collections.sort(bridges, (a,b) -> Integer.compare(a.cost, b.cost));
        int answer = 0;
        int islands = groupId - 2;
        for(Bridge bridge : bridges){
            int start = bridge.start;
            int end = bridge.end;
            int cost = bridge.cost;

            if(find(start) != find(end)){
                union(start, end);
                answer += cost;
                islands -= 1;
            }
        }
        // 섬이 전부 연결되어있는지 확인
        if(islands > 1) return -1;

        return answer;
    }

    private void makeGroup(int[][] map, int x, int y, int groupId){
        q.add(new int[]{x,y});
        map[x][y] = groupId;

        while(!q.isEmpty()){
            int[] curr = q.poll();
            int currX = curr[0];
            int currY = curr[1];

            for(int i = 0; i < 4; i++){
                int nx = currX + dxs[i];
                int ny = currY + dys[i];
                if(inRange(nx,ny) && map[nx][ny] != 0){
                    if(map[nx][ny] != groupId){
                        map[nx][ny] = groupId;
                        q.add(new int[]{nx,ny});
                    }
                }
            }
        }
    }

    private void getBridge(int[][] map,int x, int y, int dir, int groupId){
        int cnt = 1;
        int start  = groupId;
        int end = 0;
        boolean reached = false;
        // 주어진 방향으로 다른 섬이 나올때까지 전진
        while(true){
            if(!inRange(x,y)){
                break;
            }
            if(map[x][y] == groupId) break;
            if(map[x][y] != 0){
                end = map[x][y];
                cnt -= 1;
                reached = true;
                break;
            }
            x += dxs[dir];
            y += dys[dir];
            cnt += 1;
        }

        if(cnt >= 2 && reached){
            // 섬의 groupId에서 -1해서 기록
            bridges.add(new Bridge(start - 1, end - 1, cnt));
        }
    }

    private boolean inRange(int nx, int ny){
        return nx >= 0 && nx < N && ny >= 0 && ny < M;
    }

    private int find(int curr){
        if(parents[curr] == curr) return curr;
        return parents[curr] = find(parents[curr]);
    }

    private void union(int curr1, int curr2){
        int root1 = find(curr1);
        int root2 = find(curr2);

        if(root1 == root2) return;

        if(size[root1] < size[root2]){
            int temp = root1;
            root1 = root2;
            root2 = temp;
        }

        parents[root2] = root1;
        size[root1] += size[root2];
    }
}
