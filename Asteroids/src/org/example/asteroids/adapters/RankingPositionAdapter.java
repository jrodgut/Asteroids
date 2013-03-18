package org.example.asteroids.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.example.asteroids.R;
import org.example.asteroids.model.RankingPosition;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RankingPositionAdapter extends BaseAdapter{
	
	private final Activity activity;
	
	private final List<RankingPosition> ranking;
	
	public RankingPositionAdapter(Activity activity, List<RankingPosition> ranking){
		super();
		this.activity = activity;
		this.ranking = ranking;
	}
	

	@Override
	public int getCount() {
		return ranking.size();
	}

	@Override
	public Object getItem(int index) {
		return ranking.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		 LayoutInflater inflater = activity.getLayoutInflater();
		 RankingPosition rankingPosition = (RankingPosition) getItem(index);
		 
         View view = inflater.inflate(R.layout.list_element, null, true);
         
         TextView userView =(TextView)view.findViewById(R.id.list_user);
         userView.setText(rankingPosition.getName());
         
         TextView pointsView =(TextView)view.findViewById(R.id.list_points);
         StringBuilder sb = new StringBuilder(activity.getResources().getString(R.string.points));
         sb
         	.append(": ")
         	.append(rankingPosition.getPoints());
         pointsView.setText(sb.toString());
         
         TextView dateView =(TextView)view.findViewById(R.id.list_date);
         Date date = rankingPosition.getCreationDate();
         DateFormat df = SimpleDateFormat.getDateInstance();
         String formattedDate = df.format(date);
         dateView.setText(formattedDate);
         
         ImageView imageView=(ImageView)view.findViewById(R.id.profile_pic);
         
         
         switch (Math.round((float)Math.random()*3)){
         case 0:
                imageView.setImageResource(android.R.drawable.ic_dialog_info);
                break;
         case 1:
                imageView.setImageResource(android.R.drawable.ic_dialog_alert);
                break;
         default:
                imageView.setImageResource(android.R.drawable.ic_menu_call);
                break;
         }
         return view;
	}

}
