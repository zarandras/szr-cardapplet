package szr.card;

/**
 * 'B�rmilyen k�rhet�' (elmaradt jelz�sk�r�s) jelz�soszt�ly
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
	return resCardTexts.getString("B�rmilyen_k�rhet�"); //$NON-NLS-1$
}
}
