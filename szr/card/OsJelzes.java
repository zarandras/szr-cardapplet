package szr.card;

import java.io.Serializable;
import szr.shared.*;
/**
 * Jelzés és metajelzés õsosztály
 * Creation date: (2002.02.04. 10:55:26)
 */
public abstract class OsJelzes implements Serializable {
	private transient boolean underResolve = false;	// rekurzív hasonulás (resolve) detektálására
public OsJelzes() {
	super();
}
/**
 * Kétszínû kártyánál a színhez tartozó két kép közül melyik kell?
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
	if (underResolve)	// ilyenkor rekurzió van
		return Jelzes.getLimJelzes(ts.aktJelzes);
	underResolve = true;
	Jelzes j = getResolvedJelzes0(ts);
	underResolve = false;
	return j;
}
/**
 * Mivé válik lerakáskor?
 */
protected abstract Jelzes getResolvedJelzes0(TableStateRec ts);
public String getSmallImgPath() {
	return Card.getSmallImgRootPath ()
			+ Integer.toString(getID())
			+ ".gif";//$NON-NLS-1$
}
/**
 * Van-e külön ÉgSzínû kártyakép?
 */
public abstract boolean hasAImg();
/**
 * Van-e külön kártyaképe a kétszínûeknek?
 */
public abstract boolean hasKetszinuImg();
/**
 * Van-e külön kártyaképe a liláknak?
 */
public abstract boolean hasLImg();
public abstract String toString();
}
