package lv.venta.demo.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Table
@Entity(name = "ReaderTable")
public class Reader extends Person implements Serializable{


    @Column(name = "Username")
    private String username;


    @Column(name = "Password")
    private String password;


	@OneToMany(mappedBy = "reader")
    private Collection<Book> currentBooks;
	

    @Transient
	private static ArrayList<String> takenUsernames = new ArrayList<String>();
	

    @Transient
	private ArrayList<String> allBooks = new ArrayList<String>();
	

    @Transient
	private ArrayList<Book> takenBooks = new ArrayList<Book>();
	
	
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
		return "Reader " + super.toString() + "\nCurrently taken books:" + takenBooks + "\nBook History: "+ allBooks; 
	}

	
    }



    

