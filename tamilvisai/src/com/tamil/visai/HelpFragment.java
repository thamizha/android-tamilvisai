package com.tamil.visai;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Help Tab Fragment
 *
 */
public class HelpFragment extends Fragment {

	/**
	 * onCreateView : set the view layout
	 */
	public View onCreateView(LayoutInflater inflator, ViewGroup container,
			Bundle savedInstanceState) {
		View view = (LinearLayout)inflator.inflate(R.layout.tab_fragment_help, container, false);
		
		Typeface fontface = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/mylai.ttf");

		TextView helpStep1 = (TextView) view.findViewById(R.id.help_step_1);
		helpStep1.setTypeface(fontface, Typeface.NORMAL);
		helpStep1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.text_help_step_1)));
		
		TextView helpStep2 = (TextView) view.findViewById(R.id.help_step_2);
		helpStep2.setTypeface(fontface, Typeface.NORMAL);
		helpStep2.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.text_help_step_2)));
		
		TextView helpStep3 = (TextView) view.findViewById(R.id.help_step_3);
		helpStep3.setTypeface(fontface, Typeface.NORMAL);
		helpStep3.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.text_help_step_3)));
		
		return view;
	}

}
