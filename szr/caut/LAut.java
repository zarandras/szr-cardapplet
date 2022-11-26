package szr.caut;

import szr.card.*;
import szr.msg.*;
/**
 * A szerver Automata oszt�lya
 *   (v�ges determinisztikus automata:
 *    a be�rkezett GameAction-�ket dolgozza fel
 *    mint a helyes j�t�kmenetet le�r� form�lis nyelv termin�lisait)
 * Creation date: (2002.02.14. 13:41:51)
 */
public class LAut {
	private LAState currState = null;	// az automata aktu�lis �llapota
	protected final java.lang.String gameStr;	// a napl�z�s miatt kell a j�t�k azonos�t�s�hoz
public LAut(String iGameStr) {
	super();
	gameStr = iGameStr;
}
/**
 * �llapot�tmenet folytat�sa (pause ut�n, ha be�rkezett a felhaszn�l� v�lasza)
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
 * A feldolgoz�s alatt �ll� GameAction
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
 * �llapotv�lt�s (GameAction feldogoz�s�nak v�g�n)
 */
protected void setCurrState(LAState newCurrState) {
	currState = newCurrState;
	szr.msg.Logger.log(new szr.msg.InfoLog(szr.shared.SubstituteStr.doIt("------> New LAState: ========= %las <------", "%las", currState.toString())), gameStr, null);//$NON-NLS-2$//$NON-NLS-1$
}
/**
 * �llapot�tmenet k�zben (pause alatt) a k�rt jelz�s megad�sa
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
 * �llapot�tmenet k�zben (pause alatt) a k�rt sz�n megad�sa
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
 * �llapot�tmenet ind�t�s a be�rkezett GameAction-nel
 */
public void startNextState(GameAction receivedGA) {
	if (getStartedGA() == null) {	// ha m�r elkezd�d�tt egy GA, akkor csak a contNextState h�vhat�!
		LAState retState = currState.startNextState(receivedGA);
		if (retState != null && retState != currState) {
			setCurrState(retState);
			receivedGA.doLAGEAcceptFinished();
		}
	}
}
}
