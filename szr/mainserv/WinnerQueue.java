package szr.mainserv;

/**
 * A szerver potenciális nyerteseket (akiknek éppen nincs lapjuk) nyilvántartó osztálya
 * Creation date: (2002.03.02. 15:20:54)
 */
public class WinnerQueue {
	private java.util.Vector potentialWinners = null;
	private final GameController parentController;
public WinnerQueue(GameController iParentController) {
	super();
	parentController = iParentController;
}
public void addPlayer(PlayerData pd) {
  try {
	potentialWinners.addElement(pd);
	szr.msg.Logger.log(new szr.msg.InfoLog("WinnerQueue: player added."), parentController.getProperties().name, pd.getProperties().name);//$NON-NLS-1$
  } catch (NullPointerException e) {
	szr.msg.Logger.log(new szr.msg.ErrStrLog("WinnerQueue: not initialized yet, addPlayer() rejected!"), parentController.getProperties().name, null);//$NON-NLS-1$
  }
}
public PlayerData getFirstPlayer() {
	try {
		return (PlayerData)potentialWinners.elementAt(0);
	} catch (ArrayIndexOutOfBoundsException e) {
		return null;
	} catch (NullPointerException e) {
		szr.msg.Logger.log(new szr.msg.InfoLog("WinnerQueue: not initialized yet."), parentController.getProperties().name, null);//$NON-NLS-1$
		return null;
	}
}
public void removePlayer(PlayerData pd) {
  try {
	if (potentialWinners.removeElement(pd))
		szr.msg.Logger.log(new szr.msg.InfoLog("WinnerQueue: player removed."), parentController.getProperties().name, pd.getProperties().name);//$NON-NLS-1$
	else
		szr.msg.Logger.log(new szr.msg.ErrStrLog("WinnerQueue: player to remove is not found."), parentController.getProperties().name, pd.getProperties().name);//$NON-NLS-1$
  } catch (NullPointerException e) {
	szr.msg.Logger.log(new szr.msg.InfoLog("WinnerQueue: not initialized yet."), parentController.getProperties().name, pd.getProperties().name);//$NON-NLS-1$
  }
}
public void reset() {
	if (potentialWinners == null) {
		potentialWinners = new java.util.Vector();
	} else {
		potentialWinners.removeAllElements();
	}
	szr.msg.Logger.log(new szr.msg.InfoLog("WinnerQueue: reset."), parentController.getProperties().name, null);//$NON-NLS-1$
}
}
