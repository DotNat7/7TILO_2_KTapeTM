import java.util.ArrayList;
import java.util.List;

public class MultiTape {
    private final List<String> tape;
    private int head;
    private final String BLANK = "#";

    public MultiTape(List<String> initial) {
        tape = new ArrayList<>(initial);
        head = 0;
    }

    public String read() {
        ensure();
        return tape.get(head);
    }

    public void write(String s) {
        ensure();
        tape.set(head, s);
    }

    public void move(Operation op) {
        if (op == Operation.L) head--;
        if (op == Operation.R) head++;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int pos) {
        this.head = pos;
    }

    private void ensure() {
        if (head < 0) {
            tape.add(0, BLANK);
            head = 0;
        }
        if (head >= tape.size()) {
            tape.add(BLANK);
        }
    }

    public int usedCells() {
        return tape.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tape.size(); i++) {
            if (i == head) sb.append("[");
            sb.append(tape.get(i));
            if (i == head) sb.append("]");
            sb.append(" ");
        }
        return sb.toString();
    }
}