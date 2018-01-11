public class Tree{

	private Node root;

	public Tree (){
		root = null;
	}

	private void leftChild(Node bt, Character c){
		bt.left = new Node(c);
	}

	private void rightChild(Node bt, Character c){
		bt.right = new Node(c);
	}

	public void makeSimpleTree(){
		root = new Node('+');
		leftChild(root, '*');
		rightChild(root, '5');
		leftChild(root.left, '3');
		rightChild(root.left, '4');
	}

	public void printInfix(){
		infix(root);
		System.out.println();
	}

	private void infix(Node bt){//using INORDER TRAVERSAL
		if(bt.left != null){
			System.out.print("(");
			infix(bt.left);
		}
		System.out.print(bt);//print the element at root
		if(bt.right != null){
			infix(bt.right);
			System.out.print(")");
		}
	}

	public void printPostfix(){
		postfix(root);
		System.out.println();
	}

	private void postfix(Node bt){
		if(bt != null){
			postfix(bt.left);
			postfix(bt.right);
			System.out.print(bt);
		}
	}

	public int getEvaluation(){
		return evaluate(root);
	}

	private int evaluate(Node bt){
		if(bt.left == null)//found operand
			return bt.element-'0';
		else{
			int left = evaluate(bt.left);
			int right = evaluate(bt.right);
			switch(((Character)bt.element).charValue()){
				case '+':
				return left+right;
				case '-':
				return left-right;
				case '/':
				return left/right;
				case '*':
				return left*right;
			}
		}
		return 0;
	}
}