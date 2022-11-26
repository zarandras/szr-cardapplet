package szr.card;

import szr.shared.*;
/**
 * Láncsorozó jelzésosztály
 * Creation date: (2002.03.05. 17:53:42)
 */
public class LancSorozoJel extends ExSJel {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	
public int getExSPriority() {
	return 2;
}
public int getID() {
	return 11;
}
public ExSType getNewExST(Szin resolvedColor) {
	return new ExSTLancSorozo();
}
public boolean hasAImg() {
	return true;
}
public boolean hasKetszinuImg() {
	return false;
}
public boolean hasLImg() {
	return true;
}
public String toString() {
	return resCardTexts.getString("LáncSorozó"); //$NON-NLS-1$
}
}
