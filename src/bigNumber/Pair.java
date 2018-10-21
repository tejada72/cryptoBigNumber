package bigNumber;

public class Pair {

    private BigNumber quotient;
    private BigNumber remainder;

    public Pair(BigNumber quotient, BigNumber remainder) {
        this.quotient = quotient;
        this.remainder = remainder;
    }

    public BigNumber getMod() {
        return remainder;
    }

    public BigNumber getQuotient() {
        return quotient;
    }
}
