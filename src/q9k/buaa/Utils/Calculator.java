package q9k.buaa.Utils;

import q9k.buaa.AST.Syntax;
import q9k.buaa.IR.ConstantArray;
import q9k.buaa.IR.ConstantInt;
import q9k.buaa.IR.GlobalVariable;
import q9k.buaa.Symbol.Symbol;
import q9k.buaa.Symbol.SymbolTable;

import java.util.Stack;

//计算常量和全局变量真值
public class Calculator {
    private static Calculator calculator;

    public static synchronized Calculator getInstance() {
        if (calculator == null) {
            calculator = new Calculator();
        }
        return calculator;
    }

    public int calculate(Syntax syntax) {
        if (syntax == null) {
            return 0;
        } else {
            System.out.println(syntax.toString());
            return calculateExpression("0+" + syntax.toString());
        }
    }


    private static int calculateExpression(String expression) {
        char[] tokens = expression.toCharArray();

        boolean minus_prefix = false;
        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ' || tokens[i] == '\r' || tokens[i] == '\n' || tokens[i] == '\t')
                continue;
            //处理identifer, 但是后来说ConstExp都是常值表达式
            if (Character.isLetter(tokens[i]) || tokens[i] == '_') {
                StringBuilder sb = new StringBuilder();
                while (i < tokens.length && (Character.isLetterOrDigit(tokens[i]) || tokens[i] == '_' ||
                        tokens[i] == '[' || tokens[i] == ']')) {
                    sb.append(tokens[i++]);
                }
                i--;
                if (!sb.toString().contains("[")) {
                    Symbol symbol = SymbolTable.getGlobal().getSymbol(sb.toString());
                    GlobalVariable globalVariable = (GlobalVariable) symbol.getIR();
                    values.push(((ConstantInt) globalVariable.getInitializer()).getValue());
                } else {
                    String[] parts = sb.toString().split("\\]\\[|\\[|\\]");
                    if(parts.length == 2){
                        //一维数组
                        Symbol symbol = SymbolTable.getGlobal().getSymbol(parts[0]);
                        GlobalVariable globalVariable = (GlobalVariable) symbol.getIR();
                        ConstantArray constantArray = ((ConstantArray) globalVariable.getInitializer());
                        ConstantInt constantInt = (ConstantInt) constantArray.getConstants().get(Integer.parseInt(parts[1]));
                        values.push(constantInt.getValue());
                    }
                    else if(parts.length==3){
                        Symbol symbol = SymbolTable.getGlobal().getSymbol(parts[0]);
                        GlobalVariable globalVariable = (GlobalVariable) symbol.getIR();
                        ConstantArray constantArray = ((ConstantArray) globalVariable.getInitializer());
                        ConstantArray constantArray1 = (ConstantArray) constantArray.getConstants().get(Integer.parseInt(parts[1]));
                        ConstantInt constantInt = (ConstantInt) constantArray1.getConstants().get(Integer.parseInt(parts[2]));
                        values.push(constantInt.getValue());
                    }
                }
            } else if (Character.isDigit(tokens[i])) {
                StringBuilder sb = new StringBuilder();
                while (i < tokens.length && Character.isDigit(tokens[i])) {
                    sb.append(tokens[i++]);
                }
                i--;
                int value = Integer.parseInt(sb.toString());
                values.push(value);
            } else if (tokens[i] == '(') {
                operators.push(tokens[i]);
            } else if (tokens[i] == ')') {
                while (operators.peek() != '(') {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            } else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '%') {
                char character = tokens[i];
                if (i + 1 < tokens.length && (tokens[i + 1] == '+' || tokens[i + 1] == '-')) {
                    int count = 0;
                    while (i + 1 < tokens.length && (tokens[i + 1] == '+' || tokens[i + 1] == '-')) {
                        if (tokens[i + 1] == '-') {
                            count++;
                        }
                        i++;
                    }
//                    i--;
                    minus_prefix = count % 2 == 1;
                }
                while (!operators.empty() && hasPrecedence(character, operators.peek())) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(character);
            }
        }

        while (!operators.empty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private static boolean hasPrecedence(char op1, char op2) {
        return (op2 != '(' && op2 != ')' && getPrecedence(op1) <= getPrecedence(op2));
    }

    private static int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
        }
        return -1;
    }

    private static int applyOperator(char operator, int b, int a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("除数不能为零");
                return a / b;
            case '%':
                return a % b;
        }
        return 0;
    }
}
