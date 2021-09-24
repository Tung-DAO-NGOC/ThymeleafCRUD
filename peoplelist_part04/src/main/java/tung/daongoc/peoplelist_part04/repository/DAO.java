package tung.daongoc.peoplelist_part04.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface DAO<T> {
    public List<T> getAll();

    public T getByID(int id);

    public void addObject(T newObject);

    public void replaceObject(int id, T newObject);

    public void deleteByID(int id);

}
