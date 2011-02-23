/*
 * 
 * 
 */
package com.tamil.visai;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

public class TamilCandidateView extends TextView {

    private static final int OUT_OF_BOUNDS = -1;

    private TamilSoftKeyboard mService;
//    private List<String> mSuggestions;
//    private int mSelectedIndex;
//    private int mTouchX = OUT_OF_BOUNDS;
    private Drawable mSelectionHighlight;
//    private boolean mTypedWordValid;
    
//    private Rect mBgPadding;
//
//    private static final int MAX_SUGGESTIONS = 32;
//    private static final int SCROLL_PIXELS = 10;
//    
//    private int[] mWordWidth = new int[MAX_SUGGESTIONS];
//    private int[] mWordX = new int[MAX_SUGGESTIONS];
//
//    private static final int X_GAP = 10;
//    
//    private static final List<String> EMPTY_LIST = new ArrayList<String>();

    private int mColorNormal;
    private int mColorRecommended;
    private int mColorOther;
    private int mVerticalPadding;
//    private Paint mPaint;
//    private boolean mScrolled;
//    private int mTargetScrollX;
//    private AssetManager assets;
//    private int mTotalWidth;
    

    /**
     * Construct a CandidateView for showing suggested words for completion.
     * @param context
     * @param attrs
     */
    public TamilCandidateView(Context context) {
        super(context);
        mSelectionHighlight = context.getResources().getDrawable(
                android.R.drawable.list_selector_background);
        mSelectionHighlight.setState(new int[] {
                android.R.attr.state_enabled,
                android.R.attr.state_focused,
                android.R.attr.state_window_focused,
                android.R.attr.state_pressed
        });

        Resources r = context.getResources();
        
        setBackgroundColor(r.getColor(R.color.candidate_background));
        


        mColorNormal = r.getColor(R.color.candidate_normal);
        mColorRecommended = r.getColor(R.color.candidate_recommended);
        mColorOther = r.getColor(R.color.candidate_other);
        mVerticalPadding = r.getDimensionPixelSize(R.dimen.candidate_vertical_padding);
        
        setTextColor(Color.BLACK);
        setTextSize(16);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/mylai.ttf"), Typeface.BOLD); 

        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 70);
        setLayoutParams(lp);
        //setTextSize(17);
      //  setMaxLines(2);
        setHeight(70);
//        setWidth(200);
        setHorizontalFadingEdgeEnabled(true);
        setWillNotDraw(false);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        setMovementMethod(new ScrollingMovementMethod());
    }
    
    /**
     * A connection back to the service to communicate with the text field
     * @param listener
     */
    public void setService(TamilSoftKeyboard listener) {
        mService = listener;
    }


    public void setSuggestions(List<String> suggestions, boolean completions,
            boolean typedWordValid) {
    }

    public void update(String txt){
    	txt = UnicodeUtil.unicode2tsc(txt);
    	setText(txt);
//    	if(getLineCount()>2){
//    		scrollTo(0, (getLineCount()-2)* getLineHeight());
//    	}
    }
    
    public void clear() {
    }

}
