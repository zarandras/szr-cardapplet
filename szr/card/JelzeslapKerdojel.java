package szr.card;

import szr.shared.*;
/**
 * Jelz�slap-k�rd�jel metajelz�s-oszt�ly
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
		/* itt h�v�dik a getLimJelzes, ha v�gtelen rekurzi� lenne: */
		Jelzes j = ts.jelzeslap.getResolvedJelzes(ts);	// A jelz�slap jelz�s�hez hasonul
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
	return resCardTexts.getString("Jelz�slapK�rd�jel"); //$NON-NLS-1$
}
}
