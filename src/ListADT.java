
public interface ListADT <T> {

	public int size();
	public boolean isEmpty();

	public int indexOf(T findObject);
	public T get(int index);
	
	public void add(T newObject);
	
}
