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

    /** 문제 입력을 원본 값과 순서 그대로 solution의 파라미터로 변환합니다. */
    private static String runSolution(String input) {
        try (Scanner scanner = new Scanner(input)) {
            int planetCount = scanner.nextInt();
            int[][] planets = new int[planetCount][3];

            for (int i = 0; i < planetCount; i++) {
                planets[i][0] = scanner.nextInt();
                planets[i][1] = scanner.nextInt();
                planets[i][2] = scanner.nextInt();
            }

            Solution solution = new Solution();
            return String.valueOf(solution.solution(planets));
        }
    }

    private static String normalize(String value) {
        return value.replace("\r\n", "\n").stripTrailing();
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
}
