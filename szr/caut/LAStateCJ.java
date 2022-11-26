package szr.caut;

import szr.card.*;
/**
 * Szín és jelzés paraméterrel rendelkezõ õs automataállapot-osztály
 * Creation date: (2002.03.26. 19:00:15)
 */
public abstract class LAStateCJ extends LAState {
	protected Szin c;
	protected Jelzes j;
public LAStateCJ(Szin iC, Jelzes iJ) {
	super();
	c = iC;
	j = iJ;
}
public szr.card.Jelzes getJelzes() {
	return j;
}
public szr.card.Szin getSzin() {
	return c;
}
public boolean isAutoPasszState() {
	return true;
}
/**
 * A kért jelzés megadása GameAction feldolgozása (állapotátmenet) közben
 */
public boolean setKertJel(szr.card.Jelzes kertJ) {
	boolean accepted = false;
	if (c instanceof JelzesKero) {
		c = (Szin)((JelzesKero)c).applyKertJel(kertJ);
		accepted = true;
	}
	if (j instanceof JelzesKero) {
		j = (Jelzes)((JelzesKero)j).applyKertJel(kertJ);
		accepted = true;
	}
	return accepted;	
}
/**
 * A kért szín megadása GameAction feldolgozása (állapotátmenet) közben
 */
public boolean setKertSzin(szr.card.SzinesSzin kertC) {
	boolean accepted = false;
	if (c instanceof SzinKero) {
		c = (Szin)((SzinKero)c).applyKertSzin(kertC);
		accepted = true;
	}
	if (j instanceof SzinKero) {
		j = (Jelzes)((SzinKero)j).applyKertSzin(kertC);
		accepted = true;
	}
	return accepted;
}
}
