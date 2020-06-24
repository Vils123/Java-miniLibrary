package lv.venta.demo.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;


import lv.venta.demo.models.Reader;

public interface IReaderRepo extends CrudRepository<Reader,Integer>{

    Reader findByUsername(String username);
    Reader findById(int id);
    boolean existsByUsername(String username);
    boolean existsByPassword(String password);
    ArrayList<Reader> findAll();
    ArrayList<Reader> findAllByBlacklisted(boolean isBlacklistd);
    
    
}