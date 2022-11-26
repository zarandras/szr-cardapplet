package szr.card;

import szr.shared.*;
/**
 * �gSz�n metasz�n-oszt�ly
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
		return ts.aktSzin.reset();	// Az aktu�lis sz�nhez hasonul
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
	return resCardTexts.getString("�gSz�n"); //$NON-NLS-1$
}
}
