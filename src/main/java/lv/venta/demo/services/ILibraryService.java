package lv.venta.demo.services;

import java.util.ArrayList;

import lv.venta.demo.enums.Condition;
import lv.venta.demo.enums.Genre;
import lv.venta.demo.models.Admin;
import lv.venta.demo.models.Author;
import lv.venta.demo.models.Book;
import lv.venta.demo.models.Reader;

public interface ILibraryService {
	void inputdata();
	boolean addNewBook(Book book);
	boolean giveBookById(Reader reader, int id);
	boolean returnBookById(Reader reader, int id);
	boolean deleteAllBooksFromLibraryByTitle(String title);
	boolean deleteAllBooksFromLibraryByIsbn(String title);
	boolean addReader(Reader reader);
	boolean addAdmin(Admin admin);
	boolean addAuthor(Author author);
	boolean updateBook(Book book);
	boolean updateReader(Reader reader);
	boolean updateAdmin(Admin admin);
	boolean updateAuthor(Author author);
	boolean deleteReader(Reader reader);
	boolean deleteAuthor(Author author);
	boolean authoriseAdmin(Admin admin);
	boolean authoriseReader(Reader reader);

	Reader selectReaderByUsername(String username);
	ArrayList<Book> showAllBooksByTitle(String  title);
	ArrayList<Book> showAllBooks();  // izmainiju so, jo mums vajag no KUAT KA izdabut to info
	ArrayList<Reader> showAllReaders();
	ArrayList<Admin> showAllAdmins();
	ArrayList<Author> showAllAuthors();
	ArrayList<Book> selectAllBooksByCondition(Condition condition) throws Exception;
	ArrayList<Book> selectAllBooksByGenre(Genre genre) throws Exception;
	
	
	
	boolean giveBookToReader(Reader reader, String title);
	boolean takeBookFromReader(Reader reader, Book book);
}
