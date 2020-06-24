//Admin class aka the Librarian
package lv.venta.demo.models;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "AdminTable")
@Getter @Setter @NoArgsConstructor
public class Admin extends Person{

	@Column(name = "Username")
    private String username;

    @Column(name = "Password")
	private String password;
	
	@Transient
    private static ArrayList<String> takenUsernames = new ArrayList<String>();          //usernames have to be unique
	

	public Admin(String name, String surname, Date date, String username, String password){
		super(name,surname,date);
		
    	if(!takenUsernames.contains(username))
    	{
    		this.username = username;
    		this.password = password;
    		takenUsernames.add(username);
    	}
    	

    }

	
	public String toString()
	{
		return "Admin " + super.getName() + " " + super.getSurname(); 
	}


	//
}
