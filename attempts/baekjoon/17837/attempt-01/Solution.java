import java.util.*;
public class Solution {
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
        int child = 0;
        int parent = 0;

        public Node(int x, int y, int dir){
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }
    Node[] nodes;
    int[] dxs = {0,0,-1,1};
    int[] dys = {1,-1,0,0};
    // 말의 위치를 보여주는 보드(여기에 나타난 숫자가 맨아래 있는 말)
    int[][] arr;
    // 최대로 쌓인수
    int depth = Integer.MIN_VALUE;
    public int solution(int n, int k, int[][] board, int[][] pieces) {
        arr = new int[n][n];
        nodes = new Node[k+1];
        // 초기화
        for(int i = 0; i < pieces.length; i++){
            int num = i + 1;
            int x = pieces[i][0] -1;
            int y = pieces[i][1] -1;
            int dir = pieces[i][2] - 1;
            nodes[num] = new Node(x,y,dir);
            arr[x][y] = num;
        }
        int answer = 0;

        while(depth != 4){
            if(answer >= 1000){
                return -1;
            }
            for(int i  = 1; i < k + 1; i++){
                move(i,board);
            }
            answer += 1;

        }
        return answer;
    }

    private void move(int i,int[][] board){
        Node node = nodes[i];
        int x = node.x;
        int y = node.y;
        int dir = node.dir;
        int nx = x + dxs[dir];
        int ny = y + dys[dir];
        if(!inRange(nx,ny, board.length)){
            colorSimulation(i,x,y,nx,ny, dir,2, board);
        } else{
            colorSimulation(i,x,y,nx,ny,dir,board[nx][ny],board);
        }

    }

    private void colorSimulation(int num, int x,int y, int nx, int ny, int dir, int color, int[][] board){
        // 흰색 타일일때
        if(color == 0){
            // 다음칸의 숫자
            int next = arr[nx][ny];
            int parent = nodes[num].parent;
            // 만약 이미 말이 있다면 말 위에 놓는다.
            if(next != 0){
                goUp(num,next);
                // 말들 좌표이동
                go(num,nx,ny);
                depth = Math.max(depth, checkDepth(arr[nx][ny]));
                // 부모가 없으면 기존 칸은 0으로 갱신
                if(nodes[num].parent == 0){
                    arr[x][y] = 0;
                }
                // 부모 자식 연결
                nodes[num].parent = getTop(next);
                nodes[getTop(next)].child = num;
            } else{
                // 말들 좌표이동
                go(num,nx,ny);
                // 보드 갱신
                arr[nx][ny] = num;
                // 부모가 없으면 기존 칸은 0으로 갱신
                if(nodes[num].parent == 0){
                    arr[x][y] = 0;
                }
                // 움직일때 만약 아래에 말이 있다면
                if(parent != 0){
                    nodes[parent].child = 0;
                    nodes[num].parent = 0;
                }
            }

        }
        // 빨간 타일일때
        if(color == 1){
            // 다음칸의 숫자
            int next = arr[nx][ny];
            int parent = nodes[num].parent;
            // 다음칸에 말이 있을때
            if(next != 0){
                goUp(num,next);
                depth = Math.max(depth, checkDepth(arr[nx][ny]));
                go(num,nx,ny);

                // 움직일때 만약 아래에 말이 있다면
                if(parent != 0){
                    nodes[parent].child = 0;
                    nodes[num].parent = 0;
                }
                // 부모가 없으면 해당 칸은 0으로 갱신
                if(nodes[num].parent == 0){
                    arr[x][y] = 0;
                }
                // 부모 자식 연결
                nodes[num].parent = getTop(next);
                nodes[getTop(next)].child = num;
                int top = getTop(num);
                reverse(next);
                // 보드 갱신
                arr[nx][ny] = top;

            } else{
                go(num,nx,ny);
                // 움직일때 만약 아래에 말이 있다면
                if(parent != 0){
                    nodes[parent].child = 0;
                    nodes[num].parent = 0;
                }
                // 보드 갱신
                // 부모가 없으면 해당 칸은 0으로 갱신
                if(nodes[num].parent == 0){
                    arr[x][y] = 0;
                }
                arr[nx][ny] = num;
                int top = getTop(num);
                reverse(num);
                // 보드 갱신
                arr[nx][ny] = top;
            }

        }
        // 파란 타일
        if(color == 2){
            int parent = nodes[num].parent;
            dir = (5-dir) % 4;
            nx = x + dxs[dir];
            ny = y + dys[dir];
            nodes[num].dir = dir;
            if(!inRange(nx,ny,arr.length) || board[nx][ny] == 2){
                return;
            } else{
                int next = arr[nx][ny];
                // 다음 칸에 말이 이미 있다면
                if(next != 0){
                    // 올라타기
                    goUp(num,next);
                    // 말들 좌표이동
                    go(num,nx,ny);

                    // 움직일때 만약 아래에 말이 있다면
                    if(parent != 0){
                        nodes[parent].child = 0;
                        nodes[num].parent = 0;
                    }
                    depth = Math.max(depth, checkDepth(arr[nx][ny]));
                    // 보드 갱신
                    arr[nx][ny] = num;
                    if(nodes[num].parent == 0){
                        arr[x][y] = 0;
                    }
                    // 부모 자식 연결
                    nodes[num].parent = getTop(next);
                    nodes[getTop(next)].child = num;
                } else{
                    // 말들 좌표이동
                    go(num,nx,ny);
                    // 보드 갱신
                    arr[nx][ny] = num;
                    if(nodes[num].parent == 0){
                        arr[x][y] = 0;
                    }
                    // 움직일때 만약 아래에 말이 있다면
                    if(parent != 0){
                        nodes[parent].child = 0;
                        nodes[num].parent = 0;
                    }
                }
            }
        }

    }

    // 좌표 이동
    private void go(int num, int nx, int ny){
        Node curr = nodes[num];
        int child = nodes[num].child;
        int parent = nodes[num].parent;
        curr.x = nx;
        curr.y = ny;
        while(child != 0){
            nodes[child].x = nx;
            nodes[child].y = ny;
            child = nodes[child].child;
        }

    }

    // 다른 말 위에 탈때
    private void goUp(int me, int target){
        Node curr = nodes[target];
        int child = nodes[target].child;


        while(child != 0){
            curr = nodes[child];
            child = nodes[child].child;
        }
    }

    // 말의 쌓인 순서 뒤집힐때
    private void reverse(int node){
        int curr = node;
        int child = nodes[curr].child;
        nodes[curr].parent = child;

        while(child != 0){
            int temp = nodes[child].child;
            nodes[child].child = curr;
            nodes[child].parent = temp;
            curr = child;
            child = temp;
        }
        nodes[node].child = 0;
    }

    // 말의 쌓인 높이 체크
    private int checkDepth(int me){
        int cnt = 1;
        int child = nodes[me].child;
        while(child != 0){
            cnt += 1;
            child = nodes[child].child;
        }
        return cnt;
    }

    // 가장 위에 있는 말 찾기
    private int getTop(int num){
        int curr = num;
        int child = nodes[curr].child;
        while(child != 0){
            curr = child;
            child = nodes[child].child;
        }
        return curr;
    }

    private boolean inRange(int nx, int ny, int n){
        return nx >= 0 && nx < n && ny >= 0 && ny < n;
    }
}
