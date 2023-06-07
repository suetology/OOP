package expression;

public class Number extends Expression {
    private int num;

    public Number(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return Integer.toString(num);
    }
}
