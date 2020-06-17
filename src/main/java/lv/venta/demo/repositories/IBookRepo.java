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
    Book findByIsbn(String isbn);
    Book findByTitle(String title);
    ArrayList<Book> findByAuthor(Author author);  
    Book findByCondition (Condition condition);
    Book findByTitleAndCondition(String title, Condition condition);
    Book findByReturnDate(Date date);
    ArrayList<Book> findByReader(Reader reader);
    boolean existsByTitle(String title);
    boolean existsByIsbn(String isbn);
    
    // prob need something more



}