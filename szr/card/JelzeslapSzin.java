package szr.card;

import szr.shared.*;
/**
 * F�ldSz�n metasz�n-oszt�ly
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
		/* itt h�v�dik a getLimSzin, ha v�gtelen rekurzi� lenne: */
		Szin c = ts.jelzeslap.getResolvedSzin(ts);	// A jelz�slap sz�n�hez hasonul
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
	return resCardTexts.getString("F�ldSz�n"); //$NON-NLS-1$
}
}
