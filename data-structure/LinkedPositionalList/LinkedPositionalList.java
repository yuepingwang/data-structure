public class LinkedPositionalList<E> implements PositionalList<E>{
	private static class Node<E> implements Position<E>{
		private E element;
		private Node<E> prev;
		private Node<E> next;
		public Node(E e, Node<E> p, Node<E> n) {
			element = e;
			prev = p;
			next = n;
		}
		public E getElement() throws IllegalStateException {
			if (next == null) throw new IllegalStateException("Position no longer valid");
			return element;
		}
		public Node<E> getPrev() {
			return prev;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setElement(E e) {
			element = e;
		}
		public void setPrev(Node<E> p) {
			prev = p;
		}
		public void setNext(Node<E> n) {
			next = n;
		}
	}// end of nested Node class

	private Node<E> header;
	private Node<E> trailer;
	private int size = 0;

	public LinkedPositionalList(){
		Node<E> header = new Node(null, null, null);
		Node<E> trailer = new Node(null, header, null);
		header.setNext(trailer);
	}

	//cast Position type input to Node, allow methods of Node class to be used
	private Node<E> validate(Position<E> p) throws IllegalArgumentException {
		if(!(p instanceOf Node)) throw new IllegalArgumentException ("Invalid p");
		Node<E> node = (Node<E>)p;
		//convention for defunct node
		if (node.getNext()==null) throw new IllegalArgumentException ("p no longer in the list");
		return node;
	}

	//implicitly cast Node to Position and hide the sentinels(header, trailer) from the user
	private Position<E> position(Node<E> node){
		if(node == header || node == trailer)
			return null;
		return node;
	}

	public int size(){
		return size;
	}

	public boolean isEmpty(){
		return size == 0;
	}

	public Position<E> first(){
		return postion(header.getNext());
	}

	public Postion<E> last(){
		return position(trailer.getPrev());
	}

	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return position(node.getPrev());
	}

	public Position<E> after(Position<E> p) throws IllegalArgumentException{
		Node<E> node = validate(p);
		return position(node.getNext());
	}

	//private helper method for public mutator methods
	private Postion<E> addBetween(E e, Node<E> before, Node<E> after){
		Node<E> newNode = new Node<>(e, before, after);
		before.setNext(newNode);
		after.setPrev(newNode);
		size++;
		return Node;
	}

	public Positon<E> addFirst(E e){
		return addBetween(e, header, header.getNext());
	}

	public Position<E> addLast(E e){
		return addBetween(e, trailer.getPrev(), trailer);
	}

	public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException{
		Node<E> curNode = validate(p);
		return addBetween(e, curNode.getPrev(), curNode);
	}

	public Position<E> addAfter(Postiona<E> p, E e) throws IllegalArgumentException{
		Node<E> curNode = validate(p);
		return addBetween(e, curNode, curNode.getNext());
	}

	public E set(Positoin<E> p, E e) throws IllegalArgumentException{
		Node<E> curNode = validate(p);
		E val = curNode.getElement();
		curNode.setElement(e);
		return val;
	}

	public E remove(Position<E> p) throws IllegalArgumentException{
		Node<E> node = validate(p);
		Node<E> before = node.getPrev();
		Node<E> after = node.getNext();
		E val = node.getElement();
		before.setNext(after);
		after.setPrev(before);
		size --;
		//just to help garbage collection
		node.setElement(null);
		node.setPrev(null);
		node.setNext(null);
		return val;
	}
}





