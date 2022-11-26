package szr.msg;

import szr.maincli.*;
import java.util.*;
/**
 * Játéklista eseményosztály
 * Creation date: (2002.02.05. 16:49:29)
 */
public class PEJatekLista extends PersonalEvent {
	private final java.util.Vector jatekVect;
public PEJatekLista(Vector iJatekVect) {
	super();
	jatekVect = iJatekVect;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController ().getGameControlPanel ().fillJatekList (jatekVect);
}
public String toString() {
	return super.toString() + " " + jatekVect.toString();//$NON-NLS-1$
}
}
