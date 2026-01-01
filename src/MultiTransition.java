import java.util.List;

public class MultiTransition {
    public final State state;
    public final List<String> read;
    public final State next;
    public final List<String> write;
    public final List<Operation> move;

    public MultiTransition(State state, List<String> read, State next,
                           List<String> write, List<Operation> move) {
        this.state = state;
        this.read = read;
        this.next = next;
        this.write = write;
        this.move = move;
    }

    public boolean matches(State s, List<String> symbols) {
        if (!state.equals(s)) return false;
        if (read.size() != symbols.size()) return false;
        for (int i = 0; i < read.size(); i++) {
            if (!read.get(i).equals(symbols.get(i))) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s) -> (%s, %s, %s)",
                state, read, next, write, move);
    }
}