package szr.maincli;

import szr.msg.*;
/**
 * A kliens �zenetkezel� �soszt�lya
 * Creation date: (2002.01.27. 13:15:38)
 */
public abstract class ClientMsgHandler extends Thread {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private ClientController parentController = null;
	private volatile boolean stopped = false;
	private volatile boolean stopFlag = false;	// ha true, akkor le�ll�t�skor nem el�g a stopped-ed �t�ll�tani,
												// hanem meg kell h�vni a stop()-ot is
public ClientMsgHandler() {
	super("ClientMsgHandler");//$NON-NLS-1$
}
/**
 * T�nyleges kapcsolatbont�s (a disconnect h�vja)
 */
protected abstract void closeConnection();
/**
 * Kapcsolat l�tes�t�se a szerverrel
 */
private boolean connect() {
	synchronized (parentController) {
		parentController.getInfoSystem().setInfoText (resInfoTexts.getString("A_szerverrel_val�_kapcsola")); //$NON-NLS-1$
	}
	if (!openConnection()) {
		synchronized (parentController) {
			parentController.getInfoSystem().setInfoText (resInfoTexts.getString("HIBA!_-_Nem_siker�lt_kapcs")); //$NON-NLS-1$
		}
		Logger.log(new ErrStrLog("ClientMsgHandler: unable to connect!"), getGameStr(), getPlayerStr());//$NON-NLS-1$
		return false;
	} else {
		Logger.log(new InfoLog("ClientMsgHandler connected."), getGameStr(), getPlayerStr());//$NON-NLS-1$
		return true;
	}
}
/**
 * A szerverrel val� kapcsolat lez�r�sa
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
 * Le�ll�t�s
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
 * T�nyleges kapcsolatmegnyit�s (a connect h�vja)
 */
protected abstract boolean openConnection();
/**
 * A k�vetkez� �zenet (esem�ny) fogad�sa
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
			setStopFlag(true);	// a halt() met�dus ilyenkor stop()-ot h�v
			e = readNextEvent();
			setStopFlag(false);  // a halt() met�dus ilyenkor stopped = true -t hajt v�gre
			Logger.log(e, getGameStr(), getPlayerStr());

			if (e != null)
				runDoClient(e);
			else
				synchronized (parentController) {
					getParentController().rejectedAction(resInfoTexts.getString("Baj_van_a_h�l�zati_kapcsol"), szr.maincli.RejectDlgHandler.PSR_ERROR_FATAL); //$NON-NLS-1$
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
 * A kapott esem�ny callback-j�nek megh�v�sa
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
 * Akci� elk�ld�se
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
