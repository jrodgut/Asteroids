package org.example.asteroids.services;

import java.util.List;

import org.example.asteroids.model.RankingPosition;

public interface RankingService {
	
	public void saveRankingPosition(RankingPosition rankingPosition);
	
	public List<RankingPosition> retrieveRaking(int size);
}
