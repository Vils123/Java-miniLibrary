package lv.venta.demo.models;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Table
@Entity(name = "Reader_Information")
public class Reader extends Person {

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Current_Taken_Books")
    private ArrayList<Book> currentBooks;

    @Column(name = "Book_History")
    private ArrayList<Book> allBooks;
    
    private static ArrayList<String> takenUsernames = new ArrayList<String>();

    public Reader(String name, String surname, Date date, String username, String password){
    	super(name,surname,date);
    	if(!takenUsernames.contains(username))
    	{
    		this.username = username;
    		this.password = password;
    		takenUsernames.add(username);
    	}
    	

    }
    
    public boolean takeBook(Book b)
    {
    	if(currentBooks.size() > 2)     //max 3 books var reizee njemt
    		return false;
    	currentBooks.add(b);
    	allBooks.add(b);
    	return true;
    }
    
    public Book returnBook(int id)
    {
    	if(currentBooks.size() <= id+1)
    	{
    		Book temp = currentBooks.get(id+1);
    		currentBooks.remove(id+1);
    		return temp;
    	}
    	return null;
    }
    
    
    public ArrayList<Book> showCurrentBooks ()
    {
    	return currentBooks;
    }
    
    public ArrayList<Book> showAllBooks(){
    	return allBooks;
    }

	@Override
	public String toString() {
		return "Reader " + super.toString() + "\nCurrently taken books:" + currentBooks + "\nBook History: "+ allBooks; 
	}
    }



    

