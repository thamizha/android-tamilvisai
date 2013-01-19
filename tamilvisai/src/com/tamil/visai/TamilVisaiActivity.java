package com.tamil.visai;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

/**
 * Launch activity
 *
 */
public class TamilVisaiActivity extends FragmentActivity {
	
	private FragmentTabHost mTabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.tamilvisai_main);
		mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		
		mTabHost.addTab(mTabHost.newTabSpec("about").setIndicator("About"), AboutFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("help").setIndicator("Help"), HelpFragment.class, null);
	}

}
