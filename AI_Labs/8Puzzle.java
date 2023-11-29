import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

class PuzzleNode implements Comparable<PuzzleNode> {
    int[][] puzzle;
    int cost;
    int moves;
    PuzzleNode parent;

    public PuzzleNode(int[][] puzzle, int moves, PuzzleNode parent) {
        this.puzzle = puzzle;
        this.moves = moves;
        this.parent = parent;
        this.cost = calculateCost();
    }

    private int calculateCost() {
        // Manhattan distance heuristic
        int cost = 0;
        int n = puzzle.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = puzzle[i][j];
                if (value != 0) {
                    int targetX = (value - 1) / n;
                    int targetY = (value - 1) % n;
                    cost += Math.abs(i - targetX) + Math.abs(j - targetY);
                }
            }
        }

        return cost + moves;
    }

    @Override
    public int compareTo(PuzzleNode other) {
        return Integer.compare(this.cost, other.cost);
    }
}

public class Puzzle {
    private static final int[][] GOAL_STATE = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    public static void main(String[] args) {
        int[][] initial = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};

        Puzzle solver = new Puzzle();
        solver.solvePuzzle(initial);
    }

    public void solvePuzzle(int[][] initial) {
        PuzzleNode initialNode = new PuzzleNode(initial, 0, null);

        PriorityQueue<PuzzleNode> pq = new PriorityQueue<>();
        pq.add(initialNode);

        HashSet<String> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            PuzzleNode current = pq.poll();
            if (Arrays.deepEquals(current.puzzle, GOAL_STATE)) {
                printSolution(current);
                return;
            }

            visited.add(Arrays.deepToString(current.puzzle));

            int[] zeroPos = findZeroPosition(current.puzzle);

            // Generate possible moves
            int[][] moves = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

            for (int[] move : moves) {
                int newX = zeroPos[0] + move[0];
                int newY = zeroPos[1] + move[1];

                if (isValidMove(newX, newY)) {
                    int[][] newPuzzle = swap(current.puzzle, zeroPos[0], zeroPos[1], newX, newY);

                    if (!visited.contains(Arrays.deepToString(newPuzzle))) {
                        PuzzleNode newNode = new PuzzleNode(newPuzzle, current.moves + 1, current);
                        pq.add(newNode);
                    }
                }
            }
        }

        System.out.println("No solution found.");
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < 3 && y >= 0 && y < 3;
    }

    private int[] findZeroPosition(int[][] puzzle) {
        int[] position = new int[2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (puzzle[i][j] == 0) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }

    private int[][] swap(int[][] puzzle, int x1, int y1, int x2, int y2) {
        int[][] newPuzzle = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(puzzle[i], 0, newPuzzle[i], 0, 3);
        }

        int temp = newPuzzle[x1][y1];
        newPuzzle[x1][y1] = newPuzzle[x2][y2];
        newPuzzle[x2][y2] = temp;

        return newPuzzle;
    }

    private void printSolution(PuzzleNode node) {
        System.out.println("Solution found in " + node.moves + " moves:");
        while (node != null) {
            printPuzzle(node.puzzle);
            System.out.println();
            node = node.parent;
        }
    }

    private void printPuzzle(int[][] puzzle) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println();
        }
    }
}
