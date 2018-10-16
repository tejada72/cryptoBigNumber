package bigNumber;

import java.util.*;

/*
    @author: Alex Tejada
 */
public class BigNumber {

    private List<Integer>  listOfDigits = new ArrayList<>();
    private int sign;

    /*
     * Construct a big number from the given String. The string must only contains digits or be preceded by '-'.
     */
    public BigNumber(String numbers) {
        int index;
        numbers = numbers.trim();
        if(!numbers.matches("-?\\d+"))
            throw new IllegalArgumentException();


    /*  Decide the sign digit at the beginning of the Big Number
    *   0 if positive
    *   9 if negative.  Index = 1 to compensate for '-' in string
    */
        if (numbers.startsWith("-")) {
            sign = 9;
            index = 1;
        }

        else{
            sign = 0;
            index = 0;
        }

        String[] digits = numbers.split("");

        while(index < digits.length) {
            listOfDigits.add(Integer.parseInt(digits[index]));
            index++;
        }

        /*
            @author: Tyler Robinson
            Stores the appropriate form of the entered number with a sign digit
         */
        if (numbers.startsWith("-")){
            this.listOfDigits = negate().getListOfDigits();
            listOfDigits.add(0,9);
        }
        else listOfDigits.add(0,0);
    }

    /*
        @author: Tyler Robinson
        Private constructor used by methods to avoid adding new sign digits
     */
    private BigNumber(String numbers, int num) {
        int index = 0;
        numbers = numbers.trim();
        if(!numbers.matches("-?\\d+"))
            throw new IllegalArgumentException();

        String[] digits = numbers.split("");

        while(index < digits.length) {
            listOfDigits.add(Integer.parseInt(digits[index]));
            index++;
        }
    }
    /*
     *@author: Alex Tejada
     * Adds a BigNumber to this BigNumber and return the resulting sum of both BigNumbers.
     *
     * Return BigNumber
     */
    public BigNumber add(BigNumber bigNumber) {
        ArrayList<Integer> firstList = (ArrayList<Integer>) this.getListOfDigits();
        ArrayList<Integer> secondaryList = (ArrayList<Integer>) bigNumber.getListOfDigits();

        if (firstList.size() != secondaryList.size()){
            if (firstList.size() > secondaryList.size()){
                while (secondaryList.size() < firstList.size()){
                    secondaryList.add(0, bigNumber.sign);
                }
            }
            else
                while (firstList.size() < secondaryList.size()){
                    firstList.add(0, sign);
                }
        }

        int wordLength = firstList.size();

        String number = "";

        int firstIndex = firstList.size()-1;
        int secondaryIndex = secondaryList.size()-1;

        int remainder = 0;

        //Continue adding numbers while there still numbers to add.
        while(firstIndex >= 0 || secondaryIndex >= 0) {

            //Total represent the sum of remainder and the 2 digits. The total sum should be in the range of 0..19
            int total = remainder;

            //if there are still number in the first list that haven't been added, add to the total.
            if(firstIndex >= 0)
                total += firstList.get(firstIndex);
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
                remainder  = addDigits(firstList.get(firstIndex),secondaryList.get(secondaryIndex), remainder);
            else {
                if(firstIndex >= 0) {
                    remainder = addDigits(firstList.get(firstIndex),remainder);
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
        return new BigNumber(number, 1);
    }

    /*
     *@author: Alex Tejada
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
     *@author: Alex Tejada
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

    /* Perform 10's complement of the BigNumber and store it as negated
       @author: Tyler Robinson
     */
    public BigNumber negate() {
        StringBuilder sr = new StringBuilder();
        for (Integer i: this.listOfDigits) {
            sr.append(i);
        }
        String negated = sr.toString();
        int index = negated.length() - 1;
        String[] digits = negated.split("");
        while (Integer.parseInt(digits[index]) == 0){
            index--;
        }

        digits[index] = Integer.toString(10 - Integer.parseInt(digits[index]));
        index--;

        while (index >= 0){
            digits[index] = Integer.toString(9 - Integer.parseInt(digits[index]));
            index--;
        }
        sr = new StringBuilder();
        for (String i: digits) {
            sr.append(i);
        }

        return new BigNumber(sr.toString(), 1);
    }

    /*
        @author: Tyler Robinson
     */
    public BigNumber multiply(BigNumber bigNumber){
        List<Integer> target = this.getListOfDigits();
        List<Integer> multiplier = bigNumber.getListOfDigits();



        for (int i = multiplier.size() - 1; i >= 0; i--){
            for (int j = target.size() - 1; j >=0; j--){

            }
        }
        return null;
    }

    /*
        @author: Tyler Robinson
     */
    public BigNumber subtract(BigNumber bigNumber) {
        return this.add(bigNumber.negate());
    }

    /* Compares two BigNumbers for equality
    @author: Tyler Robinson
     */
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

    /*
        @author: Tyler Robinson
     */
    public int sign(){

        if (this.equals(new BigNumber("0"))){
            return 0;
        }
        if (sign == 9){
            return -1;
        }
        else
            return 1;
    }


    public List<Integer> getListOfDigits() {
        return listOfDigits;
    }

    @Override
    /*
       @author: Alex Tejada
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