import java.util.*;

class Node implements Comparable<Node> {
    int x, y;
    int cost;
    Node parent;

    public Node(int x, int y, int cost, Node parent) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.parent = parent;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.cost, other.cost);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            Node n = (Node) o;
            return this.x == n.x && this.y == n.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
