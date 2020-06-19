package lv.venta.demo.services.impl;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.demo.enums.Condition;
import lv.venta.demo.enums.Genre;
import lv.venta.demo.models.Admin;
import lv.venta.demo.models.Author;
import lv.venta.demo.models.Book;
import lv.venta.demo.models.Reader;
import lv.venta.demo.repositories.IAdminRepo;
import lv.venta.demo.repositories.IAuthorRepo;
import lv.venta.demo.repositories.IBookRepo;
import lv.venta.demo.repositories.IReaderRepo;
import lv.venta.demo.services.ILibraryService;
@Service
public class LibraryServiceImpl implements ILibraryService{

	@Autowired
	IBookRepo bookRepo;
	@Autowired
	IReaderRepo readerRepo;
	@Autowired
	IAdminRepo adminRepo;
	@Autowired
	IAuthorRepo authorRepo;
	
	
	@Override
	public void inputdata() {
		Admin admin1 = new Admin("Dat", "Boss", new Date(66,06,06), "boss", "password");
		
		adminRepo.save(admin1);
		
		Reader r1 = new Reader("Janis", "Berzins", new Date(100,05,05), "janchuks123", "parole123");
		Reader r2 = new Reader("Richards", "Everts", new Date(98,05,05), "ricijs111", "parole123");
		Reader r3 = new Reader("Karlis", "Sermuksitis", new Date(99,02,05), "bilis333", "parole123");
		Reader r4 = new Reader("Vladislavs", "Sivakovs", new Date(99,07,7), "vvssss123333", "parole123");
		
		readerRepo.save(admin1);
		readerRepo.save(r2);
		readerRepo.save(r3);
		readerRepo.save(r4);
		readerRepo.save(r1);
		
		Author a1 = new Author("Barack", "Obama", new Date(60, 03,03), "not USA", "He was a president aswell", "He writing facts", Genre.COMEDY, null);
		
		authorRepo.save(a1);
		
		
		

		Book b1 = new Book("123456789112", "Harry potter and the java code", a1, new Date(115,0,1), Genre.COMEDY, Condition.POOR);
		addNewBook(b1);
		Book b2 = new Book("123456789112", "Harry potter and the java code", a1, new Date(115,0,1), Genre.COMEDY, Condition.GOOD);
		addNewBook(b2);
		Book b3 = new Book("123456789112", "Harry potter and the java code", a1, new Date(115,0,1), Genre.COMEDY, Condition.GOOD);
		addNewBook(b3);
		Book b4 = new Book("123456789112", "Harry potter and the java code", a1, new Date(115,0,1), Genre.COMEDY, Condition.GOOD);
		addNewBook(b4);
		Book b5 = new Book("123456789112", "Harry potter and the java code", a1, new Date(115,0,1), Genre.COMEDY, Condition.POOR);
		addNewBook(b5);
		Book b6 = new Book("192929191991", "The hot Student", a1, new Date(114,0,1), Genre.ROMANCE, Condition.MINT);
		addNewBook(b6);

		
		giveBookToReader(r1,"Harry potter and the java code");    
		takeBookFromReader(r1, r1.getTakenBooks().get(0));

		giveBookToReader(r3, "Harry potter and the java code" );
		giveBookToReader(r2, "Harry potter and the java code" );
		
		
	}

	@Override
	public boolean addNewBook(Book book) {
		if(book !=null)
		{
			bookRepo.save(book);
			return true;
		}
		return false;
	}

	@Override
	public boolean giveBookById(Reader reader, int id) {
		Book temp = bookRepo.findById(id);
		if(temp == null)
			return false;
		return giveBookToReader(reader, temp.getTitle());
	}

	@Override
	public boolean returnBookById(Reader reader,  int id) {
		Book temp = bookRepo.findById(id);
		if(temp == null)
			return false;
		if(reader.returnBook(temp))
		{
			return true;
		}
		return false;
		
	}

	@Override
	public boolean deleteAllBooksFromLibraryByTitle(String title) {
		if(!bookRepo.existsByTitle(title))                     //ja saakumaa nemaz nav tads title, tad false
			return false;
		ArrayList<Book> allBooks = bookRepo.findAllByTitle(title);
		for(Book a : allBooks)
		{
			if(a.isInLibrary())
				bookRepo.delete(a);
		}
		return true;
	}

	@Override
	public boolean deleteAllBooksFromLibraryByIsbn(String isbn) {
		if(!bookRepo.existsByIsbn(isbn))                     //ja saakumaa nemaz nav tads title, tad false
			return false;
		ArrayList<Book> allBooks = bookRepo.findAllByIsbn(isbn);
		for(Book a : allBooks)
		{
			if(a.isInLibrary())
				bookRepo.delete(a);
		}
		return true;
	}

	@Override
	public boolean addReader(Reader reader) {
		if(readerRepo.existsByUsername(reader.getUsername()))  //var arii peec id
			return false;
		readerRepo.save(reader);
		return true;
	}

	@Override
	public boolean addAdmin(Admin admin) {
		if(adminRepo.existsByUsername(admin.getUsername()))
			return false;
		adminRepo.save(admin);
		readerRepo.save(admin);          //jo admins ir arii reader
		return true;
	}

	@Override
	public boolean addAuthor(Author author) {
		if(authorRepo.existsById(author.getId()))
			return false;
		authorRepo.save(author);
		return true;
	}

	
	@Override
	public boolean updateBook(Book book)
	{
		if(bookRepo.existsById(book.getId()))
		{
			Book temp = bookRepo.findById(book.getId());
			temp.setAuthors(book.getAuthors());
			temp.setCondition(book.getCondition());
			temp.setConditionCounter(book.getConditionCounter());
			temp.setGenres(book.getGenre());
			temp.setInLibrary(book.isInLibrary());
			temp.setIsbn(book.getIsbn());
			temp.setPublishDate(book.getPublishDate());
			temp.setReader(book.getReader());
			temp.setReturnDate(book.getReturnDate());
			temp.setTakenDate(book.getTakenDate());
			temp.setTitle(book.getTitle());
			bookRepo.save(temp);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean updateReader(Reader reader) {
		Reader temp = readerRepo.findByUsername(reader.getUsername());
		if(temp == null)
			return false;
		temp.setAllBooks(reader.getAllBooks());
		temp.setCurrentBooks(reader.getCurrentBooks());
		temp.setDateOfBirth(reader.getDateOfBirth());
		temp.setName(reader.getName());
		temp.setSurname(reader.getSurname());
		temp.setPassword(reader.getPassword());
		temp.setTakenBooks(reader.getTakenBooks());
		readerRepo.save(temp);
		return true;
	}

	@Override
	public boolean updateAdmin(Admin admin) {
		Admin temp = adminRepo.findByUsername(admin.getUsername());
		if(temp == null)
			return false;
		temp.setAllBooks(admin.getAllBooks());
		temp.setCurrentBooks(admin.getCurrentBooks());
		temp.setDateOfBirth(admin.getDateOfBirth());
		temp.setName(admin.getName());
		temp.setSurname(admin.getSurname());
		temp.setPassword(admin.getPassword());
		temp.setTakenBooks(admin.getTakenBooks());
		adminRepo.save(temp);
		readerRepo.save(temp);
		return true;
	}

	@Override
	public boolean updateAuthor(Author author) {
		Author temp = authorRepo.findByNameAndSurname(author.getName(), author.getSurname());
		if(temp == null)
			return false;
		temp.setCountryOfOrigin(author.getCountryOfOrigin());
		temp.setDateOfBirth(author.getDateOfBirth());
		temp.setDateOfDeath(author.getDateOfDeath());
		temp.setLiteratureStyle(author.getLiteratureStyle());
		temp.setMainGenre(author.getMainGenre());
		temp.setShortBackground(author.getShortBackground());
		temp.setWrittenBooks(author.getWrittenBooks());
		authorRepo.save(temp);
		return true;

	}

	@Override
	public boolean deleteReader(Reader reader) {
		Reader temp = readerRepo.findByUsername(reader.getUsername());
		if(temp == null){
			return false;							//Parbauda vai usernname eksiste un ja ja tad izdzes to lietotaju, ja ne nu tad nav pareizi kaut kas 
		}
		else{
			readerRepo.delete(temp);
			return true;
		}
	}


	@Override
	public boolean deleteAuthor(Author author) {
		Author temp = authorRepo.findByNameAndSurname(author.getName(), author.getSurname());
		if(temp == null)
			return false;
		else
		{
			readerRepo.deleteById(temp.getId());
			authorRepo.delete(temp);
			return true;
		}
		
	}

	@Override
	public ArrayList<Book> showAllBooks() {
		return (ArrayList<Book>) bookRepo.findAll();
		
	}

	@Override
	public ArrayList<Reader> showAllReaders() {
		return (ArrayList<Reader>)readerRepo.findAll();
		
	}

	@Override
	public ArrayList<Admin> showAllAdmins() {
		return (ArrayList<Admin>) adminRepo.findAll();
		
	}

	@Override
	public boolean giveBookToReader(Reader reader, String title) {
		ArrayList<Book> allBooks = bookRepo.findAllByTitle(title);
		Book temp = null;
		for(Book a : allBooks)
		{
			if(a.isInLibrary())
			{
				temp = a;
				break;
			}
		}
		System.out.println(temp.getTitle());
		if(temp == null)
			return false;      //ja neatrod gramatu talak neiet
		if(reader.takeBook(temp))    //ja readeram ir atlauts nemt gramatu
		{
			System.out.println("giving book to " + reader);
			updateBook(temp);
			return true;
		}
		return false;
	}

	@Override
	public boolean takeBookFromReader(Reader reader, Book book) {  
		if(reader.returnBook(book))        
		{
			System.out.println("taking book from " + reader);

			if(book.getConditionCounter() < 1)
				bookRepo.delete(book);        //if book dies
			else
				updateBook(book);
			return true;
		}
		return false;
	}



	@Override
	public ArrayList<Book> selectAllBooksByCondition(Condition condition) throws Exception {
		ArrayList<Book> selectBook = bookRepo.findByCondition(condition);
		if (selectBook != null) {
			return selectBook;
		} else {
			throw new Exception("The Condition is wrong");
		}
	}

	@Override
	public Reader selectReaderByUsername(String username) {
		return readerRepo.findByUsername(username);
	}

	@Override
	public ArrayList<Book> showAllBooksByTitle(String title) {
		return bookRepo.findAllByTitle(title);
	}

	@Override
	public ArrayList<Book> selectAllBooksByGenre(Genre genre) throws Exception {
		ArrayList<Book> selectBook = bookRepo.findByGenre(genre);
		if (selectBook != null) {
			return selectBook;
		} else {
			throw new Exception("The Genre is wrong");
		}
	}

	@Override
	public ArrayList<Author> showAllAuthors() {
		return (ArrayList<Author>) authorRepo.findAll();
	}



	


	

}
