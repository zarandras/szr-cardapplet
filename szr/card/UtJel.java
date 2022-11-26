package szr.card;

import szr.shared.*;
/**
 * Utasítójelzés-osztály
 * Creation date: (2002.03.05. 7:21:43)
 */
public class UtJel extends Jelzes implements UtHalmozo, SingleVetoJel {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	public final static int IxID = 18;
	public final static int HuzoID = 29;
	public final static int KettesHuzoID = 14;
	public final static int NegyesHuzoID = 15;
	public final static int ElhuzoID = 16;
	public final static int KettesElhuzoID = 30;
	public final static int PipaID = 19;
	public final static int KettesPipaID = 31;
	public final static int AtadoID = 32;
	public final static int KettesAtadoID = 33;
	public final static int[] ID_ARR 		= {IxID,
											   HuzoID, KettesHuzoID, NegyesHuzoID,
											   ElhuzoID, KettesElhuzoID,
											   PipaID, KettesPipaID,
											   AtadoID, KettesAtadoID};
	
	private final szr.shared.UtSzam utSzam;
	private final int id;
public UtJel(int iID) {
	id = iID;
	switch (id) {
		case AtadoID:
			utSzam = new UtSzam(0, -1);
			break;
		case ElhuzoID:
			utSzam = new UtSzam(0, 1);
			break;
		case HuzoID:
			utSzam = new UtSzam(1, 0);
			break;
		case IxID:
			utSzam = new UtSzam(0, 0);
			break;
		case PipaID:
			utSzam = new UtSzam(-1, 0);
			break;
		case KettesAtadoID:
			utSzam = new UtSzam(0, -2);
			break;
		case KettesElhuzoID:
			utSzam = new UtSzam(0, 2);
			break;
		case KettesHuzoID:
			utSzam = new UtSzam(2, 0);
			break;
		case KettesPipaID:
			utSzam = new UtSzam(-2, 0);
			break;
		case NegyesHuzoID:
			utSzam = new UtSzam(4, 0);
			break;
		default:
			logInvalidJelzesParam(SubstituteStr.doIt("Invalid UtJel ID: %id", "%id", Integer.toString(id)));//$NON-NLS-2$//$NON-NLS-1$
			utSzam = new UtSzam(0, 0);
			break;
	}
}
public boolean doLPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		jv.addUt(getUt());
	} else {
		logInvalidInvokeCounter(invokeCounter);
	}
	return true;
}
public boolean doPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		jv.addUt(getUt());
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
public UtSzam getUt() {
	return utSzam;
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
public java.lang.String toString() {
	String s;
	switch (id) {
		case AtadoID:
			s = resCardTexts.getString("Átadó"); //$NON-NLS-1$
			break;
		case ElhuzoID:
			s = resCardTexts.getString("Elhúzó"); //$NON-NLS-1$
			break;
		case HuzoID:
			s = resCardTexts.getString("Húzó"); //$NON-NLS-1$
			break;
		case IxID:
			s = resCardTexts.getString("Ix"); //$NON-NLS-1$
			break;
		case PipaID:
			s = resCardTexts.getString("Pipa"); //$NON-NLS-1$
			break;
		case KettesAtadoID:
			s = resCardTexts.getString("KettesÁtadó"); //$NON-NLS-1$
			break;
		case KettesElhuzoID:
			s = resCardTexts.getString("KettesElhúzó"); //$NON-NLS-1$
			break;
		case KettesHuzoID:
			s = resCardTexts.getString("KettesHúzó"); //$NON-NLS-1$
			break;
		case KettesPipaID:
			s = resCardTexts.getString("KettesPipa"); //$NON-NLS-1$
			break;
		case NegyesHuzoID:
			s = resCardTexts.getString("NégyesHúzó"); //$NON-NLS-1$
			break;
		default:
			logInvalidJelzesParam(SubstituteStr.doIt("Invalid UtJel ID: %id", "%id", Integer.toString(id)));//$NON-NLS-2$//$NON-NLS-1$
			s = resCardTexts.getString("?_utJel"); //$NON-NLS-1$
			break;
	}
	return szr.shared.SubstituteStr.doIt(szr.shared.SubstituteStr.doIt(resCardTexts.getString("UtJelToString"), "%jstr", s), "%ut", getUt().toString());//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
}
}
