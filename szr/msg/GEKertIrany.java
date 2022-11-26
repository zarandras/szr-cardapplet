package szr.msg;

import szr.shared.*;
/**
 * Iránykérés eseményosztály
 * Creation date: (2002.03.02. 17:07:16)
 */
public class GEKertIrany extends GameEvent {
	private final Irany kertIrany;
public GEKertIrany(Irany iKertIrany) {
	super();
	kertIrany = iKertIrany;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().getGameTable().setKertIrany(kertIrany);
}
public String toString() {
	return super.toString() + " " + ((kertIrany == null) ? "(none)" : kertIrany.toString());//$NON-NLS-2$//$NON-NLS-1$
}
}
