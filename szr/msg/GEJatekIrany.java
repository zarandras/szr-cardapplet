package szr.msg;

import szr.shared.*;
/**
 * Játékirány-átállítás eseményosztály
 * Creation date: (2002.03.02. 17:07:16)
 */
public class GEJatekIrany extends GameEvent {
	private final Irany irany;
/**
 * iIrany ne legyen null!
 */
public GEJatekIrany(Irany iIrany) {
	super();
	irany = iIrany;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().getPlayerList().setIrany(irany);
}
public String toString() {
	return super.toString() + " " + irany.toString();//$NON-NLS-1$
}
}
