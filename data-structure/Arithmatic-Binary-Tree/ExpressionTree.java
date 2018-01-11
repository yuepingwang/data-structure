public class ExpressionTree{
	public static void main(String [] args){
		Tree exTree = new Tree();
		exTree.makeSimpleTree();
		System.out.println("The infix expression(inorder traversal) is: ");
		exTree.printInfix();
		System.out.println("The postfix expression(postorder traversal) is: ");
		exTree.printPostfix();
		System.out.println("The expression evaluates to: " + exTree.getEvaluation());
	}
}