import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        checkExample(1, "input.txt", "output.txt");
        for (int number = 2; number <= 8; number++) {
            String base = "examples/baekjoon/17144/";
            checkExample(number, base + "input" + number + ".txt",
                    base + "output" + number + ".txt");
        }
    }

    private static void checkExample(int number, String inputPath, String outputPath) throws IOException {
        String expected = readFile(outputPath).trim();
        String actual = runSolution(readFile(inputPath)).trim();
        System.out.println("=== 예제 " + number + " 채점 ===");
        System.out.println("기댓값: " + expected);
        System.out.println("실제값: " + actual);
        System.out.println(expected.equals(actual) ? "정답 ✅" : "오답 ❌");
        System.out.println();
    }

    static String runSolution(String input) {
        try (Scanner scanner = new Scanner(input)) {
            if (!scanner.hasNext()) {
                return "";
            }
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            int t = scanner.nextInt();
            int[][] room = new int[r][c];
            for (int row = 0; row < r; row++) {
                for (int column = 0; column < c; column++) {
                    room[row][column] = scanner.nextInt();
                }
            }
            return String.valueOf(new Solution().solution(r, c, t, room));
        }
    }

    private static String readFile(String path) throws IOException {
        return new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(path)),
                java.nio.charset.StandardCharsets.UTF_8);
    }
}
