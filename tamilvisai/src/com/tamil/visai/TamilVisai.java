package com.tamil.visai;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TamilVisai extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tamilvisai);
        Typeface fontface = Typeface.createFromAsset(getAssets(), "fonts/mylai.ttf");        

        TextView appHeader = (TextView) findViewById(R.id.App_header);
        appHeader.setTypeface(fontface,Typeface.BOLD);
        appHeader.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.text_Header)));
        
        String urltxt = getResources().getString(R.string.thamizha_url);
        String url = new StringBuilder("<a href = \"")
        					.append(urltxt)
        					.append("\">")
        					.append(urltxt)
        					.append("</a>").toString();
        
        
        
        TextView footerText = (TextView) findViewById(R.id.Footer_txt);
        footerText.setTypeface(fontface,Typeface.BOLD);     
        footerText.setTextSize(20);
        footerText.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.thamizha_txt)));
        footerText.setTextColor(Color.WHITE);
        
        TextView footerUrl = (TextView) findViewById(R.id.Footer_url);
        footerUrl.setMovementMethod(LinkMovementMethod.getInstance());
        footerUrl.setTextColor(Color.WHITE);
        footerUrl.setText(Html.fromHtml(url));
        
        TextView welcomeTxt =(TextView) findViewById(R.id.Welcome_text);
        welcomeTxt.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.text_welcome)));
        welcomeTxt.setTypeface(fontface,Typeface.BOLD);
        welcomeTxt.setTextColor(Color.WHITE);
        
    }
}
