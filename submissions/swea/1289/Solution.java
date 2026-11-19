import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
    public static void main(String args[]) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(bf.readLine());

        for (int testCase = 1; testCase <= T; testCase++) {
            String bit = bf.readLine();
            int answer = 0;
            char before = '0';
            for(int i = 0; i < bit.length(); i++){
                if(before != bit.charAt(i)){
                    answer += 1;
                    before = bit.charAt(i);
                }
            }
            System.out.println("#" + testCase + " " + answer);
        }

    }
}
