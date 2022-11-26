package szr.msg;

import szr.maincli.*;
import szr.card.*;
/**
 * Kérhetõ jelek eseményosztály
 * Creation date: (2002.02.06. 13:49:29)
 */
public class PEKerhetoJelek extends PersonalEvent {
	private final Jelzes[] kerhetoJelek;
public PEKerhetoJelek(Jelzes[] iKerhetoJelek) {
	super();

	kerhetoJelek = iKerhetoJelek;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().getGameTable().setKerhetoJelek(kerhetoJelek);
}
public String toString() {
	return super.toString() + " " + kerhetoJelek.toString();//$NON-NLS-1$
}
}
