//Reader class - People who take books home and return them
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


	@OneToMany
	(mappedBy = "writer")
	private Collection<Review> writtenReviews;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

	@OneToMany(mappedBy = "reader")
	private Collection<Book> currentBooks;


	private boolean blacklisted = false; 

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private Date blacklistDate = null;         //a blacklisted reader cant make reviews (maybe they said a bad word?)


    @Transient
	private static ArrayList<String> takenUsernames = new ArrayList<String>();   //usernames have to be unique
	
    @Lob
	private ArrayList<String> allBooks = new ArrayList<String>();     //every book they have ever taken

	@Lob
	private ArrayList<Book> takenBooks = new ArrayList<Book>();      //books they currently have taken
	@Transient
	private ArrayList<Book> booksReadAndReturned = new ArrayList<Book>();     //books they have returned

	
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
    	if(takenBooks.size() > 2)     //max 3 books at a time
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
			taken += takenBooks.get(i).getTitle() + "; ";
		}
		String all = "";
		for(int i = 0; i < allBooks.size(); i++)
		{
			all += allBooks.get(i) + "; ";
		}
		return "Reader " + super.toString() + "[Currently taken books: " + taken + "]" + "[Book History:    "+ all + "]";
	}

	
    }



    

