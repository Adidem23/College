import java.util.*;

class State {
    int missionariesLeft;
    int cannibalsLeft;
    int missionariesRight;
    int cannibalsRight;
    boolean boatOnLeft;

    public State(int missionariesLeft, int cannibalsLeft, int missionariesRight, int cannibalsRight, boolean boatOnLeft) {
        this.missionariesLeft = missionariesLeft;
        this.cannibalsLeft = cannibalsLeft;
        this.missionariesRight = missionariesRight;
        this.cannibalsRight = cannibalsRight;
        this.boatOnLeft = boatOnLeft;
    }

    public boolean isValid() {
        if (missionariesLeft < 0 || missionariesRight < 0 || cannibalsLeft < 0 || cannibalsRight < 0) {
            return false;
        }
        if ((missionariesLeft != 0 && missionariesLeft < cannibalsLeft) ||
                (missionariesRight != 0 && missionariesRight < cannibalsRight)) {
            return false;
        }
        return true;
    }

    public boolean isGoal() {
        return missionariesLeft == 0 && cannibalsLeft == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State s = (State) obj;
            return s.missionariesLeft == missionariesLeft && s.cannibalsLeft == cannibalsLeft &&
                    s.missionariesRight == missionariesRight && s.cannibalsRight == cannibalsRight &&
                    s.boatOnLeft == boatOnLeft;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(missionariesLeft, cannibalsLeft, missionariesRight, cannibalsRight, boatOnLeft);
    }
}

public class MC {

    public static List<State> solve() {
        List<State> solution = new ArrayList<>();
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        State initialState = new State(3, 3, 0, 0, true);
        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            solution.add(currentState);

            if (currentState.isGoal()) {
                return solution;
            }

            List<State> nextStates = generateNextStates(currentState);
            for (State nextState : nextStates) {
                if (!visited.contains(nextState) && nextState.isValid()) {
                    queue.add(nextState);
                    visited.add(nextState);
                }
            }
        }

        return null;
    }

    private static List<State> generateNextStates(State currentState) {
        List<State> nextStates = new ArrayList<>();

        int[] missionaries = {1, 2, 0};
        int[] cannibals = {1, 0, 2};

        for (int i = 0; i < missionaries.length; i++) {
            for (int j = 0; j < cannibals.length; j++) {
                int moveMissionaries = missionaries[i];
                int moveCannibals = cannibals[j];

                if (currentState.boatOnLeft) {
                    nextStates.add(new State(
                            currentState.missionariesLeft - moveMissionaries,
                            currentState.cannibalsLeft - moveCannibals,
                            currentState.missionariesRight + moveMissionaries,
                            currentState.cannibalsRight + moveCannibals,
                            !currentState.boatOnLeft
                    ));
                } else {
                    nextStates.add(new State(
                            currentState.missionariesLeft + moveMissionaries,
                            currentState.cannibalsLeft + moveCannibals,
                            currentState.missionariesRight - moveMissionaries,
                            currentState.cannibalsRight - moveCannibals,
                            !currentState.boatOnLeft
                    ));
                }
            }
        }

        return nextStates;
    }

    public static void printSolution(List<State> solution) {
        if (solution == null) {
            System.out.println("No solution found.");
        } else {
            for (int i = 0; i < solution.size(); i++) {
                State state = solution.get(i);
                System.out.println("Step " + i + ": " + state.missionariesLeft + "M " + state.cannibalsLeft + "C " +
                        (state.boatOnLeft ? "B" : " ") +
                        " || " +
                        state.missionariesRight + "M " + state.cannibalsRight + "C " +
                        (!state.boatOnLeft ? "B" : " "));
            }
        }
    }

    public static void main(String[] args) {
        List<State> solution = solve();
        printSolution(solution);
    }
}
