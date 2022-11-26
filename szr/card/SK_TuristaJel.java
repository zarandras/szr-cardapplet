package szr.card;

import szr.card.*;
/**
 * Szlov�k turistajelz�s-oszt�ly
 * Creation date: (2002.03.20. 17:11:36)
 */
public class SK_TuristaJel extends TuristaJel {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	public final static int STYLE_ID = 1;
	
	public final static int ID_SAV			= TuristaJel.ID_SAV;
	public final static int ID_HELYI		= 38;
	//public final static int ID_NCH 		= 39;	<- NCHTuristaJel class
	public final static int ID_KOR			= TuristaJel.ID_KOR;
	public final static int ID_HAROMSZOG	= TuristaJel.ID_HAROMSZOG;
	public final static int ID_ROM			= TuristaJel.ID_ROM;
	public final static int ID_FORRAS		= 40;
	public final static int ID_HAZ			= 41;
	public final static int ID_BARLANG		= 42;
	public final static int ID_KITERO		= 43;

	public final static int[] ID_ARR 		= {ID_SAV,
											   ID_HELYI, /* ID_NCH, */ ID_KOR,
											   ID_HAROMSZOG, ID_ROM, ID_FORRAS,
											   ID_HAZ, ID_BARLANG, ID_KITERO};
public SK_TuristaJel(int iID) {
	super(iID);
}
public boolean getDoublePicFlag() {
	return true;
}
public String getJelzesSugoFileName() {
	return "j_znacka.html#" + Integer.toString(getID());//$NON-NLS-1$
}
public boolean hasAImg() {
	return true;
}
public boolean hasKetszinuImg() {
	return true;
}
public boolean hasLImg() {
	return true;
}
public String toString() {
	switch (getID()) {
		case ID_SAV:		return resCardTexts.getString("S�v- / P�sov�"); //$NON-NLS-1$
		case ID_HELYI:		return resCardTexts.getString("Helyi- / Miestn�"); //$NON-NLS-1$
		case ID_KOR:		return resCardTexts.getString("K�r- / Okru�n�"); //$NON-NLS-1$
		case ID_HAROMSZOG:	return resCardTexts.getString("H�romsz�g / Trojuholn�k"); //$NON-NLS-1$
		case ID_ROM:		return resCardTexts.getString("Rom / Zr�canina"); //$NON-NLS-1$
		case ID_FORRAS:		return resCardTexts.getString("Forr�s / Prame�"); //$NON-NLS-1$
		case ID_HAZ:		return resCardTexts.getString("H�z / Chata"); //$NON-NLS-1$
		case ID_BARLANG:	return resCardTexts.getString("Barlang / Jasky�a"); //$NON-NLS-1$
		case ID_KITERO:		return resCardTexts.getString("Kit�r�- / V�znamov�"); //$NON-NLS-1$
		default:			
			logInvalidJelzesParam(szr.shared.SubstituteStr.doIt("Invalid SK_TuristaJel ID: %id", "%id", Integer.toString(getID())));//$NON-NLS-2$//$NON-NLS-1$
			return resCardTexts.getString("?_zna�ka"); //$NON-NLS-1$
	}
}
}
