package szr.card;

import szr.shared.*;
/**
 * Ász jelzésosztály
 * Creation date: (2002.03.05. 17:53:42)
 */
public class AszJel extends ExSJel implements UtHalmozo {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	
public int getExSPriority() {
	return 1;
}
public int getID() {
	return 10;
}
public ExSType getNewExST(Szin resolvedColor) {
	return new ExSTAsz(resolvedColor);
}
public boolean hasAImg() {
	return false;
}
public boolean hasKetszinuImg() {
	return false;
}
public boolean hasLImg() {
	return false;
}
public String toString() {
	return resCardTexts.getString("Ász"); //$NON-NLS-1$
}
}
