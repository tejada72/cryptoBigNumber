package bigNumber;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class BigNumber {

    private List<Integer>  listOfDigits = new LinkedList<>();
    private List<Integer> negated = new LinkedList<>();

    private int index;

    /*
     * Construct a big number from the given String. The string must only contains digits.
     */
    public BigNumber(String numbers) {
        numbers = numbers.trim();
        if(!numbers.matches("-?\\d+"))
            throw new IllegalArgumentException();


    /*  Currently creates a sign digit, needs to negate if a negative number is entered
        if (numbers.startsWith("-")) {
            listOfDigits.add(9);
            index = 1;
        }

        else{
            listOfDigits.add(0);
            index = 0;
        }
    */
        String[] digits = numbers.split("");
        int index = 0;

        while(index < digits.length) {
            listOfDigits.add(Integer.parseInt(digits[index]));
            index++;
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
                remainder  = addDigits(listOfDigits.get(firstIndex),secondaryList.get(secondaryIndex));
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

    /* Does 9's complement, need to update for 10's complement
    public void negate() {
        for(Integer digit : listOfDigits) {
            digit = 9 - digit;
            negated.add(digit);
        }
        return;
    }


    public List getNegated(){
        return negated;
    }
    */

    public BigNumber substract(BigNumber bigNumber) {


        return null;
    }


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