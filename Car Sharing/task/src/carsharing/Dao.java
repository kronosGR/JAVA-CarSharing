package carsharing;

import java.util.List;

public interface Dao<T> {
    List<T> getAll();
    boolean create(T object);
}
