package ovh.zain.calculator;

import android.os.Handler;
import android.os.Looper;

import java.util.Stack;

public class Calculator {

    public static double evaluate(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int i = 0;
        while (i < expression.length()) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch)) {
                double number = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    number = number * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                numbers.push(number);
            } else if (isOperator(ch)) {
                while (!operators.empty() && hasPrecedence(ch, operators.peek())) {
                    applyOperation(numbers, operators);
                }
                operators.push(ch);
                i++;
            } else {
                throw new IllegalArgumentException("Invalid character: " + ch);
            }
        }

        while (!operators.empty()) {
            applyOperation(numbers, operators);
        }

        return numbers.pop();
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static boolean hasPrecedence(char op1, char op2) {
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }

    private static void applyOperation(Stack<Double> numbers, Stack<Character> operators) {
        char op = operators.pop();
        double num2 = numbers.pop();
        double num1 = numbers.pop();
        switch (op) {
            case '+':
                numbers.push(num1 + num2);
                break;
            case '-':
                numbers.push(num1 - num2);
                break;
            case '*':
                numbers.push(num1 * num2);
                break;
            case '/':
                numbers.push(num1 / num2);
                break;
        }
    }

    public static void evaluateAsyncWithHandler(final String expression, final CalculatorCallback callback) {
        new Thread(() -> {
            try {
                validateExpression(expression);
                double result = evaluate(expression);
                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(result));
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onError(e));
            }
        }).start();
    }

    private static void validateExpression(String expression) {
        if (expression.length() == 0) {
            throw new IllegalArgumentException("Expression is empty");
        }
        if (expression.charAt(0) == '+' || expression.charAt(0) == '*' || expression.charAt(0) == '/') {
            throw new IllegalArgumentException("Expression cannot start with " + expression.charAt(0));
        }
        if (expression.charAt(expression.length() - 1) == '+' || expression.charAt(expression.length() - 1) == '*' || expression.charAt(expression.length() - 1) == '/' || expression.charAt(expression.length() - 1) == '-') {
            throw new IllegalArgumentException("Expression cannot end with " + expression.charAt(expression.length() - 1));
        }
        for (int i = 0; i < expression.length() - 1; i++) {
            if (expression.charAt(i) == '+' || expression.charAt(i) == '*' || expression.charAt(i) == '/' || expression.charAt(i) == '-') {
                if (expression.charAt(i + 1) == '+' || expression.charAt(i + 1) == '*' || expression.charAt(i + 1) == '/' || expression.charAt(i + 1) == '-') {
                    throw new IllegalArgumentException("Expression cannot have two operators in a row");
                }
            }
        }
    }

    public interface CalculatorCallback {
        void onSuccess(double result);

        void onError(Exception e);
    }
}
