package tung.daongoc.peoplelist_part01.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class DAO<T> {
    List<T> listPerson = new ArrayList<T>();

    public abstract List<T> getAll();
}
