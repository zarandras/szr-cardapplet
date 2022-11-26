package szr.card;

import szr.card.*;
/**
 * Román turistajelzés-osztály
 * Creation date: (2002.03.20. 17:11:36)
 */
public class RO_TuristaJel extends TuristaJel {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	public final static int STYLE_ID = 2;
	
	public final static int ID_SAV			= 46;
	public final static int ID_KERESZT		= 47;
	public final static int ID_HAROMSZOG	= 48;
	public final static int ID_PONT			= 49;

	public final static int[] ID_ARR 		= {ID_SAV, ID_KERESZT,
											   ID_HAROMSZOG, ID_PONT};
public RO_TuristaJel(int iID) {
	super(iID);
}
public boolean getDoublePicFlag() {
	return false;
}
public String getJelzesSugoFileName() {
	return "j_marcaj.html#" + Integer.toString(getID());//$NON-NLS-1$
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
		case ID_SAV:		return resCardTexts.getString("Sáv / Banda"); //$NON-NLS-1$
		case ID_KERESZT:	return resCardTexts.getString("Kereszt / Cruce"); //$NON-NLS-1$
		case ID_HAROMSZOG:	return resCardTexts.getString("Háromszög / Triunghi"); //$NON-NLS-1$
		case ID_PONT:		return resCardTexts.getString("Pont / Punct"); //$NON-NLS-1$
		default:			
			logInvalidJelzesParam(szr.shared.SubstituteStr.doIt("Invalid RO_TuristaJel ID: %id", "%id", Integer.toString(getID())));//$NON-NLS-2$//$NON-NLS-1$
			return resCardTexts.getString("?_marcaj"); //$NON-NLS-1$
	}
}
}
