import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
    public static void main(String args[]) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(bf.readLine());

        for (int testCase = 1; testCase <= T; testCase++) {
            StringTokenizer st = new StringTokenizer(bf.readLine());
            int N = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(bf.readLine());
            int[] price = new int[N];
            for(int i = 0; i < N; i++){
                price[i] = Integer.parseInt(st.nextToken());
            }
            int currPrice = price[N-1];
            long answer = 0;
            for(int i = N - 2; i >= 0; i--){
                if(currPrice > price[i]){
                    answer += currPrice - price[i];

                } else{
                    currPrice = price[i];
                }
            }

            System.out.println("#" + testCase + " " + answer);
        }
    }
}
