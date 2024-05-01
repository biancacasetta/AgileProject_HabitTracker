package app.dao;

import java.util.List;

public interface DAO<T> {

    int insert(T t);

    T get(String id);

    List<T> getAll();

    int update(T t);

    int delete(T t);
}
