package lv.venta.demo.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Table
@Entity(name = "ReaderTable")
public class Reader extends Person implements Serializable{


	/////////////////////////////////
	@OneToMany//222222222222222222222222222
	(mappedBy = "writer")
	private Collection<Review> writtenReviews;
	//////////////////////////////////////

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

	@OneToMany(mappedBy = "reader")
	private Collection<Book> currentBooks;

	///for review 
	private boolean blacklisted = false; 
	///for review
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private Date blacklistDate = null;
	///blacklisting incase if admin is looking through reviews
	//and spot person with bad language use in many of reviews

    @Transient
	private static ArrayList<String> takenUsernames = new ArrayList<String>();   //usernames have to be unique
	
    @Lob
	private ArrayList<String> allBooks = new ArrayList<String>();

	@Lob
	private ArrayList<Book> takenBooks = new ArrayList<Book>();
	//books taken / in reading process

	@Transient
	private ArrayList<Book> booksReadAndReturned = new ArrayList<Book>();     //need to add it in return function
	//used so user can make review only on books that have been read and returned 
	
    public Reader(String name, String surname, Date date, String username, String password){
    	super(name,surname,date);
    	if(!takenUsernames.contains(username))
    	{
    		this.username = username;
    		this.password = password;
    		takenUsernames.add(username);
		}
    }
	
	
    public boolean takeBook(Book book)
    {
    	if(takenBooks.size() > 2)     //max 3 books var reizee njemt
    		return false;
    	if(book.giveBook(this))
    	{
    		takenBooks.add(book);
    		allBooks.add(book.getTitle());
    		return true;
    	}
    	return false;
	}
	
    
    public boolean returnBook(Book book)
    {
    	if(book.getReader() == this)
    	{
    		book.returnBook();
			takenBooks.remove(book);
			booksReadAndReturned.add(book);
    		return true;
    	}
    	return false;
    }
    
    
    public Collection<Book> showCurrentBooks ()
    {
    	return currentBooks;
	}
	
    
    public ArrayList<String> showAllBooks(){
    	return allBooks;
	}
	

    public ArrayList<Book> getTakenBooks()
    {
    	return takenBooks;
	}
	
    
	@Override
	public String toString() {

		String taken = "";
		for(int i = 0; i < takenBooks.size(); i++)
		{
			taken += takenBooks.get(i).getTitle() + "\n";
		}
		String all = "";
		for(int i = 0; i < allBooks.size(); i++)
		{
			all += allBooks.get(i) + "\n";
		}
		return "Reader " + super.toString() + "\nCurrently taken books:    " + taken + "\nBook History:    "+ all;
	}

	
    }



    

