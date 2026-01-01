public class Transition {
    public final State state;
    public final String read;
    public final State next;
    public final String write;
    public final Operation move;

    public Transition(State state, String read, State next, String write, Operation move) {
        this.state = state;
        this.read = read;
        this.next = next;
        this.write = write;
        this.move = move;
    }

    public boolean matches(State s, String symbol) {
        return state.equals(s) && read.equals(symbol);
    }
}