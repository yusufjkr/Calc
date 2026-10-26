package calc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calc extends JFrame implements ActionListener {

    private JTextField display;

    private double num1 = 0;
    private double num2 = 0;
    private double result = 0;

    private char operator = '\0';

    public Calc() {

        // Window
        setTitle("Calculator");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Display
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);

        add(display, BorderLayout.NORTH);

        // Button panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "←"
        };

        for (String text : buttons) {

            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));

            button.addActionListener(this);

            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        // Numbers and decimal point
        if ((command.charAt(0) >= '0' && command.charAt(0) <= '9')
                || command.equals(".")) {

            // Prevent multiple decimal points
            if (command.equals(".") && display.getText().contains(".")) {
                return;
            }

            display.setText(display.getText() + command);
        }

        // Clear
        else if (command.equals("C")) {

            display.setText("");

            num1 = 0;
            num2 = 0;
            result = 0;
            operator = '\0';
        }

        // Backspace
        else if (command.equals("←")) {

            String text = display.getText();

            if (!text.isEmpty()) {
                display.setText(
                    text.substring(0, text.length() - 1)
                );
            }
        }

        // Operators
        else if (command.equals("+")
                || command.equals("-")
                || command.equals("*")
                || command.equals("/")) {

            if (!display.getText().isEmpty()) {

                num1 = Double.parseDouble(display.getText());

                operator = command.charAt(0);

                display.setText("");
            }
        }

        // Equal
        else if (command.equals("=")) {

            if (!display.getText().isEmpty()
                    && operator != '\0') {

                num2 = Double.parseDouble(display.getText());

                switch (operator) {

                    case '+':
                        result = num1 + num2;
                        break;

                    case '-':
                        result = num1 - num2;
                        break;

                    case '*':
                        result = num1 * num2;
                        break;

                    case '/':

                        if (num2 == 0) {
                            display.setText("Cannot divide by 0");
                            return;
                        }

                        result = num1 / num2;
                        break;

                    default:
                        return;
                }

                display.setText(String.valueOf(result));

                operator = '\0';
            }
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Calc();
        });
    }
}