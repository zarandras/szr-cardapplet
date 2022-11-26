package szr.msg;

import szr.maincli.*;
import szr.card.*;
/**
 * Kérhetõ színek eseményosztály
 * Creation date: (2002.02.06. 13:49:29)
 */
public class PEKerhetoSzinek extends PersonalEvent {
	private final int kerhetoSzinMask;
public PEKerhetoSzinek(int iKerhetoSzinMask) {
	super();

	kerhetoSzinMask = iKerhetoSzinMask;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().getGameTable().setKerhetoSzinMask(kerhetoSzinMask);
}
public String toString() {
	return super.toString() + " " + Integer.toString(kerhetoSzinMask);//$NON-NLS-1$
}
}
