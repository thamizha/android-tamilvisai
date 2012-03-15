package com.tamil.visai;

import java.util.List;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class TamilPreview {
    private ImageView mKeyboardLayoutSelectionImageView;
	LayoutInflater mInflater; 	
	private static final int PREVIEW_MIN = 1;
	private static final int PREVIEW_MAX = 2;
	
	private int previewMaxMinState = PREVIEW_MIN;
		
	private LinearLayout mPreviewContainer;
	private Resources mEesources;
	private TextView mPreviewTextView;
	private TamilSoftKeyboard mTamilSoftKeyboard;
	
	private LinearLayout mLogoContainer;
	private LinearLayout mLeftArrowContainer;
	private LinearLayout mRightArrowContainer;
	private LinearLayout mMaxMinContainer;
	
	private ImageView mMoveToEndArrow;
	private ImageView mLefttArrow;
	private ImageView mMaxMin;
	private FrameLayout mPreviewFrame;
	
	private static TamilPreview mTamilPreview;

	
	public TamilPreview(TamilSoftKeyboard tamilSoftKeyboard){
		this.mTamilSoftKeyboard = tamilSoftKeyboard;
		this.mInflater = mTamilSoftKeyboard.getLayoutInflater();
		this.mEesources = tamilSoftKeyboard.getResources();

		initPreview();
	}
	
	private void initPreview(){
		mPreviewContainer = (LinearLayout)mInflater.inflate(R.layout.previewview, null);
		mPreviewFrame = (FrameLayout)mPreviewContainer.findViewById(R.id.previewFrame);
		initPreviewTextView();
		initKeyboardLayoutSelectionImageView();
		initArrowKeys();
	}
	
	
	private void initPreviewTextView(){
		mPreviewTextView = (TextView)mPreviewContainer.findViewById(R.id.previewText);
		mPreviewTextView.setBackgroundColor(mEesources.getColor(R.color.candidate_background));

		mPreviewTextView.setTextColor(Color.BLACK);
		mPreviewTextView.setTextSize(16);
		mPreviewTextView.setTypeface(Typeface.createFromAsset(mTamilSoftKeyboard.getAssets(), "fonts/mylai.ttf"), Typeface.BOLD); 
		mPreviewTextView.setHorizontalFadingEdgeEnabled(true);
		mPreviewTextView.setWillNotDraw(false);
		mPreviewTextView.setHorizontalScrollBarEnabled(false);
		mPreviewTextView.setVerticalScrollBarEnabled(false);
		mPreviewTextView.setMovementMethod(new ScrollingMovementMethod());
		
	}
	
	private void initKeyboardLayoutSelectionImageView(){
//		mKeyboardLayoutSelectionImageView = (ImageView)mPreviewContainer.findViewById(R.id.keyboardSelectionImageView);
//		mKeyboardLayoutSelectionImageView.setImageResource(R.drawable.icons_anjal);
//		mKeyboardLayoutSelectionImageView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//            	mTamilSoftKeyboard.showKeyboardLayoutDialog();
//            }
//        });
	}
	
	private void initArrowKeys(){
		mMaxMin = (ImageView)mPreviewContainer.findViewById(R.id.max_min);
		mLogoContainer = (LinearLayout)mPreviewContainer.findViewById(R.id.logoContainer);
		mLogoContainer.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
        	mTamilSoftKeyboard.showKeyboardLayoutDialog();
        }
        });
		
		mLeftArrowContainer = (LinearLayout)mPreviewContainer.findViewById(R.id.leftArrowContainer);
		mLeftArrowContainer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	mTamilSoftKeyboard.moveCursorToLeft();
            }
        });
		
		mRightArrowContainer = (LinearLayout)mPreviewContainer.findViewById(R.id.rightArrowContainer);
		mRightArrowContainer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	mTamilSoftKeyboard.moveCursorToRight();
            }
        });
		

		mMaxMinContainer = (LinearLayout)mPreviewContainer.findViewById(R.id.maxMinContainer);
		mMaxMinContainer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				Resources r = mTamilSoftKeyboard.getResources();					

            	switch (previewMaxMinState) {
				case PREVIEW_MIN:
					mRightArrowContainer.getLayoutParams().height = r.getDimensionPixelSize(R.dimen.arrow_exp_hight);
					mLeftArrowContainer.getLayoutParams().height = r.getDimensionPixelSize(R.dimen.arrow_exp_hight);					
					mPreviewTextView.getLayoutParams().height = r.getDimensionPixelSize(R.dimen.preview_exp_hight);
					mPreviewFrame.requestLayout();
					mMaxMin.setImageResource(R.drawable.arrow_down);
					previewMaxMinState = PREVIEW_MAX;
					break;
				default:
					mRightArrowContainer.getLayoutParams().height = r.getDimensionPixelSize(R.dimen.arrow_normal_hight);
					mLeftArrowContainer.getLayoutParams().height = r.getDimensionPixelSize(R.dimen.arrow_normal_hight);					
					mPreviewTextView.getLayoutParams().height = r.getDimensionPixelSize(R.dimen.preview_normal_hight);
					mPreviewFrame.requestLayout();
					mMaxMin.setImageResource(R.drawable.arrow_up);

					previewMaxMinState = PREVIEW_MIN;
					
					break;
				}
            }
        });
		

		

	}
	
    public void update(String txt, int cursorPos){
    	txt = UnicodeUtil.unicode2tsc(txt);
    	mPreviewTextView.setText(txt);
    	Spannable text = (Spannable)mPreviewTextView.getText();
    	if(cursorPos >2){
    		cursorPos = cursorPos - 2;
    		if(cursorPos > text.length()){
    			cursorPos = text.length();
    		}
    	}
    	try{
    	Selection.setSelection(text, cursorPos); 
    	}catch (Exception e) {
		}
    }

    public void setSuggestions(List<String> suggestions, boolean completions,
            boolean typedWordValid) {
    }	
	
    public View getPreviewView(){
    	return mPreviewContainer;
    }
    public void clear(){
    }
}
