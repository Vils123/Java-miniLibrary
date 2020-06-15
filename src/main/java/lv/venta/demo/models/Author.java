package lv.venta.demo.models;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.demo.enums.Genre;
@Getter @Setter @NoArgsConstructor
public class Author extends Person{
	
	private String countryOfOrigin;
	
	private String shortBackground;
	
	private ArrayList<Book> writtenBooks = new ArrayList<Book>();
	
	private String literatureStyle;
	
	private Genre mainGenre;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy")
	private Date dateOfDeath;
	
	public Author(String name, String surname, Date dateOfBirth, String country, String story, String style, Genre mainGenre, Date deathDate)
	{
		super(name, surname, dateOfBirth);
		this.countryOfOrigin = country;
		this.shortBackground = story;
		this.literatureStyle = style;
		this.mainGenre = mainGenre;
		if(deathDate.after(new Date()))
			this.dateOfDeath = deathDate;
	}
	
	public void addBook(Book book)           // NEEDS SUM WORK THO
	{
		if(!writtenBooks.contains(book))
			writtenBooks.add(book);
	}
}
