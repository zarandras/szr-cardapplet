package szr.msg;

import szr.card.*;
/**
 * Lap lerakása eseményosztály
 * Creation date: (2002.03.02. 16:57:38)
 */
public class GEPushLerakottLap extends GameEvent {
	private final Card lap;
public GEPushLerakottLap(Card iLap) {
	super();
	lap = iLap;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().getGameTable().pushLerakottLap(lap);
}
public String toString() {
	return super.toString() + " " + lap.toString();//$NON-NLS-1$
}
}
