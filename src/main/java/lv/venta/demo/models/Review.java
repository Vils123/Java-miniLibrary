package lv.venta.demo.models;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table
@Entity(name = "All reviews")
public class Review {

    @OneToMany
    //one review to many books (to many books by id)(to one book by title)
    //because we use id to give books out it has to be one to many

    ArrayList <Review> allReviewsByUsers = new ArrayList<>();

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
    private double review_total = (rating1 + rating2 + rating3)/ 3;  

    @Column(name = "rating aspect 3")
    private double bookOveralScore;

    @Column(name = "Review Description")
    String thoughts;

    public Review (String book , int r1 , int r2 , int r3 , String thoughts ){
        setRating1(r1);
        setRating2(r2);
        setRating3(r3);
        setThoughts(thoughts);
    }

    @Override
    public String toString() {
        return "";
    }


}