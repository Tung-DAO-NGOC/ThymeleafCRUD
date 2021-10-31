package tung.daongoc.peoplelist_part08.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import lombok.AllArgsConstructor;
import tung.daongoc.peoplelist_part08.person.PersonEntity;

@Repository
@AllArgsConstructor
public class personDAO implements DAO<PersonEntity> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String TABLENAME = "person";
    private final RowMapper<PersonEntity> rowMapper = (resultSet, rowNum) -> {
        PersonEntity personEntity = PersonEntity.builder()
                .setId(resultSet.getLong("id"))
                .setFirstName(resultSet.getString("firstName"))
                .setLastName(resultSet.getString("lastName"))
                .setEmail(resultSet.getString("email"))
                .setGender(resultSet.getString("gender"))
                .setAge(resultSet.getInt("age"))
                .setJob(resultSet.getString("job"))
                .setAvatar(resultSet.getBytes("avatar"))
                .build();
        return personEntity;
    };

    @Override
    public List<PersonEntity> list() {
        String sql = "SELECT * FROM " + TABLENAME;
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public java.util.List<PersonEntity> list(Integer limit, Integer offset) {
        String sql = "SELECT * FROM " + TABLENAME + " LIMIT ?, ?";
        return jdbcTemplate.query(sql, rowMapper, offset, limit);
    }

    @Override
    public PersonEntity getID(Long id) throws DataAccessException {
        String sql = "SELECT * FROM " + TABLENAME + " WHERE id = ?";
        PersonEntity personEntity = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return personEntity;
    }

    @Override
    public void update(Long id, PersonEntity object) {
        String sql =
                "UPDATE " + TABLENAME
                        + " SET firstName = ?, lastName = ?, email = ?, gender = ?, age = ?, job = ?, avatar = ? WHERE id = ?";
        jdbcTemplate.update(sql, object.getFirstName(), object.getLastName(),
                object.getEmail(), object.getGender(), object.getAge(), object.getJob(),
                object.getAvatar(), id);
    }

    @Override
    public void add(PersonEntity object) {
        String sql =
                "INSERT INTO " + TABLENAME
                        + " (firstName,lastName,email,gender,age,job,avatar) values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, object.getFirstName(), object.getLastName(),
                object.getEmail(), object.getGender(), object.getAge(), object.getJob(),
                object.getAvatar());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM " + TABLENAME + " WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
