/** Class that prints the Collatz sequence starting from a given number.
 *  @Weiyuan_XU
 */

/** Description for this file:
 * 1. If n is even, the next number is n/2;
 * 2. If n is odd, the next number is 3n+1;
 * 3. If n is 1, the sequence is over.
 */
public class Collatz {

    /** Returns the nextNumber in a Collatz sequence. */
    public static int nextNumber(int n) {
        if (n % 2 == 0) {
            return (n / 2);
        }
        else if ((n % 2 == 1) & (n != 1)) {
            return (3*n + 1);
        }
        else{
            return 1;
        }
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + " ");

        // Some starter code to test
        while (n != 1) {          
            n = nextNumber(n);          
            System.out.print(n + " ");
        }
        System.out.println();

    }
}

