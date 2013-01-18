package com.tamil.visai;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

public class TamilVisai extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tamilvisai);
		
		Typeface fontface = Typeface.createFromAsset(getAssets(),
				"fonts/mylai.ttf");
		
		TextView headerText = (TextView) findViewById(R.id.header_text);
		headerText.setTypeface(fontface, Typeface.BOLD);
		headerText.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.text_Header)));

		TextView welcomeText = (TextView) findViewById(R.id.welcome_text);
		welcomeText.setTypeface(fontface, Typeface.NORMAL);
		welcomeText.setText(UnicodeUtil.unicode2tsc(getResources().getString(
				R.string.text_welcome)));
		
		Button enableButton = (Button) findViewById(R.id.enable_tamilvisai);
		enableButton.setTypeface(fontface, Typeface.NORMAL);
		enableButton.setText(UnicodeUtil.unicode2tsc(getResources()
				.getString(R.string.text_enable_visai)));
		
		TextView contactText = (TextView) findViewById(R.id.contact_text);
		contactText.setTypeface(fontface, Typeface.ITALIC);
		String urltxt = getResources().getString(R.string.thamizha_url);
		String url = new StringBuilder(UnicodeUtil.unicode2tsc(getResources()
				.getString(R.string.thamizha_txt))).append("  ").append(urltxt).toString();
		contactText.setText(url);
		
		// Check if TamilVisai is enabled
		InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		List<InputMethodInfo> enabledInputMethods = inputManager
				.getEnabledInputMethodList();

		boolean visaiEnabled = false;
		for (InputMethodInfo imInfo : enabledInputMethods) {
			if (imInfo.getId().equalsIgnoreCase(
					"com.tamil.visai/.TamilSoftKeyboard")) {
				visaiEnabled = true;
				break;
			}
		}
		
		// if enabled, don't show button to enable
		if (visaiEnabled) {
			//enableButton.setVisibility(View.GONE);
		}
	}

	/**
	 * btnEnable_Click : show the input settings to enable TamilVisai
	 * 
	 * @param view
	 */
	public void btnEnable_Click(View view) {
		Intent enableInputMethodIntent = new Intent(
				Settings.ACTION_INPUT_METHOD_SETTINGS);
		enableInputMethodIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		enableInputMethodIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(enableInputMethodIntent);
	}
}
