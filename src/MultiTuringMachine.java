import java.util.ArrayList;
import java.util.List;

public class MultiTuringMachine {
    private final List<MultiTransition> transitions;
    private final List<State> endStates;
    private State current;
    private final List<MultiTape> tapes;
    private int steps = 0;
    private boolean debug = false;

    public MultiTuringMachine(
            List<MultiTransition> transitions,
            State start,
            List<State> endStates,
            List<MultiTape> tapes
    ) {
        this.transitions = transitions;
        this.current = start;
        this.endStates = endStates;
        this.tapes = tapes;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void run() {
        while (!endStates.contains(current)) {
            List<String> symbols = new ArrayList<>();
            for (MultiTape tape : tapes) {
                symbols.add(tape.read());
            }

            MultiTransition t = find(current, symbols);

            if (t == null) {
                throw new RuntimeException(
                        "Chybí přechod: (" + current + ", " + symbols + ")"
                );
            }

            if (debug) {
                System.out.println("Krok " + steps + ": " + current + " " + symbols + " -> " + t.next);
                for (int i = 0; i < tapes.size(); i++) {
                    System.out.println("  T" + (i+1) + ": " + tapes.get(i));
                }
            }

            for (int i = 0; i < tapes.size(); i++) {
                tapes.get(i).write(t.write.get(i));
                tapes.get(i).move(t.move.get(i));
            }

            current = t.next;
            steps++;
        }
    }

    private MultiTransition find(State s, List<String> symbols) {
        for (MultiTransition t : transitions) {
            if (t.matches(s, symbols)) return t;
        }
        return null;
    }

    public int getSteps() {
        return steps;
    }

    public int getTotalSpaceUsage() {
        int total = 0;
        for (MultiTape tape : tapes) {
            total += tape.usedCells();
        }
        return total;
    }

    public void printTapes() {
        for (int i = 0; i < tapes.size(); i++) {
            System.out.println("T" + (i+1) + ": " + tapes.get(i));
        }
    }
}