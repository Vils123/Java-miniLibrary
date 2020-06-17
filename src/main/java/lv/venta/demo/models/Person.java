package lv.venta.demo.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.demo.utils.Verification;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Getter @Setter @NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value=AccessLevel.PRIVATE)
	@Column(name = "Id")
	private int id;
	
	@Column(name="Name")
	@Size(min=3, max=30)
	@Pattern(regexp="[a-zA-Z\\s]+$")
	private String name;
	
	@Column(name="Surname")
	@Size(min=3, max=30)
	@Pattern(regexp="[a-zA-Z\\s]+$")
	private String surname;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date dateOfBirth;
	
	public Person(String name, String surname, Date date)
	{
		if(date.before(new Date()))      //if the person is born
		{
			setName(name);
			setSurname(surname);
			this.dateOfBirth = date;
		}
		
	}
	
	public void setName(String name)
	{
		this.name = Verification.verifyName(name);
	}

	public void setSurname(String surname)
	{
		this.surname = Verification.verifyName(surname);
	}
	
	public void setDateOfBirth(Date date)
	{
		if(date.before(new Date()))
		{
			this.dateOfBirth = date;
		}
	}
	
	public String toString()              //no date here for now, maybe later
	{
		return name + " " + surname;
	}
}
