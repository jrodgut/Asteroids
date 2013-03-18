package org.example.asteroids.services;

import java.util.ArrayList;
import java.util.List;

import org.example.asteroids.model.RankingPosition;

public class MemoryRankingService implements RankingService{
	
	private static List<RankingPosition> positions = new ArrayList<RankingPosition>();
	
	public void saveRankingPosition(RankingPosition rankingPosition){
		positions.add(rankingPosition);
	}
	
	public List<RankingPosition> retrieveRaking(int size){
		//TODO remove. added just to test interface
		if(positions.isEmpty()){
			positions.add(new RankingPosition("Juan Marcos", 127));
			positions.add(new RankingPosition("Pedro López", 138));
			positions.add(new RankingPosition("L. P. J. K.", 425));
		}
		return positions;
	}
}
