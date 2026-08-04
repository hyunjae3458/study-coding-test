import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    static class Node {
        int next;
        int weight;

        Node(int next, int weight) {
            this.next = next;
            this.weight = weight;
        }
    }

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
        List<Node>[] graph = new ArrayList[vertexCount + 1];
        for (int i = 0; i <= vertexCount; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];
            int weight = edge[2];

            graph[start].add(new Node(end, weight));
            graph[end].add(new Node(start, weight));
        }

        int[] minWeight = new int[vertexCount + 1];
        Arrays.fill(minWeight, Integer.MAX_VALUE);
        minWeight[1] = 0;

        boolean[] visited = new boolean[vertexCount + 1];
        PriorityQueue<Node> pq =
                new PriorityQueue<>((a, b) -> Integer.compare(a.weight, b.weight));
        pq.add(new Node(1, 0));

        long answer = 0L;
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int current = node.next;

            if (visited[current]) {
                continue;
            }

            visited[current] = true;
            answer += node.weight;

            for (Node nextNode : graph[current]) {
                int next = nextNode.next;
                int nextWeight = nextNode.weight;

                if (!visited[next] && minWeight[next] > nextWeight) {
                    minWeight[next] = nextWeight;
                    pq.add(new Node(next, nextWeight));
                }
            }
        }

        return answer;
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
