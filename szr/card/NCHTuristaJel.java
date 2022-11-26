package szr.card;

/**
 * Náuèný chodník (tanösvény) turistajelzés-osztály - automatikus zöld színkérés
 * Creation date: (2002.03.24. 12:19:36)
 */
public class NCHTuristaJel extends SK_TuristaJel implements SzinKero {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	public final static int ID_NCH 	= 39;
	private static final SzinesSzin FORCED_SZIN = new SzinesSzin(SzinesSzin.Zmask);	// csak zöld lehet
public NCHTuristaJel() {
	super(ID_NCH);
}
public SzinKero applyKertSzin(SzinesSzin kertSzin) {
	return new NCHTuristaJel();		// úgyis csak zöldet lehet kérni
}
public boolean doLPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return doPreEvent(jv, invokeCounter);
}
public boolean doPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		jv.setKertSzin(FORCED_SZIN);
	} else {
		logInvalidInvokeCounter(invokeCounter);
	}
	return true;
}
public boolean getDoublePicFlag() {
	return false;
}
public SzinesSzin getKertSzin() {
	return FORCED_SZIN;
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
public boolean keresreIlleszk(Szin c) {
	return FORCED_SZIN.illeszkedikRa(c);
}
public String toString() {
	return resCardTexts.getString("Tanösvény / NCH"); //$NON-NLS-1$
}
}
