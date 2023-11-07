package com.example.restoranservice.service.impl;

import com.example.restoranservice.model.Rate;
import com.example.restoranservice.repository.RateRepository;
import com.example.restoranservice.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;

    @Transactional
    public void updateDishRate(long dishId, boolean isLiked){
        Optional<Rate> rate = rateRepository.findRateByDishId(dishId);
        if (rate.isPresent()) {
            rateRepository.updateDishRate(dishId, rate.get().getCommonVotedQty() + 1, isLiked ? rate.get().getLikeVotedQty() + 1 : rate.get().getLikeVotedQty());
        } else {
            Rate newRate = new Rate(dishId, 1, isLiked ? 1 : 0);
            rateRepository.save(newRate);
        }
    }
}
