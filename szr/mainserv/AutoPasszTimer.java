package szr.mainserv;

import szr.shared.*;
/**
 * Az AutoPassz-t megval�s�t� oszt�ly (automatikus passzol�s az id� letelte ut�n)
 * Creation date: (2002.03.27. 10:46:00)
 */
public class AutoPasszTimer extends Thread {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final GameController parentController;
	private volatile boolean cancelled = false;	// AutoPassz id�z�t�s le�ll�tva
	public final static int TIMEOUT1 = 90;	// ennyi id� ut�n k�ld figyelmeztet� �zenetet
	public final static int TIMEOUT2 = 10;	// a figyelmezet� �zenet ut�n m�g ennyi id� van
	private final int myGANumber;	// milyen sorsz�m� GameAction-t kell gener�lni AutoPassz-k�nt?
	private final PlayerData myPlayerData;	// ki fog AutoPasszolni?
public AutoPasszTimer(GameController iParentController, PlayerData iMyPlayerData, int iMyGANumber) {
	super(SubstituteStr.doIt("AutoPasszTimer, GA: %i", "%i", Integer.toString(iMyGANumber)));//$NON-NLS-2$//$NON-NLS-1$
	parentController = iParentController;
	myPlayerData = iMyPlayerData;
	myGANumber = iMyGANumber;
}
/**
 * Az AutoPassz id�z�t�s le�ll�t�sa
 */
public synchronized void cancel() {
	cancelled = true;
	notify();
}
private synchronized boolean isCancelled() {
	return cancelled;
}
public void run() {
  try {
	if (timedOut(TIMEOUT1)) {
		// els� f�zis: figyelmeztet�s
		synchronized(parentController) {
			if (!isCancelled() && (parentController.expectsGA(myGANumber))) {
				myPlayerData.getMsgHandler().send(new szr.msg.PEReject(SubstituteStr.doIt(resInfoTexts.getString("Kb._%sec_m�sodperc_m�lva_l"), "%sec", Integer.toString(TIMEOUT2)), szr.maincli.RejectDlgHandler.PSR_AUTOPASSZ));//$NON-NLS-2$ //$NON-NLS-1$
			}
		}
		if (!isCancelled() && (timedOut(TIMEOUT2))) {
			// m�sodik f�zis: AutoPassz
			synchronized(parentController) {
				if (!isCancelled() && (parentController.expectsGA(myGANumber))) {
					myPlayerData.getMsgHandler().send(new szr.msg.PEReject(resInfoTexts.getString("Letelt_az_id�._Passzolt�l."), szr.maincli.RejectDlgHandler.PSR_AUTOPASSZ)); //$NON-NLS-1$
					parentController.autoPassz(myPlayerData);
				}
			}
		}
	}
  } catch (Throwable exc) {
	  if (!(exc instanceof ThreadDeath)) {
		  szr.msg.Logger.log(new szr.msg.ErrStrLog("AutoPasszTimer: uncaught exception."), null, null);//$NON-NLS-1$
		  szr.msg.Logger.log(new szr.msg.ErrExcLog(exc), null, null);
	  }
  }
}
/**
 * Id�m�r�s
 */
private synchronized boolean timedOut(int secs) {
	try {
		wait(secs * 1000);
	} catch (InterruptedException e) {}
	return (!isCancelled());
}
}
