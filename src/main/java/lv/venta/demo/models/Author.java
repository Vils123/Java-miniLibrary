//Author class - authors of books
package lv.venta.demo.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.demo.enums.Genre;

@Getter 
@Setter 
@NoArgsConstructor
@Table
@Entity(name="Author_information")
public class Author extends Person implements Serializable{

	
	@Column(name="Country_Of_Origin")
	@Size(min=3, max=30)
	@Pattern(regexp="[a-zA-Z\\s]+$")
	private String countryOfOrigin;
	

	@Column(name="Background")
	private String shortBackground;
	

	@ManyToMany(mappedBy = "authors")
	private Collection<Book> writtenBooks;
	

	@Column(name="Literature_style")
	@Size(min=3, max=30)
	@Pattern(regexp="[a-zA-Z\\s]+$"
			+ "")
	private String literatureStyle;


	@Column(name="Genre")
	private Genre mainGenre;


	@Column(name="Date_Of_Death")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "mm-yyyy") 
	private Date dateOfDeath = null;
	

	public Author(String name, String surname, Date dateOfBirth, String country, String story, String style, Genre mainGenre, Date deathDate)
	{
		super(name, surname, dateOfBirth);
		this.countryOfOrigin = country;
		this.shortBackground = story;
		this.literatureStyle = style;
		this.mainGenre = mainGenre;
		if(deathDate != null)
		{
			if(deathDate.after(new Date()))
				this.dateOfDeath = deathDate;
		}
	}

	
	public void addBook(Book book)          
	{
		if(!writtenBooks.contains(book.getIsbn()))
			writtenBooks.add(book);
	}
}
