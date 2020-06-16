package lv.venta.demo.repositories

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lv.venta.demo.models.Person;

@Repository
public interface IPersonRepo extends CrudRepository<Person,Integer>

ArrayList <Person> findByNameAndSurname(String name, String surname);
Person findById(int id);




}
