import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseCount = Integer.parseInt(br.readLine());
        StringBuilder output = new StringBuilder();

        for (int testCase = 1; testCase <= testCaseCount; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int rows = Integer.parseInt(st.nextToken());
            int columns = Integer.parseInt(st.nextToken());
            char[][] program = new char[rows][columns];

            for (int row = 0; row < rows; row++) {
                program[row] = br.readLine().toCharArray();
            }

            Solution solver = new Solution();
            boolean canStop = solver.solution(rows, columns, program);
            output.append('#').append(testCase).append(' ')
                    .append(canStop ? "YES" : "NO").append('\n');
        }

        System.out.print(output);
    }

    /**
     * @param rows 프로그램 영역의 행 크기 R (입력값 그대로)
     * @param columns 프로그램 영역의 열 크기 C (입력값 그대로)
     * @param program R×C 프로그램 명령 문자 (입력값 그대로)
     * @return 프로그램이 정지 명령어(@)를 실행할 가능성이 있으면 true
     */
    class Node{
        int x;
        int y;
        int dir;
        int val;
        public Node(int x, int y, int dir, int val){
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.val = val;
        }
    }

    class State{
        int dir;
        int val;
        public State(int dir, int val){
            this.dir = dir;
            this.val = val;
        }
    }

    // 우 하 좌 상
    int[] dxs = {0,1,0,-1};
    int[] dys = {1,0,-1,0};
    boolean possible = false;
    boolean[][][][] path;
    int N;
    int M;
    public boolean solution(int rows, int columns, char[][] program) {
        // x y val dir
        path = new boolean[rows][columns][16][4];
        N = rows;
        M = columns;
        boolean noArrive = true;

        for(char[] p : program){
            for(char c : p){
                if(c == '@'){
                    noArrive = false;
                }
            }
        }

        if(noArrive) return false;

        Queue<Node> q = new LinkedList<>();
        q.add(new Node(0,0,0,0));
        path[0][0][0][0] = true;
        while(!q.isEmpty()){
            Node curr = q.poll();
            int currX = curr.x;
            int currY = curr.y;
            int dir = curr.dir;
            int val = curr.val;


            if(program[currX][currY] == '@'){
                return true;
            }


            if(program[currX][currY] == '?'){
                for(int i = 0; i < 4; i++){

                    int nx = (currX + dxs[i] + N) % N;
                    int ny = (currY + dys[i] + M) % M;

                    if(!path[nx][ny][val][i]){
                        path[nx][ny][val][i] = true;
                        q.add(new Node(nx,ny,i,val));
                    }
                }
            }
            else{
                State nextState = commands(val, dir, program[currX][currY]);

                int nx = (currX + dxs[nextState.dir] + N) % N;
                int ny = (currY + dys[nextState.dir] + M) % M;
                if(!path[nx][ny][nextState.val][nextState.dir]){
                    path[nx][ny][nextState.val][nextState.dir] = true;
                    q.add(new Node(nx,ny,nextState.dir, nextState.val));
                }


            }
        }

        return false;
    }
    private State commands(int val, int dir, char command){
        if(command == '>'){
            dir = 0;
        }
        if(command == '<'){
            dir = 2;
        }
        if(command == '^'){
            dir = 3;
        }
        if(command == 'v'){
            dir = 1;
        }
        if(command == '_'){
            if(val == 0){
                dir = 0;
            } else{
                dir = 2;
            }
        }
        if(command == '|'){
            if(val == 0){
                dir = 1;
            } else{
                dir = 3;
            }
        }
        if(Character.isDigit(command)){
            val = command - '0';
        }
        if(command == '+'){
            val += 1;
            if(val > 15){
                val = 0;
            }
        }
        if(command == '-'){
            val -= 1;
            if(val < 0){
                val = 15;
            }
        }

        return new State(dir, val);
    }
}
