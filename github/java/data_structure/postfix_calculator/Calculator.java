import java.util.ArrayList;
import java.util.List;

public class Calculator{
	double result = 0;
	public double calculate(String in){

		ParserHelper ph = new ParserHelper();
		ArrayList<String> al = (ArrayList<String>)ph.parse(in.toCharArray());
		ArrayStack as = new ArrayStack();

		for (int i = 0; i<al.size();i++){
			String next = (String)al.get(i);
			if (Character.isDigit(next.charAt(0)))
				as.push(next);
			else {
				double o2 = Double.parseDouble(as.pop());
				double o1 = Double.parseDouble(as.pop());
				double r = calc(next,o1,o2);
				as.push(r+"");
			}
		}
		result = Double.parseDouble(as.pop());
		return result;
	}

	private double calc(String op, double o1, double o2){
		switch (op){
			case "+":
				return o1+o2;
			// break;
			case "-":
				return o1-o2;
			// break;
			case "*":
				return o1*o2;
			// break;
			case "/":
				return o1/o2;
			//break;
			case "^":
				return Math.pow(o1,o2);
			//break;
			default:
				return 0;
		}
	}
}