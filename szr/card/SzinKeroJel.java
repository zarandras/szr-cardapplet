package szr.card;

/**
 * Színkérõ jelzésosztály
 * Creation date: (2002.03.05. 7:42:08)
 */
public class SzinKeroJel extends Jelzes implements SzinKero {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	private final SzinesSzin kertSzin;
	private final int style;
public SzinKeroJel(int iStyle) {
	super();
	style = iStyle;
	kertSzin = null;
}
protected SzinKeroJel(int iStyle, SzinesSzin iKertSzin) {
	super();
	style = iStyle;
	kertSzin = iKertSzin;
}
public SzinKero applyKertSzin(SzinesSzin newKertSzin) {
	return new SzinKeroJel(style, newKertSzin);
}
public boolean doLPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		jv.forceSzinKeres();
		return false;
	} else
		if (invokeCounter > 1)
			logInvalidInvokeCounter(invokeCounter);
		return true;
}
public boolean getDoublePicFlag() {
	return false;
}
public int getID() {
	switch (style) {
		case TuristaJel.STYLE_ID:
		case SK_TuristaJel.STYLE_ID:
			return 13;
			
		case RO_TuristaJel.STYLE_ID:
			return 51;

		default:
			logInvalidJelzesParam(szr.shared.SubstituteStr.doIt("Invalid style ID: %id", "%id", Integer.toString(style)));//$NON-NLS-2$//$NON-NLS-1$
			return 13;
		
	}
}
public SzinesSzin getKertSzin() {
	return kertSzin;
}
public boolean hasAImg() {
	return false;
}
public boolean hasKetszinuImg() {
	return false;
}
public boolean hasLImg() {
	return false;
}
public boolean keresreIlleszk(Szin resolvedSzin) {
	if (kertSzin == null) {
		// ha még nem kértek, akkor minden kérhetõ illeszkedik (mert lehetne kérni)
		// (ennek láncsorozáskor van jelentõsége)
		return (resolvedSzin.getMask() != 0);
	} else {
		return kertSzin.illeszkedikRa(resolvedSzin);
	}
}
public Jelzes reset() {
	return new SzinKeroJel(style);
}
public String toString() {
	return szr.shared.SubstituteStr.doIt(resCardTexts.getString("Színkérõ%kc"), "%kc", (kertSzin == null) ? "" : ("(" + kertSzin.toString() + ")"));//$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
}
}
