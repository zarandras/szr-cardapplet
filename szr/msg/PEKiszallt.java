package szr.msg;

import szr.maincli.*;
/**
 * J�t�kb�l val� kisz�ll�s (visszajelz�se) esem�nyoszt�ly
 * Creation date: (2002.02.06. 13:49:29)
 */
public class PEKiszallt extends PersonalEvent {
public PEKiszallt() {
	super();
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController ().setPlayState (ClientController.PS_NOTJOINED);
}
public String toString() {
	return super.toString();
}
}
