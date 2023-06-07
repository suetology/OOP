package expression;

import java.util.ArrayList;
import java.util.List;

public class Program {
    private List<Expression> expressions = new ArrayList<>();

    public void addExpression(Expression e) {
        expressions.add(e);
    }
}
