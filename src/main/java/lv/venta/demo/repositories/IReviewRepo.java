package lv.venta.demo.repositories;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import lv.venta.demo.models.Review;

public interface IReviewRepo extends CrudRepository<Review, Integer> {

    Review findById(int id);
    ArrayList<Review> findByWriterId(int id);
    boolean existsByWriterId(int id); // ?
    ArrayList<Review> findAllByWriterId(int id);
    ArrayList<Review> findAllByCommentDate(Date date);
    
}