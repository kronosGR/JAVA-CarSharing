package carsharing;

import carsharing.Model.Company;

import java.util.List;

public interface Dao<T> {
    List<T> getAll();
    boolean create(T obj);

}
