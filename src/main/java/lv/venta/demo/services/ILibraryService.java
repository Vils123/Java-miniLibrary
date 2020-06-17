package lv.venta.demo.services;

import lv.venta.demo.models.Admin;
import lv.venta.demo.models.Author;
import lv.venta.demo.models.Book;
import lv.venta.demo.models.Reader;

public interface ILibraryService {
	void inputdata();
	boolean addNewBook(Book book);
	boolean giveBookById(int id);
	boolean returnBookById(int id);
	boolean deleteAllBooksFromLibraryByTitle(String title);
	boolean deleteAllBooksFromLibraryByIsbn(String title);
	boolean addReader(Reader reader);
	boolean addAdmin(Admin admin);
	boolean addAuthor(Author author);
	boolean updateReader(Reader reader);
	boolean updateAdmin(Admin admin);
	boolean updateAuthor(Author author);
	boolean deleteReader(Reader reader);
	boolean deleteAdmin(Admin admin);
	boolean deleteAuthor(Author author);
	void showAllBooks();
	void showAllReaders();
	void showAllAdmins();
}
