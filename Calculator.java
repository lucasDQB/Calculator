/*
import java.awt.*; //Abstract Windows Toolkit, a set a Java APIs for creating graphical user interfaces (buttons, etxt, layou, images, etc.)
import java.awt.event.*; //Handling events in GUI applications (button clicks, mouse movement, etc.)
import java.util.Arrays; //Provides common operations on arrays, makes it easier to work with them
import javax.swing.*; //Used for building GUIs
import javax.swing.border.LineBorder; //Creates a border around a Swing component
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    String num1 = "0";
    String operation = null;
    String num2 = null;
    boolean isFloat1 = false;
    boolean isFloat2 = false;

    int windowWidth = 360;
    int windowHeight = 540;

    Color lightGray = new Color(212, 212, 210);
    Color eerieBlack = new Color(28, 28, 28);
    Color darkGray = new Color(80, 80, 80);
    Color vividYellow = new Color(255, 149, 0);

    String[] buttons = {"CE", "+/-", "%", "√",
                        "7", "8", "9", "÷", 
                        "4", "5", "6", "*", 
                        "1", "2", "3", "-",
                        "0", ".", "=", "+"};

    JFrame frame = new JFrame("Calculator");

    JLabel displayLabel = new JLabel(); //calculator display panel
    JPanel numbersPanel = new JPanel(); //numbers panel
    JPanel buttonsPanel = new JPanel(); //buttons panel

    public Calculator(){
        frame.setVisible(true);
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true); //user can't change the width/height of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when user clicks "x" in the top-right of the window, program will terminate
        frame.setLayout(new BorderLayout()); //can place components N, S, E, or W within the window

        displayLabel.setBackground(eerieBlack);
        displayLabel.setForeground(Color.white);
        Font calcFont = new Font("Monospaced", Font.PLAIN, 60);
        displayLabel.setFont(calcFont);
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText(num1);
        displayLabel.setOpaque(true);

        numbersPanel.setLayout(new BorderLayout());
        numbersPanel.add(displayLabel);
        frame.add(numbersPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(eerieBlack);
        frame.add(buttonsPanel);

        for(int i=0; i<buttons.length; i++){
            JButton button = new JButton();
            Font buttonFont = new Font("Monospaced", Font.PLAIN, 25);
            button.setFont(buttonFont);
            button.setText(buttons[i]);
            button.setFocusable(false);
            button.setBorder(new LineBorder(eerieBlack));

            if(buttons[i].equals("CE") || buttons[i].equals("+/-") || buttons[i].equals("%") || buttons[i].equals("√")){
                button.setBackground(lightGray);
            }
            else if("+-*÷=".indexOf(buttons[i].charAt(0)) > -1){
                button.setBackground(vividYellow);
            }
            else{
                button.setBackground(darkGray);
            }
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    selectionButtonPressed(e);
                }
            });



            frame.setVisible(true);
        }
        

    }

    private void selectionButtonPressed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        String buttonVal = button.getText();
        //System.out.print(buttonVal);
        String displayText = displayLabel.getText();
        double doubleDisplayText = Double.parseDouble(displayText);

        if(buttonVal.equals("%")){
            num1 = doubleDisplayText / 100 + "";
            displayLabel.setText(num1);
        }

        else if(buttonVal.equals("CE")){
            num1 = "0";
            operation = null;
            num2 = null;
            displayLabel.setText(num1);
        }

        else if(buttonVal.equals("+/-")){
            if(num2 == null){
                if(num1.charAt(0) == '-'){
                    num1 = num1.substring(1);
                }
                else{
                    num1 = "-" + num1;
                }
            }
            else{
                if(num2.charAt(0) == '-'){
                    num2 = num2.substring(1);
                }
                else{
                    num2 = "-" + num2;
                }
            }

            if(doubleDisplayText == (int)doubleDisplayText){
                displayLabel.setText((int)(doubleDisplayText * -1) + "");
            }
            else displayLabel.setText(doubleDisplayText * -1 + "");
        }

        else if(buttonVal.equals("√")){
            String result;
            if(doubleDisplayText < 0) result = "0";
            else{
                double d = Math.sqrt(doubleDisplayText);
                if(d == (int)d){
                    result = (int)d + "";
                }
                else result = d + "";
            }

            num1 = result;
            displayLabel.setText(result);
        }

        else if("+-*÷".indexOf(buttonVal.charAt(0)) > -1){
            displayLabel.setText("0");
            operation = buttonVal;
            //num2 = button.getText();
            //how to store num2?
        }

        else if(buttonVal.equals("=")){
            double result = 0;

            if(operation.equals("+")){
                result = Double.parseDouble(num1) + Double.parseDouble(num2);
                //displayLabel.setText(result + "");
            }
            else if(operation.equals("-")){
                result = Double.parseDouble(num1) - Double.parseDouble(num2);
                //displayLabel.setText(result + "");
            }
            else if(operation.equals("*")){
                result = Double.parseDouble(num1) * Double.parseDouble(num2);
                //displayLabel.setText(result + "");
            }
            else if(operation.equals("÷")){
                result = Double.parseDouble(num1) / Double.parseDouble(num2);
                //displayLabel.setText(result + "");
            }

            if(result == (int)result){
                num1 = (int)result + "";
                displayLabel.setText((int)result + "");
            }
            else {
                num1 = result + "";
                displayLabel.setText(result + "");
            }
            num2 = null;
            operation = null;
        }

        else if(buttonVal.equals(".")){ //NOT WORKING
            if(!isFloat1){
                if(operation == null){
                    if(displayLabel.getText().equals("0")){
                        displayLabel.setText(buttonVal);            
                        num1 = displayLabel.getText();
                    }
                    else{
                        displayLabel.setText(displayLabel.getText() + buttonVal);
                        num1 = displayLabel.getText();
                    }
                    isFloat1 = true;
                }
                else{
                    if(displayLabel.getText().equals("0")){
                        displayLabel.setText(buttonVal);            
                        num2 = displayLabel.getText();
                    }
                    else{
                        displayLabel.setText(displayLabel.getText() + buttonVal);
                        num2 = displayLabel.getText();
                    }
                    isFloat2 = true;
                }
            }
            else{

            }
        }

        else {
            if(operation == null){
                if(displayLabel.getText().equals("0")){
                    displayLabel.setText(buttonVal);            
                    num1 = displayLabel.getText();
                }
                else{
                    displayLabel.setText(displayLabel.getText() + buttonVal);
                    num1 = displayLabel.getText();
                } 
            }
            else{
                if(displayLabel.getText().equals("0")){
                    displayLabel.setText(buttonVal);            
                    num2 = displayLabel.getText();
                }
                else{
                    displayLabel.setText(displayLabel.getText() + buttonVal);
                    num2 = displayLabel.getText();
                }  
            }
             
        }

        System.out.println("num1: " + num1 + " num2: " + num2 + " operation: " + operation);
    }
}
    


