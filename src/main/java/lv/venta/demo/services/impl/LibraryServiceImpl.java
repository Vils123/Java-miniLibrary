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
import lv.venta.demo.models.Review;
import lv.venta.demo.repositories.IAdminRepo;
import lv.venta.demo.repositories.IAuthorRepo;
import lv.venta.demo.repositories.IBookRepo;
import lv.venta.demo.repositories.IReaderRepo;
import lv.venta.demo.repositories.IReviewRepo;
import lv.venta.demo.services.ILibraryService;

@Service
public class LibraryServiceImpl implements ILibraryService {

	@Autowired
	IReviewRepo reviewRepo;

	@Autowired
	IBookRepo bookRepo;

	@Autowired
	IReaderRepo readerRepo;

	@Autowired
	IAdminRepo adminRepo;

	@Autowired
	IAuthorRepo authorRepo;

	

	private Reader currentReader = null;
	private Author currentAuthor = null;




	@Override
	public void inputdata() {

		Admin admin1 = new Admin("Dat", "Boss", new Date(66, 06, 06), "boss", "password");
		Admin admin2 = new Admin("Bossiks", "Tossiks", new Date(98, 07, 01), "smuks", "koks");
		Admin admin3 = new Admin("Janka", "Panka", new Date(99, 07, 01), "Janka", "paks");

		adminRepo.save(admin1);
		adminRepo.save(admin2);
		adminRepo.save(admin3);

		Reader r1 = new Reader("Janis", "Berzins", new Date(100, 05, 05), "janchuks123", "parole123");
		Reader r2 = new Reader("Richards", "Everts", new Date(98, 05, 05), "ricijs111", "parole123");
		Reader r3 = new Reader("Karlis", "Sermuksitis", new Date(99, 02, 05), "bilis333", "parole123");
		Reader r4 = new Reader("Vladislavs", "Sivakovs", new Date(99, 07, 7), "vvssss123333", "parole123");

		readerRepo.save(r2);
		readerRepo.save(r3);
		readerRepo.save(r4);
		readerRepo.save(r1);

		Author a1 = new Author("Barack", "Obama", new Date(60, 03, 03), "not USA", "He was a president aswell",
				"He writing facts", Genre.COMEDY, null);
		Author a2 = new Author("Egata", "Bristi", new Date(10, 05, 11), "America", "Great author",
				"Writes detective novels", Genre.DETECTIVE, new Date(90, 04, 9));
		Author a3 = new Author("Verners", "Smits", new Date(98, 05, 10), "Latvia", "Coder", "He do be coding",
				Genre.MYSTERY, null);

		authorRepo.save(a1);
		authorRepo.save(a2);
		authorRepo.save(a3);

		Book b1 = new Book("123456789112", "Harry potter and the java code", a1, new Date(115, 0, 1), Genre.COMEDY,
				Condition.POOR);
		addNewBook(b1);
		Book b2 = new Book("191919191919", "The master of code", a3, new Date(115, 0, 1), Genre.COMEDY, Condition.GOOD);
		addNewBook(b2);
		Book b3 = new Book("192929191991", "The lost Student", a2, new Date(114, 0, 1), Genre.DETECTIVE,
				Condition.MINT);
		addNewBook(b3);

		giveBookToReader(r1, "Harry potter and the java code");
		takeBookFromReader(r1, r1.getTakenBooks().get(0));

		Review rev1 = new Review(r1,b1,8,9,10,"DAMN DUMBLEDORE CHEEKS THO");
		reviewRepo.save(rev1);

		giveBookToReader(r3, "The lost Student");
		giveBookToReader(r2, "The master of code");
		System.out.println("UPDATING");
		updateReader(r1);
		updateReader(r2);
		updateReader(r3);
		updateReader(r4);
		System.out.println("UPDATED");
		System.out.println(r1 + "\n");
		System.out.println(r2 + "\n");
		System.out.println(r3 + "\n");
		System.out.println(r4 + "\n");

	}

	@Override
	public boolean addNewBook(Book book) {
		if (book != null) {
			bookRepo.save(book);
			return true;
		}
		return false;
	}

	@Override
	public boolean giveBookById(Reader reader, int id) {
		Book temp = bookRepo.findById(id);
		if (temp == null)
			return false;
		return giveBookToReader(reader, temp.getTitle());
	}

	@Override
	public boolean returnBookById(Reader reader, int id) {
		Book temp = bookRepo.findById(id);
		if (temp == null)
			return false;
		if (reader.returnBook(temp)) {
			return true;
		}
		return false;

	}

	@Override
	public boolean deleteAllBooksFromLibraryByTitle(String title) {
		if (!bookRepo.existsByTitle(title)) // ja saakumaa nemaz nav tads title, tad false
			return false;
		ArrayList<Book> allBooks = bookRepo.findAllByTitle(title);
		for (Book a : allBooks) {
			if (a.isInLibrary())
				bookRepo.delete(a);
		}
		return true;
	}

	@Override
	public boolean deleteAllBooksFromLibraryByIsbn(String isbn) {
		if (!bookRepo.existsByIsbn(isbn)) // ja saakumaa nemaz nav tads title, tad false
			return false;
		ArrayList<Book> allBooks = bookRepo.findAllByIsbn(isbn);
		for (Book a : allBooks) {
			if (a.isInLibrary())
				bookRepo.delete(a);
		}
		return true;
	}

	@Override
	public boolean addReader(Reader reader) {
		if (readerRepo.existsByUsername(reader.getUsername())) // var arii peec id
			return false;
		readerRepo.save(reader);
		return true;
	}

	@Override
	public boolean addAdmin(Admin admin) {
		if (adminRepo.existsByUsername(admin.getUsername()))
			return false;
		adminRepo.save(admin);
		return true;
	}

	@Override
	public boolean addAuthor(Author author) {
		if (authorRepo.existsById(author.getId()))
			return false;
		authorRepo.save(author);
		return true;
	}

	@Override
	public boolean updateBook(Book book) {
		if (bookRepo.existsById(book.getId())) {
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
		if (temp == null)
			return false;
		temp.setAllBooks(reader.getAllBooks());
		temp.setCurrentBooks(reader.getCurrentBooks());
		temp.setDateOfBirth(reader.getDateOfBirth());
		temp.setName(reader.getName());
		temp.setSurname(reader.getSurname());
		temp.setPassword(reader.getPassword());
		temp.setTakenBooks(reader.getTakenBooks());
		readerRepo.save(temp);
		System.out.println("updated reader" + reader.getUsername());
		return true;
	}

	@Override
	public boolean updateAdmin(Admin admin) {
		Admin temp = adminRepo.findByUsername(admin.getUsername());
		if (temp == null)
			return false;
		temp.setDateOfBirth(admin.getDateOfBirth());
		temp.setName(admin.getName());
		temp.setSurname(admin.getSurname());
		temp.setPassword(admin.getPassword());
		adminRepo.save(temp);
		return true;
	}

	@Override
	public boolean updateAuthor(Author author) {
		Author temp = authorRepo.findByNameAndSurname(author.getName(), author.getSurname());
		if (temp == null)
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
	public boolean deleteReader(String username) {
		Reader temp = readerRepo.findByUsername(username);
		if (temp == null) {
			return false; // Parbauda vai usernname eksiste un ja ja tad izdzes to lietotaju, ja ne nu tad
							// nav pareizi kaut kas
		} else {
			readerRepo.delete(temp);
			return true;
		}
	}

	@Override
	public boolean deleteAuthor(Author author) {
		Author temp = authorRepo.findByNameAndSurname(author.getName(), author.getSurname());
		if (temp == null)
			return false;
		else {
			readerRepo.deleteById(temp.getId());
			authorRepo.delete(temp);
			return true;
		}
	}

	@Override
	public boolean authoriseAdmin(Admin admin) {
		if (adminRepo.existsByUsername(admin.getUsername()) && adminRepo.existsByPassword(admin.getPassword())) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean authoriseReader(Reader reader) {
		if (readerRepo.existsByUsername(reader.getUsername()) && readerRepo.existsByPassword(reader.getPassword())) {
			return true;
		} else {
			return false;
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
	public ArrayList<Book> showAllBooks() {
		return (ArrayList<Book>) bookRepo.findAll();
	}

	@Override
	public ArrayList<Reader> showAllReaders() {
		return readerRepo.findAll();
	}

	@Override
	public ArrayList<Admin> showAllAdmins() {
		return (ArrayList<Admin>) adminRepo.findAll();
	}

	@Override
	public ArrayList<Author> showAllAuthors() {
		return (ArrayList<Author>) authorRepo.findAll();
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
	public ArrayList<Book> selectAllBooksByGenre(Genre genre) throws Exception {
		ArrayList<Book> selectBook = bookRepo.findByGenre(genre);
		if (selectBook != null) {
			return selectBook;
		} else {
			throw new Exception("The Genre is wrong");
		}
	}

	@Override
	public boolean giveBookToReader(Reader reader, String title) {
		ArrayList<Book> allBooks = bookRepo.findAllByTitle(title);
		Book temp = null;
		for (Book a : allBooks) {
			if (a.isInLibrary()) {
				temp = a;
				break;
			}
		}
		System.out.println(temp.getTitle());
		if (reader.takeBook(temp)) // ja readeram ir atlauts nemt gramatu
		{
			System.out.println("giving book to " + reader);
			updateBook(temp);
			updateReader(reader);
			return true;
		}
		return false;
	}

	@Override
	public boolean takeBookFromReader(Reader reader, Book book) {
		if (reader.returnBook(book)) {
			System.out.println("taking book from " + reader);

			if (book.getConditionCounter() < 1)
				bookRepo.delete(book); // if book dies
			else
			{
				updateBook(book);
				updateReader(reader);
			}
			
			return true;
		}
		return false;
	}





















	@Override
	public boolean addReview(Review review) { 
		if(reviewRepo.existsById(review.getId()))
			return false;
		String title = review.getTitle();
		ArrayList<Book> temp = bookRepo.findAllByTitle(title);
		for(Book a : temp)
		{
			a.addReview(review);
		}
		reviewRepo.save(review);
		return true;
	}


	@Override
	public boolean deleteReviewByID(int id) {
		if(!reviewRepo.existsById(id))
			return false;
		reviewRepo.deleteById(id);
		return true;
	}

	@Override
	public ArrayList<Review> showAllReviewsOfCurrentUser() {
		return (ArrayList<Review>) currentReader.getWrittenReviews();
	}

	@Override
	public ArrayList<Review> showAllReviewsByUserID(int id) {
		return reviewRepo.findAllByWriterId(id); //  i guess
	}

	@Override
	public ArrayList<Review> showAllReviewsByDate(Date date) {
		return reviewRepo.findAllByCommentDate(date);
	}

	@Override
	public ArrayList<Review> showAllReviewsOfBookByBookName(String bookName) {
		Book temp = bookRepo.findByTitle(bookName).get(0);
		return (ArrayList<Review>) temp.getReview();
	}

	@Override
	public ArrayList<Reader> showAllBlacklistedReaders() {
		ArrayList<Reader> allBlacklisted = new ArrayList<Reader>();

		allBlacklisted = readerRepo.findAllByBlacklisted(true);

		return allBlacklisted;
	}

	@Override
	public Boolean addToBlackListById(int id) {
		Reader temp = readerRepo.findById(id);
		if (temp != null) {
			temp.setBlacklisted(true);
			temp.setBlacklistDate(new Date());
			return true;
		}
		return false;
	}

	@Override
	public Boolean removeFromBlackListByID(int id) {
		Reader temp = readerRepo.findById(id);
		if (temp != null) {
			temp.setBlacklisted(false);
			temp.setBlacklistDate(null);
			return true;
		}
		return false; 
	}

	@Override
	public boolean updateReview(Review review) { //ir padots review id 
		Review temp = reviewRepo.findById(review.getId());
		if (temp == null)
			return false;
		temp.setThoughts(review.getThoughts());  
		//tiek apdeitots tikai thoughts (1 field)
		reviewRepo.save(temp);
		return true;
	}



	@Override
	public boolean deleteAllReviewsByReaderByReaderId(int id) {
		if (!reviewRepo.existsByWriterId(id))// ja saakumaa nemaz nav tads title, tad false
			return false;
		ArrayList<Review> allReviews = reviewRepo.findAllByWriterId(id);
		for (Review a : allReviews) {
				reviewRepo.delete(a);
		}
		return true;
	}


	
	

	public void setCurrentReader(Reader reader){
		this.currentReader = reader;
	}

	public Reader currentReader(){
		return currentReader;
	}

	public void setCurrentAuthor(Author author){
		this.currentAuthor = author;
	}

	public Author currentAuthor(){
		return currentAuthor;
	}
	
	
	@Override
	public boolean addBookToAuthor(Author author, Book book) {
		ArrayList<Book> allBooks = bookRepo.findAllByTitle(book.getTitle());
		Book temp = null;
		for (Book a : allBooks) {
			if (a.isInLibrary()) {
				System.out.println("ir");
				temp = a;
				break;
			}
		}
		System.out.println(temp.getTitle());
		if(authorRepo.existsByName(author.getName())){
			Author foundAuth = authorRepo.findByNameAndSurname(author.getName(),author.getSurname());
			foundAuth.addBook(temp);
			System.out.println(temp.getTitle());
			System.out.println(foundAuth.getName());
			return true;
		}
		return false;
		
		
	}

	@Override
	public boolean checkIfBookTitleExists(String title) {
		return (bookRepo.existsByTitle(title));
	}


}
