package szr.mainserv;

import szr.msg.*;
import szr.shared.*;
import java.util.*;
/**
 * A SzélrózsaSzerver õsosztály
 * Creation date: (2002.01.27. 13:17:12)
 */
public abstract class GameServer {
	private final Vector games;
	private final Vector msgHandlers;
	private boolean testMode = false;	// tesztpaklik is vannak-e?
public GameServer() {
	super();

	games = new Vector();
	msgHandlers = new Vector ();
}
public synchronized void addMsgHandler(ServerMsgHandler smh) {
	msgHandlers.addElement (smh);
	szr.msg.Logger.log(new szr.msg.InfoLog(SubstituteStr.doIt("GameServer: Client connected.  Nr. of clients is %n.", "%n", Integer.toString(msgHandlers.size()))), smh.getGameStr(), smh.getPlayerStr());//$NON-NLS-1$
}
public boolean addNewGame(GameRec gameRec, PlayerRec osztoRec, ServerMsgHandler osztoMsgHandler) {
	if (findGame(gameRec.name) < games.size ()) {
		szr.msg.Logger.log(new szr.msg.InfoLog("GameServer: duplicate game name, addNewGame() rejected."), gameRec.name, osztoRec.name);//$NON-NLS-1$
		return false;
	} else if (!szr.pakli.Pakli.isIndexValid(gameRec.pakliIdx)) {
		szr.msg.Logger.log(new szr.msg.InfoLog("GameServer: invalid pakliIdx, addNewGame() rejected."), gameRec.name, osztoRec.name);//$NON-NLS-1$
		return false;
	} else {
		GameController gc = new GameController (gameRec, this);
		games.addElement (gc);
		sendToAll (new SEJatekotHirdet (gc.getProperties ()));
		gc.addPlayer(osztoRec, osztoMsgHandler);
		return true;
	}
}
public abstract boolean checkPassword(String name, String email, String password);
public boolean delGame(GameController gc) {
	try {
		if (!games.removeElement (gc)) {
			szr.msg.Logger.log(new szr.msg.InfoLog("GameServer: game not found, delGame() rejected."), gc.getProperties().name, null);//$NON-NLS-1$
			return false;
		} else {
			sendToAll (new SEJatekTorolve (gc.getProperties ().name));
			return true;
		}
	} catch (NullPointerException e) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(e), null, null);
		return false;
	}
}
public void disconnectAll() {
	synchronized(this) {
		szr.msg.Logger.log(new szr.msg.InfoLog("GameServer: disconnecting all clients."), null, null);//$NON-NLS-1$
		while (!msgHandlers.isEmpty()) {
			((ServerMsgHandler)msgHandlers.elementAt(0)).stop();	// also removes from msgHandlers
		}
	}
}
/**
 * Code to perform when this object is garbage collected.
 * 
 * Any exception thrown by a finalize method causes the finalization to
 * halt. But otherwise, it is ignored.
 */
protected void finalize() throws Throwable {
	try {
		disconnectAll();
	} catch (Exception e) {}
	super.finalize();
}
private int findGame(String gameName) {
	int i = 0;
	while (i < games.size ()) {
		if (((GameController)games.elementAt(i)).getName().equals(gameName))
			break;
		i++;
	}
	return i;
}
public GameController getGame(String gameName) {
	try {
		int i = findGame(gameName);
		return (GameController)games.elementAt(i);
	} catch (ArrayIndexOutOfBoundsException e) {
		return null;
	}
}
public java.util.Vector getGameRecs() {
	Vector gameRecs = new Vector (games.size ());
	Enumeration e = games.elements ();
	while (e.hasMoreElements())
		gameRecs.addElement(((GameController)e.nextElement()).getProperties());
	return gameRecs;
}
private java.util.Vector getMsgHandlers() {
	return msgHandlers;
}
public boolean isTestMode() {
	return testMode;
}
public synchronized void removeMsgHandler(ServerMsgHandler smh) {
	if (msgHandlers.removeElement(smh))
		szr.msg.Logger.log(new szr.msg.InfoLog(SubstituteStr.doIt("GameServer: Client disconnected.  Nr. of clients is %n.", "%n", Integer.toString(msgHandlers.size()))), smh.getGameStr(), smh.getPlayerStr());//$NON-NLS-1$
	else
		szr.msg.Logger.log(new szr.msg.ErrStrLog(SubstituteStr.doIt("GameServer error: removeMsgHandler(): Client not found.   Nr. of clients is %n.", "%n", Integer.toString(msgHandlers.size()))), smh.getGameStr(), smh.getPlayerStr());//$NON-NLS-1$
}
public abstract void sendPassword(String name, String email);
/**
 * Üzenet küldése az összes kliensnek
 */
public void sendToAll(szr.msg.ServerEvent event) {
  try {
	Logger.log(new InfoLog(SubstituteStr.doIt("GameServer: SendToAll: %evstr", "%evstr", event.toString())), null, null);//$NON-NLS-2$//$NON-NLS-1$
	Enumeration e = msgHandlers.elements ();
	while (e.hasMoreElements ())
		((ServerMsgHandler)e.nextElement ()).send (event);
  } catch (Exception e) {
	Logger.log(new ErrExcLog(e), null, null);
  }
}
public void setTestMode(boolean newTestMode) {
	testMode = newTestMode;
}
}
