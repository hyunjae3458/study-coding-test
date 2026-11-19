import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[][] grid = new int[10][10];
    static Map<Integer, Integer> map = new HashMap<>();
    static int answer = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        // 영역 초기화
        for(int i = 0; i < 10; i++){
            StringTokenizer st = new StringTokenizer(bf.readLine());
            for(int j = 0; j < 10; j++){
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 색종이 개수 초기화
        for(int i = 1; i <= 5; i++){
            map.put(i,5);
        }

        backtracking(0,0);
        if(answer == Integer.MAX_VALUE){
            System.out.println(-1);
        } else{
            System.out.println(answer);
        }
    }

    // 각 종류의 색종이 검증 작업
    static void backtracking(int xy, int used){
        if(xy > 99){
            answer = Math.min(answer, used);
            return;
        }
        int x = xy / 10;
        int y = xy % 10;

        if(answer <= used) return;
        if(grid[x][y] == 1){
            for(int i = 5; i > 0; i--){
                if(map.get(i) > 0 && check(i, x,y)){
                    paint(i, x,y,0);
                    map.put(i, map.get(i) - 1);
                    backtracking(xy + 1, used + 1);
                    paint(i,x,y,1);
                    map.put(i, map.get(i) + 1);
                }
            }
        } else{
            backtracking(xy + 1, used);
        }
    }

    // 색종이 검증 작업
    static boolean check(int size, int x, int y){
        if(x + size > 10 || y + size > 10) return false;
        for(int i = x; i < x + size; i++){
            for(int j = y; j < y + size; j++){
                if(grid[i][j] == 0) return false;
            }
        }

        return true;
    }

    // 색종이 입히기 작업
    static void paint(int size, int x, int y, int action){
        for(int i = x; i < x + size; i++){
            for(int j = y; j < y + size; j++){
                grid[i][j] = action;
            }
        }
    }
}
