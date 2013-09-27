package enigma;

/** Class that represents a rotor in the enigma machine.
 *  @author Felix Liu
 */

class Rotor {

    /** Base fields needed for correct run */

    /** the variable TEST holds the information for the cypher
     *  or where each letter should be converted to. */
    private static String[][] test = PermutationData.ROTOR_SPECS;

    /** the variable ALPHABET creates a blueprint for the methods
     *  to look at when determining the indexing number. */
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /** the variable PARTICULAR holds the information on the specific
     *  that is currently being used. This isn't static because it needs
     *  the change when new rotors are created. */
    private String[] particular;

    /** the variable START holds the current letter's number or position
     *  so the methods can add on or alter it with ROTOR_SPECS to get the
     *  correct decoding information. */
    private int start;

    /** A Rotor with null input and start0. */
    Rotor() {
        this (null);
    }

    /** A list with type INPUT and position START0. */
    Rotor(String input) {
        int place = 0;
        while (place < test.length) {
            if (test[place][0].equals(input)) {
                particular = test[place];
            }
            place += 1;
        }
    }

    /** A getter method to obtain PARTICULAR for machine
     *  and returns particular. */
    String[] getParticular() {
        return particular;
    }

    /** the function startpos takes in START0 to change the char into
     *  an index number, easily accessed by in int methods, convertForward and
     *  convertBackwards. */
    void startpos(char start0) {
        start = toIndex(start0);
    }

    /** Size of ALPHABET used for plaintext and ciphertext. */
    static final int ALPHABET_SIZE = 26;

    /** Assuming that P is an integer in the range 0..25, returns the
     *  corresponding upper-case letter in the range A..Z. */
    static char toLetter(int p) {
        return ALPHABET.charAt(p);
    }

    /** Assuming that C is an upper-case letter in the range A-Z, return the
     *  corresponding index in the range 0..25. Inverse of toLetter. */
    static int toIndex(char c) {
        return ALPHABET.indexOf(c);
    }

    /** Returns true iff this rotor has a ratchet and can advance. */
    boolean advances() {
        return particular.length == 4;
    }

    /** Returns true iff this rotor has a left-to-right inverse. */
    boolean hasInverse() {
        return particular.length > 2;
    }


    /** Return the conversion of P (an integer in the range 0..25)
     *  according to my permutation. */
    int convertForward(int p) {
        int combo = (p + start) % ALPHABET_SIZE;
        String perm = particular[1];
        int letternumber, finalnumber;
        finalnumber = (toIndex(perm.charAt(combo)) - start);
        if (finalnumber < 0) {
            finalnumber += ALPHABET_SIZE;
        }
        return finalnumber % ALPHABET_SIZE;
    }

    /** Return the conversion of E (an integer in the range 0..25)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        int combo = (e + start) % ALPHABET_SIZE;
        String perm = particular[2];
        int letternumber, finalnumber;
        finalnumber = (toIndex(perm.charAt(combo)) - start);
        if (finalnumber < 0) {
            finalnumber += ALPHABET_SIZE;
        }
        return finalnumber % ALPHABET_SIZE;
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        if (!this.advances()) {
            return false;
        }
        if (start == toIndex(particular[3].charAt(0))) {
            return true;
        } else if (particular[3].length() > 1
                && start == toIndex(particular[3].charAt(1))) {
            return true;
        }
        return false;
    }

    /** Advance me one position. */
    void advance() {
        start = (start + 1) % ALPHABET_SIZE;
    }
}
