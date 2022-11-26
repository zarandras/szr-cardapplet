package szr.shared;

import java.io.Serializable;
/**
 * Az utasítási (kötelezõ húzási) mennyiséget leíró komplex egész számot reprezentáló osztály
 * Creation date: (2002.03.03. 15:14:51)
 */
public class UtSzam implements Serializable {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	public final int im;	// elõzõ játékostól
	public final int re;	// pakliból
public UtSzam(int iRe, int iIm) {
	super();
	re = iRe;	// pakliból
	im = iIm;	// elõzõ játékostól
}
public UtSzam hozzaAd(UtSzam ur) {
	return new UtSzam(re + ur.re, im + ur.im);
}
public UtSzam megSzoroz(UtSzam ur) {
	return new UtSzam(re * ur.re - im * ur.im, re * ur.im + im * ur.re);
}
/**
 * Az InfóBoxba kiíródó magyarázó szöveg
 */
public String toLongString() {
	String s;
	if (im == 0) {
		if (re == 0) {
			s = resInfoTexts.getString("0_lap,_csak_passzolni_kell"); //$NON-NLS-1$
		} else if (re > 0) {
			s = resInfoTexts.getString("%up_lap_a_pakliból"); //$NON-NLS-1$
		} else { // (re < 0)
			s = resInfoTexts.getString("%up_lapot_eldobhat_az_elõz"); //$NON-NLS-1$
		}
	} else if (im > 0) {
		if (re == 0) {
			s = resInfoTexts.getString("%uj_lapot_húzni_az_elõzõ_j"); //$NON-NLS-1$
		} else if (re > 0) {
			s = resInfoTexts.getString("%up_a_pakliból,_%uj-t_húzn"); //$NON-NLS-1$
		} else { // (re < 0)
			s = resInfoTexts.getString("%uj-t_húzni_az_elõzõ_játék"); //$NON-NLS-1$
		}
	} else { // (im < 0)
		if (re == 0) {
			s = resInfoTexts.getString("%uj_lapot_átad_az_elõzõ_já"); //$NON-NLS-1$
		} else if (re > 0) {
			s = resInfoTexts.getString("%up_a_pakliból,_%uj_lapot_"); //$NON-NLS-1$
		} else { // (re > 0)
			s = resInfoTexts.getString("%uj_lapot_átad_az_elõzõ_já1"); //$NON-NLS-1$
		}
	}
	s = szr.shared.SubstituteStr.doIt(s, "%uj", Integer.toString((im >= 0) ? im : -im));//$NON-NLS-1$
	s = szr.shared.SubstituteStr.doIt(s, "%up", Integer.toString((re >= 0) ? re : -re));//$NON-NLS-1$
	return SubstituteStr.doIt(SubstituteStr.doIt(resInfoTexts.getString("Kötelezõ_húzás__%descr_[Ut"), "%descr", s), "%ut", toString());//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
}
/**
 * Csak a szám szöveges formában
 */
public String toString() {
	String s;
	return ((re == 0 && im == 0) ? "0"//$NON-NLS-1$
		 : ((re > 0) ? "+" : "") //$NON-NLS-2$//$NON-NLS-1$
		 + ((re != 0) ? Integer.toString(re) : "")//$NON-NLS-1$
		 + ((im > 0) ? "+" : "")//$NON-NLS-2$//$NON-NLS-1$
		 + ((im == -1) ? "-" : "")//$NON-NLS-2$//$NON-NLS-1$
		 + ((im < -1 || im > 1) ? Integer.toString(im) : "")//$NON-NLS-1$
		 + ((im != 0) ? "i" : ""));//$NON-NLS-2$//$NON-NLS-1$
}
}
