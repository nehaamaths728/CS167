package com.rdk.crime.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static String normalizeSubstring(String source) {
		String normalized = new String(source);

		Pattern p = Pattern.compile("\"(.*?)\"");
		Matcher m = p.matcher(source);

		while (m.find()) {
			String modifiyedInlineData = m.group(1).replace(",", ";");
			normalized = normalized.replace(m.group(1), modifiyedInlineData);
		}

		return normalized;
	}

}
