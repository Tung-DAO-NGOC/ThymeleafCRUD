package tung.daongoc.peoplelist_part08.repository;

import java.util.List;

public interface DAO<T> {
    List<T> list();

    List<T> list(Integer limit, Integer offset);

    T getID(Long id);

    void update(Long id, T object);
}
