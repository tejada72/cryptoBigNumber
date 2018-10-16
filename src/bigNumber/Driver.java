package bigNumber;

/**  Test the BigNumber class.
 *   Cryptography Project 1
 *   @author (sdb)
 *   @version (Sep 2012)
 */
import java.util.Random;
import java.util.Scanner;

public class Driver
{
    public static void main (String [] args)
    {
        //runTest();
        Scanner scanner = new Scanner (System.in);
        BigNumber x,y;

        System.out.println ("Enter two BigNumbers, on separate lines, or hit Enter to terminate");
        String line = scanner.nextLine();

        while (line.length() > 0)
        {	x = new BigNumber (line);
            System.out.println ("Enter a second BigNumber");
            line = scanner.nextLine();
            y = new BigNumber (line);


            System.out.println(x.toString());
            System.out.println(y.toString());
            System.out.println("First negated = " +x.negate().toString());
            System.out.println("Second negated = " +y.negate().toString());

            System.out.println("Sum: " + x.add(y));
            System.out.println("Sum: " + y.add(x));
            System.out.println("First - Second: " + x.subtract(y));
            System.out.println("Second - First: " + y.subtract(x));
            //System.out.println("Product: " + x.multiply(y));
            //System.out.println("Product: " + y.multiply(x));
            //System.out.println("First / Second: " + x.divide(y).getQuotient());
            //System.out.println("Second / First: " + y.divide(x).getQuotient());
            //System.out.println("First % Second: " + x.divide(y).getMod());
            //System.out.println("Second % First: " + y.divide(x).getMod());

            line = scanner.nextLine();
        }
    }

    /*
     * Make 2 bigNumber and keep adding 13 to the original one. Good test case? I doubt it.
     */
    private static void runTest() {
        BigNumber thirteen = new BigNumber("13");
        BigNumber result = new BigNumber("0");

        result = result.add(thirteen);

        System.out.println("result: " + result);

        int index = 0;

        while(index < 59) {

            result = result.add(thirteen);
            System.out.println("Index: " + index + " : " + result);

            index++;
        }
    }
}
