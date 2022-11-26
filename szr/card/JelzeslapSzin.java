package szr.card;

import szr.shared.*;
/**
 * FöldSzín metaszín-osztály
 * Creation date: (2002.02.08. 18:46:19)
 */
public class JelzeslapSzin extends OsSzin {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	
public JelzeslapSzin() {
	super();
}
public String getImgPath(boolean doublePicFlag) {
	return Card.getImgRootPath () + "r.gif";//$NON-NLS-1$
}
protected Szin getResolvedSzin0(TableStateRec ts) {
	try {
		/* itt hívódik a getLimSzin, ha végtelen rekurzió lenne: */
		Szin c = ts.jelzeslap.getResolvedSzin(ts);	// A jelzéslap színéhez hasonul
		return c.reset();
	} catch (NullPointerException e) {
		return null;
	}
}
public String getSmallImgPath() {
	return Card.getSmallImgRootPath () + "r.gif";//$NON-NLS-1$
}
public String getSzinSugoFileName() {
	return "c_r.html";//$NON-NLS-1$
}
public String toString() {
	return resCardTexts.getString("FöldSzín"); //$NON-NLS-1$
}
}
