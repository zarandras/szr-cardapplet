package szr.maincli;

/**
 * A kliens v�t�zhat�s�gi blokkol�st vez�rl� oszt�lya
 *  (egy v�t�zhat� lapot lerak� j�t�kos blokkol�dik egy kis id�re, hogy a t�bbiek v�t�zhassanak)
 * Creation date: (2002.03.26. 21:10:25)
 */
public class VetoBlocker extends Thread {
	private final ClientController parentController;
	private final static int TIMEOUT = 5000;	// 5 mp.-re
public VetoBlocker(ClientController iParentController) {
	super("VetoBlocker");//$NON-NLS-1$
	parentController = iParentController;
}
public void run() {
  try {
	try {
		sleep(TIMEOUT);
	} catch (InterruptedException e) {}
	
	synchronized (parentController) {
		if (parentController.getPlayState() == ClientController.PS_ACTIVE_VETOBLOCKED)
			parentController.setPlayState(ClientController.PS_ACTIVE_LILA);
	}
  } catch (Throwable exc) {
	  if (!(exc instanceof ThreadDeath)) {
		  szr.msg.Logger.log(new szr.msg.ErrStrLog("VetoBlocker: uncaught exception."), null, null);//$NON-NLS-1$
		  szr.msg.Logger.log(new szr.msg.ErrExcLog(exc), null, null);
	  }
  }
}
}
