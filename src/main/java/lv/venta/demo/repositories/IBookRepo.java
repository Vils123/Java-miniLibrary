package lv.venta.demo.repositories;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import lv.venta.demo.enums.Condition;
import lv.venta.demo.enums.Genre;
import lv.venta.demo.models.Book;

public interface IBookRepo extends CrudRepository <Book,Integer> {

    Book findById(int id);
    
    ArrayList<Book> findByIsbn(String isbn);
    ArrayList<Book> findByTitle(String title);
    ArrayList<Book> findByCondition (Condition condition);
    ArrayList<Book> findByTitleAndCondition(String title, Condition condition);
    ArrayList<Book> findByReturnDate(Date date);
    ArrayList<Book> findAllByTitle(String title);
    ArrayList<Book> findAllByIsbn(String isbn);
    ArrayList<Book> findByGenre (Genre genre);
    boolean existsByTitle(String title);
    boolean existsByIsbn(String isbn);
    
    // prob need something more



}