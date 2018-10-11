package bigNumber;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class BigNumber {

    private List<Integer>  listOfDigits = new LinkedList<>();
    private List<Integer> negated;
    private int sign;

    /*
     * Construct a big number from the given String. The string must only contains digits or be preceded by '-'.
     */
    public BigNumber(String numbers) {
        int index;
        numbers = numbers.trim();
        if(!numbers.matches("-?\\d+"))
            throw new IllegalArgumentException();


    /*  Creates a sign digit at the beginning of the Big Number
    *   0 if positive
    *   9 if negative.  Index = 1 to compensate for '-' in string
    */
        if (numbers.startsWith("-")) {
            listOfDigits.add(9);
            sign = 9;
            index = 1;
        }

        else{
            listOfDigits.add(0);
            sign = 0;
            index = 0;
        }

        String[] digits = numbers.split("");


        while(index < digits.length) {
            listOfDigits.add(Integer.parseInt(digits[index]));
            index++;
        }

        /* If the number entered was positive, store the negated form as usual

         */
        if (sign == 0){
            negated = negate();
        }
        else if (sign == 9){
            negated = listOfDigits;
            listOfDigits = negate();
        }

    }

    /*
     * Adds a BigNumber to this BigNumber and return the resulting sum of both BigNumbers.
     *
     * Return BigNumber
     */
    public BigNumber add(BigNumber bigNumber) {
        LinkedList<Integer> secondaryList = (LinkedList<Integer>) bigNumber.getListOfDigits();

        String number = "";

        int firstIndex = listOfDigits.size()-1;
        int secondaryIndex = secondaryList.size()-1;

        int remainder = 0;

        //Continue adding numbers while there still numbers to add.
        while(firstIndex >= 0 || secondaryIndex >= 0) {

            //Total represent the sum of remainder and the 2 digits. The total sum should be in the range of 0..19
            int total = remainder;

            //if there are still number in the first list that haven't been added, add to the total.
            if(firstIndex >= 0)
                total += listOfDigits.get(firstIndex);
            //if there are still number in the second list that haven't been added, add to the total.
            if(secondaryIndex >= 0)
                total += secondaryList.get(secondaryIndex);

            //The outcome
            if(total < 10)
                number =  total + number;
            else
                number = (total % 10) + number;


            /*
             * Get the remainder left by the addition of the current number. So we can utilize it on the next addition of digits.
             */
            if(firstIndex >= 0 && secondaryIndex >= 0)
                remainder  = addDigits(listOfDigits.get(firstIndex),secondaryList.get(secondaryIndex), remainder);
            else {
                if(firstIndex >= 0) {
                    remainder = addDigits(listOfDigits.get(firstIndex),remainder);
                }
                else
                    remainder = addDigits(secondaryList.get(secondaryIndex),remainder);
            }

            firstIndex--;
            secondaryIndex--;
        }

        if(remainder > 0) {
            number = remainder + number;
        }

        return new BigNumber(number);
    }

    /*
     * Add 2 integers with a carry and returns the remainder
     *
     * Restriction:
     */
    private int addDigits(Integer firstDigit, Integer secondDigit, Integer remainder) {
        int total = firstDigit + secondDigit + remainder;

        if(total > 9)
            return total / 10;

        return 0;
    }

    /*
     * Add 2 integers and returns the remainder
     *
     * Restriction:
     */
    private int addDigits(Integer firstDigit, Integer secondDigit) {
        int total = firstDigit + secondDigit;

        if(total > 9)
            return total / 10;

        return 0;
    }

    // Perform 10's complement of the BigNumber and store it as negated
    private List negate() {
        List<Integer> negatedList = new LinkedList<>();
        /*
            Work from the lowest order digit up, copying 0's from the source number
            until the first non-zero is reached
         */
        int index = listOfDigits.size() - 1;
        while (index > 0){
            while (listOfDigits.get(index) == 0){
                negatedList.add(0, 0);
                index--;
            }

            /*
                Upon the first non-zero digit, subtract that digit from 10
                to simulate the addition of 1 at the end of radix complement
             */
            negatedList.add(0, 10-listOfDigits.get(index));
            index--;

            //Subtract all remaining digits from 9 to complete the radix complement
            while (index >=0){
                negatedList.add(0, 9-listOfDigits.get(index));
                index--;
            }
        }
        return negatedList;
    }

    /*
        Returns the negated form of the BigNumber as a LinkedList<Integer>
     */
    public List<Integer> getNegated() {
        return negated;
    }

    public BigNumber substract(BigNumber bigNumber) {


        return null;
    }

    /* Compares two BigNumbers for equality
       Works well for normally entered values, but leading 0's will affect
       determination.

       Needs normalization function or scrubbing of leading zero's at instantiation.


    public boolean equals(BigNumber compare){
        if (!(this.getListOfDigits().size() == compare.getListOfDigits().size())){
            return false;
        }
        for (int i = 0; i < this.getListOfDigits().size(); i++){
            if (!(this.getListOfDigits().get(i) == compare.getListOfDigits().get(i))){
                return false;
            }
        }
        return true;
    }
    */


    public List<Integer> getListOfDigits() {
        return listOfDigits;
    }

    @Override
    /*
     * String representation of this BigNumber
     */
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (Integer digit :
                listOfDigits) {
            str.append(digit);
        }

        return str.toString();
    }
}