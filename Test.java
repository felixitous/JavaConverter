package enigma;

import java.util.Arrays;

/** Tests all the project files separately by getting imports
* and seeing what they actually print
* @author Felix Liu
*/
class Test {

	public static void main(String[] args) {

		/** Main.java Tests. */

		/** prints True. */
		System.out.println(Main.theRecipe("* troubleshooting"));

		/** prints False. */
		System.out.println(Main.theRecipe("troubleshooting"));

		/** prints False. */
		System.out.println(Main.theRecipe("trouble * shooting"));
        
        /** prints knowr egret s. */
        Main.printX5("knowregrets");

        /** prints DIVIDECONQUER. */
        System.out.println(Main.clarity("di vi de co nq ue r"));

        /** Rotor.java Tests. */

        /** prints null */
        Rotor rotor = new Rotor("asdf");
        System.out.println(Arrays.toString(rotor.particular));

        
	}
}