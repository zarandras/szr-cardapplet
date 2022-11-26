package szr.shared;

import java.io.*;
/**
 * Paklifajta leíró rekord (a GameControlPanel pakliválasztó listájához)
 * Creation date: (2002.03.22. 19:43:15)
 */
public class PakliRec implements Serializable {
	public final java.lang.String pakliName;
	public final java.lang.String pakliDocHTML;
	public final int nrOfCards;	// ==0 : 'végtelen' (RandomPakli); <0 : a pakli nem létezik
public PakliRec(String iPakliName, int iNrOfCards, String iPakliDocHTML) {
	super();
	pakliName = iPakliName;
	nrOfCards = iNrOfCards;
	pakliDocHTML = iPakliDocHTML;
}
public String toString() {
	if (nrOfCards > 0) {
		return pakliName + " (" + Integer.toString(nrOfCards) + ")";//$NON-NLS-2$//$NON-NLS-1$
	} else if (nrOfCards == 0) {
		return pakliName + " (*)";//$NON-NLS-1$
	} else { // (nrOfCards < 0)
		return pakliName;
	}
}
}
