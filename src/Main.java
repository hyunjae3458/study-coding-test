import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Path INPUT_PATH = findFile("input.txt");
    private static final Path OUTPUT_PATH = findFile("output.txt");

    public static void main(String[] args) throws IOException {
        String input = Files.readString(INPUT_PATH);
        List<String> expectedResults = readExpectedResults(Files.readString(OUTPUT_PATH));
        List<TestResult> results = runSolutions(input, expectedResults);

        int passed = 0;
        for (int i = 0; i < results.size(); i++) {
            TestResult result = results.get(i);
            boolean success = result.expected.equals(result.actual);
            if (success) passed++;

            System.out.println("테스트 케이스 " + (i + 1));
            System.out.println("- 입력값:\n" + result.input);
            System.out.println("- 기댓값: " + result.expected);
            System.out.println("- 실제값: " + result.actual);
            System.out.println("- 결과: " + (success ? "통과 ✅" : "실패 ❌"));
            if (i + 1 < results.size()) System.out.println();
        }

        System.out.println("\n총 " + passed + "/" + results.size() + "개 통과");
    }

    /** 문제 입력을 원본 값과 순서 그대로 solution의 파라미터로 변환합니다. */
    private static List<TestResult> runSolutions(String input, List<String> expectedResults) {
        List<TestResult> results = new ArrayList<>();

        try (Scanner scanner = new Scanner(input)) {
            int caseIndex = 0;
            while (scanner.hasNextInt()) {
                int n = scanner.nextInt();
                int m = scanner.nextInt();
                int[][] map = new int[n][m];
                StringBuilder caseInput = new StringBuilder().append(n).append(' ').append(m).append('\n');

                for (int row = 0; row < n; row++) {
                    for (int col = 0; col < m; col++) {
                        map[row][col] = scanner.nextInt();
                        if (col > 0) caseInput.append(' ');
                        caseInput.append(map[row][col]);
                    }
                    if (row + 1 < n) caseInput.append('\n');
                }

                if (caseIndex >= expectedResults.size()) {
                    throw new IllegalStateException("output.txt의 예제 출력이 부족합니다.");
                }

                Solution solution = new Solution();
                String actual = String.valueOf(solution.solution(n, m, map));
                results.add(new TestResult(caseInput.toString(), expectedResults.get(caseIndex), actual));
                caseIndex++;
            }

            if (caseIndex != expectedResults.size()) {
                throw new IllegalStateException("input.txt와 output.txt의 예제 개수가 다릅니다.");
            }
        }
        return results;
    }

    private static List<String> readExpectedResults(String output) {
        List<String> results = new ArrayList<>();
        try (Scanner scanner = new Scanner(output)) {
            while (scanner.hasNext()) results.add(scanner.next());
        }
        return results;
    }

    private static Path findFile(String fileName) {
        Path current = Paths.get("").toAbsolutePath();
        while (current != null) {
            Path candidate = current.resolve(fileName);
            if (Files.exists(candidate)) return candidate;
            current = current.getParent();
        }
        throw new IllegalStateException(fileName + " 파일을 찾을 수 없습니다.");
    }

    private static class TestResult {
        private final String input;
        private final String expected;
        private final String actual;

        private TestResult(String input, String expected, String actual) {
            this.input = input;
            this.expected = expected;
            this.actual = actual;
        }
    }
}
