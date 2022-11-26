package szr.card;

import java.io.Serializable;
import szr.shared.*;
/**
 * Szín és metaszín õsosztály
 * Creation date: (2002.02.04. 10:54:48)
 */
public abstract class OsSzin implements Serializable {
	private transient boolean underResolve = false;	// rekurzív hasonulás (resolve) detektálására
public OsSzin() {
	super();
}
public abstract String getImgPath(boolean doublePicFlag);
public synchronized final Szin getResolvedSzin(TableStateRec ts) {
	if (underResolve)
		return Szin.getLimSzin(ts.aktSzin);
	underResolve = true;
	Szin c = getResolvedSzin0(ts);
	underResolve = false;
	return c;
}
/**
 * Mivé válik lerakáskor?
 */
protected abstract Szin getResolvedSzin0(TableStateRec ts);
public abstract String getSmallImgPath();
public abstract String getSzinSugoFileName();
public boolean isKetszinu() {
	return false;
}
public abstract String toString();
}
