package lv.venta.demo.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

@Getter
@Setter
@NoArgsConstructor
@Table
@Entity(name = "All reviews")
public class Review {

    @OneToMany(mappedBy = "review") 
    private Collection<Book> visasGramatas;


    ArrayList <Review> allReviewsByUsers = new ArrayList<>();


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Review_Id")
    @Setter(value = AccessLevel.PRIVATE)
    private int id;

    @Column(name = "Rating aspect 1")
    @Min(1) @Max(10)
    int rating1;

    @Column(name = "Rating axpect 2")
    @Min(1)
    @Max(10)
    int rating2;

    @Column(name = "rating aspect 3")
    @Min(1)
    @Max(10)
    int rating3;

    @Column(name = "Total this review score")
    private double review_total = (rating1 + rating2 + rating3); // 3;  

    @Column(name = "rating aspect 3")
    private double bookOveralScore;

    @Column(name = "Review Description")
    String thoughts;

    @Column(name = "Comment Post Date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date commentDate = null;

    public Review (Book book, int r1 , int r2 , int r3 , String thoughts ){
        
        setRating1(r1);
        setRating2(r2);
        setRating3(r3);
        setThoughts(thoughts);
        setCommentDate(new Date());
        
    }

}