package szr.mainserv;

import szr.msg.*;
/**
 * Virtuális SzélrózsaSzerver (a Gyakorlóasztalhoz)
 * Creation date: (2002.03.20. 9:33:21)
 */
public class VGameServer extends GameServer {
public VGameServer(boolean iTestMode) {
	super();
	setTestMode(iTestMode);
}
public boolean checkPassword(String name, String email, String password) {
	szr.msg.Logger.log(new szr.msg.InfoLog("VGameServer: checkPassword() requested, no password check in this version."), null, name);//$NON-NLS-1$
	return true; // accept anything in virtual version
}
public synchronized VServerMsgHandler connect(szr.maincli.VClientMsgHandler myCMH) {
	VServerMsgHandler smh = new VServerMsgHandler (this, myCMH);
	smh.start();
	return smh;
}
public void sendPassword(String name, String email) {
	szr.msg.Logger.log(new szr.msg.InfoLog("VGameServer: sendPassword() requested, no password check in this version."), null, name);//$NON-NLS-1$
	// empty	
}
}
