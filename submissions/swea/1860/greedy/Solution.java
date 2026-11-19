import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Solution {
    public static void main(String args[]) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(bf.readLine());

        for (int testCase = 1; testCase <= T; testCase++) {
            StringTokenizer st = new StringTokenizer(bf.readLine());
            // 손님 수
            int N = Integer.parseInt(st.nextToken());
            // 초
            int M = Integer.parseInt(st.nextToken());
            // 개수
            int K = Integer.parseInt(st.nextToken());

            int[] reserve = new int[N];
            st = new StringTokenizer(bf.readLine());
            for(int i = 0; i < N; i++){
                reserve[i] = Integer.parseInt(st.nextToken());
            }
            // 먼저 온 사람으로 정렬
            Arrays.sort(reserve);
            boolean possible = true;

            for(int i = 0; i < N; i++){
                int produced = (reserve[i] / M) * K;

                if(produced < i + 1){
                    possible = false;
                    break;
                }
            }

            System.out.println("#" + testCase + " " + (possible ? "Possible" : "Impossible"));
        }
    }
}
