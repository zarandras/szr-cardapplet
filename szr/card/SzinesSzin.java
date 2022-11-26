package szr.card;

/**
 * 'Színes' színosztály (a színes lapok színei: alapszínek és kombinációik)
 * Creation date: (2002.02.09. 21:37:35)
 */
public class SzinesSzin extends Szin {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	public final static byte Kmask = 0x01;	// kék maszk
	public final static byte Pmask = 0x02;	// piros maszk
	public final static byte Zmask = 0x04;	// zöld maszk
	public final static byte Smask = 0x08;	// sárga maszk
	public final static byte Fmask = EmptyMask;	// fekete maszk
	private final int mask;
public SzinesSzin(int iMask) {
	super();
	mask = iMask;
}
public java.lang.String getImgPath(boolean doublePicFlag) {
	return Card.getImgRootPath () + 
		((isK()) ? "k" : "") + ((isP()) ? "p" : "") + ((isZ()) ? "z" : "") + ((isS()) ? "s" : "") + ((isF()) ? "f" : "") + //$NON-NLS-10$//$NON-NLS-9$//$NON-NLS-8$//$NON-NLS-7$//$NON-NLS-6$//$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
		((isKetszinu() && doublePicFlag) ? "d" : "") +//$NON-NLS-2$//$NON-NLS-1$
		".gif";//$NON-NLS-1$
}
public int getMask() {
	return mask;
}
public java.lang.String getSmallImgPath() {
	return Card.getSmallImgRootPath () + 
		(((isK()) ? "k" : "") + ((isP()) ? "p" : "") + ((isZ()) ? "z" : "") + ((isS()) ? "s" : "") + ((isF()) ? "f" : "")) + //$NON-NLS-10$//$NON-NLS-9$//$NON-NLS-8$//$NON-NLS-7$//$NON-NLS-6$//$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
		".gif";//$NON-NLS-1$
}
public String getSzinSugoFileName() {
	return "c_" + ((isKetszinu()) ? "cd" ://$NON-NLS-2$//$NON-NLS-1$
  					((isF()) ? "f" ://$NON-NLS-1$
					((!isEgyszinu()) ? "cde" ://$NON-NLS-1$
						((isK()) ? "k" ://$NON-NLS-1$
						((isP()) ? "p" ://$NON-NLS-1$
						((isZ()) ? "z" ://$NON-NLS-1$
						((isS()) ? "s" : ""))))//$NON-NLS-2$//$NON-NLS-1$
					))
  				   )
				+ ".html";//$NON-NLS-1$
}
public boolean isEgyszinu() {
	int szinSzam = 0;
	int m = mask;
	while (m != 0) {
		if ((m & 1) == 1)
			szinSzam++;
		m >>= 1;
	}
	return (szinSzam == 1);
}
public boolean isF() {
	return (getMask() == Fmask);	// 0, EmptyMask
}
public boolean isK() {
	return ((getMask() & Kmask) == Kmask);
}
public boolean isKetszinu() {
	int szinSzam = 0;
	int m = mask;
	while (m != 0) {
		if ((m & 1) == 1)
			szinSzam++;
		m >>= 1;
	}
	return (szinSzam == 2);
}
public boolean isP() {
	return ((getMask() & Pmask) == Pmask);
}
public boolean isS() {
	return ((getMask() & Smask) == Smask);
}
public boolean isZ() {
	return ((getMask() & Zmask) == Zmask);
}
public boolean megegyezik(Szin c) {
	return (c instanceof SzinesSzin && illeszkedikRa(c));
			// pl. a K\P megegyezik a K-kel vagy a K\S-val. Nem tranzitív!
}
public String toString() {
	return (((isK())? resCardTexts.getString("Kék") : "") + ((isP())? resCardTexts.getString("Piros") : "") + ((isZ())? resCardTexts.getString("Zöld") : "") + ((isS())? resCardTexts.getString("Sárga") : "") + ((isF())? resCardTexts.getString("Fekete") : ""));//$NON-NLS-10$ //$NON-NLS-9$//$NON-NLS-8$ //$NON-NLS-7$//$NON-NLS-6$ //$NON-NLS-5$//$NON-NLS-4$ //$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
}
public boolean uresreIsIlleszk() {
	return false;
}
}
