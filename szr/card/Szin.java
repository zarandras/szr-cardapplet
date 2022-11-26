package szr.card;

import szr.shared.*;
/**
 * A színek õsosztálya
 * Creation date: (2002.02.04. 10:54:57)
 */
public abstract class Szin extends OsSzin {
	public final static byte EmptyMask = 0x00;
	public final static byte FullMask = 0x7F;	// az összes (alap)szín egyszerre
/**
 * Végtelen hasonulási lánc esetén lilát kell adni:
 */
public static Szin getLimSzin(Szin aktSzin) {
	return new Lila();
}
public abstract int getMask();
protected final Szin getResolvedSzin0(TableStateRec ts) {
	return this;	// nem metaszín
}
/**
 * Az adott szín illeszkedik-e rá?
 */
public boolean illeszkedikRa(Szin mi) {
	try {
		return ((getMask() & mi.getMask()) != 0  ||
			(getMask() == EmptyMask && (mi.getMask() == EmptyMask || mi.uresreIsIlleszk())));
				// az emptyMaskra illeszkedik az EmptyMask és az uresreIsIlleszk()-edõ
	} catch (NullPointerException e) {
		return false;
	}
}
/**
 * Két kártyaszín egyenlõségvizsgálata (kártyailleszkedéshez) - nem feltétlenül tranzitív
 */
public abstract boolean megegyezik(Szin c);
/**
 * A szín extra paramétereinek (pl. kért szín) törlése / újrainicializálása
 *	(új szín objektumot generáljon ha módosítani kell)
 */
public Szin reset() {
	return this;
}
/**
 * EmptyMask-ú színre is illeszkedik-e?
 */
public abstract boolean uresreIsIlleszk();
}
