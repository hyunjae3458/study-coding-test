import java.util.*;
public class Solution {
    class Node{
        int start;
        int end;
        int cost;
        public Node(int start, int end, int cost){
            this.start = start;
            this.end = end;
            this.cost = cost;
        }
    }

    class Node2{
        int num;
        int pos;
        public Node2(int num, int pos){
            this.num = num;
            this.pos = pos;
        }
    }

    int[] parents;
    int[] size;
    public long solution(int[][] planets) {
        parents = new int[planets.length];
        size = new int[planets.length];
        for(int i = 0; i < planets.length;i ++){
            parents[i] = i;
            size[i] = 1;
        }
        List<Node2> lst1 = new ArrayList<>();
        List<Node2> lst2 = new ArrayList<>();
        List<Node2> lst3 = new ArrayList<>();

        for(int i = 0; i < planets.length; i++){
            lst1.add(new Node2(i,planets[i][0]));
            lst2.add(new Node2(i,planets[i][1]));
            lst3.add(new Node2(i,planets[i][2]));
        }

        Collections.sort(lst1, (a,b) -> Integer.compare(a.pos, b.pos));
        Collections.sort(lst2, (a,b) -> Integer.compare(a.pos, b.pos));
        Collections.sort(lst3, (a,b) -> Integer.compare(a.pos, b.pos));

        List<Node> loadList = new ArrayList<>();

        for(int i = 1; i < lst1.size(); i++){
            int diff1 = Math.abs(lst1.get(i).pos - lst1.get(i-1).pos);
            int diff2 = Math.abs(lst2.get(i).pos - lst2.get(i-1).pos);
            int diff3 = Math.abs(lst3.get(i).pos - lst3.get(i-1).pos);
            loadList.add(new Node(lst1.get(i).num, lst1.get(i-1).num, diff1));
            loadList.add(new Node(lst2.get(i).num, lst2.get(i-1).num, diff2));
            loadList.add(new Node(lst3.get(i).num, lst3.get(i-1).num, diff3));
        }

        Collections.sort(loadList, (a,b) -> Integer.compare(a.cost, b.cost));

        long answer = 0L;
        for(int i = 0; i < loadList.size(); i++){
            int start = loadList.get(i).start;
            int end = loadList.get(i).end;
            int cost = loadList.get(i).cost;

            if(find(start) != find(end)){
                union(start,end);
                answer += cost;
            }
        }

        return answer;
    }

    private int find(int curr){
        if(parents[curr] == curr) return curr;
        return parents[curr] = find(parents[curr]);
    }

    private void union(int curr1, int curr2){
        int root1 = find(curr1);
        int root2 = find(curr2);

        if(root1 == root2) return;

        if(size[root1] < size[root2]){
            int temp = root1;
            root1 = root2;
            root2 = temp;
        }

        parents[root2] = root1;
        size[root1] += size[root2];
    }
}
