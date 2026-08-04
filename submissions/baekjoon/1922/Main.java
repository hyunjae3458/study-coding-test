import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    static int[] parents;

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int computerCount = scanner.nextInt();
        int connectionCount = scanner.nextInt();
        int[][] connections = new int[connectionCount][3];

        for (int i = 0; i < connectionCount; i++) {
            connections[i][0] = scanner.nextInt();
            connections[i][1] = scanner.nextInt();
            connections[i][2] = scanner.nextInt();
        }

        System.out.println(solution(computerCount, connections));
    }

    static long solution(int computerCount, int[][] connections) {
        parents = new int[computerCount + 1];
        Arrays.sort(connections, (a, b) -> Integer.compare(a[2], b[2]));

        for (int i = 1; i <= computerCount; i++) {
            parents[i] = i;
        }

        long answer = 0L;
        for (int[] connection : connections) {
            int start = connection[0];
            int end = connection[1];

            if (find(start) != find(end)) {
                union(start, end);
                answer += connection[2];
            }
        }
        return answer;
    }

    static int find(int current) {
        if (parents[current] == current) {
            return current;
        }
        return parents[current] = find(parents[current]);
    }

    static void union(int first, int second) {
        int firstRoot = find(first);
        int secondRoot = find(second);
        if (firstRoot != secondRoot) {
            parents[secondRoot] = firstRoot;
        }
    }

    static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int index;
        private int size;

        int nextInt() throws IOException {
            int character;
            do {
                character = read();
            } while (character <= ' ');

            int value = 0;
            while (character > ' ') {
                value = value * 10 + character - '0';
                character = read();
            }
            return value;
        }

        private int read() throws IOException {
            if (index >= size) {
                size = input.read(buffer);
                index = 0;
                if (size == -1) return -1;
            }
            return buffer[index++];
        }
    }
}
