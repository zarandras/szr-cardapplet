package szr.maincli;

import szr.msg.*;
/**
 * A kliens üzenetkezelõ õsosztálya
 * Creation date: (2002.01.27. 13:15:38)
 */
public abstract class ClientMsgHandler extends Thread {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private ClientController parentController = null;
	private volatile boolean stopped = false;
	private volatile boolean stopFlag = false;	// ha true, akkor leállításkor nem elég a stopped-ed átállítani,
												// hanem meg kell hívni a stop()-ot is
public ClientMsgHandler() {
	super("ClientMsgHandler");//$NON-NLS-1$
}
/**
 * Tényleges kapcsolatbontás (a disconnect hívja)
 */
protected abstract void closeConnection();
/**
 * Kapcsolat létesítése a szerverrel
 */
private boolean connect() {
	synchronized (parentController) {
		parentController.getInfoSystem().setInfoText (resInfoTexts.getString("A_szerverrel_való_kapcsola")); //$NON-NLS-1$
	}
	if (!openConnection()) {
		synchronized (parentController) {
			parentController.getInfoSystem().setInfoText (resInfoTexts.getString("HIBA!_-_Nem_sikerült_kapcs")); //$NON-NLS-1$
		}
		Logger.log(new ErrStrLog("ClientMsgHandler: unable to connect!"), getGameStr(), getPlayerStr());//$NON-NLS-1$
		return false;
	} else {
		Logger.log(new InfoLog("ClientMsgHandler connected."), getGameStr(), getPlayerStr());//$NON-NLS-1$
		return true;
	}
}
/**
 * A szerverrel való kapcsolat lezárása
 */
private void disconnect() {
	if (parentController != null) {
		synchronized (parentController) {
			closeConnection();
			if (parentController.getPlayerList().hasPlayer(parentController.getPlayerName()))
				parentController.getPlayerList().delPlayer(parentController.getPlayerName());
			parentController.setPlayState(ClientController.PS_DISCONNECTED);
		}
		parentController = null;
		Logger.log(new InfoLog("ClientMsgHandler disconnected."), getGameStr(), getPlayerStr());//$NON-NLS-1$
	} else {
		Logger.log(new ErrStrLog("ClientMsgHandler: disconnect() called but parentController is null!"), getGameStr(), getPlayerStr());//$NON-NLS-1$
	}
}
public String getGameStr() {
	try {
		return parentController.getGameName();
	} catch (Exception e) {
		return "";//$NON-NLS-1$
	}
}
public ClientController getParentController() {
	return parentController;
}
public String getPlayerStr() {
	try {
		return parentController.getPlayerName();
	} catch (Exception e) {
		return "";//$NON-NLS-1$
	}
}
/**
 * Leállítás
 */
public synchronized void halt() {
	Logger.log(new InfoLog(szr.shared.SubstituteStr.doIt("ClientMsgHandler stopped (stopFlag: %sf).", "%sf", ((stopFlag) ? "true" : "false"))), getGameStr(), getPlayerStr());//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
	if (!stopped) {
		stopped = true;
		if (stopFlag) {
			stopFlag = false;
			stop();
		}
	}
}
/**
 * Tényleges kapcsolatmegnyitás (a connect hívja)
 */
protected abstract boolean openConnection();
/**
 * A következõ üzenet (esemény) fogadása
 */
protected abstract Event readNextEvent();
public void run() {
  try {
	if (!connect()) {
		return;
	}
	Event e;
	try {
		do {
			setStopFlag(true);	// a halt() metódus ilyenkor stop()-ot hív
			e = readNextEvent();
			setStopFlag(false);  // a halt() metódus ilyenkor stopped = true -t hajt végre
			Logger.log(e, getGameStr(), getPlayerStr());

			if (e != null)
				runDoClient(e);
			else
				synchronized (parentController) {
					getParentController().rejectedAction(resInfoTexts.getString("Baj_van_a_hálózati_kapcsol"), szr.maincli.RejectDlgHandler.PSR_ERROR_FATAL); //$NON-NLS-1$
				}
		} while (!stopped && e != null && !(e instanceof PEBontas));
	} catch (ThreadDeath td) {
		// empty
	} finally {
		disconnect();
	}
  } catch (Throwable exc) {
	  if (!(exc instanceof ThreadDeath)) {
		  szr.msg.Logger.log(new szr.msg.ErrStrLog("ClientMsgHandler: uncaught exception."), null, null);//$NON-NLS-1$
		  Logger.log(new ErrExcLog(exc), null, null);
	  }
  }
}
/**
 * A kapott esemény callback-jének meghívása
 */
private void runDoClient(Event e) {
	try {
		synchronized (parentController) {
			e.doClient (this);
		}
	} catch (Exception ex) {
		Logger.log(new ErrStrLog("ClientMsgHandler: exception occured during event processing:"), getGameStr(), getPlayerStr());//$NON-NLS-1$
		Logger.log(new ErrExcLog(ex), getGameStr(), getPlayerStr());
	}
}
/**
 * Akció elküldése
 */
public abstract void send(Action action);
public void setParentController(ClientController newParentController) {
	parentController = newParentController;
}
protected synchronized void setStopFlag(boolean newStopFlag) {
	stopFlag = newStopFlag;
	if (newStopFlag && stopped)
		halt();
}
}
