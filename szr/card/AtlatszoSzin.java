package szr.card;

import szr.shared.*;
/**
 * ÉgSzín metaszín-osztály
 * Creation date: (2002.02.08. 18:46:19)
 */
public class AtlatszoSzin extends OsSzin {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	
public AtlatszoSzin() {
	super();
}
public String getImgPath(boolean doublePicFlag) {
	return Card.getImgRootPath () + "a.gif";//$NON-NLS-1$
}
protected Szin getResolvedSzin0(TableStateRec ts) {
	try {
		return ts.aktSzin.reset();	// Az aktuális színhez hasonul
	} catch (NullPointerException e) {
		return null;
	}
}
public String getSmallImgPath() {
	return Card.getSmallImgRootPath () + "a.gif";//$NON-NLS-1$
}
public String getSzinSugoFileName() {
	return "c_a.html";//$NON-NLS-1$
}
public String toString() {
	return resCardTexts.getString("ÉgSzín"); //$NON-NLS-1$
}
}
