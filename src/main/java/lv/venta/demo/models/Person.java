package lv.venta.demo.models;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.demo.utils.Verification;
@Getter @Setter @NoArgsConstructor
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value=AccessLevel.PRIVATE)
	private int id;
	
	private String name;
	
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
