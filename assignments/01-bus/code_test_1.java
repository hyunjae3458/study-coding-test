import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 운행횟수
        int R = Integer.parseInt(st.nextToken());
        // 최대 탑승인원
        int K = Integer.parseInt(st.nextToken());
        // 줄을 이루는 수
        int N = Integer.parseInt(st.nextToken());

        // 줄
        int[] groups = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            groups[i] = Integer.parseInt(st.nextToken());
        }

        // 대기 줄 큐
        Queue<Integer> ready = new LinkedList<>();
        // 버스 안 큐
        Queue<Integer> bus = new LinkedList<>();
        // 탑승자 수
        int people = 0;
        // 수익
        long answer = 0;
        // 초기화
        for(int i = 0; i < groups.length; i++){
            ready.add(groups[i]);
        }
        for(int i = 0; i < R; i++){
            while(people < K){
                if(ready.isEmpty() || ready.peek() + people > K){
                    break;
                }
                int count = ready.poll();
                people += count;
                answer += count;
                bus.add(count);
            }
            people = 0;
            while(!bus.isEmpty()){
                ready.add(bus.poll());
            }
        }
        System.out.println(answer);
    }

}
