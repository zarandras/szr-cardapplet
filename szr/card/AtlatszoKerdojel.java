package szr.card;

import szr.shared.*;
/**
 * Átlátszó kérdõjel metajelzés-osztály
 * Creation date: (2002.02.09. 23:21:48)
 */
public class AtlatszoKerdojel extends OsJelzes {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	
public AtlatszoKerdojel() {
	super();
}
public boolean getDoublePicFlag() {
	return false;
}
public int getID() {
	return 26;
}
protected Jelzes getResolvedJelzes0(TableStateRec ts) {
	try {
		return ts.aktJelzes.reset();	// Az aktuális jelzéshez hasonul
	} catch (NullPointerException e) {
		return null;
	}
}
public boolean hasAImg() {
	return false;
}
public boolean hasKetszinuImg() {
	return true;
}
public boolean hasLImg() {
	return false;
}
public String toString() {
	return resCardTexts.getString("ÁtlátszóKérdõjel"); //$NON-NLS-1$
}
}
