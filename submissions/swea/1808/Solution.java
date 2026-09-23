import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        int testCaseCount = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseCount; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int[] buttons = new int[10];

            for (int i = 0; i < 10; i++) {
                buttons[i] = Integer.parseInt(st.nextToken());
            }

            int target = Integer.parseInt(br.readLine());

            Solution solver = new Solution();
            int answer = solver.solution(buttons, target);

            output.append('#')
                    .append(testCase)
                    .append(' ')
                    .append(answer)
                    .append('\n');
        }

        System.out.print(output);
    }

    /**
     * @param buttons 숫자 버튼 0~9의 동작 여부 (입력 순서와 값 그대로)
     * @param target 만들고자 하는 양의 정수 X (입력값 그대로)
     * @return target을 만들기 위한 최소 버튼 클릭 횟수, 만들 수 없으면 -1
     */

    int[] dp;
    int MAX = 1000000;
    int[] buttonArr;
    public int solution(int[] buttons, int target) {
        buttonArr = buttons.clone();
        dp = new int[MAX + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);

        int answer = solve(target);
        if(answer == MAX){
            return -1;
        }
        return answer + 1;
    }

    private int solve(int x){
        int best = typeWord(x);

        for(int a = 2; a < x/a; a++){
            if(x % a != 0){
                continue;
            }

            int b = x / a;

            int left = solve(a);
            int right = solve(b);

            if(left != MAX || right != MAX){
                best = Math.min(best, left + right + 1);
            }
        }

        dp[x] = best;
        return best;
    }

    private int typeWord(int x){
        if(x == 0){
            return buttonArr[x] == 1 ? 1 : MAX;
        }

        int count = 0;
        while(x > 0){
            if(buttonArr[x % 10] != 1){
                return MAX;
            }

            count += 1;
            x /= 10;
        }

        return count;
    }
}
