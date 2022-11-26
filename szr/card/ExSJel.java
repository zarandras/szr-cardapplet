package szr.card;

import szr.shared.*;
/**
 * Extrasoroz� jelz�s �soszt�ly
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
 * Priorit�s (arra kell, ha LAExsAlap �llapotban extrasoroz� lapot tesz le -
 *			  ha nagyobb a priorit�sa, mint az aktu�lis�, akkor �j extra soroz�s indul)
 */
public abstract int getExSPriority();
/**
 * Hozz� tartoz� �jonnan inicializ�lt extra soroz�st le�r� objektumot ad
 */
public abstract ExSType getNewExST(Szin cardC);
}
