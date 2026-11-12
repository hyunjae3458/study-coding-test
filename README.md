# 코딩 테스트 연습장

백준은 `src/Main.java` 한 파일에서 표준 입력과 표준 출력을 직접 작성하며 연습합니다.
초기 코드는 `import java.io.IOException;`, `import java.util.*;`, `Main` 클래스와 빈 `main` 메서드(`throws IOException` 포함)만 제공합니다.

## 현재 문제

- [백준 1005번 ACM Craft](https://www.acmicpc.net/problem/1005)
- 상태: 로컬 확장 채점 통과 (2026-11-12, 온라인 정답 미확인)
- 난이도: 골드 III (외부 게시글 표기 교차 확인에 따른 추정, 2026-11-12 확인)
- 난이도 출처: [system32 블로그](https://blog.system32.kr/501), [dev-ho 블로그](https://dev-ho.tistory.com/16)
- 공식 페이지 접근 불가로 [입출력·공식 예제 재게시 자료](https://konghana01.tistory.com/327)를 확인했습니다.
- `input.txt`, `output.txt`: 공식 예제 1 (테스트 케이스 2개)
- `examples/baekjoon/1005/`: 공식 예제 1~2 (테스트 케이스 총 7개) 보관
- 이전 16234번 인구 이동의 최신 사용자 코드를 재검증하여 기록과 제출 파일에 보존했습니다. 상태는 로컬 확장 채점 통과입니다.

## 실행

```bash
javac -d out src/Main.java
java -cp out Main < input.txt
```

IDE에서는 `Main`을 실행하고 콘솔에 입력을 붙여 넣어도 됩니다.
초기 템플릿의 `main`은 비어 있으며, 입력과 출력은 직접 작성합니다.

## 채점과 기록

`채점해줘`라고 요청하면 사용자 코드를 수정하지 않고 공식 예제와 추가·경계·무작위·성능 테스트로 검증합니다.
확장 채점이 모두 통과하면 문제 기록과 `submissions/baekjoon/<문제번호>/Main.java`를 생성합니다.
온라인 정답이 확인되기 전에는 `로컬 확장 채점 통과`로 기록합니다.
기록에는 사용자가 작성한 `src/Main.java` 전체를 그대로 보존합니다.

기존 풀이 기록은 `problem-records/`, 제출 파일은 `submissions/`에 있습니다.
별도 과제의 실행 방법은 [assignments 안내](assignments/README.md)를 참고하세요.
