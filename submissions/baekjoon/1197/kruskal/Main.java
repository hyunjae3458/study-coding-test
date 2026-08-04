import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    static int[] parents;

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int vertexCount = scanner.nextInt();
        int edgeCount = scanner.nextInt();
        int[][] edges = new int[edgeCount][3];

        for (int i = 0; i < edgeCount; i++) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
            edges[i][2] = scanner.nextInt();
        }

        System.out.println(solution(vertexCount, edges));
    }

    static long solution(int vertexCount, int[][] edges) {
        parents = new int[vertexCount + 1];
        Arrays.sort(edges, (a, b) -> Integer.compare(a[2], b[2]));

        for (int i = 1; i <= vertexCount; i++) {
            parents[i] = i;
        }

        long answer = 0L;
        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];

            if (find(start) != find(end)) {
                answer += edge[2];
                union(start, end);
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
        int firstParent = find(first);
        int secondParent = find(second);
        parents[secondParent] = firstParent;
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

            int sign = 1;
            if (character == '-') {
                sign = -1;
                character = read();
            }

            int value = 0;
            while (character > ' ') {
                value = value * 10 + character - '0';
                character = read();
            }
            return value * sign;
        }

        private int read() throws IOException {
            if (index >= size) {
                size = input.read(buffer);
                index = 0;
                if (size == -1) {
                    return -1;
                }
            }
            return buffer[index++];
        }
    }
}
