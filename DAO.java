package messenger_project;
import java.util.*;
public interface DAO <T> {
	public List<T> getAll();
	   public T getFiltered(String key);
	   public void remove(String key);
	   public void add(T data);
}
