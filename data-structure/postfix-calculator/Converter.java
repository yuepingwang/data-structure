import java.util.ArrayList;
import java.util.List;


public class Converter {

	String output = "";//string for postfix output

    public String toPostFix(String input) {
    	//init instances
    	char[] prefChars = input.toCharArray();
    	ParserHelper ph = new ParserHelper();
		ArrayList<String> al = (ArrayList<String>)ph.parse(prefChars);
    	//stack for caching operators
    	ArrayStack as = new ArrayStack();

		for (int i = 0; i<al.size();i++){
			String next = (String)al.get(i);
			if (Character.isDigit(next.charAt(0))){
				// System.out.println("a number!");
				output+= " "+next;
			}
			else {
				switch(next){
					case "+":
						// if(as.top() == "*" || as.top() == "/"){
						if(as.isEmpty() || isHigher("+",as.top()))
							as.push("+");
						else{
							while(!as.isEmpty() && as.top()!="(")//if previous op has higher precidence, append it to postfix string (output)
								output+= " "+as.pop();
							as.push("+");
						}
					break;
					case "-":
						if(as.isEmpty() || isHigher("-",as.top()))
							as.push("-");
						else{
							while(!as.isEmpty() && as.top()!="(")//if previous op has higher precidence, append it to postfix string (output)
								output+= " "+as.pop();
							as.push("-");
						}
					break;
					case "*":
						if(as.isEmpty() || isHigher("*",as.top()))
							as.push("*");
						else{
							while(!as.isEmpty() && !isHigher("*",as.top()) && as.top() != "(")
								output+= " "+as.pop();
							as.push("*");
						}
					break;
					case "/":
						if(as.isEmpty() || isHigher("/",as.top()))
							as.push("/");
						else{
							while(!as.isEmpty() && !isHigher("/",as.top()) && as.top() != "(")
								output+= " "+as.pop();
							as.push("/");
						};
						break;
					case "(":
						as.push("(");
					break;
					case ")"://pop the stack until reaching "("
						while(as.top() != "(")
							output+= " "+as.pop();
						as.removeTop();
					break;
					case "^":
						if(as.isEmpty() || isHigher("^",as.top()))
							as.push("^");
						else{
							while(!as.isEmpty() && !isHigher("^",as.top()) && as.top() != "(")
								output+= " "+as.pop();
							as.push("^");	
						}
					break;
					default:
					break;
				}
			}
		}//end of reading prefix expression
		while (!as.isEmpty()){
			output+= " "+as.pop();
		}
		//reverse operands before and after "/" operator
    	return output;
    }

    private boolean isHigher(String e, String top){
    	if(top =="(")
    		return true;
    	if(e=="+" ||e=="-")
    		return false;
    	if(e=="*" ||e=="/")
    		return (top != "*" && top != "/" && top !="^");
    	if(e=="^")
    		return(top !="^");
    	else return true;
    }
}