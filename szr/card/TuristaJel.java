package szr.card;

/**
 * Turistajelzés-osztály	(beszúrható)
 * Creation date: (2002.02.10. 15:33:03)
 */
public class TuristaJel extends Jelzes {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	public final static int ID_SAV 			= 1;
	public final static int ID_KERESZT	 	= 2;
	public final static int ID_KOR 			= 3;
	public final static int ID_HAROMSZOG 	= 4;
	public final static int ID_NEGYZET 		= 5;
	public final static int ID_BARLANG 		= 6;
	public final static int ID_ROM 			= 7;
	public final static int ID_KORUT 		= 8;

	public final static int[] ID_ARR 		= {ID_SAV, ID_KERESZT,
											   ID_KOR, ID_HAROMSZOG, ID_NEGYZET,
											   ID_BARLANG, ID_ROM, ID_KORUT};
	
	private final int id;
	public final static int STYLE_ID = 0;
public TuristaJel(int iID) {
	super();
	id = iID;
}
public boolean doLPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		jv.forceSzinKeres();
		return false;
	} else
		if (invokeCounter > 1)
			logInvalidInvokeCounter(invokeCounter);
		return true;
}
public boolean getDoublePicFlag() {
	return true;
}
public int getID() {
	return id;
}
public String getJelzesSugoFileName() {
	return "j_jelzes.html#" + Integer.toString(getID());//$NON-NLS-1$
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
	switch (id) {
		case ID_SAV:		return resCardTexts.getString("Sáv"); //$NON-NLS-1$
		case ID_KERESZT:	return resCardTexts.getString("Kereszt"); //$NON-NLS-1$
		case ID_KOR:		return resCardTexts.getString("Kör"); //$NON-NLS-1$
		case ID_HAROMSZOG:	return resCardTexts.getString("Háromszög"); //$NON-NLS-1$
		case ID_NEGYZET:	return resCardTexts.getString("Négyzet"); //$NON-NLS-1$
		case ID_BARLANG:	return resCardTexts.getString("Barlang"); //$NON-NLS-1$
		case ID_ROM:		return resCardTexts.getString("Rom"); //$NON-NLS-1$
		case ID_KORUT:		return resCardTexts.getString("Körút"); //$NON-NLS-1$
		default:			
			logInvalidJelzesParam(szr.shared.SubstituteStr.doIt("Invalid TuristaJel ID: %id", "%id", Integer.toString(id)));//$NON-NLS-2$//$NON-NLS-1$
			return resCardTexts.getString("?_turistajel"); //$NON-NLS-1$
	}
}
}
