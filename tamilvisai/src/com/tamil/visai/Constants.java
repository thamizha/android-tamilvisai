package com.tamil.visai;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.KeyEvent;

public class Constants {
	public static final Map<Integer, Integer> PHONETIC_KEY_CODE_MAP = new HashMap<Integer, Integer>();
	public static  final Map<Integer, Integer> PHONETIC_SHIFTED_KEYS = new HashMap<Integer, Integer>();
	
	public static final Map<Integer, Integer> T99_CODE_MAP = new HashMap<Integer, Integer>();
	public static  final Map<Integer, Integer> T99_SHIFT_MAP = new HashMap<Integer, Integer>();
	public static  final Map<Integer, Integer> T99_ALT_MAP = new HashMap<Integer, Integer>();
	
    static final List<Integer> KURIL_EXECPT_AGARAM = Arrays.asList(new Integer[]{2951,2953,2958,2962});
    static final Map<Integer, Integer> UYIR_NEDIL_MAP = new HashMap<Integer, Integer>();

    static final List<Integer> KURIL_SYMBOLS = Arrays.asList(new Integer[]{3007,3009,3014,3018});
    
    static final Map<Integer, Integer> KURIL_SYMBOLS_MAP = new HashMap<Integer, Integer>();
    static final Map<Integer, Integer> NEDIL_SYMBOLS_MAP = new HashMap<Integer, Integer>();
    
	static{
		PHONETIC_SHIFTED_KEYS.put(2970, 3000);
		PHONETIC_SHIFTED_KEYS.put(2985, 2979);
		PHONETIC_SHIFTED_KEYS.put(2992, 2993);
		PHONETIC_SHIFTED_KEYS.put(2994, 2995);
		
//		PHONETIC_SHIFTED_KEYS.put(KeyEvent.KEYCODE_S, 3000);
//		PHONETIC_SHIFTED_KEYS.put(KeyEvent.KEYCODE_N, 2979);
//		PHONETIC_SHIFTED_KEYS.put(KeyEvent.KEYCODE_R, 2993);
//		PHONETIC_SHIFTED_KEYS.put(KeyEvent.KEYCODE_R, 2995);

		
		KURIL_SYMBOLS_MAP.put(2951, 3007);
		KURIL_SYMBOLS_MAP.put(2953, 3009);
		KURIL_SYMBOLS_MAP.put(2958, 3014);
		KURIL_SYMBOLS_MAP.put(2962, 3018);
		
		NEDIL_SYMBOLS_MAP.put(2951, 3008);
		NEDIL_SYMBOLS_MAP.put(2953, 3010);
		NEDIL_SYMBOLS_MAP.put(2958, 3015);
		NEDIL_SYMBOLS_MAP.put(2962, 3019);
		
		
		UYIR_NEDIL_MAP.put(2951, 2952);
		UYIR_NEDIL_MAP.put(2953, 2954);
		UYIR_NEDIL_MAP.put(2958, 2959);
		UYIR_NEDIL_MAP.put(2962, 2963);
		
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_A, 2949);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_B, 2986);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_C, 2970);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_D, 2975);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_E, 2958);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_F, 102);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_G, 2965);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_H, 3001);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_I, 2951);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_J, 2972);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_K, 2965);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_L, 2994);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_M, 2990);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_N, 2985);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_O, 2962);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_P, 2986);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_Q, 2947);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_R, 2992);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_S, 2970);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_T, 2975);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_U, 2953);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_V, 2997);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_W, 2984);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_X, 120);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_Y, 2991);
		PHONETIC_KEY_CODE_MAP.put(KeyEvent.KEYCODE_Z, 2996);
		
		
		T99_CODE_MAP.put(KeyEvent.KEYCODE_A, 2949);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_B, 2969);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_C, 2962);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_D, 2953);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_E, 2954);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_F, 3021);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_G, 2958);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_H, 2965);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_I, 2985);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_J, 2986);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_K, 2990);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_L, 2980);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_M, 2992);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_N, 2994);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_O, 2975);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_P, 2979);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_Q, 2950);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_R, 2960);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_S, 2951);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_T, 2959);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_U, 2993);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_V, 2997);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_W, 2952);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_X, 2963);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_Y, 2995);
		T99_CODE_MAP.put(KeyEvent.KEYCODE_Z, 2964);


		T99_SHIFT_MAP.put(KeyEvent.KEYCODE_Q, 3000);
		T99_SHIFT_MAP.put(KeyEvent.KEYCODE_W, 2999);
		T99_SHIFT_MAP.put(KeyEvent.KEYCODE_E, 2972);
		T99_SHIFT_MAP.put(KeyEvent.KEYCODE_R, 3001);
		T99_SHIFT_MAP.put(KeyEvent.KEYCODE_F, 2947);
		
		T99_ALT_MAP.put(KeyEvent.KEYCODE_LEFT_BRACKET, 2970);
		T99_ALT_MAP.put(KeyEvent.KEYCODE_RIGHT_BRACKET, 2974);
		T99_ALT_MAP.put(KeyEvent.KEYCODE_T, 2970);
		T99_ALT_MAP.put(KeyEvent.KEYCODE_Y, 2974);

		
		T99_ALT_MAP.put(KeyEvent.KEYCODE_SEMICOLON, 2984);
		T99_ALT_MAP.put(KeyEvent.KEYCODE_K, 2984);
		
		T99_ALT_MAP.put(KeyEvent.KEYCODE_APOSTROPHE, 2991);
		T99_ALT_MAP.put(KeyEvent.KEYCODE_R, 2991);
		T99_ALT_MAP.put(KeyEvent.KEYCODE_SLASH , 2996);
		
	}
	
	
}
