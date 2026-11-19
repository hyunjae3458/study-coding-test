import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.FileInputStream;
import java.util.StringTokenizer;

class Solution {
    public static void main(String args[]) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(bf.readLine());
        for (int testCase = 1; testCase <= T; testCase++) {
            StringTokenizer st = new StringTokenizer(bf.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            int[][] grid = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(bf.readLine());
                for (int j = 0; j < N; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            int answer = 0;
            for (int i = 0; i < N; i++) {
                int count = 0;
                for (int j = 0; j < N; j++) {
                    if (grid[i][j] == 1) {
                        count += 1;
                    } else {
                        if (count == K) {
                            answer += 1;
                        }
                        count = 0;
                    }
                }
                if (count == K) {
                    answer += 1;
                }
            }

            for (int j = 0; j < N; j++) {
                int count = 0;
                for (int i = 0; i < N; i++) {
                    if (grid[i][j] == 1) {
                        count += 1;
                    } else {
                        if (count == K) {
                            answer += 1;
                        }
                        count = 0;
                    }
                }
                if (count == K) {
                    answer += 1;
                }
            }

            System.out.println("#" + testCase + " " + answer);
        }
    }
}
