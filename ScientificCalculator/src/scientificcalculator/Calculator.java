package scientificcalculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Calculator extends JFrame implements ActionListener {

    private DecimalFormat df = new DecimalFormat("#,###.00");
    private String[] symbols
            = {
                "%", "AC", "⌫", "÷", "sin", "cos", "tan", "log",
                "7", "8", "9", "x", "cosec", "sec", "cot", "ln",
                "4", "5", "6", "-", "x^2", "x^3", "√x", "∛x",
                "1", "2", "3", "+", "e", "π", "x!", "|x|",
                "+/-", "0", ".", "=", "e^x", "10^x", "1/x", "2^x"
            };

    private JPanel panel = new JPanel(new BorderLayout(5, 5));
    private JPanel btnPanel = new JPanel(new GridLayout(5, 8, 2, 2));
    private JButton[] btns = new JButton[symbols.length];
    private JTextArea screen = new JTextArea(2, 20); // Adjust the size of the text area
    private double firstNum = 0, secondNum = 0;
    private int operator = 0;
    private Font aptosFont;

    public Calculator() {
        loadFont();
        init();
    }

    private void loadFont() {
        try {
            aptosFont = Font.createFont(Font.TRUETYPE_FONT, new File("DejaVuSansMono-Bold.ttf")).deriveFont(24f); // Change font to one that supports ⌫
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(aptosFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            aptosFont = new Font("Serif", Font.PLAIN, 18); // Fallback font
        }
    }

    private void init() {
        setTitle("Calculator");
        screen.setBackground(Color.BLACK);
        screen.setForeground(Color.WHITE);
        screen.setFont(aptosFont.deriveFont(32f)); // Set font size for main text area
        screen.setEditable(false);
        panel.setBackground(Color.BLACK);
        btnPanel.setBackground(Color.BLACK);

        for (int i = 0; i < btns.length; i++) {
            btns[i] = new JButton(symbols[i]);
            btns[i].setOpaque(true);
            btns[i].setBorderPainted(false);
            btns[i].setFont(aptosFont.deriveFont(18f)); // Set font size for buttons

            // Set colors for specific buttons
            if (isOperatorButton(symbols[i])) {
                btns[i].setBackground(Color.DARK_GRAY);
            } else if (symbols[i].matches("[0-9]")) { // Check if symbol is a digit from 0 to 9
                btns[i].setBackground(Color.GRAY);
            } else {
                btns[i].setBackground(Color.DARK_GRAY);
            }

            btns[i].setForeground(Color.WHITE);
            btns[i].addActionListener(this);
            btnPanel.add(btns[i]);
        }

        panel.add(screen, BorderLayout.NORTH);
        panel.add(btnPanel, BorderLayout.CENTER);
        add(panel);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private boolean isOperatorButton(String symbol) {
        return symbol.equals("%") || symbol.equals("AC") || symbol.equals("⌫")
                || symbol.equals("÷") || symbol.equals("x") || symbol.equals("-")
                || symbol.equals("+") || symbol.equals("=") || symbol.equals("sin")
                || symbol.equals("cos") || symbol.equals("tan") || symbol.equals("log")
                || symbol.equals("cosec") || symbol.equals("sec") || symbol.equals("cot")
                || symbol.equals("ln") || symbol.equals("x^2") || symbol.equals("x^3")
                || symbol.equals("√x") || symbol.equals("∛x") || symbol.equals("x!")
                || symbol.equals("e") || symbol.equals("π") || symbol.equals("|x|")
                || symbol.equals("(") || symbol.equals(")") || symbol.equals("1/x")
                || symbol.equals("2^x") || symbol.equals("+/-");
    }

    public static void main(String[] args) {
        new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
            case ".":
                if (!screen.getText().contains(".")) {
                    screen.setText(screen.getText() + ".");
                }
                break;
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                screen.setText(screen.getText() + cmd);
                break;
            case "+":
                if (!screen.getText().isEmpty()) {
                    firstNum = Double.parseDouble(screen.getText());
                    operator = 1;
                    screen.setText("");
                }
                break;
            case "-":
                if (!screen.getText().isEmpty()) {
                    firstNum = Double.parseDouble(screen.getText());
                    operator = 2;
                    screen.setText("");
                }
                break;
            case "x":
                if (!screen.getText().isEmpty()) {
                    firstNum = Double.parseDouble(screen.getText());
                    operator = 3;
                    screen.setText("");
                }
                break;
            case "÷":
                if (!screen.getText().isEmpty()) {
                    firstNum = Double.parseDouble(screen.getText());
                    operator = 4;
                    screen.setText("");
                }
                break;
            case "%":
                if (!screen.getText().isEmpty()) {
                    double num = Double.parseDouble(screen.getText());
                    screen.setText(String.valueOf(num / 100.0));
                }
                break;
            case "+/-":
                if (!screen.getText().isEmpty()) {
                    double neg = Double.parseDouble(screen.getText());
                    neg *= -1;
                    screen.setText(String.valueOf(neg));
                }
                break;
            case "AC":
                screen.setText("");
                break;
            case "⌫":
                if (screen.getText().length() > 0) {
                    screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
                }
                break;
            case "=":
                if (!screen.getText().isEmpty()) {
                    secondNum = Double.parseDouble(screen.getText());
                    switch (operator) {
                        case 1: // addition
                            screen.setText(String.valueOf(firstNum + secondNum));
                            break;
                        case 2: // subtraction
                            screen.setText(String.valueOf(firstNum - secondNum));
                            break;
                        case 3: // multiplication
                            screen.setText(String.valueOf(firstNum * secondNum));
                            break;
                        case 4: // division
                            screen.setText(String.valueOf(firstNum / secondNum));
                            break;
                        default:
                            break;
                    }
                }
                break;
            case "sin":
                screen.setText(String.valueOf(Math.sin(Math.toRadians(Double.parseDouble(screen.getText())))));
                break;
            case "cos":
                screen.setText(String.valueOf(Math.cos(Math.toRadians(Double.parseDouble(screen.getText())))));
                break;
            case "tan":
                screen.setText(String.valueOf(Math.tan(Math.toRadians(Double.parseDouble(screen.getText())))));
                break;
            case "log":
                screen.setText(String.valueOf(Math.log10(Double.parseDouble(screen.getText()))));
                break;
            case "cosec":
                screen.setText(String.valueOf(1 / Math.sin(Math.toRadians(Double.parseDouble(screen.getText())))));
                break;
            case "sec":
                screen.setText(String.valueOf(1 / Math.cos(Math.toRadians(Double.parseDouble(screen.getText())))));
                break;
            case "cot":
                screen.setText(String.valueOf(1 / Math.tan(Math.toRadians(Double.parseDouble(screen.getText())))));
                break;
            case "ln":
                screen.setText(String.valueOf(Math.log(Double.parseDouble(screen.getText()))));
                break;
            case "x^2":
                screen.setText(String.valueOf(Math.pow(Double.parseDouble(screen.getText()), 2)));
                break;
            case "x^3":
                screen.setText(String.valueOf(Math.pow(Double.parseDouble(screen.getText()), 3)));
                break;
            case "√x":
                screen.setText(String.valueOf(Math.sqrt(Double.parseDouble(screen.getText()))));
                break;
            case "∛x":
                screen.setText(String.valueOf(Math.cbrt(Double.parseDouble(screen.getText()))));
                break;
            case "x!":
                int num = Integer.parseInt(screen.getText());
                screen.setText(String.valueOf(factorial(num)));
                break;
            case "e":
                screen.setText(String.valueOf(Math.E));
                break;
            case "π":
                screen.setText(String.valueOf(Math.PI));
                break;
            case "|x|":
                screen.setText(String.valueOf(Math.abs(Double.parseDouble(screen.getText()))));
                break;
            case "1/x":
                screen.setText(String.valueOf(1 / Double.parseDouble(screen.getText())));
                break;
            case "2^x":
                screen.setText(String.valueOf(Math.pow(2, Double.parseDouble(screen.getText()))));
                break;
            default:
                break;
            case "e^x":
                screen.setText(String.valueOf(Math.exp(Double.parseDouble(screen.getText()))));
                break;
            case "10^x":
                screen.setText(String.valueOf(Math.pow(10, Double.parseDouble(screen.getText()))));
                break;

        }
    }

    private int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}
