package com.tamil.visai;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
	public static  final Map<Integer, Integer> SHIFTED_KEYS = new HashMap<Integer, Integer>();
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
		
	}
}
