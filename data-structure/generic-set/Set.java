public class Set<T>{

	private static class Node<T>{

		private T element;

		private Node<T> prev;

		private Node<T> next;

		private Node<T> earlier;//a pointer to refer to the order in which each element is stored in the set

		private Node<T> later;//a pointer to refer to the order in which each element is stored in the set

		private int index = -1;

		public Node(T t, Node<T> p, Node<T> n){
			element = t;
			prev = p;
			next = n;
		}

		public T getElement(){
			return element;
		}

		public Node<T> getPrev(){
			return prev;
		}

		public Node<T> getNext(){
			return next;
		}

		public void setPrev(Node<T> p){
			prev = p;
		}

		public void setNext(Node<T> n){
			next = n;
		}

		public Node<T> getEarlier(){
			return earlier;
		}

		public Node<T> getLater(){
			return later;
		}

		public void setLater(Node<T> l){
			later = l;
		}

		public void setEarlier(Node<T> e){
			earlier = e;
		}
	}

	private Node<T> header; //head sentinel of set, doesn't change

	private Node<T> trailer; //tail sentinel of set, doesn't change

	private Node<T> latest;//store the last element added to the set

	private int sz = 0;

	public Set() {
		header = new Node<>(null, null, null);
		trailer = new Node<>(null, header, null);
		header.setNext(trailer);
		latest = header;
	}

	public Set (T[] values){
		header = new Node<>(null, null, null);
		trailer = new Node<>(null, header, null);
		header.setNext(trailer);
		latest = header;
		for (int i = 0; i< values.length; i++){
			this.add(values[i]);
		}
	}

	public int size(){
		return sz;//return current total elements in set
	}

	public boolean isEmpty(){
		return sz == 0;
	}

	public T remove(T t){
		if(isEmpty())
			return null;
		Node<T> walk = header;

		while(walk.getNext().getElement() != null){
			walk = walk.getNext();
			T el = walk.getElement();
			int compare = compareEl(el,t);
			if (compare == 0){
				removeNode(walk);// if equal, remove node
				return t;
			}
			if (compare >0)
				return null;//if cur element is greater, there's no equal element in set
			//else compare with next node
		}
		return null;
	}

	public void add(T t){
		//loop through list to find the correct position (by ascending order)
		//if set is empty, add node before the trailer
		if (isEmpty()){
			addNodeBefore(trailer,t);
			return;
		}
		Node<T> walk = header;
		while(walk.getNext().getElement() != null){
			walk = walk.getNext();
			T el = walk.getElement();
			int compare = compareEl(el,t);
			if (compare == 0)// if equal, do nothing
				return;
			if (compare <0){// if cur element is greater than new element (t), add new Node between cur node and prev node
				addNodeBefore(walk,t);
				return;
			}
			//else, t is greater than cur node element, compare with next node
		}
		addNodeBefore(trailer,t);// if no greater element is found, add new node to end of set
		return;
	}
	
	public boolean contains(T t){

		if(isEmpty()) 
			return false;

		Node<T> walk = header;

		while(walk.getNext().getElement() != null){
			walk = walk.getNext();
			T el = walk.getElement();
			int compare = compareEl(el,t);
			if (compare == 0)
				return true;
			if (compare >0)
				return true;
		}
		return false;
	}

	private void addNodeBefore(Node<T> node, T t){
		Node<T> curPrev = node.getPrev();
		Node<T> newNode = new Node<T>(t, curPrev, node);
		curPrev.setNext(newNode);
		node.setPrev(newNode);
		//update node orders
		newNode.setEarlier(latest);
		latest.setLater(newNode);
		latest = newNode;
		sz++;
	}
	private void removeNode(Node<T> node){
		//update node orders
		if(node == latest){
			latest = latest.getEarlier();
			latest.setLater(null);
		}
		else{
			Node<T> curEarl = node.getEarlier();
			Node<T> curLate = node.getLater();
			curEarl.setLater(curLate);
			curLate.setEarlier(curEarl);
		}
		//remove node
		Node<T> curPrev = node.getPrev();
		Node<T> curNext = node.getNext();
		curPrev.setNext(curNext);
		curNext.setPrev(curPrev);
		sz--;
	}

	private int compareEl(T t0, T t1){
		if (t0 instanceof String){
			String s0 = (String)t0;
			String s1 = (String)t1;
			return s0.compareTo(s1);
		}
		else if (t0 instanceof Integer){
			int i0 = (Integer)t0;
			int i1 = (Integer)t1;
			if (i0>i1)
				return 1;
			if (i0<i1)
				return -1;
			return 0;
		}
		else return 0;
	}
	
	public String toString() {
		if(isEmpty())
			return "Set is empty!";
	    StringBuilder sb = new StringBuilder("");
	    // TO PRINT IN INPUT ORDER:
	    Node<T> walk = header;
	    while (walk.getLater() != null) {
	    	walk = walk.getLater();
	      	sb.append(walk.getElement());
	      	if (walk.getLater() != null)
	        	sb.append(", ");
	    }
    // TO PRINT IN ELEMENT DESCENDING ORDER:
    // Node<T> walk = trailer.getPrev();
    // while (walk != header) {
    //   sb.append(walk.getElement());
    //   walk = walk.getPrev();
    //   if (walk != header)
    //     sb.append(", ");
    // }
	// TO PRINT IN ELEMENT ASCENDING ORDER:
    // Node<T> walk = header.getNext();
    // while (walk != trailer) {
    //   sb.append(walk.getElement());
    //   walk = walk.getNext();
    //   if (walk != header)
    //     sb.append(", ");
    // }
    return sb.toString();
  }

}