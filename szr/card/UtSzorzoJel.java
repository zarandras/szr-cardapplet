package szr.card;

import szr.shared.*;
/**
 * Utasításszorzó jelzésosztály
 * Creation date: (2002.03.10. 23:56:20)
 */
public class UtSzorzoJel extends Jelzes implements UtHalmozo, SingleVetoJel {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	public final static int DuplazoID = 35;
	public final static int PipaloID = 36;
	public final static int FelPipaloID = 37;
	public final static int[] ID_ARR 		= {DuplazoID, PipaloID, FelPipaloID};

	private final UtSzam szorzo;
	private final int id;
public UtSzorzoJel(int iID) {
	id = iID;
	switch (id) {
		case DuplazoID:
			szorzo = new UtSzam(2, 0);
			break;
		case PipaloID:
			szorzo = new UtSzam(-1, 0);
			break;
		case FelPipaloID:
			szorzo = new UtSzam(0, 1);
			break;
		default:
			logInvalidJelzesParam(SubstituteStr.doIt("Invalid UtSzorzoJel ID: %id", "%id", Integer.toString(id)));//$NON-NLS-2$//$NON-NLS-1$
			szorzo = new UtSzam(1, 0);
			break;
	}
}
public boolean doLPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return doPreEvent(jv, invokeCounter);
}
public boolean doPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		jv.mulUt(getUtSzorzo());
	} else {
		logInvalidInvokeCounter(invokeCounter);
	}
	return true;
}
public boolean getDoublePicFlag() {
	return false;
}
public int getID() {
	return id;
}
public final UtSzam getUtSzorzo() {
	return szorzo;
}
public boolean hasAImg() {
	return true;
}
public boolean hasKetszinuImg() {
	return false;
}
public boolean hasLImg() {
	return true;
}
public String toString() {
	String s;
	switch (id) {
		case DuplazoID:
			s = resCardTexts.getString("Duplázó"); //$NON-NLS-1$
			break;
		case PipaloID:
			s = resCardTexts.getString("Pipáló"); //$NON-NLS-1$
			break;
		case FelPipaloID:
			s = resCardTexts.getString("FélPipáló"); //$NON-NLS-1$
			break;
		default:
			logInvalidJelzesParam(SubstituteStr.doIt("Invalid UtSzorzoJel ID: %id", "%id", Integer.toString(id)));//$NON-NLS-2$//$NON-NLS-1$
			s = resCardTexts.getString("?_UtSzorzoJel"); //$NON-NLS-1$
			break;
	}
	return szr.shared.SubstituteStr.doIt(szr.shared.SubstituteStr.doIt(resCardTexts.getString("UtSzorzoJelToString"), "%jstr", s), "%utsz", szorzo.toString());//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
}
}
