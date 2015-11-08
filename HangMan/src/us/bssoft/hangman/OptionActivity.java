package us.bssoft.hangman;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class OptionActivity extends Activity {
	TextView lbHeader, lbLanguage, txtLanguage, lbSound;
	CheckBox cbSound;
	MediaPlayer sound;
	private AdView adView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		initUI();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		// Font path
        String fontPath = "fonts/ARCENA.TTF";
        
        // text view label
        lbHeader = (TextView) findViewById(R.id.lbHeader);
        lbLanguage = (TextView) findViewById(R.id.lbLanguage);
        lbSound = (TextView) findViewById(R.id.lbSound);
        txtLanguage = (TextView) findViewById(R.id.txtLanguage);
        cbSound = (CheckBox) findViewById(R.id.cbSound);
        adView = (AdView)this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest());
        
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        
     	// Applying font
        lbHeader.setTypeface(tf);
        lbLanguage.setTypeface(tf);
        lbSound.setTypeface(tf);
        txtLanguage.setTypeface(tf);
        SharedPreferences share = getSharedPreferences("config",0);
		boolean check_sound = share.getBoolean("Sound", true);
		cbSound.setChecked(check_sound);
	}
	
	public void onClickBack(View v) {
		callSound(R.raw.click);
		finish();
	}
	
	public void onClickOption(View v) {
		//Intent i = new Intent()
		//finish();
	}
	
	public void onClickSound(View v) {
		CheckBox cb = (CheckBox) v;
		SharedPreferences sharedPref = getSharedPreferences("config",0);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean("Sound", cb.isChecked());
		editor.commit();
		
	}
	
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
}
