package bigNumber;

import java.util.*;

/*
    @author: Alex Tejada & Tyler Robinson
 */
public class BigNumber {

    private List<Integer> listOfDigits = new ArrayList<>();
    private int sign;

    /*
     * Construct a big number from the given String. The string must only contains digits or be preceded by '-'.
     *
     * Authors: Alex Tejada & Tyler Robinson
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
        @author Alex Tejada
     */
    public BigNumber add(BigNumber secondNumber) {
        ArrayList<Integer> firstList = (ArrayList<Integer>) this.getListOfDigits();
        ArrayList<Integer> secondaryList = (ArrayList<Integer>) secondNumber.getListOfDigits();

        if (firstList.size() != secondaryList.size()) {
            if (firstList.size() > secondaryList.size()) {
                if (secondNumber.sign() < 5)
                    while (secondaryList.size() < firstList.size()) {
                        secondaryList.add(0, 0);
                    }
                else
                    while (secondaryList.size() < firstList.size()) {
                        secondaryList.add(0, 9);
                    }
            } else {
                if (this.sign < 5) {
                    while (firstList.size() < secondaryList.size()) {
                        firstList.add(0, 0);
                    }
                } else {
                    while (firstList.size() < secondaryList.size()) {
                        firstList.add(0, 9);
                    }
                }
            }
        }

        int index = this.getListOfDigits().size()-1;
        int remainder = 0;
        int[] sum = null;
        StringBuilder str = new StringBuilder();

        while(index >= 0) {
            sum = addDigits(firstList.get(index),secondaryList.get(index),remainder);
            str.insert(0,sum[0] % 10);
            remainder = sum[1];
            index--;
        }

        if(remainder > 0) {
            str.insert(0,remainder);
        }

        if(this.sign()< 5 && secondNumber.sign() < 5) {
            //Both numbers are positives and return a positive.
        }
        else if((this.sign() < 5 && secondNumber.sign() > 4) || (this.sign() > 4) && secondNumber.sign() < 5) {
            if(Integer.valueOf(((Character) str.charAt(0)).toString()) < 5) {
                str = str.delete(0,1);
            }
            else {
                return new BigNumber("-" + str.toString());
            }
        }
        else {
           if(Integer.valueOf(((Character) str.charAt(0)).toString()) < 5) {
                str = str.delete(0,1);
            }
        }

        return new BigNumber(str.toString());
    }

    /*
     *@author: Alex Tejada
     * Add 2 integers with a carry and returns the remainder
     *
     * returns an array with index 0 being the result from the addition mod 10 and index 1 being the remainder
     */
    private int[] addDigits(Integer firstDigit, Integer secondDigit, Integer remainder) {
        int total = firstDigit + secondDigit + remainder;

        if (total > 9)
            return new int[]{total % 10,total / 10};

        return new int[]{total % 10,0};
    }

    /*
     *@author: Alex Tejada
     * Add 2 integers and returns the remainder
     *
     * Restriction:
     */
    private int addDigits(Integer firstDigit, Integer secondDigit) {
        int total = firstDigit + secondDigit;

        if (total > 9)
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
        return new BigNumber(sr.toString());
    }

    /*
<<<<<<< HEAD
        @author Alex Tejada
=======
        @author: Alex Tejada
>>>>>>> master
        Mutates this bigNumber into a bigNumber without any extra digit that don't have meaning.
     */
    public void normalize() {
        //Stores the first digit that represents the getSign of this number
        Integer firstDigit = listOfDigits.get(0);

        //Skip the first element
        Iterator<Integer> it = listOfDigits.iterator();
        boolean stop = false;

        //count how many leading 0 there are
        if(listOfDigits.get(0) < 5)
            while (it.hasNext() && !stop) {
                if(it.next() == 0)
                    it.remove();
                else
                    stop = true;
            }
            //count how many leading 9 there are
        else
            while (it.hasNext() && !stop) {
                if (it.next() == 0)
                    it.remove();
                else
                    stop = true;
            }


        //add the firstDigit back to the list
        listOfDigits.add(0,firstDigit);
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
        this.normalize();
        compare.normalize();
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


    /*
        @author Alex Tejada
     */
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
        boolean leadingGarbage = true;

        int index = 0;
        Integer digit = 0;
        while(index < listOfDigits.size()) {
            digit = listOfDigits.get(index);
            if(this.sign() < 5) {
                if(!digit.equals(0) && leadingGarbage)
                    leadingGarbage = false;
            }
            else {
                if(!digit.equals(9) && leadingGarbage) {
                    str.insert(0,"-");
                    leadingGarbage = false;
                }

            }

            if(!leadingGarbage)
                str.append(digit);
            index++;
        }

        return str.toString();
    }
}

