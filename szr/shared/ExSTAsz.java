package szr.shared;

import szr.card.*;
/**
 * Színsorozás
 * Creation date: (2002.03.12. 6:33:40)
 */
public class ExSTAsz extends ExSType {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final Szin aszSzin;
public ExSTAsz(Szin iAszSzin) {
	super();
	if (iAszSzin instanceof SzinKero && ((SzinKero)iAszSzin).getKertSzin() != null)
		aszSzin = ((SzinKero)iAszSzin).getKertSzin();
	else
		aszSzin = iAszSzin;
}
public void doExSFinished(szr.mainserv.GameController jv) {}
public void doExSStep(szr.mainserv.GameController jv) {}
public ExSType illeszt(Szin aktC, Jelzes aktJ, Szin cardC, Jelzes cardJ) {
	try {
		if (aszSzin.illeszkedikRa(cardC)) {
			return new ExSTAsz(aszSzin);
 		} else {
	 		return null;
 		}
	} catch (NullPointerException e) {
		return null;
	}
}
public String toString() {
	return SubstituteStr.doIt(resInfoTexts.getString("%exsc_SzínSorozási_lehetõs"), "%exsc", (aszSzin != null) ? aszSzin.toString() : "???");//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
}
}
