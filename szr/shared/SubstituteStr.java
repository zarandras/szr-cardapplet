package szr.shared;

/**
 * Substring helyettesítõ osztály
 * Creation date: (2002.03.03. 15:19:56)
 */
public class SubstituteStr {
/**
 * Az origPattern-ben az origSubstring elsõ elõfordulását newSubstring-re cseréli
 */
public static String doIt(String origPattern, String origSubstring, String newSubstring) {
	int startIdx = origPattern.indexOf(origSubstring);
	if (startIdx < 0) {
		return origPattern;
	} else {
		return origPattern.substring(0, startIdx) + newSubstring + origPattern.substring(startIdx + origSubstring.length());
	}
}
}
