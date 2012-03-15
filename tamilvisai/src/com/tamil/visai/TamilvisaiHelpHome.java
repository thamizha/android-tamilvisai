package com.tamil.visai;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TamilvisaiHelpHome extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tamilvisai_help_home);

    TextView helpHdr =(TextView) findViewById(R.id.Help_header);
    helpHdr.setText(R.string.text_help_header);
    helpHdr.setTypeface(helpHdr.getTypeface(), Typeface.BOLD);
    helpHdr.setTextColor(Color.BLACK);
    TextView helpSteps =(TextView) findViewById(R.id.Help_steps);
    helpSteps.setText(R.string.text_help_steps);
    helpSteps.setTextColor(Color.BLACK);
    
    LinearLayout layout = (LinearLayout)findViewById(R.id.helpWrapper);
    layout.setBackgroundColor(Color.WHITE);
    }
}
