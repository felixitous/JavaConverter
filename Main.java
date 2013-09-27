package enigma;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/** Class that represents the human or
 *  operator of the machine, runs the code
 *  and reads the script.
 *  @author Felix Liu
 */

public final class Main {

    /** Identify the readable LINE so the Machine knows
     * what to operate on and returns true if the line is in fact
     * the instruction line. */

    private static boolean theRecipe(String line) {
        return line.charAt(0) == '*';
    }

    /** Prints out result or the converted MSG in groups
     * of five. */

    private static void printX5(String msg) {
        String result = "";
        int count = 0;
        for (int i = 0; i < msg.length(); i += 1) {
            if (count == 5) {
                result += " ";
                count = 0;
            }
            result += msg.charAt(i);
            count += 1;
        }
        System.out.println(result);
    }

    /** Returns a String with the input LINE with all the whitespace
     * taken out and all the cases converted to capital letters. */

    private static String clarity(String line) {
        String line2 = line.replaceAll("\\s+", "");
        if (line2.matches(".*\\W.*") || line2.matches(".*\\d.*")) {
            System.out.println("not all char are letters");
            System.exit(1);
        }
        return line2.toUpperCase();

    }

    /** The king of them all, the function main returns the converted output
     *  by either taking in the cypher or taking in the regular message and
     *  prints the other, the input being UNUSED. */

    public static void main(String[] unused) {
        Machine M;
        BufferedReader input =
            new BufferedReader(new InputStreamReader(System.in));

        M = null;

        try {
            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (line.isEmpty()) {
                    System.out.println();
                } else if (theRecipe(line)) {
                    M = new Machine(line);
                    M.replaceRotors();
                    M.setRotors();
                } else if (M == null) {
                    System.out.println("configline error");
                    System.exit(1);
                } else {
                    printX5(M.convert(clarity(line)));
                }
            }
        } catch (IOException excp) {
            System.err.printf("Input error: %s%n", excp.getMessage());
            System.exit(1);
        }
    }
}
