package tung.daongoc.peoplelist_part05.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface iService<T> {
    public List<T> getAll();

    public List<T> getByKeyword(String keyword);

    public List<T> sortByOrder(List<T> list, String order);

}
