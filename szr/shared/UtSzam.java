package szr.shared;

import java.io.Serializable;
/**
 * Az utas�t�si (k�telez� h�z�si) mennyis�get le�r� komplex eg�sz sz�mot reprezent�l� oszt�ly
 * Creation date: (2002.03.03. 15:14:51)
 */
public class UtSzam implements Serializable {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	public final int im;	// el�z� j�t�kost�l
	public final int re;	// paklib�l
public UtSzam(int iRe, int iIm) {
	super();
	re = iRe;	// paklib�l
	im = iIm;	// el�z� j�t�kost�l
}
public UtSzam hozzaAd(UtSzam ur) {
	return new UtSzam(re + ur.re, im + ur.im);
}
public UtSzam megSzoroz(UtSzam ur) {
	return new UtSzam(re * ur.re - im * ur.im, re * ur.im + im * ur.re);
}
/**
 * Az Inf�Boxba ki�r�d� magyar�z� sz�veg
 */
public String toLongString() {
	String s;
	if (im == 0) {
		if (re == 0) {
			s = resInfoTexts.getString("0_lap,_csak_passzolni_kell"); //$NON-NLS-1$
		} else if (re > 0) {
			s = resInfoTexts.getString("%up_lap_a_paklib�l"); //$NON-NLS-1$
		} else { // (re < 0)
			s = resInfoTexts.getString("%up_lapot_eldobhat_az_el�z"); //$NON-NLS-1$
		}
	} else if (im > 0) {
		if (re == 0) {
			s = resInfoTexts.getString("%uj_lapot_h�zni_az_el�z�_j"); //$NON-NLS-1$
		} else if (re > 0) {
			s = resInfoTexts.getString("%up_a_paklib�l,_%uj-t_h�zn"); //$NON-NLS-1$
		} else { // (re < 0)
			s = resInfoTexts.getString("%uj-t_h�zni_az_el�z�_j�t�k"); //$NON-NLS-1$
		}
	} else { // (im < 0)
		if (re == 0) {
			s = resInfoTexts.getString("%uj_lapot_�tad_az_el�z�_j�"); //$NON-NLS-1$
		} else if (re > 0) {
			s = resInfoTexts.getString("%up_a_paklib�l,_%uj_lapot_"); //$NON-NLS-1$
		} else { // (re > 0)
			s = resInfoTexts.getString("%uj_lapot_�tad_az_el�z�_j�1"); //$NON-NLS-1$
		}
	}
	s = szr.shared.SubstituteStr.doIt(s, "%uj", Integer.toString((im >= 0) ? im : -im));//$NON-NLS-1$
	s = szr.shared.SubstituteStr.doIt(s, "%up", Integer.toString((re >= 0) ? re : -re));//$NON-NLS-1$
	return SubstituteStr.doIt(SubstituteStr.doIt(resInfoTexts.getString("K�telez�_h�z�s__%descr_[Ut"), "%descr", s), "%ut", toString());//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
}
/**
 * Csak a sz�m sz�veges form�ban
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
