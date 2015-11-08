package us.bssoft.hangman;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import us.bssoft.config.CustomConfigXML;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

public class LocalActivity extends Activity {
	TextView lbHeader, txtName, txtLevel, lbName, lbLevel;
	MediaPlayer sound;
	private AdView adView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local);
		initUI();
		
		//Toast.makeText(RankingActivity.this, arr.get(0).getEmail(), Toast.LENGTH_SHORT).show();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		// Font path
        String fontPath = "fonts/ARCENA.TTF";
        
        // text view label
        lbHeader = (TextView) findViewById(R.id.lbHeader);
        lbName = (TextView) findViewById(R.id.lbName);
        lbLevel = (TextView) findViewById(R.id.lbLevel);
        txtName = (TextView) findViewById(R.id.txtName);
        txtLevel = (TextView) findViewById(R.id.txtLevel);
        //list = (ListView) findViewById(R.id.list);
        
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        
     	// Applying font
        lbHeader.setTypeface(tf);
        lbName.setTypeface(tf);
        lbLevel.setTypeface(tf);
        txtName.setTypeface(tf);
        txtLevel.setTypeface(tf);
        CustomConfigXML xml = new CustomConfigXML(this);
		AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
    	Account[] list = manager.getAccounts();
    	String gmail = "N/A";

    	for(Account account: list)
    	{
    	    if(account.type.equalsIgnoreCase("com.google"))
    	    {
    	        gmail = account.name.split("@")[0];
    	        break;
    	    }
    	}
		txtName.setText(gmail);
		txtLevel.setText(""+xml.getBestLevel());
		adView = (AdView)this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest());
	}
	
	public void onClickBack(View v) {
		callSound(R.raw.click);
		finish();
	}
	
	public void onClickOption(View v) {
		callSound(R.raw.click);
		Intent i = new Intent(LocalActivity.this, OptionActivity.class);
		startActivity(i);
		finish();
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
	
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (adView != null) {
		      adView.destroy();
		}
		super.onDestroy();
	}
	
}
