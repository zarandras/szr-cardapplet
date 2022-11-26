package szr.msg;

import szr.mainserv.*;
/**
 * Hálózati kapcsolat bontása akcióosztály
 * Creation date: (2002.02.06. 17:06:17)
 */
public class SCABontas extends ServerControlAction {
public SCABontas() {
	super();
}
public void doServer(szr.mainserv.ServerMsgHandler smh) {
//	smh.disconnect();	<- a ServerMsgHandler hívja, így ez a metódus üres.
}
public String toString() {
	return super.toString();
}
}
