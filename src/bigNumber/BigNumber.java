package bigNumber;

import java.util.LinkedList;
import java.util.List;


public class BigNumber {

    private List<Integer>  listOfDigits = new LinkedList<>();


    /*
     * Construct a big number from the given String. The string must only contains digits.
     */
    public BigNumber(String numbers) {
        numbers = numbers.trim();
        if(!numbers.matches("\\d+"))
            throw new IllegalArgumentException();

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

        while(firstIndex >= 0 || secondaryIndex >= 0) {

            int total = remainder;

            if(firstIndex >= 0)
                total += listOfDigits.get(firstIndex);
            if(secondaryIndex >= 0)
                total += secondaryList.get(secondaryIndex);

            if(total < 10)
                number =  total + number;
            else
                number = (total % 10) + number;

            if(firstIndex >= 0 && secondaryIndex >= 0)
                remainder  = addDigits(listOfDigits.get(firstIndex),secondaryList.get(secondaryIndex));
            else
                remainder = 0;

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
