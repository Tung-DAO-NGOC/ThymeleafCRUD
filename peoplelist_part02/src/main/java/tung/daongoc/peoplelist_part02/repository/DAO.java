package tung.daongoc.peoplelist_part02.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface DAO<T> {

    public List<T> readAll();
}
