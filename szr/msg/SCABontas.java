package szr.msg;

import szr.mainserv.*;
/**
 * H�l�zati kapcsolat bont�sa akci�oszt�ly
 * Creation date: (2002.02.06. 17:06:17)
 */
public class SCABontas extends ServerControlAction {
public SCABontas() {
	super();
}
public void doServer(szr.mainserv.ServerMsgHandler smh) {
//	smh.disconnect();	<- a ServerMsgHandler h�vja, �gy ez a met�dus �res.
}
public String toString() {
	return super.toString();
}
}
