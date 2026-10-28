# 코딩 테스트 연습장

백준은 `src/Main.java` 한 파일에서 표준 입력과 표준 출력을 직접 작성하며 연습합니다.
초기 코드는 `import java.io.IOException;`, `import java.util.*;`, `Main` 클래스와 빈 `main` 메서드(`throws IOException` 포함)만 제공합니다.

## 현재 문제

- [백준 2206번 벽 부수고 이동하기](https://www.acmicpc.net/problem/2206)
- 상태: 온라인 정답 (2026-10-28 사용자 확인)
- 난이도: 골드 III (외부 게시글 표기 교차 확인에 따른 추정, 2026-10-26 확인)
- 난이도 출처: [swanzzz 블로그](https://swanzzz.tistory.com/25), [코딩 기록일지](https://akys159357.tistory.com/388)
- 과거 [게시글](https://my-coding-notes.tistory.com/169)은 골드 IV로 표기되어 있어 차이가 있습니다. 더 최근 게시글의 골드 III 표기를 참고했습니다.
- 공식 페이지 접근 불가로 [입출력·공식 예제 재게시 자료](https://idkim97.github.io/2022-05-10-%EB%B0%B1%EC%A4%80_2206%EB%B2%88/)를 확인했습니다.
- `input.txt`, `output.txt`: 공식 예제 1
- `examples/baekjoon/2206/`: 공식 예제 1~2
- 이전 10026번 적록색약의 온라인 정답 기록과 제출 파일은 사용자 코드 그대로 보존했습니다.

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
