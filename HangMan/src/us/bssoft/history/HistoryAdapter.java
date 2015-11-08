package us.bssoft.history;

import java.util.ArrayList;



import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryAdapter extends ArrayAdapter<History>{
	private Context context;
	ArrayList<History> array;
	History history;
	int resource;
    public HistoryAdapter(Context context, int textViewResourceId, ArrayList<History> array) {
		super(context, textViewResourceId , array);
		this.context = context;
        this.array = array;
        this.resource = textViewResourceId;
	}


    public View getView(int position, View convertView, ViewGroup parent) {
    	 View rowView = convertView;
    	 if(rowView ==null ){
    		 rowView = new CustomHistory(getContext());
    	 }
    	 history = array.get(position);
    	 if(history !=null){
    		 TextView name = ((CustomHistory)rowView).name;
	    	 TextView level = ((CustomHistory)rowView).level;
	    	 TextView score = ((CustomHistory)rowView).score;
	    	 // lay doi tuong friend va dua ra UI
	    	 
	    	 name.setText(history.getEmail());
	    	 level.setText(history.getLevel());
	    	 score.setText(history.getScore());
    	 }
    	 return rowView;
    }       
}
