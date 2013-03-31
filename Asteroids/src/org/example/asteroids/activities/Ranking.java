package org.example.asteroids.activities;

import java.util.List;

import org.example.asteroids.R;
import org.example.asteroids.adapters.RankingPositionAdapter;
import org.example.asteroids.model.RankingPosition;
import org.example.asteroids.services.MemoryRankingService;
import org.example.asteroids.services.RankingService;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class Ranking extends ListActivity {

	private static RankingService rankingService;
	private final int RANKING_SIZE = 10;

	public Ranking() {
		if (rankingService == null) {
			rankingService = new MemoryRankingService();
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranking);
		List<RankingPosition> ranking = rankingService
				.retrieveRaking(RANKING_SIZE);
		RankingPositionAdapter adapter = new RankingPositionAdapter(this,
				ranking);

		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int index, long id) {
		super.onListItemClick(listView, view, index, id);
		RankingPosition rp = (RankingPosition) getListAdapter().getItem(index);
		Toast.makeText(	this, rp.getName(), Toast.LENGTH_SHORT).show();
	}

}
