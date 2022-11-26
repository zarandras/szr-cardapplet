package szr.card;

import java.io.Serializable;
import szr.shared.*;
/**
 * Jelz�s �s metajelz�s �soszt�ly
 * Creation date: (2002.02.04. 10:55:26)
 */
public abstract class OsJelzes implements Serializable {
	private transient boolean underResolve = false;	// rekurz�v hasonul�s (resolve) detekt�l�s�ra
public OsJelzes() {
	super();
}
/**
 * K�tsz�n� k�rty�n�l a sz�nhez tartoz� k�t k�p k�z�l melyik kell?
 */
public abstract boolean getDoublePicFlag();
public abstract int getID();
public String getImgPath(OsSzin kartyaSzin) {	/* kartyaSzin can be null! */
	return Card.getImgRootPath () + ((kartyaSzin != null && hasKetszinuImg() && kartyaSzin.isKetszinu()) ? "0" : "")//$NON-NLS-2$//$NON-NLS-1$
								  + Integer.toString(getID())
								  + ((kartyaSzin instanceof AtlatszoSzin && hasAImg()) ? "a" : "")//$NON-NLS-2$//$NON-NLS-1$
								  + ((kartyaSzin instanceof Lila && hasLImg()) ? "l" : "")//$NON-NLS-2$//$NON-NLS-1$
								  + ".gif";//$NON-NLS-1$
}
public String getJelzesSugoFileName() {
	return "j_" + Integer.toString(getID()) + ".html";//$NON-NLS-2$//$NON-NLS-1$
}
public synchronized final Jelzes getResolvedJelzes(TableStateRec ts) {
	if (underResolve)	// ilyenkor rekurzi� van
		return Jelzes.getLimJelzes(ts.aktJelzes);
	underResolve = true;
	Jelzes j = getResolvedJelzes0(ts);
	underResolve = false;
	return j;
}
/**
 * Miv� v�lik lerak�skor?
 */
protected abstract Jelzes getResolvedJelzes0(TableStateRec ts);
public String getSmallImgPath() {
	return Card.getSmallImgRootPath ()
			+ Integer.toString(getID())
			+ ".gif";//$NON-NLS-1$
}
/**
 * Van-e k�l�n �gSz�n� k�rtyak�p?
 */
public abstract boolean hasAImg();
/**
 * Van-e k�l�n k�rtyak�pe a k�tsz�n�eknek?
 */
public abstract boolean hasKetszinuImg();
/**
 * Van-e k�l�n k�rtyak�pe a lil�knak?
 */
public abstract boolean hasLImg();
public abstract String toString();
}
