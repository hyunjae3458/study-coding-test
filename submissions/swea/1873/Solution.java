import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
    static int[] dxs = {1,0,-1,0};
    static int[] dys = {0,1,0,-1};
    public static void main(String args[]) throws Exception {
        //.	평지(전차가 들어갈 수 있다.)
        //*	벽돌로 만들어진 벽
        //#	강철로 만들어진 벽
        //-	물(전차는 들어갈 수 없다.)
        //^	위쪽을 바라보는 전차(아래는 평지이다.)
        //v	아래쪽을 바라보는 전차(아래는 평지이다.)
        //<	왼쪽을 바라보는 전차(아래는 평지이다.)
        //>	오른쪽을 바라보는 전차(아래는 평지이다.)

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(bf.readLine());

        for (int testCase = 1; testCase <= T; testCase++) {
            StringTokenizer st = new StringTokenizer(bf.readLine());
            int H = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());
            // 게임판
            String[][] grid = new String[H][W];

            Tank tank = null;
            for(int i = 0; i < H; i++){
                String[] arr = bf.readLine().split("");
                for(int j = 0; j < arr.length; j++){
                    grid[i][j] = arr[j];
                    if(arr[j].equals("^")){
                        tank = new Tank(i, j, 2);
                    }
                    if(arr[j].equals(">")){
                        tank = new Tank(i, j, 1);
                    }
                    if(arr[j].equals("v")){
                        tank = new Tank(i, j, 0);
                    }
                    if(arr[j].equals("<")){
                        tank = new Tank(i, j, 3);
                    }
                }
            }


            int N = Integer.parseInt(bf.readLine());
            char[] commends = bf.readLine().toCharArray();
            for(char commend : commends){
                processCommand(commend, tank, grid);
            }

            System.out.print("#" + testCase + " ");
            for(int i = 0; i < H; i++){
                for(int j = 0; j < W; j++){
                    System.out.print(grid[i][j]);
                }
                System.out.println();
            }
        }
    }

    static void processCommand(char command, Tank tank, String[][] grid){
        switch(command){
            case 'U':
                tank.move(2, grid);
                break;
            case 'D':
                tank.move(0, grid);
                break;
            case 'L':
                tank.move(3, grid);
                break;
            case 'R':
                tank.move(1, grid);
                break;
            case 'S':
                tank.shoot(grid);
                break;
        }
    }

    static class Tank {
        int x;
        int y;
        int dir;

        Tank(int x, int y, int dir){
            this.x = x;
            this.y = y;
            this.dir = dir;
        }

        void move(int dir, String[][] grid){
            this.dir = dir;
            int nx = x + dxs[dir];
            int ny = y + dys[dir];
            if(inRange(nx,ny,grid.length,grid[0].length) && grid[nx][ny].equals(".")){
                grid[x][y] = ".";
                x = nx;
                y = ny;
            }
            grid[x][y] = symbol();
        }

        void shoot(String[][] grid){
            int nx = x;
            int ny = y;
            while(true){
                nx = nx + dxs[dir];
                ny = ny + dys[dir];
                if(!inRange(nx,ny,grid.length,grid[0].length)) break;
                if(grid[nx][ny].equals("#")) break;
                if(grid[nx][ny].equals("*")){
                    grid[nx][ny] = ".";
                    break;
                }
            }
        }

        String symbol(){
            switch(dir){
                case 0: return "v";
                case 1: return ">";
                case 2: return "^";
                case 3: return "<";
                default: throw new IllegalStateException("Unknown direction: " + dir);
            }
        }
    }

    static boolean inRange(int nx, int ny, int H, int W){
        return nx >= 0 && nx < H && ny >= 0 && ny < W;
    }
}
