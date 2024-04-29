package app.dao;

import java.util.List;

public interface DAO<T> {

    int insert(T t);

    T get(int id);

    List<T> getAll();

    int update(T t, int id);

    int delete(T t, int id);
}
