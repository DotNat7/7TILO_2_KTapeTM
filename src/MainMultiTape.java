import java.util.*;

public class MainMultiTape {
    public static void main(String[] args) {

        String input = "# # # a a b c c a a b c d d a c b a d # # #";
        String[] symbols = input.split(" ");

        // FÁZE 1: Kopírování se smazáním {a,c} na lichých pozicích
        List<String> tape2 = new ArrayList<>();

        int symbolPosition = 0;

        for (int i = 0; i < symbols.length; i++) {
            String sym = symbols[i];

            if (!sym.equals("#")) {
                symbolPosition++;

                boolean isOdd = (symbolPosition % 2 == 1);
                boolean shouldDelete = isOdd && (sym.equals("a") || sym.equals("c"));

                if (!shouldDelete) {
                    tape2.add(sym);
                }
            }
        }

        // FÁZE 2: Třídění sestupně
        Collections.sort(tape2, Collections.reverseOrder());

        // FÁZE 3: Zápis výsledku na T1
        List<String> result = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            result.add("#");
        }

        result.addAll(tape2);
        result.add("#");

        int length = tape2.size();
        String binary = Integer.toBinaryString(length);

        for (char c : binary.toCharArray()) {
            result.add(String.valueOf(c));
        }

        for (int i = 0; i < 5; i++) {
            result.add("#");
        }

        // VÝPOČET SLOŽITOSTI
        int n = 19;
        int m = tape2.size();

        int phase1 = 19 + m + 19;
        int phase2 = m * m * 2;
        int phase3 = 19 + m + (int)Math.ceil(Math.log(m)/Math.log(2));
        int totalSteps = phase1 + phase2 + phase3;

        int t1Space = result.size();
        int t2Space = tape2.size() + 10;
        int totalSpace = t1Space + t2Space;

        // VÝSTUP
        System.out.println();
        System.out.println("Počáteční stav pásek (k=2):");
        System.out.println("                                  ↓");
        System.out.println("T1: " + input);
        System.out.println("                                        ↓");
        System.out.println("T2: # # # # # # # # # # # # # # # # # # # # # # #");
        System.out.println();

        System.out.println("Konečný stav pásek (k=2):");
        System.out.println("                                        ↓");
        System.out.println("T1: " + String.join(" ", result));
        System.out.println("                                      ↓");
        System.out.println("T2: # # # # # # # # # # # # # # # # # # # # # # #");
        System.out.println();

        System.out.println("Časová složitost: " + totalSteps + " kroků");
        System.out.println("Prostorová složitost: " + totalSpace + " buněk");
    }
}