package com.example.restoranservice.repository;

import com.example.restoranservice.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    @Modifying
    @Query(value = "UPDATE rates SET common_voted_qty = :commonVotedQty , like_voted_qty = :likeVotedQty WHERE dish_id = :dishId ", nativeQuery = true)
    void updateDishRate(@Param("dishId") long dishId, @Param("commonVotedQty") int commonVotedQty, @Param("likeVotedQty") int likeVotedQty);
    Optional<Rate> findRateByDishId(long id);
}
