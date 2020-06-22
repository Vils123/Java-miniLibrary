package lv.venta.demo.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.demo.models.Review;

public interface IReviewRepo extends CrudRepository<Review, Integer> {

    Review findById(int id);
    ArrayList<Review> findByUserId(int id);
    boolean existsByWriterId(int id); // ?
    ArrayList<Review> findAllByWriterId(int id);
    
}