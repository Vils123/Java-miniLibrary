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
	//adds new book to the library [ADMIN]

	boolean giveBookById(Reader reader, int id);
	//book is given out to reader [ADMIN]

	boolean returnBookById(Reader reader, int id);
	//book is returned by reader [ADMIN]

	boolean deleteAllBooksFromLibraryByTitle(String title);
	boolean deleteAllBooksFromLibraryByIsbn(String title);
	//deleting / removing all books from library by
	//title or isbn [ADMIN]

	boolean addReader(Reader reader);
	//registration from first page 
	//maybe [ADMIN] function too ??? boiz ?
	
	boolean addAdmin(Admin admin);
	//ONLY ONE ADMIN EXISTS AT FIRST
	//ONLY ADMIN CAN ADD ANOTHER ADMIN TO SYSTEM [ADMIN]
	
	boolean addAuthor(Author author);
	//adds author [ADMIN]

	boolean updateBook(Book book);
	boolean updateReader(Reader reader);
	boolean updateAdmin(Admin admin);
	boolean updateAuthor(Author author);
	//updating / changing info of class objeect instances

	boolean deleteReader(Reader reader);
	//[ADMIN]
	boolean deleteAuthor(Author author);
	// [ADMIN]

	boolean authoriseAdmin(Admin admin);//
	boolean authoriseReader(Reader reader);//
	//logging in from first page [ADMIN]

	Reader selectReaderByUsername(String username);
	// [ADMIN]
	ArrayList<Book> showAllBooksByTitle(String  title);
	// [USER], [ADMIN]
	ArrayList<Book> showAllBooks();  // izmainiju so, jo mums vajag no KUAT KA izdabut to info
	// [USER], [ADMIN]
	ArrayList<Reader> showAllReaders();
	//[USER], [ADMIN]
	ArrayList<Admin> showAllAdmins();
	//[ADMIN]
	ArrayList<Author> showAllAuthors();
	// [USER], [ADMIN]
	ArrayList<Book> selectAllBooksByCondition(Condition condition) throws Exception;
	//[USER],[ADMIN]
	//MAYBE only admin
	ArrayList<Book> selectAllBooksByGenre(Genre genre) throws Exception;
	// [USER], [ADMIN]

	boolean giveBookToReader(Reader reader, String title);
	// 2nd. give book ... IS THIS NEEDED?
	// 1st. line 19 gives byID ... 2nd line 74 gives byTitle
	boolean takeBookFromReader(Reader reader, Book book);
	// 2nd. take book  ...  IS THIS NEEDED?
	// 1st. line 22 takes byID ... 2nd line 77 gives instance

}
