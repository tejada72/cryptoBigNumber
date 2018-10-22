package bigNumber;


import java.util.*;

/**
 * BigNumber provides basic arithmetic operations for large signed integers
 * outside of the maximum values for primitive ints.
 * BigNumber also provides a function that will find the two prime factors of a large number
 *
 * @author Tyler Robinson & Alex Tejada
 * @version 1.0
 */
public class BigNumber {

    private List<Integer> listOfDigits = new ArrayList<>();
    private int signum;

    //The Constant BigNumber zero.
    private static final BigNumber ZERO = new BigNumber("00", 0);


    /**
     * Public Constructor
     *
     * Takes a string consisting of digits with an optional leading "-"
     * and constructs a BigNumber.
     * This constructor assigns as signum value of -1 -> 1 based on the presence
     * of "-" and non-zero characters
     *
     * @param numbers (Required) String representation of the BigNumber
     * @throws IllegalArgumentException
     */
    public BigNumber(String numbers) {
        int index;

        numbers = numbers.trim();

        //Checks that the entered string matches the accepted format
        if(!numbers.matches("-?\\d+")){
            throw new IllegalArgumentException();
        }

        //Checks if the entered string is only consisting of "-" and 0's or just
        //0's
        if(numbers.matches("-[^1-9]+")){
            signum = 0;
            index = 1;
        }

        //Sets the signum depending on the presence of "-"
        else {
            //Index set to 1 to compensate for "-"
            if (numbers.startsWith("-")) {
                signum = -1;
                index = 1;
            } else {
                signum = 1;
                index = 0;
            }
        }
        String[] digits = numbers.split("");

        //Converts the input string into an ArrayList of Integers
        //where each element is a single digit
        while (index < digits.length){
            listOfDigits.add(Integer.parseInt(digits[index]));
            index++;
        }

        //If the signum was deteremined to be negative, store the
        //10's complement negated form of this number and add
        //a leading 9 to signify that the number is negative
        if (signum == -1){
            listOfDigits = this.negate().getListOfDigits();
            listOfDigits.add(0, 9);
        }

        //If the signum was determined to be positive, append a leading zero
        //to signify the number is positive
        else if (signum == 1){
            listOfDigits.add(0,0);
        }

        //Strip all unnecessary leading 0 from the ArrayList
        normalize();
    }

    /**
     * Private constructor used by methods within the class to generate
     * new BigNumbers.
     * This constructor assumes all leading sign digits have been removed
     * and will not append additional sign digits
     * @param numbers The string representation of the BigNumber's value
     * @param signum The sign determined for the BigNumber by the calling method
     * @author Tyler Robinson
     */
    private BigNumber(String numbers, int signum){
        int index = 0;
        this.signum = signum;

        String[] digits = numbers.split("");

        while (index < digits.length){
            listOfDigits.add(Integer.parseInt(digits[index]));
            index++;
        }

    }

    /**
     * Produces the 10's complement negated form of a BigNumber
     * @return The a BigNumber that is equal to {@code -ThisBigNumber}
     * @author Tyler Robinson
     */
    private BigNumber negate() {

        if (this.signum == 0){
            return ZERO;
        }

        //Convert the list of digits for this BigNumber back into a String
        StringBuilder sr = new StringBuilder();

        for (Integer i : this.listOfDigits) {
            sr.append(i);
        }

        String negated = sr.toString();

        //Index of the least significant digit
        int index = negated.length() - 1;

        String[] digits = negated.split("");

        //Maintain all zero's, starting at the least significant digit and
        //moving up
        while (Integer.parseInt(digits[index]) == 0){
            index --;
        }

        //Subtract the first non-zero digit from 10 to simiulate adding 1
        //at the end of the complement process
        digits[index] = Integer.toString(10 - Integer.parseInt(digits[index]));
        index--;

        //Subtract all remaining digits from 9 to complete the complement
        while (index >= 0){
            digits[index] = Integer.toString(9 - Integer.parseInt(digits[index]));
            index--;
        }

        sr = new StringBuilder();

        for (String i : digits){
            sr.append(i);
        }

        //Flip the sign of the number to its negated equivalent
        if (this.signum == -1){
            return new BigNumber(sr.toString(), 1);
        }
        else if (this.signum == 1) {
            return new BigNumber(sr.toString(), -1);
        }
        else return ZERO;
    }

    /**
     * Gain access to the listOfDigits that represent this BigNumber
     * @return listOfDigits the list of digits representing this BigNumber
     */
    private List<Integer> getListOfDigits(){
        return this.listOfDigits;
    }

    /**
     * Strip all unneeded leading 0's or 9's from the front of the BigNumber
     * (Note: In the case of negative numbers, leading 0's are removed from the
     * positive equivalent of the number and then re-negated)
     *
     * @author Alex Tejada and Tyler Robinson
     */
    private void normalize(){
        int index = 0;

        //If the number is negative, convert it to its positive equivalent
        //in order to convert leading 9's to 0's
        //(0's at the front of a number can always be disregarded.  9's cannot)
        if (this.signum == -1){
            this.listOfDigits = this.negate().getListOfDigits();
        }

        //Remove all 0's from the front of the number
        while (this.listOfDigits.get(index) == 0){
            this.listOfDigits.remove(index);
                //If the number was only zero's, reinstate two 0's and set
                //the signum = 0
                if (this.listOfDigits.size() == 0){
                    this.listOfDigits.add(0,0);
                    this.listOfDigits.add(0,0);
                    this.signum = 0;
                    break;
                }
        }

        //Return one leading zero to act as a sign bit
        this.listOfDigits.add(0,0);

        //If the number was originally negative, return it to its
        //negative representation
        if (this.signum == -1){
            this.listOfDigits = this.negate().getListOfDigits();
        }
    }

    /**
     * Get the sign of the number
     * @return signum The signum or sign of the number
     */
    public int sign(){
        return signum;
    }

    /**
     * Compares a BigNumber against this BigNumber
     * @param val The BigNumber to be compared against
     * @return int The result of the comparison.  If the numbers are equal, returns
     * 0, if this number is larger than the second number, returns 1.  Otherwise returns
     * -1
     * @author Alex Tejada
     */
    public int compareTo(BigNumber val){
        //Checks if the signs differ between the two numbers
        if (!(this.signum == val.signum)){
            //If the sign of this number is larger than val, this number is positive
            //and therefore larger than either 0 or the negative number
            if (this.signum > val.signum){
                return 1;
            }
            //If the sign of this number is less that nal, this number is negative
            //and therefore smaller than 0 or the positive number
            else return -1;
        }

        //Checks if the numbers are the same length
        else if (this.getListOfDigits().size() != val.getListOfDigits().size()){
            //If they are not, compare the lengths of the two numbers to find which is larger
            if (this.getListOfDigits().size() > val.getListOfDigits().size()){
                return 1;
            }
            else return -1;
        }

        //Check if the two numbers are equal to each other
        else if (this.equals(val)){
            return 0;
        }

        //Compare each digit in both numbers starting at
        // the MSD until one digit is larger than the
        //other
        else{
            for (int i = 1; i < getListOfDigits().size() - 1; i++){
                if (this.negate().getListOfDigits().get(i) > val.getListOfDigits().get(i)){
                    return 1;
                }
                if (this.negate().getListOfDigits().get(i) < val.getListOfDigits().get(i)){
                    return -1;
                }
            }
        }
        return -1;
    }

    /**
     * Compares two BigNumbers for equality
     * @param val The BigNumber to be compared against
     * @return Boolean The Result of the comparison.  True if the numbers are equal.
     * False if they are not.
     * @author Alex Tejada
     */
    public boolean equals(BigNumber val){
        //Check if the signs are equivalent
        if (this.signum != val.signum){
            return false;
        }

        //Check if the lengths of the two numbers are equivalent
        else if (this.getListOfDigits().size() != val.getListOfDigits().size()){
            return false;
        }

        //Check if the two ListOfDigits are equivalent
        else return this.getListOfDigits().equals(val.getListOfDigits());
    }

    /**
     * Adds two BigNumbers and returns the result of that addition
     * @param val The BigNumber to be summed with this BigNumber
     * @return BigNumber The BigNumber resulting from the addition
     * @author Tyler Robinson and Alex Tejada
     */
    public BigNumber add(BigNumber val){
        //Check if either operand is zero
        //If True, return the other operand
        if (this.equals(val.negate())){
            return ZERO;
        }

        if (val.equals(ZERO)){
            return this;
        }

        if (this.equals(ZERO)){
            return val;
        }

        List<Integer> x = this.getListOfDigits();
        List<Integer> y = val.getListOfDigits();

        int xSize = x.size();
        int ySize = y.size();

        //Compare the sizes of the two numbers
        //If the sizes are not equal, pad the smaller number
        //with its sign digit until the lengths are equal
        if (xSize != ySize){
            if (xSize > ySize){
                while (xSize > ySize){
                    y.add(0, (val.sign() > 0) ? 0 : 9);
                    ySize = y.size();
                }
            }
            else while(xSize < ySize){
                x.add(0, ((this.sign() > 0) ? 0 : 9));
                xSize = x.size();
            }
        }

        //Create an int array to hold the sum
        int[] sum = new int[x.size()];
        int remainder  = 0;

        //Add the corresponding digits of each number
        //If the sum of this addition is > 9, add the remainder to the next
        //addition
        for (int i = sum.length - 1; i > 0 ; i--){
            sum[i] = (x.get(i) + y.get(i) + remainder) % 10;
            remainder = (x.get(i) + y.get(i) + remainder) / 10;
        }

        StringBuilder sr = new StringBuilder();

        for (int i = 1; i < sum.length; i++){
            sr.append(sum[i]);
        }

        //Strip any sign digits added in padding
        this.normalize();
        val.normalize();

        String result = sr.toString();

        BigNumber value;

        //If the signs of the two numbers do not match, subtraction occurred
        //and carry must be checked
        if (this.sign() != val.sign()){
            //If remainder = 0 at the end of the addition, no carry occurred
            //and sign of the larger value is assigned to the result
            if (remainder == 0){
                value = new BigNumber(result, (this.compareTo(val) > 0) ? val.sign() : this.sign());
                value.normalize();
                return value;
            }
            else {
                value = new BigNumber(result, (this.compareTo(val) > 0) ? this.sign() : val.sign());
                value.normalize();
                return value;
            }
        }
        //Signs of the numbers match

        //Check if the numbers are positive
        //If yes, prepend the final remainder to the result, normalize and return
        else if (this.sign() == 1){
            result = remainder + result;
            value = new BigNumber(result, this.sign());
            value.normalize();
            return value;
        }
        //Both numbers are negative
        //Prepend the final remainder to the result, normalize and return the negated result
        else {
            result = remainder + result;
            value = new BigNumber(result, 1);
            value.normalize();
            return value.negate();
        }
    }

    /**
     * Subtracts a BigNumber from this BigNumber
     * @param val The BigNumber to subtract from this BigNumber
     * @return The result of the Subtraction
     * @author Tyler Robinson
     */
    public BigNumber subtract(BigNumber val){
        //Equivalent to x + (-y)
        return this.add(val.negate());
    }

    /**
     * Divide a BigNumber from this BigNumber
     *
     * @param denominator BigNumber to be use as the denominator
     * @return Pair containing the quotient and remainder
     */
    public Pair divide(BigNumber denominator) {
        BigNumber numerator = this;
        BigNumber quotient;
        BigNumber remaider;

        long count = 0L;

        while(numerator.compareTo(denominator) > 0) {
            numerator = numerator.subtract(denominator);
            count++;
        }

        quotient = new BigNumber("" + count);
        remaider = numerator;

        return new Pair(quotient,remaider);
    }

    /**
     * The String representation of the BigNumber
     * @return String The String representation of this BigNumber
     * @author Alex Tejada
     */
    public String toString(){
        List<Integer> digits;
        StringBuilder sr = new StringBuilder();

        //If the sign of the number is zero, the number is zero
        if (signum == 0){
            return "0";
        }

        //If the sign of the number is -1, the number is stored in 10's complement
        //form and must be negated to a positive form
        else if (signum == -1){
            digits = negate().getListOfDigits();
        }

        //The number is positive and can be used as is
        else {
            digits = listOfDigits;
        }

        //Convert the Integers in listOfDigits into a StringBuilder
        for (Integer i : digits){
            sr.append(Integer.toString(i));
        }

        //If the BigNumber is positive, remove the leading zero and repalce
        //it with nothing
        if (signum == 1){
            sr.replace(0,1, "");
        }

        //If the BigNumber is meant to be negative, remove the leading zero and
        //replace it with "-"
        else sr.replace(0,1, "-");

        return sr.toString();
    }
}
