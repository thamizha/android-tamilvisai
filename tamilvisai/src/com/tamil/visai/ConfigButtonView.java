package com.tamil.visai;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ConfigButtonView{

	Context context;
	LinearLayout configView;
	ImageView keyboardToggleImg;
	ImageView leftArrow;
	ImageView rightArrow;
	ImageView moveToStartArrow;
	ImageView moveToEndArrow;
	
	public static final String BTN_CAPS_OFF = "a";
	public static final String BTN_CAPS_ON = "A";

	private boolean isTamilKeyboard = false;
	
	public ConfigButtonView(Context context) {
		try{
		this.context = context;
		configView = new LinearLayout(context);
		configView.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 50));
        configView.setOrientation(LinearLayout.HORIZONTAL);
        //configView.setPadding(10, 0, 10, 0);        
        Resources r = context.getResources();
//        configView.setBackgroundColor(r.getColor(R.color.candidate_other));
        configView.setWeightSum(0.5f);
        initButtons();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void toggleLanguage(){
		try{
//    	if(languageSelectionButton.getText() == BTN_TEXT_ENG)
//    		languageSelectionButton.setText(BTN_TEXT_TAMIL);
//    	else
//    		languageSelectionButton.setText(BTN_TEXT_ENG);
    	if(isTamilKeyboard){
    		keyboardToggleImg.setImageResource(R.drawable.icons_anjal);
    	}else{
    		keyboardToggleImg.setImageResource(R.drawable.icons_en);            		
    	}
		isTamilKeyboard = !isTamilKeyboard;
		}catch (Exception e) {
			// TODO: handle exception
		}		
	}
	public void initButtons(){
		try{
		keyboardToggleImg = new ImageView(context);
		if(((TamilSoftKeyboard)context).isTamilKeyboard()){
    		keyboardToggleImg.setImageResource(R.drawable.icons_en);
    		isTamilKeyboard = true;
		}
		else{
			keyboardToggleImg.setImageResource(R.drawable.icons_anjal);
    		isTamilKeyboard = false;
		}
		keyboardToggleImg.setLayoutParams(new LayoutParams(50, 50));
		configView.addView(keyboardToggleImg);
		keyboardToggleImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(isTamilKeyboard){
            		keyboardToggleImg.setImageResource(R.drawable.icons_anjal);
            	}else{
            		keyboardToggleImg.setImageResource(R.drawable.icons_en);            		
            	}
            	isTamilKeyboard = !isTamilKeyboard;
            	((TamilSoftKeyboard)context).toggleTamilKeyBoard();
            		
                // Perform action on click
            }
        });
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		LinearLayout arrowsViewContainer = new LinearLayout(context);
		arrowsViewContainer.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 50));
		
		LinearLayout arrowsView = new LinearLayout(context);
		arrowsView.setLayoutParams(new LayoutParams(160, 40));
		
		arrowsViewContainer.setGravity(Gravity.RIGHT|Gravity.BOTTOM);
		
		
		moveToStartArrow = new ImageView(context);
		moveToStartArrow.setImageResource(R.drawable.icon_arrow_first);
		moveToStartArrow.setLayoutParams(new LayoutParams(40, 40));
		moveToStartArrow.setPadding(5, 0, 5, 0);
		moveToStartArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	((TamilSoftKeyboard)context).moveCursorToFirst();
            		
                // Perform action on click
            }
        });
		arrowsView.addView(moveToStartArrow);

		leftArrow = new ImageView(context);
		leftArrow.setImageResource(R.drawable.icon_left_arrow);
		leftArrow.setLayoutParams(new LayoutParams(40, 40));
		leftArrow.setPadding(5, 0, 5, 0);
		
		leftArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	((TamilSoftKeyboard)context).moveCursorToLeft();
            		
                // Perform action on click
            }
        });
		arrowsView.addView(leftArrow);
		
		rightArrow = new ImageView(context);
		rightArrow.setImageResource(R.drawable.icon_right_arrow);
		rightArrow.setLayoutParams(new LayoutParams(40, 40));
		rightArrow.setPadding(5, 0, 5, 0);

		rightArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	((TamilSoftKeyboard)context).moveCursorToRight();
            		
                // Perform action on click
            }
        });
		arrowsView.addView(rightArrow);

		moveToEndArrow = new ImageView(context);
		moveToEndArrow.setImageResource(R.drawable.icon_arrow_last);
		moveToEndArrow.setLayoutParams(new LayoutParams(40, 40));
		moveToEndArrow.setPadding(5, 0, 5, 0);
		
		moveToEndArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	((TamilSoftKeyboard)context).moveCursorToEnd();
            		
                // Perform action on click
            }
        });
		arrowsView.addView(moveToEndArrow);
		
		
		arrowsViewContainer.addView(arrowsView);
		configView.addView(arrowsViewContainer);
		
	}
	public LinearLayout getConfigView(){
		return configView;
	}
}
