package lv.venta.demo.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.demo.models.Book;
import lv.venta.demo.models.Reader;

public interface IReaderRepo extends CrudRepository<Reader,Integer>{

    Reader findByUsername(String username);
    ArrayList<Book> findByCurrentBooks(String currentBooks);
    ArrayList<Book> findByAllBooks(String allBooks);

}