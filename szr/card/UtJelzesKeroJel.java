package szr.card;

/**
 * Utasítójelzés-kérõ jelzésosztály
 * Creation date: (2002.03.05. 7:42:08)
 */
public class UtJelzesKeroJel extends JelzesKeroJel implements UtHalmozo {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	private final static Jelzes[] UT_KERHETO_JELEK = {
		new UtJel(UtJel.IxID),
		new UtJel(UtJel.HuzoID), new UtJel(UtJel.KettesHuzoID), new UtJel(UtJel.NegyesHuzoID),
		new UtJel(UtJel.ElhuzoID), new UtJel(UtJel.KettesElhuzoID),
		new UtJel(UtJel.PipaID), new UtJel(UtJel.KettesPipaID),
		new UtJel(UtJel.AtadoID), new UtJel(UtJel.KettesAtadoID)
	};
public UtJelzesKeroJel() {
	super(-1);
}
protected UtJelzesKeroJel(Jelzes iKertJel) {
	super(-1, iKertJel);
}
public JelzesKero applyKertJel(Jelzes newKertJel) {
	return new UtJelzesKeroJel(newKertJel);
}
public int getID() {
	return 34;
}
public Jelzes[] getKerhetoJelek() {
	return UT_KERHETO_JELEK;
}
public Jelzes reset() {
	return new UtJelzesKeroJel();
}
public String toString() {
	return szr.shared.SubstituteStr.doIt(resCardTexts.getString("Utasítójelzés-kérõ%kj"), "%kj", (getKertJel() == null) ? "" : ("(" + getKertJel().toString() + ")"));//$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
}
}
