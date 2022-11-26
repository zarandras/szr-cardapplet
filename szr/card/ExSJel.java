package szr.card;

import szr.shared.*;
/**
 * Extrasorozó jelzés õsosztály
 * Creation date: (2002.03.05. 7:20:43)
 */
public abstract class ExSJel extends Jelzes {
public boolean doLPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		jv.forceSzinKeres();
		return false;
	} else {
		if (invokeCounter > 1)
			logInvalidInvokeCounter(invokeCounter);
		return true;
	}
}
public boolean getDoublePicFlag() {
	return false;
}
/**
 * Prioritás (arra kell, ha LAExsAlap állapotban extrasorozó lapot tesz le -
 *			  ha nagyobb a prioritása, mint az aktuálisé, akkor új extra sorozás indul)
 */
public abstract int getExSPriority();
/**
 * Hozzá tartozó újonnan inicializált extra sorozást leíró objektumot ad
 */
public abstract ExSType getNewExST(Szin cardC);
}
