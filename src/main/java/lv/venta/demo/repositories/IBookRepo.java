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
    Book findByISBN(String isbn);
    Book findByTitle(String title);
    ArrayList<Author> findByAuthor(Author author);  
    Book findByCondition (Condition condition);
    Book findByReturnDate(Date date);
    ArrayList<Reader> findByReader(Reader reader); // prob need something more



}