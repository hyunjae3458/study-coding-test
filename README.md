# 코딩 테스트 연습장

## 현재 문제

- [SWEA 4050번 재관이의 대량 할인](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWIseXoKEUcDFAWN)
- 상태: 온라인 정답 (사용자 확인, 2026-11-19)
- 난이도: D4 (SWEA 체계, https://toastfactory.tistory.com/197 에서 확인, 2026-11-19)
- 입력: T, 각 사례의 옷 개수 N, 다음 줄에 N개의 가격.
- 출력: `#테스트번호 최소구매비용`.
- `input.txt`, `output.txt`: 실행용 자체 예제 1개. 공식 예제는 미확인.
- 기존 문제 기록과 제출 파일은 그대로 보존했습니다.

## 작성할 파일

SWEA 제출 형식 그대로 `src/Solution.java`의 `main`에서 작성합니다.
입력은 BufferedReader와 StringTokenizer를 사용합니다.
문제별 입력 처리, 풀이, 결과 출력은 직접 작성합니다.

## 실행

```bash
javac -d out src/Solution.java
java -cp out Solution < input.txt
```

사용자 코드를 그대로 기록·제출 파일에 보존했습니다. 자체 예제 1개와 추가 695개 테스트를 통과했으며 공식 예제는 미확인입니다.

- 기록: `problem-records/swea/그리디/4050-재관이의-대량-할인.md`
- 제출: `submissions/swea/4050/Solution.java`

## 채점과 기록

채점을 요청하면 사용자 코드를 수정하지 않고 공식 예제와 추가·경계·무작위·성능 테스트를 수행합니다.
확장 채점을 모두 통과하면 문제 기록과 제출 파일을 생성합니다. 온라인 결과 확인 전에는 로컬 확장 채점 통과로 기록합니다.

기존 풀이 기록은 `problem-records/`, 제출 파일은 `submissions/`에 있습니다.
백준은 `src/Main.java`, SWEA는 `src/Solution.java`에서 각 플랫폼의 표준 입출력 형식으로 연습합니다.
별도 과제는 [assignments 안내](assignments/README.md)를 참고하세요.
