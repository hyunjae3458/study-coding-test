# 코딩 테스트 연습장

백준 문제도 프로그래머스처럼 `solution` 함수만 구현하며 연습하기 위한 Java 템플릿입니다.

## 사용 방법

1. `src/Solution.java`의 `solution` 함수를 구현합니다.
2. `src/Main.java`를 실행합니다.
3. 프로그램이 `input.txt`를 읽어 실행한 뒤, 결과를 `output.txt`와 비교합니다.

현재는 **백준 6497번 전력난** 문제로 세팅되어 있습니다.

- 난이도: 골드 IV (solved.ac, 2026-08-04 확인)

```java
public long solution(int houseCount, int[][] roads) {
    return 0L;
}
```

집 번호는 원본 입력의 0-based 값을 그대로 전달합니다. 입력에 여러 테스트 케이스가
있으면 각 테스트 케이스마다 `solution`을 한 번씩 호출하고 결과를 줄별로 비교합니다.

## 파일 역할

- `src/Solution.java`: 직접 문제를 푸는 파일
- `src/Main.java`: 입력 변환 및 결과 채점기
- `input.txt`: 문제의 예제 입력
- `output.txt`: 문제의 예제 출력
- `problem-records/`: 정답 문제의 해설, 코드, 오답 원인 및 복습 기록

앞으로 문제를 바꿀 때는 문제의 입력 형식에 맞게 `Main.runSolution()`과
`Solution.solution(...)`, `input.txt`, `output.txt`를 함께 갱신합니다.

온라인 채점에서 정답을 받은 뒤에는 `problem-records`에 문제별 복습 기록을
남깁니다. 기록 형식은 `problem-records/TEMPLATE.md`에서 확인할 수 있습니다.

풀이를 작성한 뒤 `채점해줘`라고 요청하면 공식 예제뿐 아니라 추가·경계 테스트로
검증합니다. 이때는 입력값, 기댓값, 실제값과 통과 여부만 알려주며, 사용자가 직접
도움을 요청하기 전에는 풀이법이나 힌트를 제공하지 않습니다.

확장 채점의 모든 테스트를 통과하면 문제 기록을 자동으로 생성합니다. 온라인 제출
전이라면 `로컬 확장 채점 통과`, 실제 제출 정답이 확인되면 `온라인 정답`으로 구분해
표시합니다.

동시에 `submissions/<플랫폼>/<문제번호>/`에 플랫폼 제출용 파일도 자동 생성합니다.
백준 Java 문제는 독립 실행 가능한 단일 `Main.java`로 만들고, 디버그 출력과 연습용
파일 의존성을 제거한 뒤 별도로 컴파일하고 다시 검증합니다.

## 터미널에서 실행

```bash
javac -d out src/Main.java src/Solution.java
java -cp out Main
```
