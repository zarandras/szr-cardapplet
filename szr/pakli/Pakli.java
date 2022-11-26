package szr.pakli;

import szr.msg.*;
import java.util.*;
import szr.card.*;
import szr.shared.*;
import szr.mainserv.*;
/**
 * Pakli �soszt�ly
 * Creation date: (2002.02.05. 20:02:25)
 */
public abstract class Pakli {
	private static java.util.ResourceBundle resWidgetTexts = java.util.ResourceBundle.getBundle("szr.shared.WidgetTexts");  //$NON-NLS-1$
	public final static Object[][] PAKLI_ARR = {	// v�laszthat� paklik
		{null, new PakliRec(resWidgetTexts.getString("<-_Klasszikus__->"), -1, null)},		// separator //$NON-NLS-1$
		{"szr.pakli.ClassicPakli", new PakliRec(resWidgetTexts.getString("Magyar"), 160, "magyar.html")}, //$NON-NLS-2$//$NON-NLS-1$//$NON-NLS-3$
		{"szr.pakli.SzlovakPakli", new PakliRec(resWidgetTexts.getString("Szlov�k"), 180, "szlovak.html")}, //$NON-NLS-2$//$NON-NLS-1$//$NON-NLS-3$
		{"szr.pakli.RomanPakli", new PakliRec(resWidgetTexts.getString("Rom�n"), 160, "roman.html")}, //$NON-NLS-2$//$NON-NLS-1$//$NON-NLS-3$
		{null, new PakliRec(resWidgetTexts.getString("<-_Mini__->"), -1, null)},		// separator //$NON-NLS-1$
		{"szr.pakli.UnoPakli", new PakliRec(resWidgetTexts.getString("Sz�zas"), 100, "szazas.html")}, //$NON-NLS-2$//$NON-NLS-1$//$NON-NLS-3$
		{"szr.pakli.MiniPakli", new PakliRec(resWidgetTexts.getString("Mini"), 93, "mini.html")}, //$NON-NLS-2$//$NON-NLS-1$//$NON-NLS-3$
		{"szr.pakli.MiniRandomPakli", new PakliRec(resWidgetTexts.getString("Mini_R"), 0, "mini_r.html")}, //$NON-NLS-2$//$NON-NLS-1$//$NON-NLS-3$
		{null, new PakliRec(resWidgetTexts.getString("<-_Maxi__->"), -1, null)},		// separator //$NON-NLS-1$
		{"szr.pakli.MaxiPakli", new PakliRec(resWidgetTexts.getString("Maxi"), 200, "maxi.html")}, //$NON-NLS-2$//$NON-NLS-1$//$NON-NLS-3$
		{"szr.pakli.MaxiRandomPakli", new PakliRec(resWidgetTexts.getString("Maxi_R"), 0, "maxi_r.html")}, //$NON-NLS-2$//$NON-NLS-1$//$NON-NLS-3$
		{null, new PakliRec(resWidgetTexts.getString("<-_Extr�m__->"), -1, null)},		// separator //$NON-NLS-1$
		{"szr.pakli.HazardRandomPakli", new PakliRec(resWidgetTexts.getString("Haz�rd_R"), 0, "hazard_r.html")}, //$NON-NLS-2$//$NON-NLS-1$//$NON-NLS-3$
	};
	public final static Object[][] P_PAKLI_ARR = {	// tesztpaklik
		{null, new PakliRec("<- Tesztpaklik: ->", -1, null)},		// separator//$NON-NLS-1$
		{"szr.pakli.ProbaRandomPakli", new PakliRec("P_MindentBele_R", 0, null)},//$NON-NLS-2$//$NON-NLS-1$
		{"szr.pakli.ProbaAzHatRandomPakli", new PakliRec("P_AzHat_R", 0, null)},//$NON-NLS-2$//$NON-NLS-1$
		{"szr.pakli.ProbaUtRandomPakli", new PakliRec("P_Ut_R", 0, null)},//$NON-NLS-2$//$NON-NLS-1$
		{"szr.pakli.ProbaExSRandomPakli", new PakliRec("P_ExS_R", 0, null)},//$NON-NLS-2$//$NON-NLS-1$
		{"szr.pakli.ProbaLimPakli", new PakliRec("P_Lim", 5, null)},//$NON-NLS-2$//$NON-NLS-1$
		{"szr.pakli.ProbaEgylaposPakli", new PakliRec("P_Egylapos", 1, null)},//$NON-NLS-2$//$NON-NLS-1$
	};
	private final Vector pushedCards = new Vector();	// beletett k�rty�k
	protected Random random = new java.util.Random ();
	private GameController parentController = null;		// arra kell, hogy ha elfogy �s �j paklit kezd, a rendszer �zenetet k�ldhessen r�la
/**
 * A pakli felt�lt�se k�rty�kkal (keverve kell felt�lteni!)
 */
public abstract void fillPakli();
/**
 * A v�laszt� paklik list�ja tartalm�nak �ssze�ll�t�sa
 */
public static Vector getPakliRecs(boolean testMode) {
	Vector v = new Vector(PAKLI_ARR.length);
	for (int i = 0; i < PAKLI_ARR.length; ++i) {
		v.addElement(PAKLI_ARR[i][1]);
	}
	if (testMode) {
		v.addElement(new PakliRec("<---------------->", -1, null));// separator//$NON-NLS-1$
		for (int i = 0; i < P_PAKLI_ARR.length; ++i) {
			v.addElement(P_PAKLI_ARR[i][1]);
		}
	}
	return v;
}
public GameController getParentController() {
	return parentController;
}
protected int getPushedCardCount() {
	return pushedCards.size();
}
/**
 * Milyen alapsz�nek vannak a pakliban? (default: mind a n�gy)
 */
public int getSzinMask() {
	return SzinesSzin.Kmask | SzinesSzin.Pmask | SzinesSzin.Zmask | SzinesSzin.Smask;
}
/**
 * K�rtya beilleszt�se adott poz�ci�ra
 */
protected void insertCardAt(Card cj, int idx) {
	if (pushedCards.size() <= idx)
		pushCard(cj);
	else
		pushedCards.insertElementAt(cj, idx);
}
/**
 * Paklifajta-index j�-e? (a lista:[PAKLI_ARR, {separator}, P_PAKLI_ARR])
 */
public static boolean isIndexValid(int pakliIdx) {
	return ((pakliIdx >= 0 && pakliIdx < PAKLI_ARR.length && PAKLI_ARR[pakliIdx][0] != null)
		 || (pakliIdx >= PAKLI_ARR.length + 1 && pakliIdx < PAKLI_ARR.length + P_PAKLI_ARR.length + 1 && P_PAKLI_ARR[pakliIdx - PAKLI_ARR.length - 1][0] != null));
}
public static Pakli newPakli(int pakliIdx, GameController iParentController) {
	try {
		Pakli p;
		if (pakliIdx < PAKLI_ARR.length) {
			p = (Pakli)Class.forName((String)PAKLI_ARR[pakliIdx][0]).newInstance();
		} else {
			p = (Pakli)Class.forName((String)P_PAKLI_ARR[pakliIdx - PAKLI_ARR.length - 1][0]).newInstance();
		}
		p.setParentController(iParentController);
		return p;
	} catch (Exception e) {
		Logger.log(new ErrExcLog(e), iParentController.getProperties().name, null);
		return null;
	}
}
/**
 * A k�vetkez� k�rtya (ha a pushedCards �res)
 */
protected abstract Card nextCard();
/**
 * H�z�s
 */
public Card popCard() {
	if (pushedCards.isEmpty()) {
		return nextCard();
	} else {
		int lastIdx = pushedCards.size() - 1;
		Card c = (Card)pushedCards.elementAt(lastIdx);
		pushedCards.removeElementAt(lastIdx);
		return c;
	}
}
/**
 * Tetej�re rak�s
 */
public void pushCard(Card c) {
	pushedCards.addElement(c);
}
public void setParentController(GameController iParentController) {
	parentController = iParentController;
}
}
