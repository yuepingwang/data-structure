public class Node{
	public Character element;
	public Node left;
	public Node right;

	public Node (Character c){
		this (c, null, null);
	}

	public Node (Character c, Node l, Node r){
		element = c;
		left = l;
		right = r;
	}

	public String toString(){
		return ""+element;
	}
}