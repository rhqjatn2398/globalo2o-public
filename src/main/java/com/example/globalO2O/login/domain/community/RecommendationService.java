package com.example.globalO2O.login.domain.community;

import com.example.globalO2O.login.domain.event.RecommendationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public RecommendationService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @EventListener
    public void add(RecommendationEvent event) {
        recommendationRepository.save(Recommendation.builder()
                .user(event.getUser())
                .board(event.getBoard())
                .build());
    }
}
