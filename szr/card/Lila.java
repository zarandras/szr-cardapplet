package szr.card;

/**
 * Lila színosztály (sorozhatatlan, vétózható)
 * Creation date: (2002.02.09. 21:50:04)
 */
public class Lila extends Szin implements SzinKero {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	private final SzinesSzin kertSzin;
public Lila() {
	super();
	kertSzin = null;
}
protected Lila(SzinesSzin iKertSzin) {
	super();
	kertSzin = iKertSzin;
}
public SzinKero applyKertSzin(SzinesSzin newKertSzin) {
	return new Lila(newKertSzin);
}
public String getImgPath(boolean doublePicFlag) {
	return Card.getImgRootPath () + "l.gif";//$NON-NLS-1$
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
	return Card.getSmallImgRootPath () + "l.gif";//$NON-NLS-1$
}
public String getSzinSugoFileName() {
	return "c_l.html";//$NON-NLS-1$
}
public boolean keresreIlleszk(Szin c) {
	return illeszkedikRa(c);
}
public boolean megegyezik(Szin c) {
	return (c instanceof Lila);
}
public Szin reset() {
	return new Lila();
}
public String toString() {
	return szr.shared.SubstituteStr.doIt(resCardTexts.getString("Lila%kc"), "%kc", (kertSzin == null) ? "" : ("(" + kertSzin.toString() + ")"));//$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
}
public boolean uresreIsIlleszk() {
	return true;
}
}
