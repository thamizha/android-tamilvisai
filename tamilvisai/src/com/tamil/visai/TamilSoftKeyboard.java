package com.tamil.visai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.res.Configuration;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.opengl.Visibility;
import android.text.method.MetaKeyKeyListener;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class TamilSoftKeyboard extends InputMethodService 
        implements KeyboardView.OnKeyboardActionListener {
    static final boolean DEBUG = false;
    
    /**
     * This boolean indicates the optional example code for performing
     * processing of hard keys in addition to regular text generation
     * from on-screen interaction.  It would be used for input methods that
     * perform language translations (such as converting text entered on 
     * a QWERTY keyboard to Chinese), but may not be used for input methods
     * that are primarily intended to be used for on-screen text entry.
     */
    static final boolean PROCESS_HARD_KEYS = true;
    static final List<Integer> UYIR_MAI_LIST = Arrays.asList(new Integer[]{2965, 2969, 2970, 2972, 2974, 2975, 2979, 2980, 2984, 2985,2986, 2990, 2991, 2992, 2993, 2994, 2995, 2996, 2997, 2999, 3000, 3001});

    private boolean showingSoftKeyboard = true;
    private KeyboardView mInputView;
    private TamilCandidateView mCandidateView;
    private ConfigButtonView configBtns;
    
    private CompletionInfo[] mCompletions;
    
    private StringBuilder mComposing = new StringBuilder();
//    private char mPrevChar;
    private int mPrevChar;
    private int m2ndPrevChar;

    private StringBuilder mText = new StringBuilder();
    private boolean mPredictionOn;
    private boolean mCompletionOn;
    private int mLastDisplayWidth;
    private boolean mCapsLock;
    private long mLastShiftTime;
    private long mMetaState;
    
    private TamilKeyboard mSymbolsKeyboard;
    private TamilKeyboard mSymbolsShiftedKeyboard;
    private TamilKeyboard mQwertyKeyboard;
    private TamilKeyboard mTamilKeyboard;
    
    private TamilKeyboard mCurKeyboard;
    
    private String mWordSeparators;
    
    private Boolean mAlt = false;
    private Boolean mTamil = false;
    
    /**
     * Main initialization of the input method component.  Be sure to call
     * to super class.
     */
    @Override public void onCreate() {
        super.onCreate();
        mWordSeparators = getResources().getString(R.string.word_separators);
    }
    
    /**
     * This is the point where you can do all of your UI initialization.  It
     * is called after creation and any configuration change.
     */
    @Override public void onInitializeInterface() {
        if (mQwertyKeyboard != null) {
            // Configuration changes can happen after the keyboard gets recreated,
            // so we need to be able to re-build the keyboards if the available
            // space has changed.
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth) return;
            mLastDisplayWidth = displayWidth;
        }
        mQwertyKeyboard = new TamilKeyboard(this, R.xml.qwerty);
        mSymbolsKeyboard = new TamilKeyboard(this, R.xml.symbols);
        mSymbolsShiftedKeyboard = new TamilKeyboard(this, R.xml.symbols_shift);
        mTamilKeyboard = new TamilKeyboard(this, R.xml.tamil);
    }
    
    /**
     * Called by the framework when your view for creating input needs to
     * be generated.  This will be called the first time your input method
     * is displayed, and every time it needs to be re-created such as due to
     * a configuration change.
     */
    @Override public View onCreateInputView() {
        mInputView = (KeyboardView) getLayoutInflater().inflate(
                R.layout.input, null);
        mInputView.setOnKeyboardActionListener(this);
        mInputView.setKeyboard(mQwertyKeyboard);
        return mInputView;
    }

    /**
     * Called by the framework when your view for showing candidates needs to
     * be generated, like {@link #onCreateInputView}.
     */
    @Override public View onCreateCandidatesView() {
    	LinearLayout layout = new LinearLayout(this);
    	layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    	layout.setOrientation(LinearLayout.VERTICAL);
      // CandidateView candidateView = new CandidateView(this);
    	mCandidateView = new TamilCandidateView(this);
        mCandidateView.setService(this);

        configBtns = new ConfigButtonView(this);
        setCandidatesViewShown(true);
        mCandidateView.update("");
    	layout.addView(mCandidateView);
    	layout.addView(configBtns.getConfigView());
        return layout;
    }

    /**
     * This is the main point where we do our initialization of the input method
     * to begin operating on an application.  At this point we have been
     * bound to the client, and are now receiving all of the detailed information
     * about the target of our edits.
     */
    @Override public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        
        // Reset our state.  We want to do this even if restarting, because
        // the underlying state of the text editor could have changed in any way.
        mComposing.setLength(0);
        updateCandidates();
        updateCandidateText();
        if (!restarting) {
            // Clear shift states.
            mMetaState = 0;
        }
        
        mPredictionOn = false;
        mCompletionOn = false;
        mCompletions = null;
        
        // We are now going to initialize our state based on the type of
        // text being edited.
        switch (attribute.inputType&EditorInfo.TYPE_MASK_CLASS) {
            case EditorInfo.TYPE_CLASS_NUMBER:
            case EditorInfo.TYPE_CLASS_DATETIME:
                // Numbers and dates default to the symbols keyboard, with
                // no extra features.
                mCurKeyboard = mSymbolsKeyboard;
                break;
                
            case EditorInfo.TYPE_CLASS_PHONE:
                // Phones will also default to the symbols keyboard, though
                // often you will want to have a dedicated phone keyboard.
                mCurKeyboard = mSymbolsKeyboard;
                break;
                
            case EditorInfo.TYPE_CLASS_TEXT:
                // This is general text editing.  We will default to the
                // normal alphabetic keyboard, and assume that we should
                // be doing predictive text (showing candidates as the
                // user types).
                mCurKeyboard = mQwertyKeyboard;
                mPredictionOn = true;
                
                // We now look for a few special variations of text that will
                // modify our behavior.
                int variation = attribute.inputType &  EditorInfo.TYPE_MASK_VARIATION;
                if (variation == EditorInfo.TYPE_TEXT_VARIATION_PASSWORD ||
                        variation == EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    // Do not display predictions / what the user is typing
                    // when they are entering a password.
                    mPredictionOn = false;
                }
                
                if (variation == EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS 
                        || variation == EditorInfo.TYPE_TEXT_VARIATION_URI
                        || variation == EditorInfo.TYPE_TEXT_VARIATION_FILTER) {
                    // Our predictions are not useful for e-mail addresses
                    // or URIs.
                    mPredictionOn = false;
                }
                
                if ((attribute.inputType&EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE) != 0) {
                    // If this is an auto-complete text view, then our predictions
                    // will not be shown and instead we will allow the editor
                    // to supply their own.  We only show the editor's
                    // candidates when in fullscreen mode, otherwise relying
                    // own it displaying its own UI.
                    mPredictionOn = false;
                    mCompletionOn = isFullscreenMode();
                }
                
                // We also want to look at the current state of the editor
                // to decide whether our alphabetic keyboard should start out
                // shifted.
                updateShiftKeyState(attribute);
                break;
                
            default:
                // For all unknown input types, default to the alphabetic
                // keyboard with no special features.
                mCurKeyboard = mQwertyKeyboard;
                updateShiftKeyState(attribute);
        }
        
        // Update the label on the enter key, depending on what the application
        // says it will do.
        mCurKeyboard.setImeOptions(getResources(), attribute.imeOptions);
    }
    
    private void updateCandidateText(){
        try{
	        ExtractedText txt = getCurrentInputConnection().getExtractedText(new ExtractedTextRequest(), InputConnection.GET_EXTRACTED_TEXT_MONITOR);
	        mCandidateView.update(txt.text.toString());
        }
        catch (Exception e) {
        	Log.e("t", "errr", e);
		}

    }

    /**
     * This is called when the user is done editing a field.  We can use
     * this to reset our state.
     */
    @Override public void onFinishInput() {
        super.onFinishInput();
        
        // Clear current composing text and candidates.
        mComposing.setLength(0);
        updateCandidates();
        
        // We only hide the candidates window when finishing input on
        // a particular editor, to avoid popping the underlying application
        // up and down if the user is entering text into the bottom of
        // its window.
//        setCandidatesViewShown(false);
        
        mCurKeyboard = mQwertyKeyboard;
        if (mInputView != null) {
            mInputView.closing();
        }
    }
    
    @Override public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        // Apply the selected keyboard to the input view.
        mInputView.setKeyboard(mCurKeyboard);
        mInputView.closing();
    	mInputView.setVisibility(View.VISIBLE);
        if(getResources().getConfiguration().hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO){
        	mInputView.setVisibility(View.GONE);
        }
    }
    
    /**
     * Deal with the editor reporting movement of its cursor.
     */
    @Override public void onUpdateSelection(int oldSelStart, int oldSelEnd,
            int newSelStart, int newSelEnd,
            int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd,
                candidatesStart, candidatesEnd);
        InputConnection ic = getCurrentInputConnection();
        
        // If the current selection in the text view changes, we should
        // clear whatever candidate text we have.
        if (mComposing.length() > 0 && (newSelStart != candidatesEnd
                || newSelEnd != candidatesEnd)) {
            mComposing.setLength(0);
            updateCandidates();
            if (ic != null) {
                ic.finishComposingText();
            }
        }
        if (ic != null) {
        	ExtractedText txt = ic.getExtractedText(new ExtractedTextRequest(), InputConnection.GET_EXTRACTED_TEXT_MONITOR);
        	try{
        		mPrevChar = txt.text.charAt(newSelStart-1);        
        	}catch (Exception e) {
				// TODO: handle exception
			}
        }        
    }

    /**
     * This tells us about completions that the editor has determined based
     * on the current text in it.  We want to use this in fullscreen mode
     * to show the completions ourself, since the editor can not be seen
     * in that situation.
     */
    @Override public void onDisplayCompletions(CompletionInfo[] completions) {
        if (mCompletionOn) {
            mCompletions = completions;
            if (completions == null) {
                setSuggestions(null, false, false);
                return;
            }
            
            List<String> stringList = new ArrayList<String>();
            for (int i=0; i<(completions != null ? completions.length : 0); i++) {
                CompletionInfo ci = completions[i];
                if (ci != null) stringList.add(ci.getText().toString());
            }
            setSuggestions(stringList, true, true);
        }
    }
    
    /**
     * This translates incoming hard key events in to edit operations on an
     * InputConnection.  It is only needed when using the
     * PROCESS_HARD_KEYS option.
     */
    private boolean translateKeyDown(int keyCode, KeyEvent event) {
        mMetaState = MetaKeyKeyListener.handleKeyDown(mMetaState,
                keyCode, event);
        int c = event.getUnicodeChar(MetaKeyKeyListener.getMetaState(mMetaState));
        mMetaState = MetaKeyKeyListener.adjustMetaAfterKeypress(mMetaState);
        InputConnection ic = getCurrentInputConnection();
        if (c == 0 || ic == null) {
            return false;
        }
        boolean dead = false;

        if ((c & KeyCharacterMap.COMBINING_ACCENT) != 0) {
            dead = true;
            c = c & KeyCharacterMap.COMBINING_ACCENT_MASK;
        }
        
        if (mComposing.length() > 0) {
            char accent = mComposing.charAt(mComposing.length() -1 );
            int composed = KeyEvent.getDeadChar(accent, c);

            if (composed != 0) {
                c = composed;
                mComposing.setLength(mComposing.length()-1);
            }
        }
        
        onKey(c, null);
        
        return true;
    }
    
    /**
     * Use this to monitor key events being delivered to the application.
     * We get first crack at them, and can either resume them or let them
     * continue to the app.
     */
    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(mCandidateView == null){
    		setCandidatesView(onCreateCandidatesView());
    	}
    	setCandidatesViewShown(true);
    	mCandidateView.setVisibility(View.VISIBLE);
    	switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                // The InputMethodService already takes care of the back
                // key for us, to dismiss the input method if it is shown.
                // However, our keyboard could be showing a pop-up window
                // that back should dismiss, so we first allow it to do that.
                if (event.getRepeatCount() == 0 && mInputView != null) {
                    if (mInputView.handleBack()) {
                        return true;
                    }
                }
                break;
                
            case KeyEvent.KEYCODE_DEL:
                // Special handling of the delete key: if we currently are
                // composing text for the user, we want to modify that instead
                // of let the application to the delete itself.
            	 handleBackspace();

            	 return true;
              
            	//                if (mComposing.length() > 0) {
//                    onKey(Keyboard.KEYCODE_DELETE, null);
//                    return true;
//                }
              //  break;
                
            case KeyEvent.KEYCODE_ENTER:
                // Let the underlying text editor always handle these.
                return false;
            case KeyEvent.KEYCODE_SHIFT_LEFT:
            case KeyEvent.KEYCODE_SHIFT_RIGHT:
            	handleShift();
            	return false;
//            case KeyEvent.KEYCODE_A:
//            case KeyEvent.KEYCODE_B:
//            case KeyEvent.KEYCODE_C:
//            case KeyEvent.KEYCODE_D:
//            case KeyEvent.KEYCODE_E:
//            case KeyEvent.KEYCODE_F:
//            case KeyEvent.KEYCODE_G:
//            case KeyEvent.KEYCODE_H:
//            case KeyEvent.KEYCODE_I:
//            case KeyEvent.KEYCODE_J:
//            case KeyEvent.KEYCODE_K:
//            case KeyEvent.KEYCODE_L:
//            case KeyEvent.KEYCODE_M:
//            case KeyEvent.KEYCODE_N:
//            case KeyEvent.KEYCODE_O:
//            case KeyEvent.KEYCODE_P:
//            case KeyEvent.KEYCODE_Q:
//            case KeyEvent.KEYCODE_R:
//            case KeyEvent.KEYCODE_S:
//            case KeyEvent.KEYCODE_T:
//            case KeyEvent.KEYCODE_U:
//            case KeyEvent.KEYCODE_V:
//            case KeyEvent.KEYCODE_W:
//            case KeyEvent.KEYCODE_X:            	
//            case KeyEvent.KEYCODE_Y:
//            case KeyEvent.KEYCODE_Z:
            case KeyEvent.KEYCODE_ALT_LEFT:
            case KeyEvent.KEYCODE_ALT_RIGHT:
            	if(mTamil)
            		return true;
            	return false;
            default:
              if(mTamil && Constants.KEY_CODE_MAP.containsKey(keyCode)){
            	  onKey(Constants.KEY_CODE_MAP.get(keyCode), null);
            	  return true;
              }
//              keyDownUp(keyCode);
//              updateCandidateText();
              return false;
//            default:
//                // For all other keys, if we want to do transformations on
//                // text being entered with a hard keyboard, we need to process
//                // it and do the appropriate action.
//                if (PROCESS_HARD_KEYS) {
//                    //keyDownUp(2965);
//                	if(KeyEvent.KEYCODE_ALT_LEFT == keyCode || KeyEvent.KEYCODE_ALT_RIGHT == keyCode){
////                        handleCharacter(2970, null);
//                		  return false;
//                	}
//                    handleCharacter(2965, null);
//                 updateCandidateText();
//                  return true;
////                    if (keyCode == KeyEvent.KEYCODE_SPACE
////                           /* && (event.getMetaState()&KeyEvent.META_ALT_ON) != 0*/) {
////                        // A silly example: in our input method, Alt+Space
////                        // is a shortcut for 'android' in lower case.
////                        InputConnection ic = getCurrentInputConnection();
////                        if (ic != null) {
////                            // First, tell the editor that it is no longer in the
////                            // shift state, since we are consuming this.
////                            ic.clearMetaKeyStates(KeyEvent.META_ALT_ON);
////                            keyDownUp(KeyEvent.KEYCODE_A);
////                            keyDownUp(KeyEvent.KEYCODE_N);
////                            keyDownUp(KeyEvent.KEYCODE_D);
////                            keyDownUp(KeyEvent.KEYCODE_R);
////                            keyDownUp(KeyEvent.KEYCODE_O);
////                            keyDownUp(KeyEvent.KEYCODE_I);
////                            keyDownUp(KeyEvent.KEYCODE_D);
////                            updateCandidateText();
////                            // And we consume this event.
////                            return true;
////                        }
////                    }
////                    if (mPredictionOn && translateKeyDown(keyCode, event)) {
////                        return true;
////                    }
//                }
        }
        boolean returnVal = super.onKeyDown(keyCode, event);
        updateCandidateText();
        return returnVal;
    }

    /**
     * Use this to monitor key events being delivered to the application.
     * We get first crack at them, and can either resume them or let them
     * continue to the app.
     */
    @Override public boolean onKeyUp(int keyCode, KeyEvent event) {
        // If we want to do transformations on text being entered with a hard
        // keyboard, we need to process the up events to update the meta key
        // state we are tracking.
        if (PROCESS_HARD_KEYS) {
            if (mPredictionOn) {
                mMetaState = MetaKeyKeyListener.handleKeyUp(mMetaState,
                        keyCode, event);
            }
            updateCandidateText();
        }
        
        return super.onKeyUp(keyCode, event);
    }

    /**
     * Helper function to commit any text being composed in to the editor.
     */
    private void commitTyped(InputConnection inputConnection) {
        if (mComposing.length() > 0) {
            inputConnection.commitText(mComposing, mComposing.length());
            mComposing.setLength(0);
            updateCandidates();
        }
    }

    /**
     * Helper to update the shift state of our keyboard based on the initial
     * editor state.
     */
    private void updateShiftKeyState(EditorInfo attr) {
        if (attr != null 
                && mInputView != null && mQwertyKeyboard == mInputView.getKeyboard()) {
            int caps = 0;
            EditorInfo ei = getCurrentInputEditorInfo();
            if (ei != null && ei.inputType != EditorInfo.TYPE_NULL) {
                caps = getCurrentInputConnection().getCursorCapsMode(attr.inputType);
            }
            mInputView.setShifted(mCapsLock || caps != 0);
        }
    }
    
    /**
     * Helper to determine if a given character code is alphabetic.
     */
    private boolean isAlphabet(int code) {
        if (Character.isLetter(code)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Helper to send a key down / key up pair to the current editor.
     */
    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
        updateCandidateText();
    }
    
    /**
     * Helper to send a character to the editor as raw key events.
     */
    private void sendKey(int keyCode) {
        switch (keyCode) {
            case '\n':
                keyDownUp(KeyEvent.KEYCODE_ENTER);
                break;
            default:
                if (keyCode >= '0' && keyCode <= '9') {
                    keyDownUp(keyCode - '0' + KeyEvent.KEYCODE_0);
                } else {
                    getCurrentInputConnection().commitText(String.valueOf((char) keyCode), 1);
                }
                break;
        }
        mText.append(String.valueOf((char) keyCode));
        updateCandidateText();
    }

    // Implementation of KeyboardViewListener

    public void onKey(int primaryCode, int[] keyCodes) {
        if (isWordSeparator(primaryCode)) {
            // Handle separator
            if (mComposing.length() > 0) {
                commitTyped(getCurrentInputConnection());
            }
            sendKey(primaryCode);
            updateShiftKeyState(getCurrentInputEditorInfo());
        } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
            handleBackspace();
        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            handleShift();
        } else if (primaryCode == -8) {
            Keyboard current = mInputView.getKeyboard();
            if(current == mTamilKeyboard){
            	mInputView.setKeyboard(mQwertyKeyboard);
            }else{
            	mInputView.setKeyboard(mTamilKeyboard);
            }
            mTamil = !mTamil;
            configBtns.toggleLanguage();
        } else if (primaryCode == Keyboard.KEYCODE_CANCEL) {
        	handleClose();
            return;
        } else if (primaryCode == TamilKeyboardView.KEYCODE_OPTIONS) {
            // Show a menu or somethin'
        } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE
                && mInputView != null) {
            Keyboard current = mInputView.getKeyboard();
            if (current == mSymbolsKeyboard || current == mSymbolsShiftedKeyboard) {
                current = mQwertyKeyboard;
            } else {
                current = mSymbolsKeyboard;
            }
            mInputView.setKeyboard(current);
            if (current == mSymbolsKeyboard) {
                current.setShifted(false);
            }
        } else {
            handleCharacter(primaryCode, keyCodes);
        }
    }

    public void onText(CharSequence text) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        ic.beginBatchEdit();
        if (mComposing.length() > 0) {
            commitTyped(ic);
        }
        ic.commitText(text, 0);
        ic.endBatchEdit();
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    /**
     * Update the list of available candidates from the current composing
     * text.  This will need to be filled in by however you are determining
     * candidates.
     */
    private void updateCandidates() {
        if (!mCompletionOn) {
            if (mComposing.length() > 0) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(mComposing.toString());
                setSuggestions(list, true, true);
            } else {
                setSuggestions(null, false, false);
            }
        }
    }
    
    public void setSuggestions(List<String> suggestions, boolean completions,
            boolean typedWordValid) {
        if (suggestions != null && suggestions.size() > 0) {
            setCandidatesViewShown(true);
        } else if (isExtractViewShown()) {
            setCandidatesViewShown(true);
        }
        if (mCandidateView != null) {
            mCandidateView.setSuggestions(suggestions, completions, typedWordValid);
        }
    }
    
    private void handleBackspace() {

        
        final int length = mComposing.length();
        if (length > 1) {
            mComposing.delete(length - 1, length);
            getCurrentInputConnection().commitText(mComposing, 1);
            updateCandidates();
        } else if (length > 0) {
            mComposing.setLength(0);
            getCurrentInputConnection().commitText("", 0);
            updateCandidates();
        } else {
            keyDownUp(KeyEvent.KEYCODE_DEL);
        }
       // updateShiftKeyState(getCurrentInputEditorInfo());
        updateCandidateText();

    }

    private void handleShift() {
        if (mInputView == null) {
            return;
        }
        
        Keyboard currentKeyboard = mInputView.getKeyboard();
        if (mQwertyKeyboard == currentKeyboard || mTamilKeyboard == currentKeyboard) {
            // Alphabet keyboard or tamil 
            checkToggleCapsLock();
            mInputView.setShifted(mCapsLock || !mInputView.isShifted());
        } else if (currentKeyboard == mSymbolsKeyboard) {
            mSymbolsKeyboard.setShifted(true);
            mInputView.setKeyboard(mSymbolsShiftedKeyboard);
            mSymbolsShiftedKeyboard.setShifted(true);
        } else if (currentKeyboard == mSymbolsShiftedKeyboard) {
            mSymbolsShiftedKeyboard.setShifted(false);
            mInputView.setKeyboard(mSymbolsKeyboard);
            mSymbolsKeyboard.setShifted(false);
        }
    }
    
    private void handleCharacter(int primaryCode, int[] keyCodes) {
        if (isInputViewShown()) {
            if (mInputView.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
                if(Constants.SHIFTED_KEYS.containsKey(primaryCode))
                {
                	primaryCode = Constants.SHIFTED_KEYS.get(primaryCode);
                }
                Keyboard currentKeyboard = mInputView.getKeyboard();

                if(currentKeyboard == mTamilKeyboard){
                	mInputView.setShifted(false);
                }
            }
        }
        
        //Agaram
        if(primaryCode == 2949){
        	if(mPrevChar == 3021){
        		mPrevChar = m2ndPrevChar;
        		m2ndPrevChar = 0; 
        		handleBackspace();
        		updateCandidateText();
        	}
        	else{ 
        		if(mPrevChar==2949){
        			handleBackspace();        			
    				primaryCode = 2950;        		
        		}
        		else if(UYIR_MAI_LIST.contains(mPrevChar)){
    				primaryCode = 3006;        		
        		}
                getCurrentInputConnection().commitText(
                        String.valueOf((char) primaryCode), 1);
                mText.append(String.valueOf((char) primaryCode));
                mPrevChar = primaryCode;

        		updateCandidateText();
        	}
        }        
        else if(Constants.KURIL_EXECPT_AGARAM.contains(primaryCode)){
        	if(mPrevChar == 3021){
        		handleBackspace();
        		primaryCode = Constants.KURIL_SYMBOLS_MAP.get(primaryCode);        		
        	}
        	else if(Constants.KURIL_SYMBOLS.contains(mPrevChar) && mPrevChar == Constants.KURIL_SYMBOLS_MAP.get(primaryCode)){
        		handleBackspace();
        		primaryCode = Constants.NEDIL_SYMBOLS_MAP.get(primaryCode);        		        		
        	}
        	else if(primaryCode == 2951 && mPrevChar == 2949){
        		handleBackspace();
        		primaryCode = 2960;
        	}
        	else if(primaryCode == 2951 && UYIR_MAI_LIST.contains(mPrevChar)){
        		primaryCode = 3016;
        	}

        	
        	else if(primaryCode == mPrevChar){
        		handleBackspace();
        		primaryCode = Constants.UYIR_NEDIL_MAP.get(primaryCode);        		        		        		
        	}
            getCurrentInputConnection().commitText(
                    String.valueOf((char) primaryCode), 1);
            mText.append(String.valueOf((char) primaryCode));
            mPrevChar = primaryCode;

    		updateCandidateText();        	
        }
        else if(UYIR_MAI_LIST.contains(primaryCode)){
            //nj
            if(primaryCode == 2972 && m2ndPrevChar == 2985 && mPrevChar == 3021){
    			handleBackspace();        			
    			handleBackspace();        			
            	primaryCode = 2974;
            }
            //ng
            if(primaryCode == 2965 && m2ndPrevChar == 2985 && mPrevChar == 3021){
    			handleBackspace();        			
    			handleBackspace();        			
            	primaryCode = 2969;
            }
            //th
            if(primaryCode == 3001 && m2ndPrevChar == 2975 && mPrevChar == 3021){
    			handleBackspace();        			
    			handleBackspace();        			
            	primaryCode = 2980;
            }
            //sh
            if(primaryCode == 3001 && m2ndPrevChar == 2970 && mPrevChar == 3021){
    			handleBackspace();        			
    			handleBackspace();        			
            	primaryCode = 2999;
            }


        	getCurrentInputConnection().commitText(
        			String.valueOf((char) primaryCode), 1);
        	
        	mText.append(String.valueOf((char) primaryCode));
        	mPrevChar = primaryCode;

            getCurrentInputConnection().commitText(
                    String.valueOf((char) 3021), 1);
            mText.append(String.valueOf((char) 3021));
            m2ndPrevChar = primaryCode;
            mPrevChar = 3021;
            updateCandidateText();
        }
        else{
        	getCurrentInputConnection().commitText(
        			String.valueOf((char) primaryCode), 1);
        	
        	mText.append(String.valueOf((char) primaryCode));
        	mPrevChar = primaryCode;       	
            updateCandidateText();

        }
        
//    	if(primaryCode == 2949 && mPrevChar == 3021 ){
//    		mPrevChar = m2ndPrevChar;
//    		m2ndPrevChar = 0; 
//
//    		handleBackspace();
//    	}else{
//    		if(mPrevChar==2949 && primaryCode == 2949){
//        		handleBackspace();        			
//    			primaryCode = 2950;
//    		}else if(mPrevChar==2984 && primaryCode == 2949){
//    			primaryCode = 3006;
//    		}
//            getCurrentInputConnection().commitText(
//                    String.valueOf((char) primaryCode), 1);
//            mText.append(String.valueOf((char) primaryCode));
//            mPrevChar = primaryCode;
//
//            if(UYIR_MAI_LIST.contains(primaryCode)){
//                getCurrentInputConnection().commitText(
//                        String.valueOf((char) 3021), 1);
//                mText.append(String.valueOf((char) 3021));
//                m2ndPrevChar = 2984;
//                mPrevChar = 3021;
//
//            }
//	        updateCandidateText();
//    	}
    }

    private void handleClose() {
        commitTyped(getCurrentInputConnection());
        requestHideSelf(0);
        mInputView.closing();
    }

    private void checkToggleCapsLock() {
        long now = System.currentTimeMillis();
        if (mLastShiftTime + 800 > now) {
            mCapsLock = !mCapsLock;
            mLastShiftTime = 0;
        } else {
            mLastShiftTime = now;
        }
    }
    
    private String getWordSeparators() {
        return mWordSeparators;
    }
    
    public boolean isWordSeparator(int code) {
        String separators = getWordSeparators();
        return separators.contains(String.valueOf((char)code));
    }

    public void pickDefaultCandidate() {
        pickSuggestionManually(0);
    }
    public void toggleTamilKeyBoard(){
    	mTamil = ! mTamil;
    	if(mTamil){
    		mInputView.setKeyboard(mTamilKeyboard);
    	}else{
    		mInputView.setKeyboard(mQwertyKeyboard);
    	}
    }
    public void showSoftKeyboardView(){
    	showingSoftKeyboard = true;;
		mInputView.setVisibility(View.VISIBLE);
    	
    }
    public void hideSoftKeyboardView(){
    	showingSoftKeyboard = false;
    		mInputView.setVisibility(View.GONE);
    	
    }

    
    public void toggleSoftKeyboardView(){
    	showingSoftKeyboard = ! showingSoftKeyboard;
    	if(showingSoftKeyboard){
    		mInputView.setVisibility(View.VISIBLE);
    	}else{
    		mInputView.setVisibility(View.GONE);
    	}
    	
    }
    public void pickSuggestionManually(int index) {
        if (mCompletionOn && mCompletions != null && index >= 0
                && index < mCompletions.length) {
            CompletionInfo ci = mCompletions[index];
            getCurrentInputConnection().commitCompletion(ci);
            if (mCandidateView != null) {
                mCandidateView.clear();
            }
            updateShiftKeyState(getCurrentInputEditorInfo());
        } else if (mComposing.length() > 0) {
            // If we were generating candidate suggestions for the current
            // text, we would commit one of them here.  But for this sample,
            // we will just commit the current text.
            commitTyped(getCurrentInputConnection());
        }
    }
    
    public void swipeRight() {
        if (mCompletionOn) {
            pickDefaultCandidate();
        }
    }
    
    public void swipeLeft() {
        handleBackspace();
    }

    public void swipeDown() {
        handleClose();
    }

    public void swipeUp() {
    }
    
    public void onPress(int primaryCode) {
    }
    
    public void onRelease(int primaryCode) {
    }
}
