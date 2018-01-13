import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E> {
	//default capacity
	public static final int CAPACITY = 16;

	//Array of List elements
	private E[] data;

	private int size = 0;

	public ArrayList(){
		this(CAPACITY);
	}

	public ArrayList(int capacity){
		data = (E[]) new Object[capacity];
	}

	public int getSize(){
		return size;
	}

	public boolean isEmpty(){
		return size == 0;
	}

	public E get(int i) throws IndexOutOfBoundsException{
		checkIndex(i,size);
		return data[i];
	}

	public E set(int i, E e) throws IndexOutOfBoundsException{
		checkIndex(i, size);
		E temp = data[i];
		data[i] = e;
		return temp;
	}

	//add new element to the end of the ArrayList
	public void append(E e) throws IndexOutOfBoundsException{
		if (size == data.length)
			resize(2*data.length);
		data[size]= e;
		size++;
	}
	public void insert(int i, E e) throws IndexOutOfBoundsException{
		checkIndex(i, size + 1);
		if (size == data.length)//double array size if data exceeds current capacity
			resize(2*data.length);
		for (int j = size -1; j>=i; j--)
			data[j+1] = data[j];
		data[i]=e;
		size++;
	}

	public E remove(int i){
		checkIndex(i, size);
		E temp = data[i];
		for(int j = i; j<size-1;j++){
			data[j]=data[j+1];
		}
		data[size-1]=null; //make sure element is cleared
		size--;
		return temp;
	}

	protected void checkIndex(int i, int n) throws IndexOutOfBoundsException{
		if(i < 0 || i >= n){
			throw new IndexOutOfBoundsException("Illegal index: "+i);
		}
	}

	protected void resize(int capacity){
		E[] temp = (E[]) new Object[capacity];
		for (int i = 0; i< size; i++){
			temp[i] = data[i];
		}
		data = temp;
	}

	//Nested ArrayIterator class
	private class ArrayIterator implements Iterator<E>{
		//index of the next element to report
		private int j = 0;
		private boolean removable = false;

		public boolean hasNext(){
			return j<size;
		}

		public E next() throws NoSuchElementException {
			if (j == size) throw new NoSuchElementException("No next element");
			removable = true;
			return data[j++];
		}
		//remove the most recently returned element by this.next()
		//throws IllegalStateException if next() hasn't been called, or remove() is recently called
		public void remove() throws IllegalStateException{
			if(!removable) return;
			for (int k = j; k< size -1; k++){
				data[k] = data[k+1];
				data[size-1]=null;
				size --;
			}
		}
	}//end of nested ArrayIterator class

	@Override
	public Iterator<E> iterator() {
		return new ArrayIterator();
	}

	public String toString(){
		StringBuilder sb = new StringBuilder("(");
		for (int j = 0; j< size; j++){
			if ( j>0 )
				sb.append(", ");
			sb.append(data[j]);
		}
		sb.append(")");
		return sb.toString();
	}

}





