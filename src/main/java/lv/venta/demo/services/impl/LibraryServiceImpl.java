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
	IBookRepo libraryBookRepo;
	@Autowired
	IBookRepo takenBookRepo;
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
		readerRepo.save(r1);
		readerRepo.save(r2);
		readerRepo.save(r3);
		readerRepo.save(r4);

		
		Author a1 = new Author("Barack", "Obama", new Date(60, 03,03), "not USA", "He was a president aswell", "He writing facts", Genre.COMEDY, null);
		
		authorRepo.save(a1);
		
		
		

		Book b1 = new Book("123456789112", "Harry potter and the java code", a1, new Date(115,0,1), Genre.COMEDY, Condition.GOOD);
		addNewBook(b1);
		Book b2 = new Book("123456789112", "Harry potter and the java code", a1, new Date(115,0,1), Genre.COMEDY, Condition.GOOD);
		addNewBook(b2);
		Book b3 = new Book("123456789112", "Harry potter and the java code", a1, new Date(115,0,1), Genre.COMEDY, Condition.GOOD);
		addNewBook(b3);
		Book b4 = new Book("123456789112", "Harry potter and the java code", a1, new Date(115,0,1), Genre.COMEDY, Condition.GOOD);
		addNewBook(b4);
		Book b5 = new Book("123456789112", "Harry potter and the java code", a1, new Date(115,0,1), Genre.COMEDY, Condition.POOR);
		addNewBook(b5);
		
		giveBookToReader(r1,"Harry potter and the java code", Condition.GOOD);    //funkcija lejaa
		
		giveBookToReader(r2,"Harry potter and the java code", Condition.GOOD);
		
		giveBookToReader(r3,"Harry potter and the java code", Condition.GOOD);
		
		takeBookFromReader(r1, r1.getTakenBooks().get(0));                    //arii lejaa
				
		
	}

	@Override
	public boolean addNewBook(Book book) {
		if(book !=null)
		{
			libraryBookRepo.save(book);
			return true;
		}
		return false;
	}

	@Override
	public boolean giveBookById(Reader reader, int id) {
		Book temp = libraryBookRepo.findById(id);
		if(temp == null)
			return false;
		return giveBookToReader(reader, temp.getTitle(), temp.getCondition());
	}

	@Override
	public boolean returnBookById(Reader reader,  int id) {
		Book temp = takenBookRepo.findById(id);
		if(temp == null)
			return false;
		if(reader.returnBook(temp))
		{
			libraryBookRepo.save(temp);
			takenBookRepo.delete(temp);
			return true;
		}
		return false;
		
	}

	@Override
	public boolean deleteAllBooksFromLibraryByTitle(String title) {
		if(!libraryBookRepo.existsByTitle(title))                     //ja saakumaa nemaz nav tads title, tad false
			return false;
		while(libraryBookRepo.existsByTitle(title))
		{
			libraryBookRepo.delete(libraryBookRepo.findByTitle(title).get(0));        //nem aaraa kameer aizliegto graamatu vairs nav
		}
		return true;
	}

	@Override
	public boolean deleteAllBooksFromLibraryByIsbn(String isbn) {
		if(!libraryBookRepo.existsByIsbn(isbn))                     //ja saakumaa nemaz nav tads isbn, tad false
			return false;
		while(libraryBookRepo.existsByTitle(isbn))
		{
			libraryBookRepo.delete(libraryBookRepo.findByIsbn(isbn).get(0));        //nem aaraa kameer aizliegto graamatu vairs nav
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
		return (ArrayList<Book>) libraryBookRepo.findAll();
		
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
	public boolean giveBookToReader(Reader reader, String title, Condition condition) {
		Book temp = libraryBookRepo.findByTitleAndCondition(title, condition).get(0);
		if(temp == null)
			return false;      //ja neatrod gramatu talak neiet
		if(reader.takeBook(temp))    //ja readeram ir atlauts nemt gramatu
		{
			System.out.println("giving book to " + reader);
			takenBookRepo.save(temp);
			libraryBookRepo.delete(temp);
			return true;
		}
		return false;
	}

	@Override
	public boolean takeBookFromReader(Reader reader, Book book) {  
		if(reader.returnBook(book))        //ja reader atgriezh kadu gramatu, tad taa gramata jasaglabaa atpakal biblioteekaa un jaaiznjem no taken repo
		{
			System.out.println("taking book from " + reader);
			libraryBookRepo.save(book);
			takenBookRepo.delete(book);
			return true;
		}
		return false;
	}

}
