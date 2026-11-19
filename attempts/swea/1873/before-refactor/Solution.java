import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
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

            Integer dir = 0;
            Integer x = 0;
            Integer y = 0;
            for(int i = 0; i < H; i++){
                String[] arr = bf.readLine().split("");
                for(int j = 0; j < arr.length; j++){
                    grid[i][j] = arr[j];
                    if(arr[j].equals("^")){
                        dir = 2;
                        x = i;
                        y = j;
                    }
                    if(arr[j].equals(">")){
                        dir = 1;
                        x = i;
                        y = j;
                    }
                    if(arr[j].equals("v")){
                        dir = 0;
                        x = i;
                        y = j;
                    }
                    if(arr[j].equals("<")){
                        dir = 3;
                        x = i;
                        y = j;
                    }
                }
            }


            int N = Integer.parseInt(bf.readLine());
            char[] commends = bf.readLine().toCharArray();
            for(char commend : commends){
                if(commend == 'U'){
                    dir = 2;
                    String tank = "^";
                    grid[x][y] = tank;
                    int nx = x + dxs[dir];
                    int ny = y + dys[dir];
                    if(inRange(nx,ny,H,W) && grid[nx][ny].equals(".")){
                        grid[nx][ny] = tank;
                        grid[x][y] = ".";
                        x = nx;
                        y = ny;
                    } else{
                        grid[x][y] = tank;
                    }
                }
                if(commend == 'D'){
                    dir = 0;
                    String tank = "v";
                    grid[x][y] = tank;
                    int nx = x + dxs[dir];
                    int ny = y + dys[dir];
                    if(inRange(nx,ny,H,W) && grid[nx][ny].equals(".")){
                        grid[nx][ny] = tank;
                        grid[x][y] = ".";
                        x = nx;
                        y = ny;
                    } else{
                        grid[x][y] = tank;
                    }
                }
                if(commend == 'L'){
                    dir = 3;
                    String tank = "<";
                    grid[x][y] = tank;
                    int nx = x + dxs[dir];
                    int ny = y + dys[dir];
                    if(inRange(nx,ny,H,W) && grid[nx][ny].equals(".")){
                        grid[nx][ny] = tank;
                        grid[x][y] = ".";
                        x = nx;
                        y = ny;
                    } else{
                        grid[x][y] = tank;
                    }
                }
                if(commend == 'R'){
                    dir = 1;
                    String tank = ">";
                    grid[x][y] = tank;
                    int nx = x + dxs[dir];
                    int ny = y + dys[dir];
                    if(inRange(nx,ny,H,W) && grid[nx][ny].equals(".")){
                        grid[nx][ny] = tank;
                        grid[x][y] = ".";
                        x = nx;
                        y = ny;
                    } else{
                        grid[x][y] = tank;
                    }
                }
                if(commend == 'S') {
                    int nx = x;
                    int ny = y;
                    while (true) {
                        nx = nx + dxs[dir];
                        ny = ny + dys[dir];
                        if (!inRange(nx, ny, H, W)) break;
                        if (grid[nx][ny].equals("#")) break;
                        if (grid[nx][ny].equals("*")) {
                            grid[nx][ny] = ".";
                            break;
                        }
                    }
                }
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

    static boolean inRange(int nx, int ny, int H, int W){
        return nx >= 0 && nx < H && ny >= 0 && ny < W;
    }
}
