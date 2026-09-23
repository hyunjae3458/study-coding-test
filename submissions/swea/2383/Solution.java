import java.io.*;
import java.util.*;
public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseCount = Integer.parseInt(br.readLine());
        StringBuilder output = new StringBuilder();

        for (int testCase = 1; testCase <= testCaseCount; testCase++) {
            int size = Integer.parseInt(br.readLine());
            int[][] map = new int[size][size];

            for (int row = 0; row < size; row++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int column = 0; column < size; column++) {
                    map[row][column] = Integer.parseInt(st.nextToken());
                }
            }

            Solution solver = new Solution();
            int answer = solver.solution(size, map);
            output.append('#').append(testCase).append(' ').append(answer).append('\n');
        }

        System.out.print(output);
    }

    /**
     * @param size 방의 한 변의 길이 N (입력값 그대로)
     * @param map N×N 지도 (0: 빈칸, 1: 사람, 2 이상: 계단 길이)
     * @return 모든 사람이 계단을 내려가 이동을 완료하는 최소 시간
     */
    class Person{
        int x;
        int y;
        public Person(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    class Stair{
        int x;
        int y;
        int length;
        public Stair(int x, int y, int length){
            this.x = x;
            this.y = y;
            this.length = length;
        }
    }
    int answer = Integer.MAX_VALUE;
    List<Person> personInfo = new ArrayList<>();
    List<Stair> stairInfo = new ArrayList<>();
    public int solution(int size, int[][] map) {
        // 사람, 계단 위치 정보 저장
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(map[i][j] == 1){
                    personInfo.add(new Person(i,j));
                }
                if(map[i][j] > 1){
                    stairInfo.add(new Stair(i,j,map[i][j]));
                }
            }
        }

        int[] people = new int[personInfo.size()];
        dfs(0, people);
        return answer;
    }

    private void dfs(int peopleIdx, int[] people){
        if(peopleIdx == people.length){
            int totalTime = simulate(people);
            answer = Math.min(answer, totalTime);
            return;
        }

        people[peopleIdx] = 0;
        dfs(peopleIdx + 1, people);
        people[peopleIdx] = 1;
        dfs(peopleIdx + 1, people);
    }

    private int simulate(int[] people){
        // 각 계단 대기 큐
        PriorityQueue<Integer> readQ1 = new PriorityQueue<>((a,b) -> a-b);
        PriorityQueue<Integer> readQ2 = new PriorityQueue<>((a,b) -> a-b);
        // 각 계단의 길이
        int length1 = stairInfo.get(0).length;
        int length2 = stairInfo.get(1).length;
        // 초기화
        for(int i = 0; i < people.length; i++){
            if(people[i] == 0){
                readQ1.add(cal(i,people[i]) + 1);
            }
            else{
                readQ2.add(cal(i,people[i]) + 1);;
            }
        }

        // 계단 위에 있는 사람 큐
        PriorityQueue<Integer> stairQ1 = new PriorityQueue<>((a,b) -> a-b);
        PriorityQueue<Integer> stairQ2 = new PriorityQueue<>((a,b) -> a-b);

        // 실시간 시뮬
        // 1번 계단 시뮬
        while(!readQ1.isEmpty()){
            int startTime = readQ1.poll();

            // 나가는 시간과 도착하는 시간이 같거나 이하면 계쏙 뺸다.
            while(!stairQ1.isEmpty() && stairQ1.peek() <= startTime){
                stairQ1.poll();
            }

            // 계단에 이미 3명이 있을 시
            if(stairQ1.size() == 3){
                int recentArriveTime = stairQ1.peek();
                // 가장 빨리 나오는 사람의 시간 비교 후
                if(recentArriveTime > startTime){
                    startTime = recentArriveTime;
                }
                // 계딴에서 뺌
                stairQ1.poll();
            }

            // 대기 끝나고 들어가서 완전 도착시간을 계단 큐에 넣음
            stairQ1.add(startTime + length1);
        }

        // 2번 계단 시뮬
        while(!readQ2.isEmpty()){
            int startTime = readQ2.poll();

            // 나가는 시간과 도착하는 시간이 같거나 이하면 계쏙 뺸다.
            while(!stairQ2.isEmpty() && stairQ2.peek() <= startTime){
                stairQ2.poll();
            }

            // 계단에 이미 3명이 있을 시
            if(stairQ2.size() == 3){
                int recentArriveTime = stairQ2.peek();
                // 가장 빨리 나오는 사람의 시간 비교 후
                if(recentArriveTime > startTime){
                    startTime = recentArriveTime;
                }
                // 계딴에서 뺌
                stairQ2.poll();
            }

            // 대기 끝나고 들어가서 완전 도착시간을 계단 큐에 넣음
            stairQ2.add(startTime + length2);
        }

        // 1번 계단 마지막에 나오는 시간
        int stair1LastTime = 0;
        while(!stairQ1.isEmpty()){
            stair1LastTime = stairQ1.poll();
        }

        // 2번 계단 마지막에 나오는 시간
        int stair2LastTime = 0;
        while(!stairQ2.isEmpty()){
            stair2LastTime = stairQ2.poll();
        }

        // 둘 중 더 늦게 나오는 시간 구해서 반환
        return Math.max(stair1LastTime, stair2LastTime);
    }

    private int cal(int person, int stair){
        return Math.abs(personInfo.get(person).x - stairInfo.get(stair).x) + Math.abs(personInfo.get(person).y - stairInfo.get(stair).y);
    }
}
