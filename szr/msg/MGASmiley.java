package szr.msg;

import szr.mainserv.*;
/**
 * Smiley átállítás akcióosztály
 * Creation date: (2002.02.06. 23:48:55)
 */
public class MGASmiley extends MetaGameAction {
	private final java.lang.String smiley;
public MGASmiley(String iSmiley) {
	super();
	smiley = iSmiley;
}
public void doServer(szr.mainserv.ServerMsgHandler smh) {
	if (smh.getPlayerData() != null) {
		smh.getPlayerData().setSmiley(smiley);
	}
}
public String toString() {
	return super.toString() + " " + smiley;//$NON-NLS-1$
}
}
