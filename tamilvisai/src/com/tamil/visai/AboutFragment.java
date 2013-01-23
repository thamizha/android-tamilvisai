package com.tamil.visai;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * About Tab Fragment
 */
public class AboutFragment extends Fragment {
	
	/**
	 * onCreateView : set the view layout
	 */
	public View onCreateView(LayoutInflater inflator, ViewGroup container,
			Bundle savedInstanceState) {
		View view = (LinearLayout)inflator.inflate(R.layout.tab_fragment_about, container, false);
		
		// Use tamil fonts
		Typeface fontface = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/mylai.ttf");
		
		TextView headerText = (TextView) view.findViewById(R.id.header_text);
		headerText.setTypeface(fontface, Typeface.BOLD);
		headerText.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.text_Header)));

		TextView welcomeText = (TextView) view.findViewById(R.id.welcome_text);
		welcomeText.setTypeface(fontface, Typeface.NORMAL);
		welcomeText.setText(UnicodeUtil.unicode2tsc(getResources().getString(
				R.string.text_welcome)));
		
		Button enableButton = (Button) view.findViewById(R.id.enable_tamilvisai);
		enableButton.setTypeface(fontface, Typeface.NORMAL);
		enableButton.setText(UnicodeUtil.unicode2tsc(getResources()
				.getString(R.string.text_enable_visai)));
		enableButton.setOnClickListener(new OnClickListener() {
			
			/**
			 * show the input method activation settings screen
			 */
			@Override
			public void onClick(View v) {
				Intent enableInputMethodIntent = new Intent(
						Settings.ACTION_INPUT_METHOD_SETTINGS);
				enableInputMethodIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				enableInputMethodIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getActivity().startActivity(enableInputMethodIntent);
			}
		});
		
		TextView contactText = (TextView) view.findViewById(R.id.contact_text);
		contactText.setTypeface(fontface, Typeface.ITALIC);
		String urltxt = getResources().getString(R.string.thamizha_url);
		String url = new StringBuilder(UnicodeUtil.unicode2tsc(getResources()
				.getString(R.string.thamizha_txt))).append("  ").append(urltxt).toString();
		contactText.setText(url);
		
		return view;
	}
}
