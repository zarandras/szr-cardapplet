package szr.shared;

import szr.card.*;
/**
 * L�ncSoroz�s
 * Creation date: (2002.03.12. 6:43:56)
 */
public class ExSTLancSorozo extends ExSType {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final ExSType nestedExS;
public ExSTLancSorozo() {
	super();
	nestedExS = null;
}
protected ExSTLancSorozo(ExSType iNestedExS) {
	super();
	nestedExS = iNestedExS;
}
public void doExSFinished(szr.mainserv.GameController jv) {
	if (nestedExS != null) {
		nestedExS.doExSFinished(jv);
	}
}
public void doExSStep(szr.mainserv.GameController jv) {
	// �resm mert a nestedExS-re nem h�vjuk meg,
	// ui. a k�zb�ls� lapok jelent�se nem �rv�nyes�l a l�ncsoroz�skor
}
public ExSType illeszt(Szin aktC, Jelzes aktJ, Szin cardC, Jelzes cardJ) {
	ExSType newExS;
	ExSType newNestedExS;

	if (nestedExS == null) {
		newNestedExS = null;
	} else {
		newNestedExS = nestedExS.illeszt(aktC, aktJ, cardC, cardJ);
	}

	if (newNestedExS != null) {
		newExS = new ExSTLancSorozo(newNestedExS);
	} else if (aktJ.illeszkedikRa(cardC, cardJ, aktC)) {
		newNestedExS = (cardJ instanceof ExSJel) ? ((ExSJel)cardJ).getNewExST(cardC) : null;
		newExS = new ExSTLancSorozo(newNestedExS);
	} else {
		newExS = null;
	}
	return newExS;
}
public String toString() {
	return resInfoTexts.getString("L�ncSoroz�si_lehet�s�g!"); //$NON-NLS-1$
}
}
