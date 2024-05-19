package app.dao;

import java.util.List;

public interface DAO<T> {

    int insert(T t);

    T get(String id);

    List<T> getAllNotDeleted();

    int update(T t);

    int delete(T t);
}
