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
import javax.validation.constraints.Min;
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

	protected static ArrayList<Book> allBooksInLibrary = new ArrayList<Book>();///
	//to add them from constructor and storing them after construction so 
	//used as a main function for library book lookthrough

	////////////////////////////////////////////////
	@ManyToMany//11111111111111
	@JoinTable(name = "Book_Review", joinColumns = @JoinColumn(name = "B_R_Id"), inverseJoinColumns = @JoinColumn(name = "Review_Id"))
	private Collection<Review> review;
	////////////////////////////////////
	@ManyToOne
	@JoinColumn(name = "Reader_Id")
	private Reader reader;

	@ManyToMany
	@JoinTable(name = "Book_Author", joinColumns = @JoinColumn(name = "B_Id"), inverseJoinColumns = @JoinColumn(name = "Id"))
	private Collection<Author> authors;
	/////////////////////////////////////////////////

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
	
	
	@Column(name = "Genres")
	private Genre genre = Genre.COMEDY;
	

	@Column(name = "Condition")
	private Condition condition = Condition.MINT;
	@Transient
	private int conditionCounter = 60; //
	

	@Column(name = "Date_when_taken")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date takenDate = null;
	

	@Column(name = "Return_by")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date returnDate = null;


	@Min(0)
	int book_count; 
	// can be 0 at first 
	// can be changed 
	// one of parametres in constructor ... ? how to for loop a constructor

	@Column(name = "In_Library")
	private boolean inLibrary = true;
	
	
	//constructor for one author
	// public Book(String isbn, String title, Author author, Date publishDate, Genre genre,
	// 		Condition condition,int book_count) {
	// 	Book temp = new Book();
	// 	if (checkDate(publishDate)) // no time travelers
	// 	{
	// 		if(book_count>=0){
	// 			for (int i = 0; i < book_count; i++) {
	// 				temp = new Book(isbn, title, author, publishDate, genre, condition);
	// 				allBooksInLibrary.add(temp);
	// 			}
	// 		}
	// 		// addBookToAuthors();
	// 	}
	// }

	// public Book(String isbn, String title, Collection<Author> authors, Date publishDate, Genre genre,
	//  Condition condition, int book_count ) {
	// 	Book temp = new Book();
	// 	if (checkDate(publishDate)) // no time travelers
	// 	{
	// 		if (book_count >= 0) {
	// 			for (int i = 0; i < book_count; i++) {
	// 				temp = new Book(isbn, title, authors, publishDate, genre, condition);
	// 				allBooksInLibrary.add(temp);
	// 			}
	// 		}
	// 		// addBookToAuthors();
	// 	}
	// }

	// made it private because there is noo need of single book adding used in loop constructor
	public Book(String isbn, String title, Collection<Author> authors, Date publishDate, Genre genre,
			Condition condition) {
		if(checkDate(publishDate))   //no time travelers
		{
			this.isbn = isbn;
			this.title = title;
			this.authors = authors;
			this.publishDate = publishDate;
			this.genre = genre;
			if(condition != null)
			{	
				this.condition = condition;
				startConditionCounter();
			}
			//addBookToAuthors();
		}
	}

	//made it private because there is noo need of single book adding used in loop constructor 
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
			this.genre = genre;
			
			if(condition != null)
			{	
				this.condition = condition;
				startConditionCounter();
			}
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
	}
	
	public void setGenres(Genre genre)
	{
		this.genre = genre;
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
	
	public boolean decreaseCondition()
	{
		conditionCounter = conditionCounter - 1;
		if(conditionCounter == 45)
			this.condition = Condition.GOOD;
		if(conditionCounter == 30)
			this.condition = Condition.USED;
		if(conditionCounter == 15)
			this.condition = Condition.POOR;
		if(conditionCounter < 0) 
			return false;                       
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
			conditionCounter = 45;
		if(this.condition == Condition.USED)
			conditionCounter = 30;
		if(this.condition == Condition.POOR)
			conditionCounter = 15;
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
	
	public boolean returnBook()   //graamata ir atpakal biblioteekaa - tai zuud condition,datumi,reader
	{
		if(!inLibrary)
		{
			inLibrary = true;
			reader = null;
			takenDate = null;
			returnDate = null;
			decreaseCondition();
			System.out.println("returned book" + this.getId());
			return true;
		}
		return false;
	}
	
	public boolean giveBook(Reader reader)
	{
		if(inLibrary)
		{
			inLibrary = false;
			this.reader = reader;
			Date today = new Date();
			Date returnBy = new Date();
			returnBy.setDate(today.getDate()+7);
			this.takenDate = today;
			this.returnDate = returnBy;
			System.out.println("gave book " + this.getId() + " to " + reader.getName());
			return true;
		}
		return false;
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
	
	public void addReview(Review review)
	{
		if(!this.review.contains(review))
			this.review.add(review);
	}
	
	public String toString()
	{
		String autori = "";
		for(Author a : authors)
		{
			autori += a.toString() + ", ";
		}
		autori = autori.substring(0, autori.length()-2);
		String inLib = "In Library";
		if(!inLibrary)
			inLib = "Taken";
			
		return title + "\nWritten By: " + autori +  "\nGenre: " + genre + "\nCondition: " + condition + inLib; 
	}


	//
}
