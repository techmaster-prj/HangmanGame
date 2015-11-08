package us.bssoft.hangman;

import com.google.ads.*;

import us.bssoft.config.CustomConfigXML;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

public class MainActivity extends Activity implements AnimationListener{
	Animation animMove,animMove02,animMove03;
	TextView lbHangman;
	Button lbPlay, lbRanking, lbOption, lbExit;
	ImageButton btnCordage;
	LinearLayout list;
	MediaPlayer sound;
	boolean check = false;
	private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        callSound(R.raw.logon);
        CustomConfigXML xml = new CustomConfigXML(this);
        /*//Ma Hoa
		try {
			MCrypt mcrypt = new MCrypt();
			//Encrypt
	        String encrypted;
			encrypted = MCrypt.bytesToHex( mcrypt.encrypt("Text to Encrypt") );
			// Decrypt
	        String decrypted = new String( mcrypt.decrypt( encrypted ) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        // Create an ad.
        
    }
    
    private void initUI() {
    	// Font path
        String fontPath = "fonts/BRUSHSCRIPTSTD.OTF";
        String fontPath2 = "fonts/ARBERKLEY.TTF";
 
        // text view label
        lbHangman = (TextView) findViewById(R.id.lbHangman);
        lbPlay = (Button) findViewById(R.id.lbPlay);
        lbRanking = (Button) findViewById(R.id.lbRanking);
        lbOption = (Button) findViewById(R.id.lbOption);
        lbExit = (Button) findViewById(R.id.lbExit);
        btnCordage = (ImageButton) findViewById(R.id.btnCordage);
        list = (LinearLayout) findViewById(R.id.list);
        adView = (AdView)this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest());
        
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath2);
 
        // Applying font
        lbHangman.setTypeface(tf);
        lbPlay.setTypeface(tf2);
        lbRanking.setTypeface(tf2);
        lbOption.setTypeface(tf2);
        lbExit.setTypeface(tf2);
        
        // load the animation
    	animMove = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
    	animMove02 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_top_show);
    	animMove03 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_bottom_show);
    	
    	// set animation listener
    	animMove.setAnimationListener(this);
    	animMove02.setAnimationListener(this);
    	animMove03.setAnimationListener(this);
	}
    
    public void onClickPlay(View v) {
    	
    	//Toast.makeText(MainActivity.this, gmail, Toast.LENGTH_LONG).show();
    	callSound(R.raw.click);
		Intent i = new Intent(MainActivity.this, PlayActivity.class);
		i.putExtra("play", true);
		startActivityForResult(i, 1);
	}
    
    public void onClickRanking(View v) {
    	callSound(R.raw.click);
    	//Toast.makeText(MainActivity.this, gmail, Toast.LENGTH_LONG).show();
    	Intent i = new Intent(MainActivity.this, LocalActivity.class);
		startActivity(i);
	}

	public void onClickOption(View v) {
		callSound(R.raw.click);
		Intent i = new Intent(MainActivity.this, OptionActivity.class);
		startActivity(i);
	}
	
	public void onClickExit(View v) {
		callSound(R.raw.click);
		finish();
	}
	
	@Override
    protected void onStart() {
    	// TODO Auto-generated method stub
		if(!check)
		{
			lbHangman.startAnimation(animMove);
	    	btnCordage.startAnimation(animMove02);
	    	list.startAnimation(animMove03);
	    	check = true;
		}
    	super.onStart();
    }
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (adView != null) {
		      adView.destroy();
		}
		super.onDestroy();
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
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}
}
