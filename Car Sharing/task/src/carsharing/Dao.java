package carsharing;

import carsharing.Model.Company;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    List<T> getAll();
    boolean create(T obj);

    boolean delete(long id);
    boolean update(T obj);

    Optional<T> getByName(String name);
}
