package lv.venta.demo.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "All_reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review implements Serializable {


    //////////////////////////////////////////////
    @ManyToMany(mappedBy = "review") //1111111111
    private Collection<Book> allBooksReviewPost; 
    // One review to many books (because id different for each book with same name)
    //////////////////////////////////////////////
    @ManyToOne //2222222222222222222
    @JoinColumn(name = "reader_id")  // so it is  possible to find reviews by reader id !?
    private Reader writer;           //reader can write many separate reviews
    //////////////////////////////////////////////

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Review_Id")
    @Setter(value = AccessLevel.PRIVATE)
    private int id;

    
    @Column(name = "Book_Title")
    private String title;
    
    @Column(name = "Rating_aspect_1")
    @Min(1) @Max(10)
    private int rating1 =1 ;

    @Column(name = "Rating_axpect_2")
    @Min(1)
    @Max(10)
    private int rating2 = 1;

    @Column(name = "rating_aspect_3")
    @Min(1)
    @Max(10)
    private int rating3 = 1 ;

    @Column(name = "Total_this_review_score")
    private double review_total = 0.0d; // 3;  

    // @Column(name = "Book total rating by users")
    // private double bookOveralScore;

    @Column(name = "Review_Description")
    String thoughts;

    @Column(name = "Comment_Post_Date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date commentDate = null;

    public Review (Reader reader, Book book, int r1 , int r2 , int r3 , String thoughts ){

        //if(postReview(reader, book)){
            setRating1(r1);
            setRating2(r2);
            setRating3(r3);
            setReview_total((r1+r2+r3)/3);
            setThoughts(thoughts);
            setCommentDate(new Date());
            setTitle(book.getTitle());
            postReview(reader, book); 
        //}
        //make repo ad in repo to allreviews
        
    }

    ///should this be here ....
    public boolean postReview (Reader reader, Book book){

        if(reader.getBooksReadAndReturned().contains(book)){

            for(Book temp : Book.allBooksInLibrary){
                if(temp.getTitle().equals(book.getTitle())){
                    allBooksReviewPost.add(temp);  // pieliek pie sasaistes
                }
            } 
            this.writer = reader;  // pieliek pie sasaistes
            return true;
        }  
        return false; 
        //
    }

}