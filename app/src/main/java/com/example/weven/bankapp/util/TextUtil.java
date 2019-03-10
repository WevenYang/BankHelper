package com.example.weven.bankapp.util;

import java.util.Collection;
import java.util.Map;

public class TextUtil {
	public static boolean isValidate(String content) {
		return content != null && !"".equals(content.replaceAll("\\s*",""));
	}
	public static boolean isValidate(CharSequence content) {
		return content != null && !"".equals(content.toString().replaceAll("\\s*",""));
	}

	public static boolean isValidate(Collection<?> list) {
		return list != null && list.size() > 0;
	}

	public static boolean isValidate(Map<?, ?> map) {
		return map != null && map.size() > 0;
	}
}
