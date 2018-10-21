package bigNumber;

import java.util.*;


public class BigNumber {

    private List<Integer> listOfDigits = new ArrayList<>();
    private int sign;

    /*
     * Construct a big number from the given String. The string must only contains digits or be preceded by '-'.
     *
     * Authors: Alex Tejada & Tyler Robison
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
        while(Integer.valueOf(((Character) numbers.charAt(0)).toString()) == 0 && numbers.length() > 1) {
            numbers = numbers.subSequence(1,numbers.length()).toString();
        }

        if(Integer.valueOf(((Character) numbers.charAt(0)).toString()) > 4) {
            numbers = "0" + numbers;
        }

        //String[] digits = numbers.split("");

        initiateList(numbers);

        if (negate) {
            listOfDigits = negate().listOfDigits;
        }
    }

    private BigNumber(String numbers, boolean negated) {
        initiateList(numbers);
    }

    private BigNumber(List<Integer> list) {
        StringBuilder builder = new StringBuilder();

        for (Integer number: list) {
            builder.append(number);
        }

        initiateList(builder.toString());
    }


    private void initiateList(String numbers) {
        String[] digits = numbers.split("");

        listOfDigits = new ArrayList<>();

        int index = 0;

        while (index < digits.length) {
            listOfDigits.add(Integer.parseInt(digits[index]));
            index++;
        }

        sign = listOfDigits.get(0);
    }

    public BigNumber add(BigNumber secondNumber) {
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
        sign = Character.digit(sr.charAt(0),10);
        return new BigNumber(sr.toString(),true);
    }

    /*
        Return this bigNumber as bigNumber without any extra digit that don't have meaning.
     */
    public BigNumber normalize() {
        List<Integer> list = listOfDigits.subList(0,listOfDigits.size());
        //Stores the first digit that represents the getSign of this number
        Integer firstDigit = list.get(0);

        //Skip the first element
        Iterator<Integer> it = list.iterator();
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
        list.add(0,firstDigit);
        return new BigNumber(list);
    }

    public BigNumber subtract(BigNumber bigNumber) {
        //if(getSign() > 4 && bigNumber.getSign() > 4) {
        //    return this.negate().subtract(bigNumber.negate()).negate();
       // }
        if(bigNumber.getSign() < 5)
            bigNumber.negate();
        return this.add(bigNumber);
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
}