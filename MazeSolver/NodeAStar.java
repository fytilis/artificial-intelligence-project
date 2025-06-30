import java.util.Objects;

public class NodeAStar implements Comparable<NodeAStar> {
    int x, y;
    int g; // kostos Start
    int h; // euretikh(ektimisi mexri Goal)
    int f; // f = g + h
    NodeAStar parent;

    public NodeAStar(int x, int y, int g, int h, NodeAStar parent) {
        this.x = x;
        this.y = y;
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.parent = parent;
    }

    @Override
    public int compareTo(NodeAStar other) {
        return Integer.compare(this.f, other.f);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof NodeAStar) {
            NodeAStar n = (NodeAStar) o;
            return this.x == n.x && this.y == n.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
