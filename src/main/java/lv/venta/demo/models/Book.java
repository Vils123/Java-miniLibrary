package lv.venta.demo.models;

import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.demo.enums.Condition;
import lv.venta.demo.enums.Genre;
@Entity
@Table(name = "BookTable")
@Getter @Setter @NoArgsConstructor
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Book_Id")
	@Setter(value=AccessLevel.PRIVATE)
	private int id;
	
	@Column(name = "ISBN") 
	private String isbn;  	// needs to be 13 chars long, idk how do
	
	@Column(name = "Title")
	private String title;
	
	@Column(name = "Authors")
	private ArrayList<Author> authors;
	
	@Column(name = "Published")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy")  // i think just year is enough?
	private Date publishDate;
	@Column(name = "Genre")
	private ArrayList<Genre> genres;
	
	@Column(name = "Condition")
	private Condition condition = Condition.GOOD;
	private int conditionCounter = 15;
	
	@Column(name = "Date when taken")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date takenDate = null;
	
	@Column(name = "Return by")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date returnDate = null;

	public Book(String isbn, String title, ArrayList<Author> authors, Date publishDate, ArrayList<Genre> genres,
			Condition condition) {
		if(checkDate(publishDate))   //no time travelers
		{
			this.isbn = isbn;
			this.title = title;
			this.authors = authors;
			this.publishDate = publishDate;
			this.genres = genres;
			if(condition != null)
			{	
				this.condition = condition;
				startConditionCounter();
			}
		}
	}
	
	public Book(String isbn, String title, Author author, Date publishDate, ArrayList<Genre> genres,
			Condition condition) {
		if(checkDate(publishDate))   //no time travelers
		{
			this.isbn = isbn;
			this.title = title;
			this.authors.add(author);
			this.publishDate = publishDate;
			this.genres = genres;
			if(condition != null)
			{	
				this.condition = condition;
				startConditionCounter();
			}
		}
	}
	
	public Book(String isbn, String title, ArrayList<Author> authors, Date publishDate, Genre genre,
			Condition condition) {
		if(checkDate(publishDate))   //no time travelers
		{
			this.isbn = isbn;
			this.title = title;
			this.authors = authors;
			this.publishDate = publishDate;
			this.genres.add(genre);
			if(condition != null)
			{	
				this.condition = condition;
				startConditionCounter();
			}
		}
	}
	
	public Book(String isbn, String title, Author author, Date publishDate, Genre genre,
			Condition condition) {
		if(checkDate(publishDate))   //no time travelers
		{
			this.isbn = isbn;
			this.title = title;
			this.authors.add(author);
			this.publishDate = publishDate;
			this.genres.add(genre);
			if(condition != null)
			{	
				this.condition = condition;
				startConditionCounter();
			}
		}
	}
	
	public void setPublishDate(Date date)
	{
		if(checkDate(date))
			this.publishDate = date;
	}
	public void setCondition(Condition condition)
	{
		this.condition = condition;
		startConditionCounter();
	}
	
	
	public boolean addAuthor(Author author)
	{
		if(!authors.contains(author))
		{
			authors.add(author);
			return true;
		}
		return false;
	}
	
	public boolean addGenre(Genre genre)
	{
		if(genres.contains(genre))
		{
			genres.add(genre);
			return true;
		}
		return false;
	}
	
	public boolean decreaseCondition()
	{
		conditionCounter--;
		if(conditionCounter == 10)
			this.condition = Condition.USED;
		if(conditionCounter == 5)
			this.condition = Condition.POOR;
		if(conditionCounter < 1) 
			return false;                       // idk what to do with dead book
		return true;
	}
	
	
	
	private boolean checkDate(Date date)
	{
		Date today = new Date();
		if(date.getYear() <= today.getYear())
			return true;
		return false;
	}
	private void startConditionCounter()
	{
		if(this.condition == Condition.GOOD)
			conditionCounter = 15;
		if(this.condition == Condition.USED)
			conditionCounter = 10;
		if(this.condition == Condition.POOR)
			conditionCounter = 5;
	}
	
	public void addTakenDate()
	{
		takenDate = new Date();
	}
	
	public void addReturnDate(Date date)
	{
		if(date.after(takenDate))
			returnDate = date;
	}
	
	public void returnBook()   //graamata ir atpakal biblioteekaa - tai zuud condition un datumi
	{
		takenDate = null;
		returnDate = null;
		decreaseCondition();
	}
	
	public String toString()
	{
		String autori = "";
		for(int i = 0 ; i < authors.size(); i++)
		{
			autori += authors.get(i).toString();
			if(i != authors.size())
				autori += ", ";
		}
		
			
		return title + "\nWritten By: " + autori +  "\nGenre: " + genres + "\nCondition: " + condition; 
	}
}
