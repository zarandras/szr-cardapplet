package szr.mainserv;

import szr.msg.*;
/**
 * A szerver �zenetkezel� �soszt�lya
 * Creation date: (2002.01.27. 13:16:36)
 */
public abstract class ServerMsgHandler extends Thread {
	private GameServer parentGameServer = null;
	private PlayerData playerData = null;	// j�t�kon k�v�l null
	private final boolean testMode;	// vannak-e tesztpaklik?
public ServerMsgHandler(GameServer iParentGameServer) {
	super("ServerMsgHandler");//$NON-NLS-1$
	parentGameServer = iParentGameServer;
	testMode = parentGameServer.isTestMode();
}
public void clearPlayerData() {
	setPlayerData(null);
}
/**
 * T�nyleges kapcsolatbont�s (a disconnect h�vja)
 */
protected abstract void closeConnection();
/**
 * Kapcsolat l�tes�t�se a klienssel
 */
private boolean connect() {
	if (!openConnection()) {
		Logger.log(new ErrStrLog("ServerMsgHandler: unable to connect!"), getGameStr(), getPlayerStr());//$NON-NLS-1$
		return false;
	} else {
		Logger.log(new InfoLog("ServerMsgHandler connected."), getGameStr(), getPlayerStr());//$NON-NLS-1$
		return true;
	}
}
/**
 * A klienssel val� kapcsolat bont�sa
 */
private void disconnect() {
	if (parentGameServer != null) {
		closeConnection();
		if (playerData != null) {
			playerData.getParentController().delPlayer(playerData, true);
			playerData.removeParentController();
			clearPlayerData();
		}
		parentGameServer = null;
		Logger.log(new InfoLog("ServerMsgHandler disconnected."), getGameStr(), getPlayerStr());//$NON-NLS-1$
	} else {
		Logger.log(new ErrStrLog("ServerMsgHandler: disconnect() called but parentGameServer is null!"), getGameStr(), getPlayerStr());//$NON-NLS-1$
	}
}
/**
 * A kliens c�me
 */
protected abstract String getClientAddrStr();
public String getGameStr() {
	try {
		if (playerData != null)
			return playerData.getParentController().getProperties().name;
		else
			return "";//$NON-NLS-1$
	} catch (Exception e) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(e), null, null);
		return "";//$NON-NLS-1$
	}
}
public GameServer getParentGameServer() {
	return parentGameServer;
}
/**
 * A hozz� tartoz� j�t�kos PlayerDat�ja (j�t�kon k�v�l null)
 */
public PlayerData getPlayerData() {
	return playerData;
}
public String getPlayerStr() {
	try {
		if (playerData != null)
			return playerData.getProperties().name;
		else
			return "{" + getClientAddrStr() + "}";//$NON-NLS-2$//$NON-NLS-1$
	} catch (Exception e) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(e), null, null);
		return "";//$NON-NLS-1$
	}
}
/**
 * T�nyleges kapcsolatmegnyit�s (a connect h�vja)
 */
protected abstract boolean openConnection();
/**
 * A k�vetkez� �zenet (akci�) fogad�sa
 */
protected abstract Action readNextAction();
public void run() {
  try {
	if (!connect()) {
		return;
	}
	parentGameServer.addMsgHandler(this);
	sendWelcomeMsgs();
	Action a;
	try {
		do {
			a = readNextAction();
			Logger.log(a, getGameStr(), getPlayerStr());
			
			if (a != null)
				runDoServer(a);
			else
				Logger.log (new InfoLog("ServerMsgHandler: (null) action received, disconnecting."), getGameStr(), getPlayerStr());//$NON-NLS-1$
		} while (a != null && !(a instanceof SCABontas));
	} catch (ThreadDeath td) {
		send(new PEBontas());
	} finally {
		parentGameServer.removeMsgHandler(this);
		disconnect();
	}
  } catch (Throwable exc) {
	  if (!(exc instanceof ThreadDeath)) {
		  szr.msg.Logger.log(new szr.msg.ErrStrLog("ServerMsgHandler: uncaught exception."), null, null);//$NON-NLS-1$
		  szr.msg.Logger.log(new szr.msg.ErrExcLog(exc), null, null);
	  }
  }
}
/**
 * A kapott akci� callback-j�nek megh�v�sa
 */
private void runDoServer(Action a) {
	try {
		if (a instanceof ServerControlAction) {
			synchronized (parentGameServer) {
				if (playerData != null) {
					synchronized (playerData.getParentController()) {
						a.doServer (this);
					}
				} else {
					a.doServer (this);
				}
			}
		} else if (playerData != null) {
			synchronized (playerData.getParentController()) {
				if (!(playerData.getParentController().getPlayerCount() <= 1 && a instanceof GeneralGameAction)) {
					a.doServer (this);
				} else {
					Logger.log(new InfoLog("ServerMsgHandler: GGA/MGA received but game has only one player."), getGameStr(), getPlayerStr());		//$NON-NLS-1$
				}
			}
		} else {
			Logger.log(new ErrStrLog("ServerMsgHandler: GGA/MGA received but playerData is null!"), getGameStr(), getPlayerStr());		//$NON-NLS-1$
		}
	} catch (Exception ex) {
		Logger.log(new ErrStrLog("ServerMsgHandler: exception occured during action processing:"), getGameStr(), getPlayerStr());//$NON-NLS-1$
		Logger.log(new ErrExcLog(ex), getGameStr(), getPlayerStr());
	}
}
/**
 * Esem�ny elk�ld�se
 */
public abstract void send(Event event);
/**
 * �dv�zl� �zenetek elk�ld�se
 */
protected void sendWelcomeMsgs() {
	send (new PEWelcome(szr.pakli.Pakli.getPakliRecs(testMode), GameController.OSZTOTTLAPOK_MIN, GameController.OSZTOTTLAPOK_MAX));
	synchronized (parentGameServer) {
		send (new PEJatekLista(parentGameServer.getGameRecs()));
	}
}
public void setPlayerData(PlayerData newPlayerData) {
	playerData = newPlayerData;
}
}
