package szr.card;

import szr.shared.*;
/**
 * ComboLerakó jelzésosztály
 * Creation date: (2002.03.05. 17:53:42)
 */
public class ComboJel_HU extends ExSJel {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	
public int getExSPriority() {
	return 0;
}
public int getID() {
	return 27;
}
public ExSType getNewExST(Szin resolvedColor) {
	return new ExSTCombo();
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
	return resCardTexts.getString("ComboLerakó"); //$NON-NLS-1$
}
}
