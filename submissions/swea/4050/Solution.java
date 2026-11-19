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
            int N = Integer.parseInt(st.nextToken());
            int[] price = new int[N];

            int totalPrice = 0;
            st = new StringTokenizer(bf.readLine());
            for(int i = 0; i < N; i++){
                int p = Integer.parseInt(st.nextToken());
                price[i] = p;
                totalPrice += p;
            }
            Arrays.sort(price);
            int[] sortedPrice = new int[N];
            for(int i = 0; i < N; i++){
                sortedPrice[i] = price[N - 1 - i];
            }
            int discountCnt = price.length / 3;
            int answer = 0;
            for(int i = 2; i < N; i += 3){
                answer += sortedPrice[i];
                discountCnt -= 1;
                if(discountCnt == 0){
                    break;
                }
            }

            totalPrice -= answer;

            System.out.println("#" + testCase + " " + totalPrice);
        }
    }
}
