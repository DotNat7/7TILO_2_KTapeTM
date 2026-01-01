import java.util.List;

public class TuringMachine {
    private final List<Transition> transitions;
    private final List<State> endStates;
    private State current;
    private final Tape tape;
    private int steps = 0;

    public TuringMachine(
            List<Transition> transitions,
            State start,
            List<State> endStates,
            Tape tape
    ) {
        this.transitions = transitions;
        this.current = start;
        this.endStates = endStates;
        this.tape = tape;
    }

    public void run() {
        while (!endStates.contains(current)) {
            String symbol = tape.read();
            Transition t = find(current, symbol);

            if (t == null) {
                throw new RuntimeException(
                        "Chybí přechod: (" + current + ", " + symbol + ")"
                );
            }

            tape.write(t.write);
            tape.move(t.move);
            current = t.next;
            steps++;
        }
    }

    private Transition find(State s, String sym) {
        for (Transition t : transitions) {
            if (t.matches(s, sym)) return t;
        }
        return null;
    }

    public int getSteps() {
        return steps;
    }

    public int getSpaceUsage() {
        return tape.usedCells();
    }
}