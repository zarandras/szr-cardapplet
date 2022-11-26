package szr.msg;

import szr.shared.*;
import szr.maincli.*;
/**
 * Játékos adatainak változása eseményosztály
 * Creation date: (2002.02.06. 23:46:39)
 */
public class GEAdjustPlayer extends GameEvent {
	private final PlayerRec playerRec;
public GEAdjustPlayer(PlayerRec iPlayerRec) {
	super();
	playerRec = iPlayerRec;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().getPlayerList().adjustPlayer(playerRec);
}
public String toString() {
	return super.toString() + " " + playerRec.toString();//$NON-NLS-1$
}
}
