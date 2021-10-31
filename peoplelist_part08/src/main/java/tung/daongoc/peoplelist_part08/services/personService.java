package tung.daongoc.peoplelist_part08.services;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import tung.daongoc.peoplelist_part08.person.PersonEntity;
import tung.daongoc.peoplelist_part08.person.PersonModel;
import tung.daongoc.peoplelist_part08.repository.DAO;

@Service
public class personService implements iService<PersonModel> {
        @Autowired
        DAO<PersonEntity> personDAO;

        private List<PersonModel> listMappingEntityToModel(List<PersonEntity> list) {
                List<PersonModel> returnList = list.stream()
                                .map(personEntity -> PersonModel.builder()
                                                .setId(personEntity.getId())
                                                .setFirstName(personEntity.getFirstName())
                                                .setLastName(personEntity.getLastName())
                                                .setEmail(personEntity.getEmail())
                                                .setGender(personEntity.getGender())
                                                .setAge(personEntity.getAge())
                                                .setJob(personEntity.getJob())
                                                .setAvatar(personEntity.getAvatar())
                                                .build())
                                .collect(Collectors.toList());
                returnList.forEach(item -> item.setFullName());
                return returnList;
        }

        private PersonModel objectMappingEntityToModel(PersonEntity entity) {
                PersonModel personModel = PersonModel.builder()
                                .setId(entity.getId())
                                .setFirstName(entity.getFirstName())
                                .setLastName(entity.getLastName())
                                .setEmail(entity.getEmail())
                                .setGender(entity.getGender())
                                .setAge(entity.getAge())
                                .setJob(entity.getJob())
                                .setAvatar(entity.getAvatar())
                                .build();
                personModel.setFullName();
                return personModel;
        }

        private PersonEntity objectMappingModelToEntity(PersonModel model) {
                PersonEntity personEntity = PersonEntity.builder()
                                .setId(model.getId())
                                .setFirstName(model.getFirstName())
                                .setLastName(model.getLastName())
                                .setEmail(model.getEmail())
                                .setGender(model.getGender())
                                .setJob(model.getJob())
                                .setAge(model.getAge())
                                .setAvatar(model.getAvatar())
                                .build();
                return personEntity;
        }

        @Override
        public List<PersonModel> getList() {
                return listMappingEntityToModel(personDAO.list());
        }

        @Override
        public List<PersonModel> getList(Integer limit, Integer offset) {
                return listMappingEntityToModel(personDAO.list(limit, offset));
        }


        @Override
        public PersonModel getByID(Long id) throws DataAccessException {
                PersonModel personModel = objectMappingEntityToModel(personDAO.getID(id));
                if (personModel.getAvatar() != null) {
                        personModel.setAvatarBase64(Base64.getEncoder().encodeToString(
                                        personModel.getAvatar()));
                }
                return personModel;
        }

        @Override
        public void update(Long id, PersonModel model) {
                if (model.getAvatar() != null && model.getAvatar().length == 0) {
                        model.setAvatar(null);
                }
                personDAO.update(id, objectMappingModelToEntity(model));
        }

        @Override
        public void add(PersonModel personModel) {
                if (personModel.getAvatar().length == 0) {
                        personModel.setAvatar(null);
                }
                personDAO.add(objectMappingModelToEntity(personModel));
        }

        @Override
        public void delete(Long id) {
                personDAO.delete(id);
        }



}
