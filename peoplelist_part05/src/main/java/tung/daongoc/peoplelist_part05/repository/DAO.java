package tung.daongoc.peoplelist_part05.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface DAO<T> {
    public List<T> getAll();

    public List<T> sortByKeyword(String keyword);

    public List<T> sortByOrder(List<T> list, String order);

}
