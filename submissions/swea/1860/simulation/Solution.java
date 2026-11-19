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
            int lastPerson = reserve[N- 1];
            int[] visited = new int[lastPerson + 1];
            for(int r : reserve){
                visited[r] += 1;
            }

            int time = 1;
            int currCnt = 0;
            int answer = 0;
            while(time <= lastPerson){
                if(time % M == 0){
                    currCnt += K;
                }
                while(visited[time] > 0){
                    if(currCnt <= 0){
                        break;
                    }
                    currCnt -= 1;
                    answer += 1;
                    visited[time] -= 1;
                }
                time += 1;
            }
            if(answer == N){
                System.out.println("#" +testCase + " Possible");
            } else{
                System.out.println("#" +testCase + " Impossible");
            }
        }
    }
}
