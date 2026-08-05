import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static int[] parents;
    private static int[] size;

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        StringBuilder answer = new StringBuilder();

        while (true) {
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

            if (answer.length() > 0) {
                answer.append('\n');
            }
            answer.append(solution(houseCount, roads));
        }

        System.out.print(answer);
    }

    private static long solution(int houseCount, int[][] roads) {
        parents = new int[houseCount];
        size = new int[houseCount];
        for (int i = 0; i < houseCount; i++) {
            parents[i] = i;
            size[i] = 1;
        }

        Arrays.sort(roads, (a, b) -> Integer.compare(a[2], b[2]));

        long totalCost = 0;
        long minimumCost = 0;
        for (int[] road : roads) {
            int start = road[0];
            int end = road[1];
            int cost = road[2];
            totalCost += cost;

            if (find(start) != find(end)) {
                union(start, end);
                minimumCost += cost;
            }
        }

        return totalCost - minimumCost;
    }

    private static int find(int current) {
        if (parents[current] == current) {
            return current;
        }
        return parents[current] = find(parents[current]);
    }

    private static void union(int first, int second) {
        int firstRoot = find(first);
        int secondRoot = find(second);

        if (firstRoot == secondRoot) {
            return;
        }

        if (size[firstRoot] < size[secondRoot]) {
            int temp = firstRoot;
            firstRoot = secondRoot;
            secondRoot = temp;
        }

        parents[secondRoot] = firstRoot;
        size[firstRoot] += size[secondRoot];
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int index;
        private int size;

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

        private int nextInt() throws IOException {
            int character;
            do {
                character = read();
            } while (character <= ' ' && character != -1);

            int value = 0;
            while (character > ' ') {
                value = value * 10 + character - '0';
                character = read();
            }
            return value;
        }
    }
}
