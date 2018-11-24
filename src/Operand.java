/*
 * Project: Project5
 * Author: Alex Zeigler
 * Creation date: 11/8/18
 * Completion time: 3 hours
 *
 * Honor Code: I pledge that this program represents my
 *   own program code.
 */

import java.io.File;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

/**
 * The purpose of this class is to demonstrate an understanding of the Stack class by using a Post Fix Expression
 * to calculate a value. A post fix expression can be defined as an expression who's operands appear before the
 * operators. To receive the expression to perform the calculation on, the program will search for a file
 * called "expression.txt" and read it from that file. An example of a post fix expression:
 *  	3 4 5 + *
 *  In order to solve this equation the this class reads the expression left to right. If the character the program
 *  is on is a character, that character gets pushed to the stack. If the character is an operator, the program first
 *  attempts to pop the last two values from the stack. If the program runs into an EmptyStackException, the expression
 *  is not in a valid order. If it does not run into this error, the program will pop the last two values on the stack
 *  and perform the operation on those two values and pushes the result to the stack. In this case, the stack before
 *  it reaches the first operator will look like: 3, 4, 5. After the first operator: 3, 9. After the second operator:
 *  27. The program will then print the result.
 *
 *  If the file has spaces in between each character, it will not cause errors in this program because the program
 *  handles spaces and discards them.
 *
 *  Several conditions must be met in order to execute:
 *  	1. The number of operators must be the number of operands - 1
 *  	2. The expression must be in a valid order
 *  	3. the file must exist
 */
public class Operand{
	
	/**
	 * Entry point to program
	 * @param args args
	 */
	public static void main(String[] args){
		new Operand().run();
	}
	
	/**
	 * Runs the program.
	 * Inside this method, the validity of the file, and expression are checked and calculation is called.
	 * Two scanner objects are created of exactly the same type. one is called "scanner" and the other is passed into
	 * a method as a parameter. This is because the expression is scanned twice. The first time is to check the validity
	 * of the expression. The second performs the calculation. If the same scanner object is used, the calculation will
	 * never have any values to calculate.
	 */
	private void run(){
		File file = new File("expression.txt");
		Stack<Double> characters = new Stack<>();
		Scanner scanner;
		try{
			scanner = new Scanner(file).useDelimiter(" ");
			if (validate(new Scanner(file).useDelimiter(" "))){
				while (scanner.hasNext()){
					String str = scanner.next();
					while (str.isEmpty())
						str = scanner.next();
					parseInput(str, characters);
				}
				for (double i : characters){
					print(i);
				}
				
			}
		}catch (IOException e){
			print("Invalid file.");
		}
		
	}
	
	/**
	 * Checks the validity of the expression. This method takes a scanner object as a parameter. It uses that scanner
	 * to scan through the expression and count the number of Operators and Operands. The number of operators must be
	 * one less than the number of operands. If this condition is not met, the calculation will not execute.
	 * @param scanner A scanner object that contains the the file information
	 * @return true if the expression is valid and false if not valid.
	 */
	private boolean validate(Scanner scanner){
		int digitCount = 0, opCount = 0;
		while (scanner.hasNext()){
			String input = scanner.next();
			while (input.isEmpty())
				input = scanner.next();
			try{
				if (Character.isDigit(input.toCharArray()[0])){
					digitCount++;
				}
			} catch (Exception ignored){
			}
			if (input.contains("+") || input.contains("-") || input.contains("/") || input.contains("*")){
				opCount++;
			}
		}
		if(!(digitCount - 1 == opCount)){
			if(digitCount > opCount){
				print("Not enough operands");
			}else {
				print("Not enough numbers");
			}
		}
		return digitCount - 1 == opCount;
	}
	
	/**
	 * This method performs the calculations. First it checks if the input is an operator. If it is, it will enter a
	 * switch case and determine which operation to perform. Based on that case, it calls the corresponding function
	 * for the arithmetic. If that condition is not met, the program will push the value to the stack as it is assumed
	 * to be a double. The reason this assumption does not need to be caught with a catch for InputMismatchException is
	 * because of the validate() method. Any symbol that is not an operator or a number is not counted and this method
	 * does not execute.
	 * @param input the string of the value to perform the calculation on.
	 * @param characters The stack of characters.
	 */
	private void parseInput(String input, Stack<Double> characters){
		if (input.equals("+") || input.equals("-") || input.equals("/") || input.equals("*")){
			char c = input.toCharArray()[0];
			try{
				switch (c){
					case '+':
						characters.push(addition(characters.pop(),characters.pop()));
						break;
					case '-':
						characters.push(subtraction(characters.pop(),characters.pop()));
						break;
					case '/':
						characters.push(division(characters.pop(),characters.pop()));
						characters.peek();
						break;
					case '*':
						characters.push(multiplication(characters.pop(),characters.pop()));
						break;
				}
			} catch (EmptyStackException e){
				print("The expression is not in a valid order.");
				System.exit(1);
			}
		}else{
			characters.push(Double.parseDouble(input));
		}
	}
	
	/**
	 * Performs addition on x and y
	 * @param x first value
	 * @param y second value
	 * @return the result of the addition
	 */
	private double addition(double x, double y){
		return x + y;
	}
	/**
	 * Performs subtraction on x and y
	 * @param x first value
	 * @param y second value
	 * @return the result of the subtraction
	 */
	private double subtraction(double x, double y){
		return x - y;
	}
	/**
	 * Performs division on x and y. If attempting to divide by zero, the method will return zero so the program does
	 * not crash.
	 * @param x first value
	 * @param y second value
	 * @return the result of the division
	 */
	private double division(double x, double y){
		if(y == 0)
			return 0;
		return x / y;
	}
	/**
	 * Performs multiplication on x and y
	 * @param x first value
	 * @param y second value
	 * @return the result of the multiplication
	 */
	private double multiplication(double x, double y){
		return x * y;
	}
	
	/**
	 * Shortcut to the System.out.print() method.
	 * @param obj object to print
	 */
	private void print(Object obj){
	    System.out.print(obj);
	}
}

