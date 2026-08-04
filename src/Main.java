import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static final Path INPUT_PATH = findFile("input.txt");
    private static final Path OUTPUT_PATH = findFile("output.txt");

    public static void main(String[] args) throws IOException {
        String input = Files.readString(INPUT_PATH);
        String expected = normalize(Files.readString(OUTPUT_PATH));

        String actual = runSolution(input);

        System.out.println("입력값");
        System.out.println(input.stripTrailing());
        System.out.println("\n기댓값");
        System.out.println(expected);
        System.out.println("\n실행 결과");
        System.out.println(actual);
        System.out.println("\n" + (expected.equals(actual) ? "✅ 정답입니다." : "❌ 오답입니다."));
    }

    /**
     * 문제를 세팅할 때 입력을 solution의 파라미터로 바꾸고,
     * 반환값을 문자열로 바꾸는 부분입니다.
     */
    private static String runSolution(String input) {
        try (Scanner scanner = new Scanner(input)) {
            StringBuilder results = new StringBuilder();

            while (scanner.hasNextInt()) {
                int houseCount = scanner.nextInt();
                int roadCount = scanner.nextInt();

                if (houseCount == 0 && roadCount == 0) {
                    break;
                }

                int[][] roads = new int[roadCount][3];
                for (int i = 0; i < roadCount; i++) {
                    roads[i][0] = scanner.nextInt();
                    roads[i][1] = scanner.nextInt();
                    roads[i][2] = scanner.nextInt();
                }

                Solution solution = new Solution();
                long result = solution.solution(houseCount, roads);

                if (!results.isEmpty()) {
                    results.append('\n');
                }
                results.append(result);
            }

            return results.toString();
        }
    }

    private static String normalize(String value) {
        return value.replace("\r\n", "\n").stripTrailing();
    }

    private static Path findFile(String fileName) {
        Path current = Paths.get("").toAbsolutePath();

        while (current != null) {
            Path candidate = current.resolve(fileName);
            if (Files.exists(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }

        throw new IllegalStateException(fileName + " 파일을 찾을 수 없습니다.");
    }
}
