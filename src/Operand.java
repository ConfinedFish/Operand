import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Operand{
	private Stack<Double> characters = new Stack<>();
	public static void main(String[] args){
		new Operand().run();
	}
	private void run(){
		File file = new File("expression.txt");
		Scanner scanner;
		try{
			scanner = new Scanner(file).useDelimiter(" ");
			if (validate(new Scanner(file).useDelimiter(" "))){
				while (scanner.hasNext()){
					String str = scanner.next();
					while (str.isEmpty())
						str = scanner.next();
					parseInput(str);
				}
			}
		}catch (IOException e){
			print("Invalid file.");
		}
		for (double i : characters){
			print(i);
		}
	}
	private boolean validate(Scanner scanner){
		int digitCount = 0, opCount = 0;
		while (scanner.hasNext()){
			String input = scanner.next();
			while (input.isEmpty())
				input = scanner.next();
			try {
				if(Character.isDigit(input.toCharArray()[0])){
					digitCount++;
				}
			} catch (Exception ignored){ }
			if (input.contains("+") || input.contains("-") || input.contains("/") || input.contains("*")){
				opCount++;
			}
		}
		boolean check = digitCount - 1 == opCount;
		if(!check){
			if(digitCount > opCount){
				print("Not enough operands");
			}else {
				print("Not enough numbers");
			}
		}
		return check;
	}
	private void parseInput(String input){
		if (input.contains("+") || input.contains("-") || input.contains("/") || input.contains("*")){
			char c = input.toCharArray()[0];
			switch (c){
				case '+':
					characters.push(addition(characters.pop(),characters.pop()));
					break;
				case '-':
					characters.push(subtraction(characters.pop(),characters.pop()));
					break;
				case '/':
					characters.push(division(characters.pop(),characters.pop()));
					break;
				case '*':
					characters.push(multiplication(characters.pop(),characters.pop()));
					break;
			}
		}else{
			characters.push(Double.parseDouble(input));
		}
	}
	private double addition(double x, double y){
		return x + y;
	}
	private double subtraction(double x, double y){
		return x - y;
	}
	private double division(double x, double y){
		if(y == 0)
			return 0;
		return x / y;
	}
	private double multiplication(double x, double y){
		return x * y;
	}
	private void print(Object obj){
	    System.out.print(obj);
	}
}

