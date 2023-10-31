package model;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * @author samue
 */

public class Calculadora {

    public static double avaliarInfixa(String infix) {
        infix = infix.replaceAll(",", ".");
        String posfixa = infixaParaPosfixa(infix);
        return avaliarPosfixa(posfixa);
    }

    public static String infixaParaPosfixa(String infix) {
        infix = infix.replaceAll(",", ".");
        // regex que identifica (inteiros ou decimais) 
        // que estão no início da expressão ou após um parêntese 
        // ou operador (+, -, *, /, %), incluindo números negativos.
        infix = infix.replaceAll("(?<=\\(|\\+|\\-|\\*|\\/|\\%|^)-?\\d+(\\.\\d+)?", "0$0");
        StringBuilder posfixa = new StringBuilder();
        Stack<Character> pilhaOperadores = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, "+-*/%()", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (token.isEmpty()) {
                continue;
            } else if (Character.isDigit(token.charAt(0)) || token.equals(".")) {
                posfixa.append(token).append(" ");
            } else if (token.equals("(")) {
                pilhaOperadores.push('(');
            } else if (token.equals(")")) {
                while (!pilhaOperadores.isEmpty() && pilhaOperadores.peek() != '(') {
                    posfixa.append(pilhaOperadores.pop()).append(" ");
                }
                if (!pilhaOperadores.isEmpty() && pilhaOperadores.peek() == '(') {
                    pilhaOperadores.pop();
                } else {
                    throw new IllegalArgumentException("Expressão infixa inválida.");
                }
            } else if (ehOperador(token.charAt(0))) {
                while (!pilhaOperadores.isEmpty() && pilhaOperadores.peek() != '('
                        && precedencia(pilhaOperadores.peek()) >= precedencia(token.charAt(0))) {
                    posfixa.append(pilhaOperadores.pop()).append(" ");
                }
                pilhaOperadores.push(token.charAt(0));
            }
        }

        while (!pilhaOperadores.isEmpty()) {
            if (pilhaOperadores.peek() == '(' || pilhaOperadores.peek() == ')') {
                throw new IllegalArgumentException("Expressão infixa inválida.");
            }
            posfixa.append(pilhaOperadores.pop()).append(" ");
        }

        return posfixa.toString().trim();
    }

    public static double avaliarPosfixa(String posfixa) {
        Stack<Double> pilhaOperandos = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(posfixa, " ");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (Character.isDigit(token.charAt(0)) || token.equals(".")) {
                pilhaOperandos.push(Double.parseDouble(token));
            } else if (ehOperador(token.charAt(0))) {
                if (token.equals("%")) {
                    tratarOperadorPorcentagem(pilhaOperandos);
                } else {
                    realizarOperacao(pilhaOperandos, token.charAt(0));
                }
            }
        }

        if (pilhaOperandos.size() != 1) {
            throw new IllegalArgumentException("Expressão posfixa inválida.");
        }

        return pilhaOperandos.pop();
    }

    public static boolean ehOperador(char c) {
        // add "-"
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^');
    }

    public static int precedencia(char c) {
        if (c == '+' || c == '-') {
            return 1;
        } else if (c == '*' || c == '/' || c == '%') {
            return 2;
        }
        return 0;
    }

    private static void tratarOperadorPorcentagem(Stack<Double> pilhaOperandos) {
        if (pilhaOperandos.size() < 1) {
            throw new IllegalArgumentException("Expressão posfixa inválida.");
        }

        double valor = pilhaOperandos.pop();
        double resultado = valor / 100.0;
        pilhaOperandos.push(resultado);
    }

    private static void realizarOperacao(Stack<Double> pilhaOperandos, char operador) {
        if (pilhaOperandos.size() < 2) {
            throw new IllegalArgumentException("Expressão posfixa inválida.");
        }

        double operando2 = pilhaOperandos.pop();
        double operando1 = pilhaOperandos.pop();
        double resultado = 0;

        switch (operador) {
            case '+':
                resultado = operando1 + operando2;
                break;
            case '-':
                resultado = operando1 - operando2;
                break;
            case '*':
                resultado = operando1 * operando2;
                break;
            case '/':
                if (operando2 == 0) {
                    throw new ArithmeticException("Divisão por zero.");
                }
                resultado = operando1 / operando2;
                break;
        }

        pilhaOperandos.push(resultado);
    }
}
