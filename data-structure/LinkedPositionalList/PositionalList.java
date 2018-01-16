public interface PositionalList<E> implements Position<E>{
	int size();
	boolean isEmpty();

	Position<E> first();
	Position<E> last();
	Position<E> before(Position<E> p) throws IllegalArgumentException;
	Position<E> after(Position<E> p) throws IllegalArgumentException;

	Position<E> addFirst(E e);
	Position<E> addLast(E e);
	Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException;
	Positoin<E> addAfter(Position<E> p, E e) throws IllegalArgumentException;

	E set(Position<E> p, E e) throws IllegalArgumentException;
	E remove(Position<E> p) throws IllegalArgumentException;
}