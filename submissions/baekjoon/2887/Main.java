import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    private static class Edge {
        int start;
        int end;
        int cost;

        Edge(int start, int end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }
    }

    private static class Coordinate {
        int number;
        int position;

        Coordinate(int number, int position) {
            this.number = number;
            this.position = position;
        }
    }

    private static int[] parents;
    private static int[] size;

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int planetCount = scanner.nextInt();
        int[][] planets = new int[planetCount][3];
        for (int i = 0; i < planetCount; i++) {
            planets[i][0] = scanner.nextInt();
            planets[i][1] = scanner.nextInt();
            planets[i][2] = scanner.nextInt();
        }
        System.out.print(solution(planets));
    }

    private static long solution(int[][] planets) {
        parents = new int[planets.length];
        size = new int[planets.length];
        for (int i = 0; i < planets.length; i++) {
            parents[i] = i;
            size[i] = 1;
        }

        List<Coordinate> xCoordinates = new ArrayList<>();
        List<Coordinate> yCoordinates = new ArrayList<>();
        List<Coordinate> zCoordinates = new ArrayList<>();
        for (int i = 0; i < planets.length; i++) {
            xCoordinates.add(new Coordinate(i, planets[i][0]));
            yCoordinates.add(new Coordinate(i, planets[i][1]));
            zCoordinates.add(new Coordinate(i, planets[i][2]));
        }

        Collections.sort(xCoordinates, (a, b) -> Integer.compare(a.position, b.position));
        Collections.sort(yCoordinates, (a, b) -> Integer.compare(a.position, b.position));
        Collections.sort(zCoordinates, (a, b) -> Integer.compare(a.position, b.position));

        List<Edge> edges = new ArrayList<>();
        for (int i = 1; i < planets.length; i++) {
            edges.add(new Edge(xCoordinates.get(i).number, xCoordinates.get(i - 1).number,
                    Math.abs(xCoordinates.get(i).position - xCoordinates.get(i - 1).position)));
            edges.add(new Edge(yCoordinates.get(i).number, yCoordinates.get(i - 1).number,
                    Math.abs(yCoordinates.get(i).position - yCoordinates.get(i - 1).position)));
            edges.add(new Edge(zCoordinates.get(i).number, zCoordinates.get(i - 1).number,
                    Math.abs(zCoordinates.get(i).position - zCoordinates.get(i - 1).position)));
        }

        Collections.sort(edges, (a, b) -> Integer.compare(a.cost, b.cost));
        long answer = 0L;
        for (Edge edge : edges) {
            if (find(edge.start) != find(edge.end)) {
                union(edge.start, edge.end);
                answer += edge.cost;
            }
        }
        return answer;
    }

    private static int find(int current) {
        if (parents[current] == current) return current;
        return parents[current] = find(parents[current]);
    }

    private static void union(int first, int second) {
        int firstRoot = find(first);
        int secondRoot = find(second);
        if (firstRoot == secondRoot) return;

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
        private int length;

        private int read() throws IOException {
            if (index >= length) {
                length = input.read(buffer);
                index = 0;
                if (length == -1) return -1;
            }
            return buffer[index++];
        }

        private int nextInt() throws IOException {
            int character;
            do {
                character = read();
            } while (character <= ' ' && character != -1);

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
    }
}
