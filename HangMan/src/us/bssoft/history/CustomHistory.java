package us.bssoft.history;

import us.bssoft.hangman.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomHistory extends LinearLayout{
	TextView name, level, score;
	Context context;
	public CustomHistory(Context context) {
		super(context);
		this.context = context;
		LayoutInflater li = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.item_ranking, this,true);
		name = (TextView) findViewById(R.id.lbName);
		level = (TextView) findViewById(R.id.lbLevel);
		score = (TextView) findViewById(R.id.lbScore);
	}
}
