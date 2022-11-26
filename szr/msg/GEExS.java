package szr.msg;

import szr.mainserv.*;
import szr.shared.*;
/**
 * Extra sorozási lehetõség eseményosztály
 * Creation date: (2002.03.02. 17:07:16)
 */
public class GEExS extends GameEvent {
	private final ExSType exS;
public GEExS(ExSType iExS) {
	super();
	exS = iExS;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().getInfoSystem().setExS(exS);
}
public String toString() {
	return super.toString() + " " + ((exS == null) ? "(null)" : exS.toString());//$NON-NLS-2$//$NON-NLS-1$
}
}
