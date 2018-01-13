import java.util.*;

public class Program {
	public static void main(String args[]){
		ArrayList<String> obj = new ArrayList<String>();

		obj.append("Zero");
		obj.append("One");
		obj.append("Two");
		obj.append("Three");

		System.out.println("Current ArrayList instance contains: " + obj);

		obj.insert(1,"First-Insert");
		obj.insert(3,"Second-Insert");
		obj.insert(5,"Third-Insert");

		System.out.println("After Insertion: " + obj);

		obj.remove(1);
		obj.remove(1);
		obj.remove(obj.getSize()-1);

		System.out.println("After Removal: " + obj);

	}
}