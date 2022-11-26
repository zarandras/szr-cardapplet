package szr.mainserv;

import szr.msg.*;
/**
 * Virtuális szerver üzenetkezelõ osztály
 * Creation date: (2002.03.20. 9:42:14)
 */
public class VServerMsgHandler extends ServerMsgHandler {
	private szr.maincli.VClientMsgHandler myCMH = null;	// a vele kapcsolatban lévõ kliens üzenetkezelõ
	private final java.util.Vector msgBuffer = new java.util.Vector();
public VServerMsgHandler(GameServer iParentGameServer, szr.maincli.VClientMsgHandler iMyCMH) {
	super(iParentGameServer);

	myCMH = iMyCMH;
}
protected void closeConnection() {
	if (myCMH != null) {
		myCMH = null;
	} else {
		szr.msg.Logger.log(new szr.msg.ErrStrLog("VServerMsgHandler: closeConnection() called but connection already closed."), getGameStr(), getPlayerStr());//$NON-NLS-1$
	}
}
protected java.lang.String getClientAddrStr() {
	return "localhost:(virtual)";//$NON-NLS-1$
}
protected boolean openConnection() {
	// do nothing
	return true;
}
protected szr.msg.Action readNextAction() {
	synchronized (this) {
		Action a;
		while (msgBuffer.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		a = (szr.msg.Action)msgBuffer.firstElement();
		msgBuffer.removeElementAt(0);
		return a;
	}
}
public synchronized void receive(szr.msg.Action action) {
	msgBuffer.addElement(action);
	if (msgBuffer.size() == 1)
		notify();
}
public void send(szr.msg.Event event) {
	if (myCMH != null) {
		myCMH.receive (event);
	} else {
		szr.msg.Logger.log(new szr.msg.InfoLog("VServerMsgHandler: send() called but connection not opened."), getGameStr(), getPlayerStr());//$NON-NLS-1$
	}
}
}
