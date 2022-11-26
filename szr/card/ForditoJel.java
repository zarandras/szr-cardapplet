package szr.card;

/**
 * Fordító jelzésosztály
 * Creation date: (2002.02.04. 10:55:43)
 */
public class ForditoJel extends Jelzes implements NemSorozhatoJel, UtHalmozo, SingleVetoJel {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	private final int style;
public ForditoJel(int iStyle) {
	super();
	style = iStyle;
}
public boolean doLPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return doPreEvent(jv, invokeCounter);
}
public boolean doPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		jv.reverse();
	} else {
		logInvalidInvokeCounter(invokeCounter);
	}
	return true;
}
public boolean doStartEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return doPreEvent(jv, invokeCounter);
}
public boolean getDoublePicFlag() {
	return true;
}
public int getID() {
	switch (style) {
		case TuristaJel.STYLE_ID:
		case RO_TuristaJel.STYLE_ID:
			return 9;
			
		case SK_TuristaJel.STYLE_ID:
			return 44;

		default:
			logInvalidJelzesParam(szr.shared.SubstituteStr.doIt("Invalid style ID: %id", "%id", Integer.toString(style)));//$NON-NLS-2$//$NON-NLS-1$
			return 9;
		
	}
}
public boolean hasAImg() {
	return true;
}
public boolean hasKetszinuImg() {
	return true;
}
public boolean hasLImg() {
	return true;
}
/**
 * A különbözõ fordítójelek azonosaknak számítanak a játék szempontjából:
 */
public boolean megegyezik(Jelzes j) {
	return j instanceof ForditoJel;
}
public String toString() {
	return resCardTexts.getString("Fordító"); //$NON-NLS-1$
}
}
