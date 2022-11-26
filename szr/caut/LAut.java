package szr.caut;

import szr.card.*;
import szr.msg.*;
/**
 * A szerver Automata osztálya
 *   (véges determinisztikus automata:
 *    a beérkezett GameAction-öket dolgozza fel
 *    mint a helyes játékmenetet leíró formális nyelv terminálisait)
 * Creation date: (2002.02.14. 13:41:51)
 */
public class LAut {
	private LAState currState = null;	// az automata aktuális állapota
	protected final java.lang.String gameStr;	// a naplózás miatt kell a játék azonosításához
public LAut(String iGameStr) {
	super();
	gameStr = iGameStr;
}
/**
 * Állapotátmenet folytatása (pause után, ha beérkezett a felhasználó válasza)
 */
public void contNextState() {
	GameAction startedGA = getStartedGA();
	if (startedGA != null) {
		LAState retState = currState.contNextState();
		if (retState != null && retState != currState) {
			setCurrState(retState);
			startedGA.doLAGEAcceptFinished();
		}
	}
}
public LAState getCurrState() {
	return currState;
}
/**
 * A feldolgozás alatt álló GameAction
 */
public GameAction getStartedGA() {
	try {
		return currState.getStartedGA();
	} catch (NullPointerException e) {
		return null;
	}
}
public void initialize() {
	setCurrState(new LASKezd());
}
/**
 * Állapotváltás (GameAction feldogozásának végén)
 */
protected void setCurrState(LAState newCurrState) {
	currState = newCurrState;
	szr.msg.Logger.log(new szr.msg.InfoLog(szr.shared.SubstituteStr.doIt("------> New LAState: ========= %las <------", "%las", currState.toString())), gameStr, null);//$NON-NLS-2$//$NON-NLS-1$
}
/**
 * Állapotátmenet közben (pause alatt) a kért jelzés megadása
 */
public void setKertJel(Jelzes kertJ) {
	boolean askLAState;
	GameAction ga = getStartedGA();
	if (ga != null)
		askLAState = !ga.setKertJel(kertJ);
	else
		askLAState = true;

	if (askLAState) {
		try {
			LAStateCJ currState = (LAStateCJ)getCurrState();
			if (!currState.setKertJel(kertJ))
				; // error
		} catch (ClassCastException e) {
				; // error
		}
	}
}
/**
 * Állapotátmenet közben (pause alatt) a kért szín megadása
 */
public void setKertSzin(SzinesSzin kertC) {
	boolean askLAState;
	GameAction ga = getStartedGA();
	if (ga != null)
		askLAState = !ga.setKertSzin(kertC);
	else
		askLAState = true;

	if (askLAState) {
		try {
			LAStateCJ currState = (LAStateCJ)getCurrState();
			if (!currState.setKertSzin(kertC))
				; // error
		} catch (ClassCastException e) {
				; // error
		}
	}
}
/**
 * Állapotátmenet indítás a beérkezett GameAction-nel
 */
public void startNextState(GameAction receivedGA) {
	if (getStartedGA() == null) {	// ha már elkezdõdött egy GA, akkor csak a contNextState hívható!
		LAState retState = currState.startNextState(receivedGA);
		if (retState != null && retState != currState) {
			setCurrState(retState);
			receivedGA.doLAGEAcceptFinished();
		}
	}
}
}
