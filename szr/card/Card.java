package szr.card;

import java.io.Serializable;
import szr.gui.CardApplet;
import szr.shared.*;
/**
 * A kártyákat reprezentáló osztály
 * Creation date: (2002.02.04. 10:53:51)
 */
public class Card implements Serializable {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	private final OsSzin szin;
	private final OsJelzes jelzes;
	private final static java.lang.String imgRootPath = "cardimg/";//$NON-NLS-1$
	private final static java.lang.String smallImgRootPath = "cardimg/smallimg/";//$NON-NLS-1$
public Card(OsSzin iSzin, OsJelzes iJelzes) {
	super();

	szin = iSzin;
	jelzes = iJelzes;
}
public String getImg1Path() {
	try {
		return szin.getImgPath(jelzes.getDoublePicFlag());
	} catch (NullPointerException e) {
		return getImgRootPath() + "c.gif";//$NON-NLS-1$
	}
}
public String getImg2Path() {
	try {
		return jelzes.getImgPath(szin);
	} catch (NullPointerException e) {
		return null;
	}
}
public static java.lang.String getImgRootPath() {
	return imgRootPath;
}
public OsJelzes getJelzes() {
	return jelzes;
}
public Jelzes getResolvedJelzes(TableStateRec ts) {
	return getJelzes().getResolvedJelzes(ts);	// nem hozhat létre új Jelzes objektumot
}
public Szin getResolvedSzin(TableStateRec ts) {
	return getSzin().getResolvedSzin(ts);	// nem hozhat létre új Szin objektumot
}
public static java.lang.String getSmallImgRootPath() {
	return smallImgRootPath;
}
public OsSzin getSzin() {
	return szin;
}
public String toString() {
	return SubstituteStr.doIt(SubstituteStr.doIt(resCardTexts.getString("CardToString"), "%c", (szin == null) ? "()": szin.toString()), "%j", (jelzes == null) ? "()": jelzes.toString());//$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
}
}
