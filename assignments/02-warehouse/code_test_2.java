import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 상자 개수
        int N = Integer.parseInt(st.nextToken());
        // 각 상자 물건 개수
        int K = Integer.parseInt(st.nextToken());
        // 꺼낼 물건 수
        int P = Integer.parseInt(st.nextToken());

        // 각 상자 안에 들어있는 물건들의 누적합 (1개 집을때값, 2개집을때값)
        int[][] boxes = new int[N+1][K+1];
        for(int i = 1; i < N+1; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j < K+1; j++){
                boxes[i][j] = Integer.parseInt(st.nextToken()) + boxes[i][j-1];
            }
        }

        // 꺼낼 개수
        int pick = Math.min(K,P);

        // dp 초기화 dp[i][i] = 앞의 i개의 상자에서 j를 선택했을때 최대값
        int[][] dp = new int[N + 1][P + 1];
        for(int i = 0; i < N + 1; i++){
            for(int j = 0; j < dp[0].length; j++){
                dp[i][j] = Integer.MIN_VALUE;
            }
        }
        dp[0][0] = 0;

        // 점화식 - dp[i][j] = max(dp[i][j], dp[i-1][j-k] + boxes[i][k])
        for(int i = 1; i < N + 1; i++){
            for(int j = 0; j <= P; j++){
                // 꺼낼 수가 상자 내용물 개수보다 많을 수 있으니
                for(int m = 0; m <= Math.min(j,pick); m++){
                    if(dp[i-1][j-m] == Integer.MIN_VALUE) continue;
                    dp[i][j] = Math.max(dp[i][j], dp[i-1][j-m] + boxes[i][m]);
                }
            }
        }

        System.out.println(dp[N][P]);
    }

}
