import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.PriorityQueue;

public class Main {
    static class Node {
        int start;
        int end;
        double cost;

        Node(int start, int end, double cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }
    }

    static int[] parents;
    static int[] size;

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int godCount = scanner.nextInt();
        int connectedPathCount = scanner.nextInt();
        int[][] positions = new int[godCount][2];
        int[][] connectedPaths = new int[connectedPathCount][2];

        for (int i = 0; i < godCount; i++) {
            positions[i][0] = scanner.nextInt();
            positions[i][1] = scanner.nextInt();
        }

        for (int i = 0; i < connectedPathCount; i++) {
            connectedPaths[i][0] = scanner.nextInt();
            connectedPaths[i][1] = scanner.nextInt();
        }

        System.out.printf("%.2f%n", solution(positions, connectedPaths));
    }

    static double solution(int[][] positions, int[][] connectedPaths) {
        int n = positions.length + 1;
        parents = new int[n];
        size = new int[n];
        PriorityQueue<Node> pq =
                new PriorityQueue<>((a, b) -> Double.compare(a.cost, b.cost));

        for (int i = 0; i < positions.length - 1; i++) {
            for (int j = i + 1; j < positions.length; j++) {
                double cost = Math.hypot(
                        positions[i][0] - positions[j][0],
                        positions[i][1] - positions[j][1]);
                pq.add(new Node(i + 1, j + 1, cost));
            }
        }

        for (int i = 1; i < n; i++) {
            parents[i] = i;
            size[i] = 1;
        }

        for (int[] path : connectedPaths) {
            if (find(path[0]) != find(path[1])) {
                union(path[0], path[1]);
            }
        }

        double answer = 0.0;
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (find(current.start) != find(current.end)) {
                union(current.start, current.end);
                answer += current.cost;
            }
        }
        return answer;
    }

    static int find(int current) {
        if (parents[current] == current) return current;
        return parents[current] = find(parents[current]);
    }

    static void union(int first, int second) {
        int firstRoot = find(first);
        int secondRoot = find(second);

        if (firstRoot == secondRoot) return;

        if (size[firstRoot] < size[secondRoot]) {
            int temporary = size[firstRoot];
            size[firstRoot] = size[secondRoot];
            size[secondRoot] = temporary;
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
