package szr.msg;

import szr.card.*;
/**
 * Legfelsõ lerakott lap elvétele eseményosztály
 * Creation date: (2002.03.02. 16:57:38)
 */
public class GEPopLerakottLap extends GameEvent {
public GEPopLerakottLap() {
	super();
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().getGameTable().popLerakottLap();
}
public String toString() {
	return super.toString();
}
}
