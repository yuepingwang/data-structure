import java.util.Scanner;

public class Program{
	public static void main(String[] args) {
		//parse input string to char array
		Scanner inputString = new Scanner(System.in);
		System.out.println("Please input the prefix expression: ");
		// String prefixString = inputString.nextLine().replaceAll("\\s","");
		String inf = inputString.nextLine();
		//instantiate an object of the converter class
		Converter converter = new Converter();
		//turn infix to postfix
		String postf = converter.toPostFix(inf);

		Calculator ca = new Calculator();

		double result = ca.calculate(postf);

		System.out.println("The post fix expression is: "+postf);
		System.out.println("The result is: "+result);
		//add operator "^" to evaluate as power
	}
}