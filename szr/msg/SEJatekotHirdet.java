package szr.msg;

import szr.shared.*;
/**
 * Új játék meghirdetése eseményosztály
 * Creation date: (2002.02.05. 18:28:10)
 */
public class SEJatekotHirdet extends ServerEvent {
	private final GameRec jatekRec;
public SEJatekotHirdet(GameRec iJatekRec) {
	super();
	jatekRec = iJatekRec;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController ().getGameControlPanel ().ujJatek(jatekRec, true);
}
public String toString() {
	return super.toString() + " " + jatekRec.toString ();//$NON-NLS-1$
}
}
