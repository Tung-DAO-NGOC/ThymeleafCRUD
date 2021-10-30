package tung.daongoc.peoplelist_part08.services;

import java.util.List;

public interface iService<T> {
    List<T> getList();

    List<T> getList(Integer limit, Integer offset);

    T getByID(Long id);

    void update(Long id, T object);
}
