import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    static int[] parents;
    static int[] size;

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int houseCount = scanner.nextInt();
        int roadCount = scanner.nextInt();
        int[][] roads = new int[roadCount][3];

        for (int i = 0; i < roadCount; i++) {
            roads[i][0] = scanner.nextInt();
            roads[i][1] = scanner.nextInt();
            roads[i][2] = scanner.nextInt();
        }

        System.out.println(solution(houseCount, roads));
    }

    static long solution(int houseCount, int[][] roads) {
        parents = new int[houseCount + 1];
        size = new int[houseCount + 1];

        for (int i = 1; i <= houseCount; i++) {
            parents[i] = i;
            size[i] = 1;
        }

        Arrays.sort(roads, (a, b) -> Integer.compare(a[2], b[2]));

        long answer = 0L;
        int max = Integer.MIN_VALUE;
        for (int[] road : roads) {
            int start = road[0];
            int end = road[1];
            int cost = road[2];

            if (find(start) != find(end)) {
                union(start, end);
                answer += cost;
                max = Math.max(max, cost);
            }
        }
        return answer - max;
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

        if (firstRoot == secondRoot) return;

        if (size[firstRoot] < size[secondRoot]) {
            int temporary = firstRoot;
            firstRoot = secondRoot;
            secondRoot = temporary;
        }

        parents[secondRoot] = firstRoot;
        size[firstRoot] += size[secondRoot];
    }

    static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int index;
        private int length;

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
            if (index >= length) {
                length = input.read(buffer);
                index = 0;
                if (length == -1) return -1;
            }
            return buffer[index++];
        }
    }
}
