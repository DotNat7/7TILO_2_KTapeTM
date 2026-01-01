import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        State q0 = new State("q0");
        State q1 = new State("q1");
        State q2 = new State("q2");
        State q3 = new State("q3");

        State q_odc1 = new State("q_odc1");
        State q_odc2 = new State("q_odc2");
        State q_odc3 = new State("q_odc3");
        State q_odc4 = new State("q_odc4");

        State q_pujc1 = new State("q_pujc1");
        State q_pujc2 = new State("q_pujc2");

        State q_klid = new State("q_klid");
        State q_acc = new State("q_acc");

        List<Transition> T = List.of(

                // --- FÁZE 1: navigace ---
                new Transition(q0, "1", q1, "1", Operation.R),
                new Transition(q0, "0", q1, "0", Operation.R),
                new Transition(q0, "#", q0, "#", Operation.R),

                new Transition(q1, "0", q1, "0", Operation.R),
                new Transition(q1, "1", q1, "1", Operation.R),
                new Transition(q1, "#", q1, "#", Operation.R),
                new Transition(q1, "∸", q2, "∸", Operation.R),

                new Transition(q2, "0", q2, "0", Operation.R),
                new Transition(q2, "1", q2, "1", Operation.R),
                new Transition(q2, "#", q3, "#", Operation.L),

                // --- FÁZE 2: odčítání ---
                new Transition(q3, "0", q_odc1, "0", Operation.S),
                new Transition(q3, "1", q_odc1, "1", Operation.S),


                // čtení y
                new Transition(q_odc1, "0", q_odc2, "X", Operation.L),
                new Transition(q_odc1, "1", q_odc2, "X", Operation.L),

                // návrat k x
                new Transition(q_odc2, "0", q_odc2, "0", Operation.L),
                new Transition(q_odc2, "1", q_odc2, "1", Operation.L),
                new Transition(q_odc2, "∸", q_odc3, "∸", Operation.L),

                // výpočet bez půjčky
                new Transition(q_odc3, "1", q_odc4, "X", Operation.L),
                new Transition(q_odc3, "0", q_pujc1, "0", Operation.S),

                // --- PŮJČKA ---
                new Transition(q_pujc1, "0", q_pujc1, "0", Operation.L),
                new Transition(q_pujc1, "1", q_pujc2, "0", Operation.R),

                new Transition(q_pujc2, "0", q_odc4, "1", Operation.L),

                // návrat
                new Transition(q_odc4, "0", q_odc4, "0", Operation.L),
                new Transition(q_odc4, "1", q_odc4, "1", Operation.L),
                new Transition(q_odc4, "#", q_klid, "#", Operation.R),

                // --- ÚKLID ---
                new Transition(q_klid, "X", q_klid, "#", Operation.R),
                new Transition(q_klid, "∸", q_klid, "#", Operation.R),
                new Transition(q_klid, "0", q_klid, "0", Operation.R),
                new Transition(q_klid, "1", q_klid, "1", Operation.R),
                new Transition(q_klid, "#", q_acc, "#", Operation.S)
        );

        Tape tape = new Tape(
                Arrays.asList("#", "#", "1", "0", "0", "∸", "1", "0", "#", "#")
        );

        TuringMachine tm = new TuringMachine(
                T,
                q0,
                List.of(q_acc),
                tape
        );

        tm.run();

        System.out.println("Časová složitost: " + tm.getSteps() + " kroků");
        System.out.println("Prostorová složitost: " + tm.getSpaceUsage() + " buněk");
    }
}