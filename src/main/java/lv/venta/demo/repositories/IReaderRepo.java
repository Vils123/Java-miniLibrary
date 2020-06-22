package lv.venta.demo.repositories;



import org.springframework.data.repository.CrudRepository;


import lv.venta.demo.models.Reader;

public interface IReaderRepo extends CrudRepository<Reader,Integer>{

    Reader findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByPassword(String password);
    //Reader isBlacklisted();

}