package szr.card;

/**
 * Tarka színosztály
 * Creation date: (2002.03.12. 7:24:34)
 */
public class HalmozoSzinKeroSzin extends Szin implements SzinKero, UtHalmozo {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	private final SzinesSzin kertSzin;
public HalmozoSzinKeroSzin() {
	super();
	kertSzin = null;
}
protected HalmozoSzinKeroSzin(SzinesSzin iKertSzin) {
	super();
	kertSzin = iKertSzin;
}
public SzinKero applyKertSzin(SzinesSzin newKertSzin) {
	return new HalmozoSzinKeroSzin(newKertSzin);
}
public String getImgPath(boolean doublePicFlag) {
	return Card.getImgRootPath () + "t.gif";//$NON-NLS-1$
}
public SzinesSzin getKertSzin() {
	return kertSzin;
}
public int getMask() {
	if (kertSzin == null) {
		// ha még nem kértek, akkor minden kérhetõ illeszkedik (mert lehetne kérni)
		return Szin.FullMask;
	} else {
		return kertSzin.getMask();
	}
}
public String getSmallImgPath() {
	return Card.getSmallImgRootPath () + "t.gif";//$NON-NLS-1$
}
public String getSzinSugoFileName() {
	return "c_t.html";//$NON-NLS-1$
}
public boolean keresreIlleszk(Szin c) {
	return illeszkedikRa(c);
}
public boolean megegyezik(Szin c) {
	return (c instanceof HalmozoSzinKeroSzin);
}
public Szin reset() {
	return new HalmozoSzinKeroSzin();
}
public String toString() {
	return szr.shared.SubstituteStr.doIt(resCardTexts.getString("Tarka%kc"), "%kc", (kertSzin == null) ? "" : ("(" + kertSzin.toString() + ")"));//$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
}
public boolean uresreIsIlleszk() {
	return true;
}
}
