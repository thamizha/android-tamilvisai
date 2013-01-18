package com.tamil.visai;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class TamilvisaiHelpHome extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tamilvisai_help_home);
		
		Typeface fontface = Typeface.createFromAsset(getAssets(),
				"fonts/mylai.ttf");

		TextView helpStep1 = (TextView) findViewById(R.id.help_step_1);
		helpStep1.setTypeface(fontface, Typeface.NORMAL);
		helpStep1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.text_help_step_1)));
		
		TextView helpStep2 = (TextView) findViewById(R.id.help_step_2);
		helpStep2.setTypeface(fontface, Typeface.NORMAL);
		helpStep2.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.text_help_step_2)));
		
		TextView helpStep3 = (TextView) findViewById(R.id.help_step_3);
		helpStep3.setTypeface(fontface, Typeface.NORMAL);
		helpStep3.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.text_help_step_3)));
	}

	
}
