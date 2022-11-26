package szr.msg;

import szr.maincli.*;
/**
 * Játékosállapot-változás eseményosztály
 * Creation date: (2002.02.06. 13:49:29)
 */
public class PEChangeState extends PersonalEvent {
	private final int newState;	// játékosállapot-kód
public PEChangeState(int iNewState) {
	super();
	newState = iNewState;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController ().setPlayState (newState);
}
public String toString() {
	return super.toString() + " state: " + newState;//$NON-NLS-1$
}
}
