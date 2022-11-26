package szr.card;

/**
 * Turistajelzés-kérõ jelzésosztály
 * Creation date: (2002.03.05. 7:42:08)
 */
public class JelzesKeroJel extends Jelzes implements JelzesKero {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	private final Jelzes kertJel;
	private final static Jelzes[] KERHETO_JELEK_HU = {
		new TuristaJel(TuristaJel.ID_SAV), new TuristaJel(TuristaJel.ID_KERESZT),
		new TuristaJel(TuristaJel.ID_KOR), new TuristaJel(TuristaJel.ID_HAROMSZOG), new TuristaJel(TuristaJel.ID_NEGYZET),
		new TuristaJel(TuristaJel.ID_BARLANG), new TuristaJel(TuristaJel.ID_ROM), new TuristaJel(TuristaJel.ID_KORUT),
		new ForditoJel(TuristaJel.STYLE_ID)
	};
	private final static Jelzes[] KERHETO_JELEK_SK = {
		new SK_TuristaJel(SK_TuristaJel.ID_SAV),
		new SK_TuristaJel(SK_TuristaJel.ID_HELYI), new NCHTuristaJel(), new SK_TuristaJel(SK_TuristaJel.ID_KOR),
		new SK_TuristaJel(SK_TuristaJel.ID_HAROMSZOG), new SK_TuristaJel(SK_TuristaJel.ID_ROM), new SK_TuristaJel(SK_TuristaJel.ID_FORRAS), 
		new SK_TuristaJel(SK_TuristaJel.ID_HAZ), new SK_TuristaJel(SK_TuristaJel.ID_BARLANG), new SK_TuristaJel(SK_TuristaJel.ID_KITERO),
		new ForditoJel(SK_TuristaJel.STYLE_ID)
	};
	private final static Jelzes[] KERHETO_JELEK_RO = {
		new RO_TuristaJel(RO_TuristaJel.ID_SAV), new RO_TuristaJel(RO_TuristaJel.ID_KERESZT), 
		new RO_TuristaJel(RO_TuristaJel.ID_PONT), new RO_TuristaJel(RO_TuristaJel.ID_HAROMSZOG),
		new ForditoJel(RO_TuristaJel.STYLE_ID)
	};
	private final int style;
public JelzesKeroJel(int iStyle) {
	super();
	style = iStyle;
	kertJel = null;
}
protected JelzesKeroJel(int iStyle, Jelzes iKertJel) {
	super();
	style = iStyle;
	kertJel = iKertJel;
}
public JelzesKero applyKertJel(Jelzes newKertJel) {
	return new JelzesKeroJel(style, newKertJel);
}
public boolean doLPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	switch (invokeCounter) {
		case 0:
			jv.forceJelzesKeres(getKerhetoJelek());
			return false;
			
		case 1:
			jv.forceSzinKeres();
			return false;
			
		case 2:
			return true;
			
		default:
			logInvalidInvokeCounter(invokeCounter);
			return true;
	}
}
public boolean getDoublePicFlag() {
	return false;
}
public int getID() {
	switch (style) {
		case TuristaJel.STYLE_ID:		return 12;
		case SK_TuristaJel.STYLE_ID:	return 45;
		case RO_TuristaJel.STYLE_ID:	return 50;
		default:
			logInvalidJelzesParam(szr.shared.SubstituteStr.doIt("Invalid style ID: %id", "%id", Integer.toString(style)));	//$NON-NLS-2$//$NON-NLS-1$
			return 0;
	}
}
public Jelzes[] getKerhetoJelek() {
	switch (style) {
		case TuristaJel.STYLE_ID:		return KERHETO_JELEK_HU;
		case SK_TuristaJel.STYLE_ID:	return KERHETO_JELEK_SK;
		case RO_TuristaJel.STYLE_ID:	return KERHETO_JELEK_RO;
		default:						return null;
	}
}
public Jelzes getKertJel() {
	return kertJel;
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
public Jelzes reset() {
	return new JelzesKeroJel(style);
}
public String toString() {
	return szr.shared.SubstituteStr.doIt(resCardTexts.getString("Jelzéskérõ%kj"), "%kj", (kertJel == null) ? "" : ("(" + kertJel.toString() + ")"));//$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
}
}
