package szr.msg;

import szr.mainserv.*;
import szr.shared.*;
/**
 * Kötelezõ húzás mennyisége eseményosztály
 * Creation date: (2002.03.02. 17:07:16)
 */
public class GEUtRec extends GameEvent {
	private final UtSzam ut;
public GEUtRec(UtSzam iUt) {
	super();
	ut = iUt;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().getInfoSystem().setUtSzam(ut);
}
public String toString() {
	return super.toString() + " " + ((ut == null) ? "(null)" : ut.toString());//$NON-NLS-2$//$NON-NLS-1$
}
}
