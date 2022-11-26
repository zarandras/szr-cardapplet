package szr.msg;

import szr.maincli.*;
/**
 * Játékhoz csatlakozás (visszajelzése) eseményosztály
 * Creation date: (2002.02.06. 13:49:29)
 */
public class PECsatlakozott extends PersonalEvent {
	private final boolean firstPlayer;
public PECsatlakozott(boolean iFirstPlayer) {
	super();
	firstPlayer = iFirstPlayer;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	ClientController controller = cmh.getParentController ();
	controller.setPlayState ((firstPlayer) ? ClientController.PS_JOINED_FIRST : ClientController.PS_JOINED);
	controller.getChatter().printJoinMessage(firstPlayer);
}
public String toString() {
	return super.toString() + ((firstPlayer) ? " (firstPlayer)" : "");//$NON-NLS-2$//$NON-NLS-1$
}
}
