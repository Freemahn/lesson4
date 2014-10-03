package ru.ifmo.md.lesson4;

public class DummyCalculateEngine implements CalculationEngine {

    private Token curlex;
    private String v1;
    private int currentChar;
    private String expression;


    enum Token {
        NUM, OPEN, CLOSE, END, PLUS, MUL, MINUS, DIV
    }

    @Override
    public double calculate(String expression) throws CalculationException {
        currentChar = -1;
        this.expression = expression.trim();
        nextToken();

        double answer = expr();

        //  return answer;
        return answer;
    }


    public void nextToken() throws CalculationException {
        currentChar++;
        if (currentChar >= expression.length()) {
            curlex = Token.END;
            return;
        }
        if (Character.isWhitespace(expression.charAt(currentChar))) {
            nextToken();
            return;
        }
        switch (expression.charAt(currentChar)) {
            case '(':
                curlex = Token.OPEN;
                break;
            case ')':
                curlex = Token.CLOSE;
                break;
            case '+':
                curlex = Token.PLUS;
                break;
            case '-':
                if (curlex == Token.NUM || curlex == Token.CLOSE)
                    curlex = Token.MINUS;
                else {
                    currentChar++;
                    v1 = "-" + findNum();
                    curlex = Token.NUM;
                }
                break;
            case '*':
                curlex = Token.MUL;
                break;
            case '/':
                curlex = Token.DIV;
                break;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '0':
                v1 = findNum();
                curlex = Token.NUM;
                break;
            default:
                throw new CalculationException();
        }

        //System.out.println(curlex);
    }

    public double expr() throws CalculationException {
        Token t;
        double result = item();
        while (curlex == Token.PLUS || curlex == Token.MINUS) {
            t = curlex;
            nextToken();
            if (t == Token.PLUS) result += item();
            if (t == Token.MINUS) result -= item();
        }
        return result;

    }

    public double item() throws CalculationException {
        Token t;
        double result = mult();
        while (curlex == Token.MUL || curlex == Token.DIV) {
            t = curlex;
            nextToken();
            if (t == Token.MUL)
                result *= mult();
            if (t == Token.DIV)
                result /= mult();
        }
        return result;
    }

    public double mult() throws CalculationException {
        double result;
        switch (curlex) {
            case NUM:
                result = Double.parseDouble(v1);
                nextToken();
                break;
            case OPEN:
                nextToken();
                result = expr();
                if (curlex == Token.CLOSE) nextToken();
                else throw new CalculationException();
                break;
            default:
                throw new CalculationException();

        }
        return result;
    }

    public String findNum() {
        String answer = "";
        while (currentChar < expression.length() &&
                (Character.isDigit(expression.charAt(currentChar))
                        || expression.charAt(currentChar) == '.')) {
            answer += expression.charAt(currentChar);
            currentChar++;
        }
        currentChar--;
        return answer;
    }
}
