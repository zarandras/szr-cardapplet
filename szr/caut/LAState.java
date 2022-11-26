package szr.caut;

import szr.msg.GameAction;
import szr.card.*;
import szr.shared.*;
/**
 * Automata állapot õsosztály
 * Creation date: (2002.02.14. 13:13:51)
 */
public abstract class LAState {
	private int transPhase = 0;				// állapotátmenet fázisszámláló
	private GameAction startedGA = null;	// a GameAction, melynek a feldolgozása megkezdõdött
	private boolean transPaused;			// állapotátmenet szünet (szünet után újra ugyanaz a fázis kerül végrehajtásra)
	private int invokeCount = 0;			// az aktuális állapotátmenet-fázis hányadszorra indult el
public LAState() {
	super();
}
/**
 * Állapotátmenet folytatás (kezdés, ill. pause vége)
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
			// pauseTrans() híváskor ugyanaz a fázis fog újra kezdõdni, amelyikben hívtuk!
			transPhase++;
			invokeCount = 0;
		} else {
			invokeCount++;
		}
	} while (!transPaused && nextState == this);
	return nextState;
}
/**
 * Az akutális játékos állapot-kódja ebben a játékállapotban
 */
public abstract int getActivePlayerState();
/**
 * Az extra sorozási állapot ebben a játékállapotban
 */
public ExSType getExS() {
	return null;
}
/**
 * Az akutális játékos állapot-kódja ebben a játékállapotban
 */
public abstract int getInactivePlayerState();
/**
 * Az akutális jelzés ebben a játékállapotban
 */
public abstract Jelzes getJelzes();
/**
 * GameAction feldolgozásának kezdete
 */
protected szr.msg.GameAction getStartedGA() {
	return startedGA;
}
/**
 * Az akutális szín ebben a játékállapotban
 */
public abstract Szin getSzin();
/**
 * AutoPassz funkció van-e ebben a játékállapotban?
 */
public abstract boolean isAutoPasszState();
/**
 * Csaj utasításhalmozó jelzések kérhetõk-e ebben a játékállapotban?
 */
public boolean isUtRestricted() {
	return false;
}
/**
 * Adott színhez és jelzéshez megfelelõ alapállapot létrehozás
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
 * Adott színhez (lila, kért színnel vagy anélkül) és jelzéshez megfelelõ lila állapot létrehozás
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
 * Adott színhez és jelzéshez megfelelõ sorozó állapot létrehozás
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
 * Állapotátmenet indítása/folytatása beszúrás (vagy vétózás) akcióra
 */
protected abstract LAState nextStateBeszurCJ(int transPhase, int invokeCount);
/**
 * Állapotátmenet indítása/folytatása kezdõlaphúzás akcióra
 */
protected LAState nextStateKezdolapCJ(int transPhase, int invokeCount) {
	return null;
}
/**
 * Állapotátmenet indítása/folytatása kártyalerakás (vagy sorozás, halmozás) akcióra
 */
protected abstract LAState nextStateLerakCJ(int transPhase, int invokeCount);
/**
 * Állapotátmenet indítása/folytatása nyerés akcióra (ha van potenciális nyertes, akkor hívódik meg)
 */
protected LAState nextStateNyer(int transPhase, int invokeCount) {
	getStartedGA().doLAGERejectNyer();
	return null;
}
/**
 * Állapotátmenet indítása/folytatása passz (vagy befejezés) akcióra
 */
protected abstract LAState nextStatePassz(int transPhase, int invokeCount);
/**
 * Ha felhasználói interakció szükséges az állapotátmenet folytatásához, akkor szüneteltetni kell
 */
protected void pauseTrans() {
	transPaused = true;
}
/**
 * Állapotátmenet indítása a kapott GameAction-nel
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
