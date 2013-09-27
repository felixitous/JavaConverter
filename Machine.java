package enigma;

import java.util.HashSet;
/** Class that represents a complete enigma machine.
 *  @author Felix Liu
 */

class Machine {

    /** An array of Strings that contains the information on
     *  what rotors to use. */
    private static String[] instructions;

    /** The last element of instructions which holds the information
     *  on where each rotor should begin at. */
    private static String setting;

    /** Initializes the array that will hold the rotors in the correct
     *  position. */
    private static Rotor[] rotors = new Rotor[5];

    /** Returns a Machine with no components or null. */
    Machine() {
        this (null);
    }

    /** Return a Machine with the information CONFIGLINE or recipe to correctly
     *  decrypt or encrypt the messages. */
    Machine(String configline) {
        HashSet<String> dup = new HashSet<String>();
        instructions = ((configline.substring(1)).trim()).split("\\s+");
        if (instructions.length != rotors.length + 1) {
            System.out.println("not the correct amount of arguments");
            System.exit(1);
        }
        for (String str : instructions) {
            dup.add(str);
        }
        if (dup.size() != instructions.length) {
            System.out.println("repeated rotor");
            System.exit(1);
        }
    }

    /** Set my rotors to (from left to right) ROTORS.  Initially, the rotor
     *  settings are all 'A'. */
    void replaceRotors() {
        for (int i = 0; i < rotors.length; i += 1) {
            rotors[i] = new Rotor(instructions[i]);
        }
        for (Rotor rotor : rotors) {
            if (rotor.getParticular() == null) {
                System.out.println("misnamed rotor");
                System.exit(1);
            }
        }
        if (rotors[0].hasInverse()) {
            System.out.println("wrong rotor in reflector position");
            System.exit(1);
        }
        if (rotors[1].advances() || !rotors[1].hasInverse()) {
            System.out.println("wrong rotor in fixedrotor position");
            System.exit(1);
        }
        for (int k = 2; k < rotors.length; k += 1) {
            if (!rotors[k].advances()) {
                System.out.println("wrong rotor in advancing rotors position");
                System.exit(1);
            }
        }
    }

    /** Set my rotors according to SETTING, which must be a string of four
     *  upper-case letters. The first letter refers to the leftmost
     *  rotor setting.  */
    void setRotors() {
        setting = instructions[instructions.length - 1];
        if (setting.length() != rotors.length - 1
            || setting.matches(".*\\d.*")) {
            System.out.println("issue with initial position");
            System.exit(1);
        }
        for (int i = 1; i < setting.length() + 1; i += 1) {
            rotors[i].startpos(setting.charAt(i - 1));
        }
    }

    /** Assists convert in identifying when specific rotors
     *  should kick the next on or not kick the next one at all. */
    void setPosition() {
        if (rotors[3].atNotch()) {
            rotors[2].advance();
            rotors[3].advance();
        } else if (rotors[4].atNotch()) {
            rotors[3].advance();
        }
        rotors[4].advance();
    }

    /** Returns the encoding/decoding of MSG, result, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        Rotor func = new Rotor();
        String result = "";
        int x = 0;
        for (int i = 0; i < msg.length(); i += 1) {
            this.setPosition();
            x = func.toIndex(msg.charAt(i));
            for (int k = rotors.length - 1; k > -1; k -= 1) {
                x = rotors[k].convertForward(x);
            }
            for (int k = 1; k < rotors.length; k += 1) {
                x = rotors[k].convertBackward(x);
            }
            result += func.toLetter(x);
        }
        return result;
    }
}
