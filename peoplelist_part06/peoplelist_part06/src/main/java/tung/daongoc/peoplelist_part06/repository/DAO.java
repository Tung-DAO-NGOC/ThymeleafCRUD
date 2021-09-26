package tung.daongoc.peoplelist_part06.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface DAO<T> {
    public List<T> getAll();

    public T getByName(String name);

    public void add(T newObject);
}
