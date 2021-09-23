package tung.daongoc.peoplelist_part03.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface DAO<T> {
    public List<T> getAll();

    /**
     * Get person data by ID
     * @param id ID of the selected person
     * @return Optional form of Person 
     */
    public T getById(int id);
}
