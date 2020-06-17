package lv.venta.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lv.venta.demo.enums.Genre;
import lv.venta.demo.models.Author;

@Repository
public interface IAuthorRepo extends CrudRepository<Author,Integer> {
    
	Author findByNameAndSurname(String name, String surname);
    Author findByCountryOfOrigin (String origin);
    Author findByLiteratureStyle (String literatureStyle);
    


}