package szr.card;

/**
 * 'Bármilyen kérhetõ' (elmaradt jelzéskérés) jelzésosztály
 * Creation date: (2002.04.02. 12:22:35)
 */
public class ExtremalJel extends Jelzes {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	
public boolean getDoublePicFlag() {
	return false;
}
public int getID() {
	return 0;
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
public String toString() {
	return resCardTexts.getString("Bármilyen_kérhetõ"); //$NON-NLS-1$
}
}
