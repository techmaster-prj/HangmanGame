package us.bssoft.hangman;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import us.bssoft.config.CustomConfigXML;
import us.bssoft.data.*;


import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;

public class PlayActivity extends Activity implements AnimationListener{
	Animation aniFadeIn, aniFadeOut;
	TextView lbLevel, txtScore, txtTries, txtClock, lbYouWin, lbYouLose, lbBigScore, lbBigScore2, lbAnswer, lbAnswer2, lbYouVictory;
	ImageButton btnHelp01, btnHelp02, btnHelp03, btnBack, btnOption;
	ImageView imgHangman;
	LinearLayout llTextGame, llGame, llYouWin, llYouLose, llVictory;
	GridView gvKeyboard;
	Typeface tf;
	MediaPlayer sound;
	int tries, countCorrect, currentLevel, totalLevel, currentHelp;
	long currentTime;
	private int firstScore;
	private int lastScore;
	private int firstScoreHelp;
	private int lastScoreHelp;
	String data;
	ArrayList<String> txtGame;
	ArrayList<Data> arrayData;
	CounterClass timer;
	Random randomItem;
	static final String[] key = new String[]{"Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M"};
	static final Integer[] imgShow = new Integer[]{R.drawable.hang6, R.drawable.hang5, R.drawable.hang4, R.drawable.hang3, R.drawable.hang2, R.drawable.hang1, R.drawable.hang0};
	double[] percent = new double[]{100, 150, 300}; 
	private AdView adView;
	static final String STATE_LEVEL = "currentLevel";
	static final String STATE_HELP = "currentHelp";
	static final String STATE_TIME = "currentTime";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		initUI();
		StartGame();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		if(llYouWin.getVisibility()==View.GONE&&llYouLose.getVisibility()==View.GONE&&llVictory.getVisibility()==View.GONE)
		{
			timer = new CounterClass(currentTime,1000);
			timer.start();
		}
		super.onRestart();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		// Font path
        String fontPath = "fonts/ARCENA.TTF";
        
        // text view label
        lbLevel = (TextView) findViewById(R.id.lbLevel);
        llGame = (LinearLayout) findViewById(R.id.llGame);
        llTextGame = (LinearLayout) findViewById(R.id.llTextGame);
        llYouWin = (LinearLayout) findViewById(R.id.llYouWin);
        llYouLose = (LinearLayout) findViewById(R.id.llYouLose);
        llVictory = (LinearLayout) findViewById(R.id.llYouVictory);
        gvKeyboard = (GridView) findViewById(R.id.gvKeyboard);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtTries= (TextView) findViewById(R.id.txtTries);
        txtClock = (TextView) findViewById(R.id.txtClock);
        btnHelp01 = (ImageButton) findViewById(R.id.btnHelp01);
        btnHelp02 = (ImageButton) findViewById(R.id.btnHelp02);
        btnHelp03 = (ImageButton) findViewById(R.id.btnHelp03);
        btnBack =  (ImageButton) findViewById(R.id.btnBack);
        btnOption =  (ImageButton) findViewById(R.id.btnOption);
        lbYouWin = (TextView) findViewById(R.id.lbYouWin);
        lbYouLose = (TextView) findViewById(R.id.lbYouLose);
        lbBigScore = (TextView) findViewById(R.id.lbBigScore);
        lbBigScore2 = (TextView) findViewById(R.id.lbBigScore2);
        lbAnswer = (TextView) findViewById(R.id.lbAnswer);
        lbAnswer2 = (TextView) findViewById(R.id.lbAnswer2);
        lbYouVictory = (TextView) findViewById(R.id.lbYouVictory);
        imgHangman = (ImageView) findViewById(R.id.imgHangman);
        adView = (AdView)this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest());
        // Loading Font Face
        tf = Typeface.createFromAsset(getAssets(), fontPath);
        
     	// Applying font
        lbLevel.setTypeface(tf);
        lbYouWin.setTypeface(tf);
        lbYouLose.setTypeface(tf);
        lbBigScore.setTypeface(tf);
        lbBigScore2.setTypeface(tf);
        lbAnswer.setTypeface(tf);
        lbAnswer2.setTypeface(tf);
        lbYouVictory.setTypeface(tf);
        
        aniFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_top_show_play);
        aniFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_top_hide_play);
        currentLevel = 1;
        currentHelp = 0;
        parseXML();
	}
	
	private void StartGame() {
		ArrayList<Data> currentData = new ArrayList<Data>();
		for (int i = 0; i < arrayData.size(); i++) {
			if(arrayData.get(i).getLevel() == currentLevel)
				currentData.add(arrayData.get(i));
		}
		
		txtGame = new ArrayList<String>();
		tries = 6;
		countCorrect = 0;
		randomItem = new Random();
		int position = randomItem.nextInt(currentData.size());
		//Toast.makeText(PlayActivity.this, String.valueOf(arrayData.size()), Toast.LENGTH_SHORT).show();
		data = currentData.get(position).getName().toUpperCase();
		//data = "MONKEY";
		txtGame.clear();
		llTextGame.removeAllViews();
		btnHelp01.setEnabled(true);
		btnHelp01.setBackgroundResource(R.drawable.btn_help);
		btnHelp02.setEnabled(true);
		btnHelp02.setBackgroundResource(R.drawable.btn_help);
		btnHelp03.setEnabled(true);
		btnHelp03.setBackgroundResource(R.drawable.btn_help);
		imgHangman.setBackgroundResource(R.drawable.hang0);
		LoadText();
		LoadKeyboard();
		if(timer != null) {
	        timer.cancel();
	    }
		timer = new CounterClass(60000,1000);  
		timer.start();
	}
	
	private void LoadText() {
		
		for (int i = 0; i < data.length(); i++) {
			txtGame.add(data.substring(i, i+1));
		}
		for (int i = 0; i < txtGame.size(); i++) {
			TextView txt = new TextView(PlayActivity.this);	
			txt.setPadding(5, 0, 5, 0);
			txt.setTextAppearance(PlayActivity.this, R.style.lbGame);
			txt.setTypeface(tf);
			txt.setText("_");
			llTextGame.addView(txt);
		}
		
		txtTries.setText(String.valueOf(tries));
	}
	
	private void LoadKeyboard() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_keyboard, key);
		gvKeyboard.setAdapter(adapter);
		gvKeyboard.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int postion,
					long id) {
				// TODO Auto-generated method stub
				TextView btn = (TextView) v;
				if(btn.getText() != "")
				{
					try{
						List<Integer> pos = getPositionKey(btn.getText().toString());
						
						if(pos.size() > 0)
						{
							callSound(R.raw.correct);
							for (int i = 0; i < pos.size(); i++) {
								TextView t = (TextView) llTextGame.getChildAt(pos.get(i));
								t.setText(btn.getText().toString());
								countCorrect++;
							}
							if(countCorrect == txtGame.size()){
								if(currentLevel == totalLevel)
									GameStatus(2);
								else
									GameStatus(1);
							}
						}
						else{
							callSound(R.raw.click);
							if(tries == 0){
								GameStatus(0);
							}
							else{
								tries = tries - 1;
								imgHangman.setBackgroundResource(imgShow[tries]);
								txtTries.setText(String.valueOf(tries));
							}
						}
						btn.setEnabled(false);
						btn.setText("");
						btn.setBackgroundResource(R.drawable.bg_gray);
					}
					catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(PlayActivity.this, "error", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
	
	public void onClickBack(View v) {
		callSound(R.raw.click);
		AlertDialog.Builder n = new AlertDialog.Builder(this);
		n.setTitle("Warning");
		n.setMessage("If you back it now, you must start again");
		n.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				timer.cancel();
				finish();
			}
		});
		n.setPositiveButton("Cancel",  new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		n.show();
	}
	
	public void onClickOption(View v) {
		callSound(R.raw.click);
		Intent i = new Intent(PlayActivity.this, OptionActivity.class);
		startActivity(i);
		
		//finish();
	}
	
	public void onClickHelp(View v) {
		callSound(R.raw.help);
		
		ImageButton btn = (ImageButton) v;
		if(currentLevel > 1 && Integer.parseInt(txtScore.getText().toString()) >= percent[currentHelp]){
			btn.setEnabled(false);
			btn.setBackgroundResource(R.drawable.help_icon_gray);
			String save = null;
			for (int i = 0; i < txtGame.size(); i++) {
				TextView txt = (TextView) llTextGame.getChildAt(i);
				if(txt.getText().equals("_"))
				{
					//txt.setText(txtGame.get(i));
					save = txtGame.get(i);
					break;
				}
			}
			for (int j = 0; j < key.length; j++) {
				TextView txt2 = (TextView)gvKeyboard.getChildAt(j);
				if(txt2.getText().equals(save))
				{
					txt2.setEnabled(false);
					txt2.setText("");
					txt2.setBackgroundResource(R.drawable.bg_gray);
					break;
				}
			}
			
			firstScoreHelp = (currentHelp>0)?lastScoreHelp:Integer.parseInt(txtScore.getText().toString().trim());
			lastScoreHelp = (int)(firstScoreHelp - percent[currentHelp]);
			//txtScore.setText(String.valueOf(score));
			new CountDownTimer(500, 1)
			{
			    public void onTick(long millisUntilFinished) {txtScore.setText(""+firstScoreHelp); firstScoreHelp--;}
			    public void onFinish() {txtScore.setText(""+lastScoreHelp);}
			}.start();
			currentHelp++;
			
			List<Integer> pos = getPositionKey(save);
			if(pos.size() > 0)
			{
				for (int i = 0; i < pos.size(); i++) {
					TextView t = (TextView) llTextGame.getChildAt(pos.get(i));
					t.setText(save);
					countCorrect++;
				}
				if(countCorrect == txtGame.size()){
					if(currentLevel == totalLevel)
						GameStatus(2);
					else
						GameStatus(1);
				}
			}
			
		}
		else{
			if(currentLevel == 1){
				btn.setEnabled(false);
				btn.setBackgroundResource(R.drawable.help_icon_gray);
				String save = null;
				for (int i = 0; i < txtGame.size(); i++) {
					TextView txt = (TextView) llTextGame.getChildAt(i);
					if(txt.getText().equals("_"))
					{
						//txt.setText(txtGame.get(i));
						save = txtGame.get(i);
						break;
					}
				}
				for (int j = 0; j < key.length; j++) {
					TextView txt2 = (TextView)gvKeyboard.getChildAt(j);
					if(txt2.getText().equals(save))
					{
						txt2.setEnabled(false);
						txt2.setText("");
						txt2.setBackgroundResource(R.drawable.bg_gray);
						break;
					}
				}
				List<Integer> pos = getPositionKey(save);
				if(pos.size() > 0)
				{
					for (int i = 0; i < pos.size(); i++) {
						TextView t = (TextView) llTextGame.getChildAt(pos.get(i));
						t.setText(save);
						countCorrect++;
					}
					if(countCorrect == txtGame.size()){
						if(currentLevel == totalLevel)
							GameStatus(2);
						else
							GameStatus(1);
					}
				}
				currentHelp++;
			}
			else{
				Toast.makeText(PlayActivity.this, "You haven't enough points to request help", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	private List<Integer> getPositionKey(String a) {
		List<Integer> arrayPosition = new ArrayList<Integer>();
		for (int i = 0; i < txtGame.size(); i++) {
			if(txtGame.get(i).equals(a))
			{
				arrayPosition.add(i);	
			}
		}
		return arrayPosition;
	}
	
	private void parseXML() {
		// TODO Auto-generated method stub
		AssetManager assetManager = getBaseContext().getAssets();
        try {
            InputStream is = assetManager.open("data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
 
            DataXML myXMLHandler = new DataXML();
            xr.setContentHandler(myXMLHandler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);
            arrayData = myXMLHandler.getList();
            totalLevel = myXMLHandler.getTotalLevel();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private void GameStatus(int a) {
		if(a == 1) //you win
		{
			callSound(R.raw.you_win);
			lbBigScore.setText(txtScore.getText());
			firstScore = (currentHelp > 0)? lastScoreHelp:Integer.parseInt(txtScore.getText().toString().trim());
			//count point for time
			String[] lastTime = txtClock.getText().toString().split(":");
			int pointTime = (Integer.parseInt(lastTime[0])*60 + Integer.parseInt(lastTime[1]));
			//count point for tries
			int pointTries = Integer.parseInt(txtTries.getText().toString())*10;
			//count point for level
			int pointLevel = (currentLevel > 1)?75:500;
			//Total point
			lastScore = firstScore + pointTime + pointTries + pointLevel;
			llYouWin.setVisibility(View.VISIBLE);
			//llYouWin.startAnimation(aniFadeIn);
			new CountDownTimer(2000, 1)
			{
			    public void onTick(long millisUntilFinished) {lbBigScore.setText(""+firstScore); firstScore++;}
			    public void onFinish() {lbBigScore.setText(""+lastScore);}
			}.start();
			lbAnswer.setText(data);
		}
		else if(a == 0){//you lose
			callSound(R.raw.you_lose);
			llYouLose.setVisibility(View.VISIBLE);
			lbBigScore2.setText(lbLevel.getText().toString());
			lbAnswer2.setText(data);
			CustomConfigXML xml = new CustomConfigXML(this);
			xml.writeXML(currentLevel);
			SharedPreferences share = getSharedPreferences("config",0);
			boolean check_rating = share.getBoolean("Rate", false);
			if(!check_rating){
				AlertDialog.Builder n = new AlertDialog.Builder(this);
				n.setTitle("Hi");
				n.setMessage("Please help me have some feedback and rating good about this app on market now. Thank you so much!!");
				n.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						RatingGame();
					}
				});
				n.setPositiveButton("Rating late",  new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				n.show();
			}
		}
		else{
			callSound(R.raw.victory);
			llVictory.setVisibility(View.VISIBLE);
			CustomConfigXML xml = new CustomConfigXML(this);
			xml.writeXML(currentLevel++);
		}
		adView.setVisibility(View.VISIBLE);
		isEnable(false);
		timer.cancel();
	}
	
	private void CheckOut(int a) {
		//Toast.makeText(this, "test_"+currentLevel, Toast.LENGTH_SHORT).show();
		if(a > 0)
		{
			if(currentLevel < totalLevel)
			{
				currentLevel++;
				//llYouWin.startAnimation(aniFadeOut);
				llYouWin.setVisibility(View.GONE);
				StartGame();
				lbLevel.setText("Level "+currentLevel);
				txtScore.setText(String.valueOf(lastScore));
			}
			else{
				
			}
		}
		else{
			currentLevel=1;
			firstScore = 0;
			firstScoreHelp = 0;
			lastScoreHelp = 0;
			lastScore = 0;
			lbLevel.setText("Level 1");
			llYouLose.setVisibility(View.GONE);
			txtScore.setText("0");
			StartGame();
		}
		isEnable(true);
		adView.setVisibility(View.GONE);
		currentHelp = 0;
	}
	
	private void isEnable(boolean check) {
		gvKeyboard.setEnabled(check);
		btnHelp01.setEnabled(check);
		btnHelp02.setEnabled(check);
		btnHelp03.setEnabled(check);
		btnBack.setEnabled(check);
		btnOption.setEnabled(check);
	}
	
	public void onClickReStart(View v) {
		CheckOut(0);
	}
	
	public void onClickNextLevel(View v) {
		CheckOut(1);
	}
	
	public class CounterClass extends CountDownTimer {  
        public CounterClass(long millisInFuture, long countDownInterval) {  
             super(millisInFuture, countDownInterval);  
        }  
        @Override  
       public void onFinish() {  
        	txtClock.setText("00:00");
        	GameStatus(0);
         //textViewTime.setText("Completed.");  
       }  
       @Override  
       public void onTick(long millisUntilFinished) {  
              currentTime = millisUntilFinished;  
               String hms = String.format("%02d:%02d",  
                   TimeUnit.MILLISECONDS.toMinutes(currentTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(currentTime)),  
                   TimeUnit.MILLISECONDS.toSeconds(currentTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentTime)));   
               txtClock.setText(hms);  
        }  
    }
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//StartGame();
	}
	
	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		timer.cancel();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (adView != null) {
		      adView.destroy();
		}
		timer.cancel();
		CustomConfigXML xml = new CustomConfigXML(this);
		xml.writeXML(currentLevel);
		super.onDestroy();
	}
	
	public void RatingGame() {
		SharedPreferences sharedPref = getSharedPreferences("config",0);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean("Rate", true);
		editor.commit();
		Intent intent = new Intent(Intent.ACTION_VIEW);
	    //Try Google play
	    intent.setData(Uri.parse("market://details?id=us.bssoft.hangman"));
	    if (MyStartActivity(intent) == false) {
	        //Market (Google play) app seems not installed, let's try to open a webbrowser
	        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=us.bssoft.hangman"));
	        if (MyStartActivity(intent) == false) {
	            //Well if this also fails, we have run out of options, inform the user.
	            Toast.makeText(this, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
	        }
	    }
	}
	
	private boolean MyStartActivity(Intent aIntent) {
	    try
	    {
	        startActivity(aIntent);
	        return true;
	    }
	    catch (ActivityNotFoundException e)
	    {
	        return false;
	    }
	}
	
	private void callSound(int s) {
		SharedPreferences share = getSharedPreferences("config",0);
		boolean check_sound = share.getBoolean("Sound", true);
		if(check_sound)
		{
			sound = MediaPlayer.create(getApplicationContext(), s);
			sound.setVolume(1,1);
			sound.start();
			sound.setOnCompletionListener(new OnCompletionListener() {
			    public void onCompletion(MediaPlayer mp) {
			        mp.release();
	
			    };
			});
		}
	}
	
	@Override
	public void onAnimationEnd(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub
		
	}
}
