package szr.card;

import szr.shared.*;
/**
 * A sz�nek �soszt�lya
 * Creation date: (2002.02.04. 10:54:57)
 */
public abstract class Szin extends OsSzin {
	public final static byte EmptyMask = 0x00;
	public final static byte FullMask = 0x7F;	// az �sszes (alap)sz�n egyszerre
/**
 * V�gtelen hasonul�si l�nc eset�n lil�t kell adni:
 */
public static Szin getLimSzin(Szin aktSzin) {
	return new Lila();
}
public abstract int getMask();
protected final Szin getResolvedSzin0(TableStateRec ts) {
	return this;	// nem metasz�n
}
/**
 * Az adott sz�n illeszkedik-e r�?
 */
public boolean illeszkedikRa(Szin mi) {
	try {
		return ((getMask() & mi.getMask()) != 0  ||
			(getMask() == EmptyMask && (mi.getMask() == EmptyMask || mi.uresreIsIlleszk())));
				// az emptyMaskra illeszkedik az EmptyMask �s az uresreIsIlleszk()-ed�
	} catch (NullPointerException e) {
		return false;
	}
}
/**
 * K�t k�rtyasz�n egyenl�s�gvizsg�lata (k�rtyailleszked�shez) - nem felt�tlen�l tranzit�v
 */
public abstract boolean megegyezik(Szin c);
/**
 * A sz�n extra param�tereinek (pl. k�rt sz�n) t�rl�se / �jrainicializ�l�sa
 *	(�j sz�n objektumot gener�ljon ha m�dos�tani kell)
 */
public Szin reset() {
	return this;
}
/**
 * EmptyMask-� sz�nre is illeszkedik-e?
 */
public abstract boolean uresreIsIlleszk();
}
