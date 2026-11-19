import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Solution {
    public static void main(String args[]) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(bf.readLine());

        for (int testCase = 1; testCase <= T; testCase++) {
            StringTokenizer st = new StringTokenizer(bf.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            int[] waiting = new int[N];
            st = new StringTokenizer(bf.readLine());
            for(int i = 0; i < N; i++){
                waiting[i] = Integer.parseInt(st.nextToken());
            }
            Arrays.sort(waiting);
            boolean possible = true;
            for(int i = 0; i < N; i++){
                int produced = (waiting[i] / M) * K;

                if(produced < i + 1){
                    possible = false;
                    break;
                }
            }

            System.out.println("#" + testCase + " " + (possible ? "Possible" : "Impossible"));
        }
    }
}
