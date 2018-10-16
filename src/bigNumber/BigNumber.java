package bigNumber;

import java.util.*;


public class BigNumber implements Comparable<BigNumber>{

    private List<Integer> listOfDigits = new ArrayList<>();
    private int sign;

    /*
     * Construct a big number from the given String. The string must only contains digits or be preceded by '-'.
     *
     * Authors: Alex Tejada &
     */
    public BigNumber(String numbers) {
        boolean negate = false;
        numbers = numbers.trim();
        if (!numbers.matches("-?\\d+"))
            throw new IllegalArgumentException();

        if (numbers.startsWith("-")) {
            numbers = numbers.replace("-","");
            negate = true;
        }

        //Remove garbage 0's if user decided to be funny
        while(Integer.valueOf(((Character) numbers.charAt(0)).toString()) == 0 && numbers.length() > 0) {
            numbers = numbers.subSequence(1,numbers.length()).toString();
        }

        if(Integer.valueOf(((Character) numbers.charAt(0)).toString()) > 4) {
            numbers = "0" + numbers;
        }

        createAndChangeList(numbers);

        if (negate) {
            negate();
            sign = listOfDigits.get(0);
        }
    }

    /*
     * Adds a BigNumber to this BigNumber and return the resulting sum of both BigNumbers.
     *
     * Return BigNumber
     */


    public BigNumber add1(BigNumber bigNumber) {
        ArrayList<Integer> firstList = (ArrayList<Integer>) this.getListOfDigits();
        ArrayList<Integer> secondaryList = (ArrayList<Integer>) bigNumber.getListOfDigits();

        if (firstList.size() != secondaryList.size()) {
            if (firstList.size() > secondaryList.size()) {
                if(bigNumber.sign >= 0)
                    while (secondaryList.size() < firstList.size()) {
                        secondaryList.add(0, bigNumber.getSign());
                    }
                else
                    while (secondaryList.size() < firstList.size()) {
                        secondaryList.add(9, bigNumber.getSign());
                    }
            } else {
                if(this.sign >= 0) {
                    while (firstList.size() < secondaryList.size()) {
                        firstList.add(0, getSign());
                    }
                }
                else {
                    while (firstList.size() < secondaryList.size()) {
                        firstList.add(9, getSign());
                    }
                }

            }

        }


        String number = "";

        int firstIndex = firstList.size() - 1;
        int secondaryIndex = secondaryList.size() - 1;

        int remainder = 0;

        //Continue adding numbers while there still numbers to add.
        while (firstIndex >= 0 || secondaryIndex >= 0) {

            //Total represent the sum of remainder and the 2 digits. The total sum should be in the range of 0..19
            int total = remainder;

            //if there are still number in the first list that haven't been added, add to the total.
            if (firstIndex >= 0)
                total += firstList.get(firstIndex);
            //if there are still number in the second list that haven't been added, add to the total.
            if (secondaryIndex >= 0)
                total += secondaryList.get(secondaryIndex);

            //The outcome
            if (total < 10)
                number = total + number;
            else
                number = (total % 10) + number;


            /*
             * Get the remainder left by the addition of the current number. So we can utilize it on the next addition of digits.
             */
            if (firstIndex >= 0 && secondaryIndex >= 0)
                remainder = addDigits(firstList.get(firstIndex), secondaryList.get(secondaryIndex), remainder)[1];
            else {
                if (firstIndex >= 0) {
                    remainder = addDigits(firstList.get(firstIndex), remainder);
                } else
                    remainder = addDigits(secondaryList.get(secondaryIndex), remainder);
            }

            firstIndex--;
            secondaryIndex--;
        }

        if (remainder > 0) {
            number = remainder + number;
        }

        return new BigNumber(number);
    }

    private void createAndChangeList(String numbers) {
        String[] digits = numbers.split("");

        listOfDigits = new ArrayList<>();


        int index = 0;

        while (index < digits.length) {
            listOfDigits.add(Integer.parseInt(digits[index]));
            index++;
        }
    }

    public BigNumber add2(BigNumber secondNumber) {
        ArrayList<Integer> firstList = (ArrayList<Integer>) this.getListOfDigits();
        ArrayList<Integer> secondaryList = (ArrayList<Integer>) secondNumber.getListOfDigits();

        if (firstList.size() != secondaryList.size()) {
            if (firstList.size() > secondaryList.size()) {
                if (secondNumber.getSign() < 5)
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

        if(this.getSign()< 5 && secondNumber.getSign() < 5) {
            //Both numbers are positives and return a positive.
        }
        else if((this.getSign() < 5 && secondNumber.getSign() > 4) || (this.getSign() > 4) && secondNumber.getSign() < 5) {
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

    // Perform 10's complement of the BigNumber and store it as negated
    private BigNumber negate() {
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

        //System.out.println("Negated number: " + sr.toString());
        createAndChangeList(sr.toString());
        return this;
    }

    /*
        Mutates this bigNumber into a bigNumber without any extra digit that don't have meaning.
     */
    public void normalize() {
        //Stores the first digit that represents the getSign of this number
        Integer firstDigit = listOfDigits.get(0);

        //Skip the first element
        Iterator<Integer> it = listOfDigits.iterator();
        boolean stop = false;

        //count how many leading 0 there are
        if(getSign() < 5)
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

    public BigNumber subtract(BigNumber bigNumber) {
        if(bigNumber.getSign() < 5)
            bigNumber.negate();
        return this.add2(bigNumber);
    }

    /* Compares two BigNumbers for equality
       Works well for normally entered values, but leading 0's will affect
       determination.

       Needs normalization function or scrubbing of leading zero's at instantiation.

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

    public int getSign(){
        return sign;
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
        boolean leadingGarbage = true;

        int index = 0;
        Integer digit = 0;
        while(index < listOfDigits.size()) {
            digit = listOfDigits.get(index);
            if(this.getSign() < 5) {
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

    @Override
    public int compareTo(BigNumber bigNumber) {
        if(this.getListOfDigits().get(0) < 5 && bigNumber.getListOfDigits().get(0) > 4) {
            return 1;
        }
        else if(bigNumber.getListOfDigits().get(0)< 5 && this.getListOfDigits().get(0) > 4) {
            return -1;
        }
        return 0;
    }
}