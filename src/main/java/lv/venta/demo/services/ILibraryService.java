package lv.venta.demo.services;

import java.util.ArrayList;
import java.util.Date;

import lv.venta.demo.enums.Condition;
import lv.venta.demo.enums.Genre;
import lv.venta.demo.models.Admin;
import lv.venta.demo.models.Author;
import lv.venta.demo.models.Book;
import lv.venta.demo.models.Reader;
import lv.venta.demo.models.Review;

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

	boolean checkIfBookTitleExists(String title);
	
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

	boolean deleteReader(String username);
	//[ADMIN]
	boolean deleteAuthor(Author author);
	// [ADMIN]

	boolean authoriseAdmin(Admin admin);//
	boolean authoriseReader(Reader reader);//
	//logging in from first page [ADMIN]

	Reader selectReaderByUsername(String username);
	// [ADMIN]
	ArrayList<Book> showAllBooksByTitle(String  title);
	// [ALL]
	ArrayList<Book> showAllBooks();  // izmainiju so, jo mums vajag no KUAT KA izdabut to info
	// [ALL]
	ArrayList<Reader> showAllReaders();
	//[All]
	ArrayList<Admin> showAllAdmins();
	//[ADMIN]
	ArrayList<Author> showAllAuthors();
	// [ALL]
	ArrayList<Book> selectAllBooksByCondition(Condition condition) throws Exception;
	//[ALL]
	//MAYBE only admin
	ArrayList<Book> selectAllBooksByGenre(Genre genre) throws Exception;
	// [ALL]
	boolean giveBookToReader(Reader reader, String title);
	// 2nd. give book ...
	// 1st. line 19 gives byID ... 2nd line 74 gives byTitle
	boolean takeBookFromReader(Reader reader, Book book);
	// 2nd. take book  ...  
	// 1st. line 22 takes byID ... 2nd line 77 gives instance




	boolean addReview(Review review);
	
	
	
	boolean deleteReviewByID(int id);
	//[ALL]
	ArrayList<Review> showAllReviewsOfCurrentUser();
	//[ALL]
	ArrayList<Review> showAllReviewsByUserID(int id); // maybe username
	//[ADMIN]
	ArrayList<Review> showAllReviewsByDate(Date date); 
	//[ADMIN]
	ArrayList<Review> showAllReviewsOfBookByBookName(String bookName);  
	//[ALL]
	ArrayList<Reader> showAllBlacklistedReaders();
	//[ADMIN]
	Boolean addToBlackListById(int id);
	// [ADMIN]
	Boolean removeFromBlackListByID(int id);
	// [ADMIN]
	boolean deleteAllReviewsByReaderByReaderId(int id);

	boolean updateReview(Review review);


	void setCurrentReader(Reader reader);

	Reader currentReader();

	boolean addBookToAuthor(Author author, Book book);

	ArrayList<Book> showCurrentBooks(Reader reader) 


}
