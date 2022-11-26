package szr.card;

/**
 * CsúcsCsoki színosztály
 * Creation date: (2002.03.12. 7:14:36)
 */
public class HalmozoJelzesKeroSzin_HU extends Szin implements JelzesKero, UtHalmozo {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	private final Jelzes kertJel;
	private final static Jelzes[] KERHETO_JELEK = {
		// TuristaJel, ForditoJel:
		new TuristaJel(1), new TuristaJel(2), new TuristaJel(3), 
		new TuristaJel(4), new TuristaJel(5), new TuristaJel(6), 
		new TuristaJel(7), new TuristaJel(8), new ForditoJel(TuristaJel.STYLE_ID),

		// UtJel:
		new UtJel(UtJel.IxID),
		new UtJel(UtJel.HuzoID), new UtJel(UtJel.KettesHuzoID), new UtJel(UtJel.NegyesHuzoID),
		new UtJel(UtJel.ElhuzoID), new UtJel(UtJel.KettesElhuzoID),
		new UtJel(UtJel.PipaID), new UtJel(UtJel.KettesPipaID),
		new UtJel(UtJel.AtadoID), new UtJel(UtJel.KettesAtadoID),

		// UtSzorzoJel:
		new UtSzorzoJel(UtSzorzoJel.DuplazoID),
		new UtSzorzoJel(UtSzorzoJel.FelPipaloID),
		new UtSzorzoJel(UtSzorzoJel.PipaloID)
	};
public HalmozoJelzesKeroSzin_HU() {
	super();
	kertJel = null;
}
protected HalmozoJelzesKeroSzin_HU(Jelzes iKertJel) {
	super();
	kertJel = iKertJel;
}
public JelzesKero applyKertJel(Jelzes newKertJel) {
	return new HalmozoJelzesKeroSzin_HU(newKertJel);
}
public String getImgPath(boolean doublePicFlag) {
	return Card.getImgRootPath () + "b.gif";//$NON-NLS-1$
}
public szr.card.Jelzes[] getKerhetoJelek() {
	return KERHETO_JELEK;
}
public Jelzes getKertJel() {
	return kertJel;
}
public int getMask() {
	return Szin.FullMask;
	// (Bármire illeszkedik. Az illeszkedikRa() viszont nem engedi meg,
	//  hogy rá illeszkedjen egy nem HalmozoJelzesKeroSzin!)
}
public String getSmallImgPath() {
	return Card.getSmallImgRootPath () + "b.gif";//$NON-NLS-1$
}
public String getSzinSugoFileName() {
	return "c_b.html";//$NON-NLS-1$
}
public boolean illeszkedikRa(Szin mi) {
	return (mi instanceof HalmozoJelzesKeroSzin_HU);
	// a jelzéskérésre való illeszkedést a Jelzes.illeszkedikRa vizsgálja
}
public boolean keresreIlleszk(Jelzes resolvedJelzes) {
	if (kertJel == null || kertJel instanceof ExtremalJel) {
		// ha még nem kértek, akkor minden kérhetõ illeszkedik (mert lehetne kérni)
		// (ennek láncsorozáskor van jelentõsége)
		return (kerheto(resolvedJelzes));
	} else {
		return ((Jelzes)kertJel).megegyezik(resolvedJelzes);
	}
}
public boolean kerheto(Jelzes j) {
	int i = 0;
	while (i < getKerhetoJelek().length && !getKerhetoJelek()[i].megegyezik(j)) {
		i++;
	}
	return (i < getKerhetoJelek().length);
}
public boolean megegyezik(Szin c) {
	return (c instanceof HalmozoJelzesKeroSzin_HU);
}
public Szin reset() {
	return new HalmozoJelzesKeroSzin_HU();
}
public String toString() {
	return szr.shared.SubstituteStr.doIt(resCardTexts.getString("CsúcsCsoki%kj"), "%kj",(kertJel == null) ? "" : ("(" + kertJel.toString() + ")"));//$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
}
public boolean uresreIsIlleszk() {
	return true;
}
}
