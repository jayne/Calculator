import java.lang.management.OperatingSystemMXBean;
import java.util.HashSet;
import java.util.Stack;

/**
 * Created by jaynehsu on 3/6/17.
 */
public class Calculator {
    public static void main(String[] args) {
        String a1 = "-5 - 10 * 2 + 3/2";
        Calculator c = new Calculator();
        System.out.println(c.eval(a1));
    }

    public double eval(String str) {

        Stack<Double> num = new Stack<Double>();
        Stack<Character> ops = new Stack<Character>();

        parseString(str, num, ops);
        double result = calcResult(num, ops);
        return result;
    }

    double calcResult(Stack<Double> num, Stack<Character> ops) {

        HashSet<Character> op1 = new HashSet<Character>();
        op1.add('*');
        op1.add('/');

        HashSet<Character> op2 = new HashSet<Character>();
        op2.add('+');

        HashSet<Character> op3 = new HashSet<Character>();
        op3.add('-');

        Stack<HashSet> orderOfOps = new Stack<HashSet>();
        orderOfOps.add(op2);
        orderOfOps.add(op3);
        orderOfOps.add(op1);

        Stack<Double> numTemp = new Stack<Double>();
        Stack<Character> opsTemp = new Stack<Character>();
        while (orderOfOps.size() != 0) {

            HashSet<Character> currentOps = orderOfOps.pop();

            while (ops.size() != 0 && num.size() > 1) {
                char theOp = ops.pop();
                double n1 = num.pop();
                double n2 = num.pop();
                if (currentOps.contains(theOp)) {
                    double result = performOp(theOp, n1, n2);
                    numTemp.add(result);

                    while (numTemp.size() != 0) {
                        num.push(numTemp.pop());
                    }
                    while (opsTemp.size() != 0) {
                        ops.push(opsTemp.pop());
                    }
                } else {
                    numTemp.push(n1);
                    num.push(n2);
                    opsTemp.push(theOp);
                }
            }
            while (numTemp.size() != 0) {
                num.push(numTemp.pop());
            }
            while (opsTemp.size() != 0) {
                ops.push(opsTemp.pop());
            }
        }

        return num.pop();
    }

    double performOp(Character theOp, double n1, double n2) {
        switch (theOp) {
            case '+':
                return n2 + n1;
            case '-':
                return n2 - n1;
            case '*':
                return n2 * n1;
            case '/':
                return n2 / n1;

        }
        throw new RuntimeException("Invalid binary op");
    }


    void parseString(String str, Stack<Double> num, Stack<Character> ops) {
        boolean firstNumber = false;
        String number = "";

        //TODO: error check to see if it is a valid expression

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == ' ') {

            } else if (c == '-' && firstNumber == false) {
                number = number + c;
                firstNumber = true;
            } else if (c != '-' && c != '+' && c != '*' && c != '/') {
                number = number + c;
                firstNumber = true;

            } else {
                double val = Double.valueOf(number);
                num.push(val);
                number = "";
                ops.push(c);
            }
        }
        num.push(Double.valueOf(number));

    }

}
