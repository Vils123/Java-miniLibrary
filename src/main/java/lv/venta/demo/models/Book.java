package lv.venta.demo.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class Book implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Book_Id")
	@Setter(value=AccessLevel.PRIVATE)
	private int id;
	
	@Size(min=10, max=13)
	@Pattern(regexp="[0-9A-Z\\s]+$")
	@Column(name = "ISBN") 
	private String isbn;  
	
	@Column(name = "Title")
	private String title;
	
	@Column(name = "Published")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy")  // i think just year is enough?
	
	private Date publishDate;
	
	@Transient
	private ArrayList<Genre> genres = new ArrayList<Genre>();
	
	@Column(name = "Genres")
	private String genresString = "";
	
	@Column(name = "Condition")
	private Condition condition = Condition.GOOD;
	@Transient
	private int conditionCounter = 15;
	
	@Column(name = "Date_when_taken")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date takenDate = null;
	
	@Column(name = "Return_by")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date returnDate = null;
	
	@ManyToOne
	@JoinColumn(name = "Reader_Id")
	private Reader reader;
	
	@ManyToMany
	@JoinTable(name = "Book_Author", joinColumns =@JoinColumn(name = "B_Id"), inverseJoinColumns =@JoinColumn(name = "Id"))
	private Collection<Author> authors;
	
	
	
	public Book(String isbn, String title, Collection<Author> authors, Date publishDate, ArrayList<Genre> genres,
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
			this.genresString = stringifyGenres();
			//addBookToAuthors();
		}
	}
	
	public Book(String isbn, String title, Author author, Date publishDate, ArrayList<Genre> genres,
			Condition condition) {
		if(checkDate(publishDate))   //no time travelers
		{
			this.isbn = isbn;
			this.title = title;
			ArrayList<Author> a = new ArrayList<Author>();
			a.add(author);
			this.authors = a;
			this.publishDate = publishDate;
			this.genres = genres;
			if(condition != null)
			{	
				this.condition = condition;
				startConditionCounter();
			}
			this.genresString = stringifyGenres();
			//addBookToAuthors();
		}
	}
	
	public Book(String isbn, String title, Collection<Author> authors, Date publishDate, Genre genre,
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
			this.genresString = stringifyGenres();
			//addBookToAuthors();
		}
	}
	
	public Book(String isbn, String title, Author author, Date publishDate, Genre genre,
			Condition condition) {
		if(checkDate(publishDate))   //no time travelers
		{
			this.isbn = isbn;
			this.title = title;
			ArrayList<Author> a = new ArrayList<Author>();
			a.add(author);
			this.authors = (Collection) a;
			this.publishDate = publishDate;
			this.genres.add(genre);
			
			if(condition != null)
			{	
				this.condition = condition;
				startConditionCounter();
			}
			this.genresString = stringifyGenres();
			//addBookToAuthors();
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
	
	public void setGenres(ArrayList<Genre> genres)
	{
		this.genres = genres;
		this.genresString = stringifyGenres();
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
			this.genresString = stringifyGenres();
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
	
	
	public void addBookToAuthors()
	{
		if(authors.isEmpty())
			return;
		for(Author a : authors)
		{
			if(a !=null)
			{
				if(!a.getWrittenBooks().contains(this.title))
				{
					a.addBook(this);
				}
			}
		}
	}
	
	private String stringifyGenres()
	{
		String gen = "";
		for(Genre g : genres)
		{
			gen += g;
		}
		return gen;
	}
	
	public String toString()
	{
		String autori = "";
		for(Author a : authors)
		{
			autori += a.toString() + ", ";
		}
		autori = autori.substring(0, autori.length()-2);
		
			
		return title + "\nWritten By: " + autori +  "\nGenre: " + genres + "\nCondition: " + condition; 
	}
}
