package szr.msg;

import szr.maincli.*;
import java.util.*;
/**
 * Játékoslista (lekérdezés eredménye) eseményosztály
 * Creation date: (2002.02.06. 13:12:02)
 */
public class PEJatekosLista extends PersonalEvent {
	private final java.util.Vector jatekosVect;
public PEJatekosLista(Vector iJatekosVect) {
	super();

	jatekosVect = iJatekosVect;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController ().getPlayerList ().fillPlayerList (jatekosVect);
}
public String toString() {
	return super.toString() + " " + jatekosVect.toString();//$NON-NLS-1$
}
}
