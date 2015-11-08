 package us.bssoft.history;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import us.bssoft.tools.JSONParser;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class HistoryDB {
    ArrayList<History> listHistory;
    JSONParser jParser = new JSONParser();
    // url to get all products list
    private static String url_top10 = "http://192.168.56.1/hangman/index.php?type=history";
    //private static String url_addHistory = "http://192.168.56.1/hangman/index.php?type=add_history";
 
    // JSON Node names
    private static final String TAG_HISTORY = "history";
    private static final String TAG_ID = "id";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_LEVEL = "level";
    private static final String TAG_SCORE = "score";
 
    // products JSONArray
    JSONArray history = null;
    public HistoryDB() {
    	
		// TODO Auto-generated constructor stub
    	listHistory = new ArrayList<History>();
    	new LoadAllHistory().execute();
	}
    
    public ArrayList<History> getListRanking() {
		return listHistory;
	}
    
    class LoadAllHistory extends AsyncTask<String, String, String> {
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}
    	
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = jParser.makeHttpRequest(url_top10, "GET", params);
			
			//json.length();
			try {
					// products found
					// Getting Array of Products
					//Log.d("JSON Parser", "" + json);
					history = json.getJSONArray(TAG_HISTORY);
					Log.d("JSON Parser", ""+history.length());
					// looping through All Products
					for (int i = 0; i < history.length(); i++) {
						JSONObject c = history.getJSONObject(i);

						// Storing each json item in variable
						int id = Integer.parseInt(c.getString(TAG_ID));
						String email = c.getString(TAG_EMAIL);
						int level = Integer.parseInt(c.getString(TAG_LEVEL));
						int score = Integer.parseInt(c.getString(TAG_SCORE));

						// creating new HashMap
						//HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						History h = new History(id,email,level,score);
						
						// adding HashList to ArrayList
						listHistory.add(h);
					}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
    	
		
    }
}
