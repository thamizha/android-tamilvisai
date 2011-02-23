package com.tamil.visai;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.KeyEvent;

public class Constants {
	public static  final Map<Integer, Integer> SHIFTED_KEYS = new HashMap<Integer, Integer>();
	
	public static final Map<Integer, Integer> KEY_CODE_MAP = new HashMap<Integer, Integer>();
    static final List<Integer> KURIL_EXECPT_AGARAM = Arrays.asList(new Integer[]{2951,2953,2958,2962});
    static final Map<Integer, Integer> UYIR_NEDIL_MAP = new HashMap<Integer, Integer>();

    static final List<Integer> KURIL_SYMBOLS = Arrays.asList(new Integer[]{3007,3009,3014,3018});
    
    static final Map<Integer, Integer> KURIL_SYMBOLS_MAP = new HashMap<Integer, Integer>();
    static final Map<Integer, Integer> NEDIL_SYMBOLS_MAP = new HashMap<Integer, Integer>();
    
	static{
		SHIFTED_KEYS.put(2970, 3000);
		SHIFTED_KEYS.put(2985, 2979);
		SHIFTED_KEYS.put(2992, 2993);
		SHIFTED_KEYS.put(2994, 2995);
		
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
		
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_A, 2949);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_B, 2986);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_C, 2970);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_D, 2975);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_E, 2958);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_F, 102);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_G, 2965);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_H, 3001);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_I, 2951);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_J, 2972);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_K, 2965);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_L, 2994);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_M, 2990);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_N, 2985);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_O, 2962);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_P, 2986);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_Q, 2947);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_R, 2992);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_S, 2970);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_T, 2975);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_U, 2953);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_V, 2997);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_W, 2984);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_X, 120);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_Y, 2991);
		KEY_CODE_MAP.put(KeyEvent.KEYCODE_Z, 2996);
		
	}
	
	
}
