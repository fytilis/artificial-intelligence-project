import java.util.*;
import java.util.Locale;

public class MazeSolver {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        sc.useLocale(Locale.US);

        System.out.print("Give me size Ν: ");
        int N = sc.nextInt();

        System.out.print("Give me posibility p (0,0 - 1,0): ");
        double p = sc.nextDouble();

        Cell[][] grid = new Cell[N][N];
        Random rand = new Random();

        // dimiourgia lavirinthou
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = new Cell();
            }
        }
        
        // Orismos eidikwn keliwn A kai B
        grid[N - 1][0].isBlocked = false;
        grid[N - 1][0].isTeleportA = true;

        grid[0][N - 1].isBlocked = false;
        grid[0][N - 1].isTeleportB = true;

        // Eisagwgi Start kai Goal
        System.out.print("Give x of starting cell S: ");
        int sx = sc.nextInt();
        System.out.print("Give y of starting cell S: ");
        int sy = sc.nextInt();

        System.out.print("Give x of last cell G: ");
        int gx = sc.nextInt();
        System.out.print("Give y of last cell G: ");
        int gy = sc.nextInt();

        if (sx == gx && sy == gy) {
            System.out.println(" Start and Goal cant be in the same cell! Run again.");
            return;
        }

        grid[sx][sy].isStart = true;
        grid[gx][gy].isGoal = true;

        for (int i = 0; i < N; i++) {
    for (int j = 0; j < N; j++) {
        if (!grid[i][j].isStart && !grid[i][j].isGoal) {
            grid[i][j].isBlocked = rand.nextDouble() < p;
        }
    }
}

        // ektypwsi
        System.out.println("\nMaze:");
        printMaze(grid);

        // treximo UCS
        int[] ucsExpansions = {0}; 
        List<Node> pathUCS = solveUCS(grid, sx, sy, gx, gy, ucsExpansions);

        if (pathUCS == null) {
    System.out.println("We  didn't find  UCS route!");
} else {
    System.out.println("We find UCS route with cost: " + (pathUCS.size() - 1));
    System.out.println("Expanded " + ucsExpansions[0] + " nodes with UCS.");
    printMazeWithPath(grid, pathUCS);
}


        // Bima 7: Ektelesi A*
    int[] aStarExpansions = {0};
        List<NodeAStar> pathAStar = solveAStar(grid, sx, sy, gx, gy, aStarExpansions);
    if (pathAStar == null) {
    System.out.println("We didn't found A* route!");
} else {
    System.out.println("We found A* route with  cost: " + (pathAStar.size() - 1));
    System.out.println("Expanded " + aStarExpansions[0] + " nodes with A*.");
    printMazeWithPathAStar(grid, pathAStar);
}

    }

    public static void printMaze(Cell[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printMazeWithPath(Cell[][] grid, List<Node> path) {
        Set<String> pathCells = new HashSet<>();
        for (Node n : path) {
            pathCells.add(n.x + "," + n.y);
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (pathCells.contains(i + "," + j) && !grid[i][j].isStart && !grid[i][j].isGoal) {
                    System.out.print("* ");
                } else {
                    System.out.print(grid[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public static List<Node> solveUCS(Cell[][] grid, int sx, int sy, int gx, int gy,int[] expansions) {
        int N = grid.length;
        PriorityQueue<Node> frontier = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        frontier.add(new Node(sx, sy, 0, null));

        int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            { 0, -1},          { 0, 1},
            { 1, -1}, { 1, 0}, { 1, 1}
        };

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
            expansions[0]++;
            String key = current.x + "," + current.y;

            if (visited.contains(key)) continue;
            visited.add(key);

            if (current.x == gx && current.y == gy) {
                return buildPath(current);
            }

            // Tilemetafora A -> B h B -> A
            if ((current.x == N - 1 && current.y == 0) && !grid[0][N - 1].isBlocked)
                frontier.add(new Node(0, N - 1, current.cost + 2, current));
            if ((current.x == 0 && current.y == N - 1) && !grid[N - 1][0].isBlocked)
                frontier.add(new Node(N - 1, 0, current.cost + 2, current));

            for (int[] d : directions) {
                int nx = current.x + d[0];
                int ny = current.y + d[1];
                if (nx >= 0 && ny >= 0 && nx < N && ny < N && !grid[nx][ny].isBlocked) {
                    String neighborKey = nx + "," + ny;
                    if (!visited.contains(neighborKey)) {
                        frontier.add(new Node(nx, ny, current.cost + 1, current));
                    }
                }
            }
        }

        return null; // Den vrethike diadromi
    }

    private static List<Node> buildPath(Node goal) {
        List<Node> path = new ArrayList<>();
        Node current = goal;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }


    public static List<NodeAStar> solveAStar(Cell[][] grid, int sx, int sy, int gx, int gy, int[] expansions) {
    int N = grid.length;
    PriorityQueue<NodeAStar> frontier = new PriorityQueue<>();
    Set<String> visited = new HashSet<>();

    NodeAStar startNode = new NodeAStar(sx, sy, 0, heuristic(sx, sy, gx, gy), null);
    frontier.add(startNode);

    int[][] directions = {
        {-1, -1}, {-1, 0}, {-1, 1},
        { 0, -1},          { 0, 1},
        { 1, -1}, { 1, 0}, { 1, 1}
    };

    while (!frontier.isEmpty()) {
        NodeAStar current = frontier.poll();
        expansions[0]++;
        String key = current.x + "," + current.y;

        if (visited.contains(key)) continue;
        visited.add(key);

        if (current.x == gx && current.y == gy) {
            return buildPathAStar(current);
        }

          // Tilemetafora A -> B h B -> A
        if ((current.x == N - 1 && current.y == 0) && !grid[0][N - 1].isBlocked)
            frontier.add(new NodeAStar(0, N - 1, current.g + 2, heuristic(0, N-1, gx, gy), current));
        if ((current.x == 0 && current.y == N - 1) && !grid[N - 1][0].isBlocked)
            frontier.add(new NodeAStar(N - 1, 0, current.g + 2, heuristic(N-1, 0, gx, gy), current));

        for (int[] d : directions) {
            int nx = current.x + d[0];
            int ny = current.y + d[1];
            if (nx >= 0 && ny >= 0 && nx < N && ny < N && !grid[nx][ny].isBlocked) {
                String neighborKey = nx + "," + ny;
                if (!visited.contains(neighborKey)) {
                    frontier.add(new NodeAStar(nx, ny, current.g + 1, heuristic(nx, ny, gx, gy), current));
                }
            }
        }
    }

    return null; // den vrethike diadromi
}
    public static int heuristic(int x, int y, int gx, int gy) {
    return Math.max(Math.abs(gx - x), Math.abs(gy - y));
}

    private static List<NodeAStar> buildPathAStar(NodeAStar goal) {
        List<NodeAStar> path = new ArrayList<>();
        NodeAStar current = goal;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
    public static void printMazeWithPathAStar(Cell[][] grid, List<NodeAStar> path) {
    Set<String> pathCells = new HashSet<>();
    for (NodeAStar n : path) {
        pathCells.add(n.x + "," + n.y);
    }

    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid.length; j++) {
            if (pathCells.contains(i + "," + j) && !grid[i][j].isStart && !grid[i][j].isGoal) {
                System.out.print("* ");
            } else {
                System.out.print(grid[i][j] + " ");
            }
        }
        System.out.println();
    }
}


}

