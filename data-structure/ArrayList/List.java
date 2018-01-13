import java.util.Iterator;

public interface List<E> extends Iterable<E>{
	int getSize();

	boolean isEmpty();

	E get(int i) throws IndexOutOfBoundsException;

	//replaces list element at index i
	//returns the original element at index i
	E set(int i, E e) throws IndexOutOfBoundsException;

	//add element at index i, shift subsequent elemenst one position further
	void insert(int i, E e) throws IndexOutOfBoundsException;

	//remove and return element at index i
	//shift all subsequent elements one postion forward
	E remove(int i) throws IndexOutOfBoundsException;

	Iterator<E> iterator();
}