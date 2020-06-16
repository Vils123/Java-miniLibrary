package lv.venta.demo.models;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    public Reader(String name, String surname, Date date, String username, String password,
            ArrayList<Book> currentBooks, ArrayList<Book> allBooks) {
        super(name, surname, date);
        this.username = username;
        this.password = password;
        this.currentBooks = currentBooks;
        this.allBooks = allBooks;
    }

	@Override
	public String toString() {
		return "Reader [allBooks=" + allBooks + ", currentBooks=" + currentBooks + ", password=" + password
				+ ", username=" + username + "]";
	}

    }



    

}