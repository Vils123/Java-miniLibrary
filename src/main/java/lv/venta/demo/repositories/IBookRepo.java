package lv.venta.demo.repositories;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import lv.venta.demo.enums.Condition;
import lv.venta.demo.models.Author;
import lv.venta.demo.models.Book;
import lv.venta.demo.models.Reader;

public interface IBookRepo extends CrudRepository <Book,Integer> {
    Book findById(int id);
    ArrayList<Book> findByIsbn(String isbn);
    ArrayList<Book> findByTitle(String title);
    ArrayList<Book> findByCondition (Condition condition);
    ArrayList<Book> findByTitleAndCondition(String title, Condition condition);
    ArrayList<Book> findByReturnDate(Date date);
    boolean existsByTitle(String title);
    boolean existsByIsbn(String isbn);
    
    // prob need something more



}