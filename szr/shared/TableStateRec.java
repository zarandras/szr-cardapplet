package szr.shared;

import java.io.Serializable;
import szr.card.*;
/**
 * A játékTér állapotát leíró rekord
 * Creation date: (2002.02.09. 23:38:35)
 */
public class TableStateRec implements Serializable {
	public final Szin aktSzin;
	public final Jelzes aktJelzes;
	public final Card jelzeslap;
public TableStateRec(Szin iAktSzin, Jelzes iAktJelzes, Card iJelzeslap) {
	super();
	aktSzin = iAktSzin;
	aktJelzes = iAktJelzes;
	jelzeslap = iJelzeslap;
}
/**
 * Illeszkedik-e az adott kártya ebben az állapotban?
 */
public boolean illeszkedikRa(Card cj) {
	Szin resolvedSzin = cj.getResolvedSzin(this);
	Jelzes resolvedJelzes = cj.getResolvedJelzes(this);
	
	if (resolvedSzin == null || resolvedJelzes == null)
		return false;
	else if (aktSzin == null || aktJelzes == null)
		return true;
	else
		return aktJelzes.illeszkedikRa(resolvedSzin, resolvedJelzes, aktSzin);
}
public String toString() {
	return "[" + ((aktSzin == null) ? "()": aktSzin.toString()) + " " + ((aktJelzes == null) ? "()": aktJelzes.toString()) + "], jelzéslap: " + jelzeslap.toString();//$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
}
