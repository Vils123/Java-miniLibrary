package lv.venta.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import lv.venta.demo.models.Review;

public interface IReviewRepo extends CrudRepository<Review, Integer> {

    
    
}