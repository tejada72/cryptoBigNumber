package bigNumber;

/**  Test the BigNumber class.
 *   Cryptography Project 1
 *   @author (sdb)
 *   @version (Sep 2012)
 */
import java.util.Scanner;

public class Driver
{
    public static void main (String [] args)
    {

        System.out.println("Testing cases and factorization: \n*\n*\n*\n");

        System.out.println("    **** SUBMITION OF THE REQUIREMENTS ****");
        long startTime = System.currentTimeMillis();
        //System.out.println("Prime factors for the number 117852727: " + new BigNumber("117852727").factor());
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Total time: " + totalTime);


        startTime = System.currentTimeMillis();
        //System.out.println("Prime factors for the number 2168211218041261: " + new BigNumber("2168211218041261").factor());
        totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Total time: " + totalTime);

        System.out.println("    **** TEST CASES ****");
        //System.out.println("Prime factors for the number 1581: " + new BigNumber("1581").factor());
        //System.out.println("Prime factors for the number 681199: " + new BigNumber("681199").factor());
        //System.out.println("Prime factors for the number 37403: " + new BigNumber("37403").factor());
        //System.out.println("Prime factors for the number 223357: " + new BigNumber("223357").factor());




        System.out.println("\n*\n*\n*\n");
        System.out.println("User testing phase: \n*\n*\n*\n*\n*\n*\n");

        Scanner scanner = new Scanner (System.in);
        BigNumber x,y;

        System.out.println ("Enter two BigNumbers, on separate lines, or hit Enter to terminate");
        String line = scanner.nextLine();

        while (line.length() > 0)
        {	x = new BigNumber (line);
            System.out.println ("Enter a second BigNumber");
            line = scanner.nextLine();
            y = new BigNumber (line);

            System.out.println("X: " + x);
            System.out.println("Y: " + y);
            System.out.println("Sum: X + Y = " + x.add(y));
            System.out.println("Sum: Y + X = " + y.add(x));
            System.out.println("First - Second: X - Y = " + x.subtract(y));
            System.out.println("Second - First: Y - X = " + y.subtract(x));
            System.out.println("Product: " + x.multiply(y));
            System.out.println("Product: " + y.multiply(x));
            System.out.println("First / Second: X / Y = " + x.divide(y).getQuotient());
            System.out.println("Second / First: Y / X = " + y.divide(x).getQuotient());
            System.out.println("First % Second: X % Y = " + x.divide(y).getMod());
            System.out.println("Second % First: Y % X = " + y.divide(x).getMod());
            System.out.println("10 % 1 == 0: " + (new BigNumber("10").divide(new BigNumber("1")).getMod()).equals(new BigNumber("0")));

            line = scanner.nextLine();
        }
    }
}
