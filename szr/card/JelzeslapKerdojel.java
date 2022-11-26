package szr.card;

import szr.shared.*;
/**
 * Jelzéslap-kérdõjel metajelzés-osztály
 * Creation date: (2002.02.09. 23:23:45)
 */
public class JelzeslapKerdojel extends OsJelzes {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	
public JelzeslapKerdojel() {
	super();
}
public boolean getDoublePicFlag() {
	return true;
}
public int getID() {
	return 25;
}
protected Jelzes getResolvedJelzes0(TableStateRec ts) {
	try {
		/* itt hívódik a getLimJelzes, ha végtelen rekurzió lenne: */
		Jelzes j = ts.jelzeslap.getResolvedJelzes(ts);	// A jelzéslap jelzéséhez hasonul
		return j.reset();
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
	return resCardTexts.getString("JelzéslapKérdõjel"); //$NON-NLS-1$
}
}
