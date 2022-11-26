package szr.caut;

import szr.msg.GameAction;
import szr.card.*;
import szr.shared.*;
/**
 * Automata �llapot �soszt�ly
 * Creation date: (2002.02.14. 13:13:51)
 */
public abstract class LAState {
	private int transPhase = 0;				// �llapot�tmenet f�zissz�ml�l�
	private GameAction startedGA = null;	// a GameAction, melynek a feldolgoz�sa megkezd�d�tt
	private boolean transPaused;			// �llapot�tmenet sz�net (sz�net ut�n �jra ugyanaz a f�zis ker�l v�grehajt�sra)
	private int invokeCount = 0;			// az aktu�lis �llapot�tmenet-f�zis h�nyadszorra indult el
public LAState() {
	super();
}
/**
 * �llapot�tmenet folytat�s (kezd�s, ill. pause v�ge)
 */
public LAState contNextState() {
	transPaused = false;
	LAState nextState;
	do {
		switch (getStartedGA().getGAConst()) {
			case GameAction.GA_BESZUR_CJ:	 nextState = nextStateBeszurCJ(transPhase, invokeCount); break;
			case GameAction.GA_KEZDOLAP_CJP: nextState = nextStateKezdolapCJ(transPhase, invokeCount); break;
			case GameAction.GA_LERAK_CJ:	 nextState = nextStateLerakCJ(transPhase, invokeCount); break;
			case GameAction.GA_NYER:		 nextState = nextStateNyer(transPhase, invokeCount); break;
			case GameAction.GA_PASSZ:		 nextState = nextStatePassz(transPhase, invokeCount); break;
			default:						 nextState = null; break;
		}
		if (!transPaused) {
			// pauseTrans() h�v�skor ugyanaz a f�zis fog �jra kezd�dni, amelyikben h�vtuk!
			transPhase++;
			invokeCount = 0;
		} else {
			invokeCount++;
		}
	} while (!transPaused && nextState == this);
	return nextState;
}
/**
 * Az akut�lis j�t�kos �llapot-k�dja ebben a j�t�k�llapotban
 */
public abstract int getActivePlayerState();
/**
 * Az extra soroz�si �llapot ebben a j�t�k�llapotban
 */
public ExSType getExS() {
	return null;
}
/**
 * Az akut�lis j�t�kos �llapot-k�dja ebben a j�t�k�llapotban
 */
public abstract int getInactivePlayerState();
/**
 * Az akut�lis jelz�s ebben a j�t�k�llapotban
 */
public abstract Jelzes getJelzes();
/**
 * GameAction feldolgoz�s�nak kezdete
 */
protected szr.msg.GameAction getStartedGA() {
	return startedGA;
}
/**
 * Az akut�lis sz�n ebben a j�t�k�llapotban
 */
public abstract Szin getSzin();
/**
 * AutoPassz funkci� van-e ebben a j�t�k�llapotban?
 */
public abstract boolean isAutoPasszState();
/**
 * Csaj utas�t�shalmoz� jelz�sek k�rhet�k-e ebben a j�t�k�llapotban?
 */
public boolean isUtRestricted() {
	return false;
}
/**
 * Adott sz�nhez �s jelz�shez megfelel� alap�llapot l�trehoz�s
 */
protected static LAState newAlapLAState(Szin cardResolvedC, Jelzes cardResolvedJ) {
	if (cardResolvedJ instanceof ExSJel) {
		return new LASExSAlap(cardResolvedC, (ExSJel)cardResolvedJ, ((ExSJel)cardResolvedJ).getNewExST(cardResolvedC));
	} else if (cardResolvedJ instanceof UtJel) {
		return new LASUtAlap(cardResolvedC, cardResolvedJ);
	} else {
		return new LASAlap(cardResolvedC, cardResolvedJ);
	}
}
/**
 * Adott sz�nhez (lila, k�rt sz�nnel vagy an�lk�l) �s jelz�shez megfelel� lila �llapot l�trehoz�s
 */
protected static LAState newLilaLAState(Lila cardResolvedC, Jelzes cardResolvedJ) {
	if (cardResolvedJ instanceof ExSJel) {
		return new LASLilaExS(cardResolvedC, (ExSJel)cardResolvedJ, ((ExSJel)cardResolvedJ).getNewExST(cardResolvedC));
	} else if (cardResolvedJ instanceof UtJel) {
		return new LASLilaUt(cardResolvedC, cardResolvedJ);
	} else {
		return new LASLila(cardResolvedC, cardResolvedJ);
	}
}
/**
 * Adott sz�nhez �s jelz�shez megfelel� soroz� �llapot l�trehoz�s
 */
protected static LAState newSorozoLAState(Szin cardResolvedC, Jelzes cardResolvedJ) {
	if (cardResolvedJ instanceof ExSJel) {
		return new LASExS(cardResolvedC, (ExSJel)cardResolvedJ, ((ExSJel)cardResolvedJ).getNewExST(cardResolvedC));
	} else if (cardResolvedJ instanceof UtJel) {
		return new LASUtSorozo(cardResolvedC, cardResolvedJ);
	} else {
		return new LASSorozo(cardResolvedC, cardResolvedJ);
	}
}
/**
 * �llapot�tmenet ind�t�sa/folytat�sa besz�r�s (vagy v�t�z�s) akci�ra
 */
protected abstract LAState nextStateBeszurCJ(int transPhase, int invokeCount);
/**
 * �llapot�tmenet ind�t�sa/folytat�sa kezd�laph�z�s akci�ra
 */
protected LAState nextStateKezdolapCJ(int transPhase, int invokeCount) {
	return null;
}
/**
 * �llapot�tmenet ind�t�sa/folytat�sa k�rtyalerak�s (vagy soroz�s, halmoz�s) akci�ra
 */
protected abstract LAState nextStateLerakCJ(int transPhase, int invokeCount);
/**
 * �llapot�tmenet ind�t�sa/folytat�sa nyer�s akci�ra (ha van potenci�lis nyertes, akkor h�v�dik meg)
 */
protected LAState nextStateNyer(int transPhase, int invokeCount) {
	getStartedGA().doLAGERejectNyer();
	return null;
}
/**
 * �llapot�tmenet ind�t�sa/folytat�sa passz (vagy befejez�s) akci�ra
 */
protected abstract LAState nextStatePassz(int transPhase, int invokeCount);
/**
 * Ha felhaszn�l�i interakci� sz�ks�ges az �llapot�tmenet folytat�s�hoz, akkor sz�neteltetni kell
 */
protected void pauseTrans() {
	transPaused = true;
}
/**
 * �llapot�tmenet ind�t�sa a kapott GameAction-nel
 */
public LAState startNextState(GameAction iStartedGA) {
	try {
		if (iStartedGA.getGAConst() == GameAction.GA_NONE)
			return null;
	} catch (NullPointerException e) {
		return null;
	}
	startedGA = iStartedGA;
	transPhase = 0;
	invokeCount = 0;
	LAState retState = contNextState();
	if (retState == null) {
		startedGA = null;
	}
	return retState;
}
public abstract String toString();
}
