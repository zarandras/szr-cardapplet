package szr.msg;

import szr.shared.*;
/**
 * Egységes kliens / szerver naplózó osztály
 * Creation date: (2002.04.02. 18:29:31)
 */
public class Logger {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private static szr.maincli.ClientController clientController = null;	// ha kliensben vagyunk, akkor állítandó be
public synchronized static void log(Msg message, String game, String player) {
	String s = new java.util.Date().toString() + ":  <"//$NON-NLS-1$
		+ ((game != null) ? game : "") + ">[" //$NON-NLS-2$//$NON-NLS-1$
		+ ((player != null) ? player : "") + "]:  ";//$NON-NLS-2$//$NON-NLS-1$
	try {
		s += ((message != null) ? message.toString() : "(null_msg)");//$NON-NLS-1$
	} catch (Exception e) {
		if (!(message instanceof ErrExcLog)) // hogy ne legyen rekurzió
			log(new ErrExcLog(e), game, player);
		s += "ERROR in message.toString() !";//$NON-NLS-1$
	}
	System.out.println(s);
	if (message instanceof ErrLog) {
		System.err.println(s);
		if (message instanceof ErrExcLog) {
			((ErrExcLog)message).getMsgException().printStackTrace(System.out);
			((ErrExcLog)message).getMsgException().printStackTrace(System.err);
		}
		if (clientController != null) {
			szr.maincli.ClientController cc = clientController;
			setClientController(null);		// ne lehessen rekurzió
			try {
				cc.rejectedAction(SubstituteStr.doIt(resInfoTexts.getString("Programüzenet__n%err"),"%err", s), szr.maincli.RejectDlgHandler.PSR_ERROR_FATAL);//$NON-NLS-2$ //$NON-NLS-1$
			} catch (Exception e) {}
			setClientController(cc);
		}
	}
}
public synchronized static void setClientController(szr.maincli.ClientController newClientController) {
	clientController = newClientController;
}
}
