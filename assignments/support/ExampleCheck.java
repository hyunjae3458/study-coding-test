import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/** 로컬 예제 비교 전용. 제출할 때는 문제 폴더의 Main.java만 사용합니다. */
public class ExampleCheck {
    public static void main(String[] args) throws Exception {
        List<Path> inputs = new ArrayList<>();
        inputs.add(Path.of("input.txt"));
        try (Stream<Path> files = Files.list(Path.of("examples"))) {
            files.filter(p -> p.getFileName().toString().matches("input\\d+\\.txt"))
                    .sorted().forEach(inputs::add);
        }
        int passed = 0;
        for (int i = 0; i < inputs.size(); i++) {
            Path input = inputs.get(i);
            Path expectedFile = input.resolveSibling(input.getFileName().toString().replace("input", "output"));
            String expected = Files.readString(expectedFile).trim();
            Path output = Files.createTempFile("assignment-output-", ".txt");
            Path error = Files.createTempFile("assignment-error-", ".txt");
            Process process = null;
            System.out.println("테스트 케이스 " + (i + 1));
            System.out.println("- 입력값: " + Files.readString(input).trim());
            System.out.println("- 기댓값: " + expected);
            long start = System.nanoTime();
            try {
                process = new ProcessBuilder(
                        Path.of(System.getProperty("java.home"), "bin", "java").toString(),
                        "-cp", System.getProperty("java.class.path"), "Main")
                        .redirectInput(input.toFile()).redirectOutput(output.toFile())
                        .redirectError(error.toFile()).start();
                if (!process.waitFor(10, TimeUnit.SECONDS)) {
                    process.destroyForcibly().waitFor();
                    System.out.println("- 결과: 로컬 실행 제한 10초 초과 ❌");
                    continue;
                }
                String actual = Files.readString(output).trim();
                String stderr = Files.readString(error).trim();
                boolean ok = process.exitValue() == 0 && expected.equals(actual);
                if (ok) passed++;
                System.out.println("- 실제값: " + (actual.isEmpty() ? "(출력 없음)" : actual));
                if (!stderr.isEmpty()) System.out.println(stderr);
                System.out.println("- 결과: " + (ok ? "통과 ✅" : "실패 ❌"));
            } finally {
                if (process != null && process.isAlive()) process.destroyForcibly().waitFor();
                Files.deleteIfExists(output);
                Files.deleteIfExists(error);
                System.out.printf("- 실행 시간: %.3f초 (JVM 시작 포함)%n%n", (System.nanoTime() - start) / 1e9);
            }
        }
        System.out.println("예제 " + inputs.size() + "개 중 " + passed + "개 통과");
        if (passed != inputs.size()) System.exit(1);
    }
}
