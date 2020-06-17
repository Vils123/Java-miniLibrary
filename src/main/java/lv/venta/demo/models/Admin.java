package lv.venta.demo.models;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Admin extends Reader{

	
	public Admin(String name, String surname, Date date, String username, String password)
	{
    	super(name,surname,date,username,password);
	}

	
	
	public String toString()
	{
		return "Admin " + super.getName() + " " + super.getSurname(); 
	}
}
