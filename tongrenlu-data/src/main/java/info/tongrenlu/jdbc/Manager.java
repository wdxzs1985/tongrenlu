package info.tongrenlu.jdbc;

import java.util.List;

public interface Manager<T, I> {

    public List<T> findAll();

    public T findOne(I id);

    public void deleteAll();

    public void deleteOne(I id);

    public void save(T bean);

    public void insert(T bean);

    public void update(T bean);
}
