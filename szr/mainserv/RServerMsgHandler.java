package szr.mainserv;

import java.io.*;
import java.net.*;
import szr.msg.*;
/**
 * Valós szerver üzenetkezelõ osztály
 * Creation date: (2002.03.25. 18:18:25)
 */
public class RServerMsgHandler extends ServerMsgHandler {
	private ObjectOutputStream oStream = null;
	private ObjectInputStream iStream = null;
	private Socket mySock;
public RServerMsgHandler(GameServer iParentGameServer, Socket iMySock) {
	super(iParentGameServer);
	mySock = iMySock;
}
protected void closeConnection() {
  if (mySock != null && iStream != null && oStream != null) {
	try {
		iStream.close();
		oStream.close();
		mySock.close();
	} catch (IOException e) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(e), getGameStr(), getPlayerStr());
	} finally {
		mySock = null;
		iStream = null;
		oStream = null;
	}
  } else {
	szr.msg.Logger.log(new szr.msg.ErrStrLog("RServerMsgHandler: closeConnection() called but connection already closed."), getGameStr(), getPlayerStr());//$NON-NLS-1$
  }
}
protected java.lang.String getClientAddrStr() {
	try {
		return mySock.getInetAddress().toString() + ":" + Integer.toString(mySock.getPort());//$NON-NLS-1$
	} catch (NullPointerException e) {
		return "no_client";//$NON-NLS-1$
	}
}
protected boolean openConnection() {
  if (mySock != null) {
   if (iStream == null && oStream == null) {	// mySock should be initialized by creator
	try {
		oStream = new ObjectOutputStream(mySock.getOutputStream());
		iStream = new ObjectInputStream(mySock.getInputStream());
		return true;
	} catch (IOException e) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(e), getGameStr(), getPlayerStr());
		return false;
	}
   } else {
	szr.msg.Logger.log(new szr.msg.ErrStrLog("RServerMsgHandler: openConnection() called but connection already opened."), getGameStr(), getPlayerStr());//$NON-NLS-1$
	return false;
   }
 } else {
	szr.msg.Logger.log(new szr.msg.ErrStrLog("RServerMsgHandler: openConnection() called but socket is null."), getGameStr(), getPlayerStr());//$NON-NLS-1$
	return false;
 }
}
protected szr.msg.Action readNextAction() {
	if (mySock != null && iStream != null) {
		try {
			Action a = (Action)iStream.readObject();
			return a;
		} catch (Exception ex) {
			szr.msg.Logger.log(new szr.msg.ErrExcLog(ex), getGameStr(), getPlayerStr());
			return null;
		}
	} else {
		szr.msg.Logger.log(new szr.msg.ErrStrLog("RServerMsgHandler: readNextEvent() called but connection not opened."), getGameStr(), getPlayerStr());//$NON-NLS-1$
		return null;
	}
}
/**
 * Kliens visszautasítása
 */
public void reject(String rejectMsg) {
	if (!openConnection()) {
		return;
	}
	sendWelcomeMsgs();
	send(new PEReject(rejectMsg));
	closeConnection();
	szr.msg.Logger.log(new szr.msg.ErrStrLog("RServerMsgHandler: client rejected."), getGameStr(), getPlayerStr());//$NON-NLS-1$
}
public void send(szr.msg.Event event) {
	if (mySock != null && oStream != null) {
		try {
			oStream.writeObject(event);
			oStream.flush();
		} catch (Exception ex) {
			szr.msg.Logger.log(new szr.msg.ErrExcLog(ex), getGameStr(), getPlayerStr());
		}
	} else {
		szr.msg.Logger.log(new szr.msg.InfoLog("RServerMsgHandler: send() called but connection not opened."), getGameStr(), getPlayerStr());//$NON-NLS-1$
	}
}
}
