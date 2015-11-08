package us.bssoft.hangman;

import java.util.ArrayList;

import us.bssoft.history.History;
import us.bssoft.history.HistoryAdapter;
import us.bssoft.history.HistoryDB;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RankingActivity extends Activity {
	TextView lbHeader, lbTop, lbName, lbLevel, lbScore;
	ListView list;
	private ArrayList<History> arr;
	private HistoryAdapter arrayAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking);
		initUI();
		HistoryDB db = new HistoryDB();
		arr = db.getListRanking();
		arrayAdapter = new HistoryAdapter(this, R.layout.item_ranking, arr);
        list.setAdapter(arrayAdapter);
		Toast.makeText(RankingActivity.this, arr.get(0).getEmail(), Toast.LENGTH_SHORT).show();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		// Font path
        String fontPath = "fonts/ARCENA.TTF";
        
        // text view label
        lbHeader = (TextView) findViewById(R.id.lbHeader);
        lbTop = (TextView) findViewById(R.id.lbTop);
        lbName = (TextView) findViewById(R.id.lbName);
        lbLevel = (TextView) findViewById(R.id.lbLevel);
        lbScore = (TextView) findViewById(R.id.lbScore);
        list = (ListView) findViewById(R.id.list);
        
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        
     	// Applying font
        lbHeader.setTypeface(tf);
        lbTop.setTypeface(tf);
        lbName.setTypeface(tf);
        lbLevel.setTypeface(tf);
        lbScore.setTypeface(tf);
	}
	
	public void onClickBack(View v) {
		finish();
	}
	
	public void onClickOption(View v) {
		Intent i = new Intent(RankingActivity.this, OptionActivity.class);
		startActivity(i);
		finish();
	}
}
