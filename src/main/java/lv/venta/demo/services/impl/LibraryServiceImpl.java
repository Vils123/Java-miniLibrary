package lv.venta.demo.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

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
		
		
		
		for(int i = 0 ; i < 10; i++)          //saliek 10 vienadas gramatas biblioteekaa
		{
			Book temp = new Book("1234567891123", "Harry potter and the java code", a1, new Date(115,0,1), Genre.COMEDY, Condition.GOOD);
			libraryBookRepo.save(temp);
		}
		
		giveBookToReader(r1,"Harry potter and the java code", Condition.GOOD);    //funkcija lejaa
		
		giveBookToReader(r2,"Harry potter and the java code", Condition.GOOD);
		
		giveBookToReader(r3,"Harry potter and the java code", Condition.GOOD);
		
		takeBookFromReader(r1, 1);                                                //arii lejaa
				
		
	}

	@Override
	public boolean addNewBook(Book book) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean giveBookById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean returnBookById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAllBooksFromLibraryByTitle(String title) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAllBooksFromLibraryByIsbn(String title) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addReader(Reader reader) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAdmin(Admin admin) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAuthor(Author author) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateReader(Reader reader) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAdmin(Admin admin) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAuthor(Author author) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteReader(Reader reader) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAdmin(Admin admin) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAuthor(Author author) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showAllBooks() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAllReaders() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAllAdmins() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean giveBookToReader(Reader reader, String title, Condition condition) {
		Book temp = libraryBookRepo.findByTitleAndCondition(title, condition);
		if(temp == null)
			return false;      //ja neatrod gramatu talak neiet
		if(reader.takeBook(temp))    //ja readeram ir atlauts nemt gramatu
		{
			takenBookRepo.save(temp);
			libraryBookRepo.delete(temp);
			return true;
		}
		return false;
	}

	@Override
	public boolean takeBookFromReader(Reader reader, int id) {    //id - kura no max3 graamataam reader panjemto graamatu sarakstaa
		Book temp = reader.returnBook(1);          //ja reader atgriezh kadu gramatu, tad taa gramata jasaglabaa atpakal biblioteekaa un jaaiznjem no taken repo
		if(temp != null)
		{
			libraryBookRepo.save(temp);
			takenBookRepo.delete(temp);
			return true;
		}
		return false;
	}

}
