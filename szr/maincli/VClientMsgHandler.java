package szr.maincli;

import szr.mainserv.*;
import java.util.Vector;
import szr.msg.*;
/**
 * Virtuális kliens (Gyakorlóasztal) üzenetkezelõ osztálya
 * Creation date: (2002.03.20. 9:52:32)
 */
public class VClientMsgHandler extends ClientMsgHandler {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private szr.mainserv.VGameServer myServer = null;
	private volatile szr.mainserv.VServerMsgHandler mySMH = null;	// a virtuális szerver oldali üzenetkezelõ
	private final Vector msgBuffer = new java.util.Vector();
public VClientMsgHandler(szr.mainserv.VGameServer iMyServer) {
	super();
	myServer = iMyServer;
}
protected void closeConnection() {
	if (mySMH != null) {
		mySMH = null;
	} else {
		szr.msg.Logger.log(new szr.msg.ErrStrLog("VClientMsgHandler: closeConnection() called but connection already closed."), getGameStr(), getPlayerStr());//$NON-NLS-1$
	}
}
public szr.mainserv.GameServer getMyServer() {
	return myServer;
}
public boolean openConnection() {
	// wait for MyServer
	if (myServer == null) {
		synchronized (getParentController()) {
			getParentController().getInfoSystem().setInfoText(resInfoTexts.getString("Az_'ÚJ_ABLAK'_gomb_elsõ_me")); //$NON-NLS-1$
		}
		setStopFlag(true);
		synchronized(this) {
			while (myServer == null)
				try {
					wait();
				} catch (InterruptedException e) {}
		}
		setStopFlag(false);
	}

	// connect to myServer
	try {
		mySMH = myServer.connect (this);
		if (mySMH != null) {
			return true;
		} else {
			szr.msg.Logger.log(new szr.msg.ErrStrLog("VClientMsgHandler: unable to connect to server."), getGameStr(), getPlayerStr());//$NON-NLS-1$
			return false;
		}
	} catch (NullPointerException e) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(e), getGameStr(), getPlayerStr());
		return false;
	}
}
public Event readNextEvent() {
	synchronized (this) {
		while (msgBuffer.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		Event e = (szr.msg.Event)msgBuffer.firstElement();
		msgBuffer.removeElementAt(0);
		return e;
	}
}
/**
 * Üzenet odaadása (a VServerMsgHandler hívja)
 */
public synchronized void receive(szr.msg.Event event) {
	msgBuffer.addElement(event);
	if (msgBuffer.size() == 1)
		notify();
}
public void send(szr.msg.Action action) {
	if (mySMH != null) {
		mySMH.receive (action);
	} else {
		szr.msg.Logger.log(new szr.msg.InfoLog("VClientMsgHandler: send() called but connection not opened."), getGameStr(), getPlayerStr());//$NON-NLS-1$
	}
}
public synchronized void setMyServer(szr.mainserv.VGameServer newMyServer) {
	myServer = newMyServer;
	notify();
}
}
