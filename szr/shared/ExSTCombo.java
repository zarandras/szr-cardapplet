package szr.shared;

import szr.card.*;
import java.util.*;
import szr.mainserv.*;
/**
 * Combo lerakás
 * Creation date: (2002.03.12. 6:57:24)
 */
public class ExSTCombo extends ExSType {
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final transient Vector addedCards;
	private transient Random random = new Random();
	private final Vector megLerakhatoJelek;
public ExSTCombo() {
	super();
	addedCards = new Vector();
	megLerakhatoJelek = new Vector(TuristaJel.ID_ARR.length);
	for (int i = 0; i < TuristaJel.ID_ARR.length; ++i)
		megLerakhatoJelek.addElement(new TuristaJel(TuristaJel.ID_ARR[i]));
}
protected ExSTCombo(Vector iMegLerakhatoJelek, Vector iAddedCards) {
	super();
	megLerakhatoJelek = iMegLerakhatoJelek;
	addedCards = iAddedCards;
}
public void doExSFinished(szr.mainserv.GameController jv) {
	if (getSzint() != 0) {
		OsSzin c;
		switch (getSzint()) {
			case 1:
				c = new JelzeslapSzin();
				break;
			case 2:
				c = new HalmozoSzinKeroSzin();
				break;
			case 3:
				c = new HalmozoJelzesKeroSzin_HU();
				break;
			default:
				c = null;
				break;
		}
		if (c != null) {
			jv.sendSysChatMsg(SubstituteStr.doIt(SubstituteStr.doIt(SubstituteStr.doIt(resChatTexts.getString("%p_%l.szintû_COMBO-t_rakot"), "%c", c.toString()), "%l", Integer.toString(getSzint())), "%p", jv.getActivePlayer().getProperties().name));//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
			szinCsere(c, jv.getActivePlayer());
		}
	}
}
public void doExSStep(szr.mainserv.GameController jv) {
	byte[] b = new byte[1];
	random.nextBytes(b);
	Card c = new Card(new AtlatszoSzin(), (b[0] >= 0) ? (OsJelzes)(new AtlatszoKerdojel()) : (OsJelzes)(new FelkialtoJel()));
	addedCards.addElement(c);
	Vector v = new Vector();
	v.addElement(c);
	jv.getActivePlayer().addCards(v);
}
protected String getInfoStr() {
	switch (getSzint()) {
		case 3:  return resInfoTexts.getString("Combo_Kész!__-)"); //$NON-NLS-1$
		case 0:  return SubstituteStr.doIt(resInfoTexts.getString("Combo_Elsõ_jel__%sav"), "%sav", new TuristaJel(TuristaJel.ID_ARR[0]).toString());//$NON-NLS-2$ //$NON-NLS-1$
		default: return SubstituteStr.doIt(resInfoTexts.getString("Combo_Lerakható__%jelek"), "%jelek", megLerakhatoJelek.toString());//$NON-NLS-2$ //$NON-NLS-1$
	}
}
protected int getLerakhatoJelIdx(Jelzes jel) {
	int i = 0;
	while (i < megLerakhatoJelek.size() && !((Jelzes)megLerakhatoJelek.elementAt(i)).megegyezik(jel)) {
		i++;
	}
	return i;
}
protected String getLevelStr() {
	return " "+ ((getSzint() > 0) ? "1 " : ". ") + //$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
				((getSzint() > 1) ? "2 " : ". ") +//$NON-NLS-2$//$NON-NLS-1$
				((getSzint() > 2) ? "3 " : ". ");//$NON-NLS-2$//$NON-NLS-1$
}
protected int getSzint() {
	int i = megLerakhatoJelek.size();
	if (i == 0)
		return 3;
	else if (i <= 3)
		return 2;
	else if (i == TuristaJel.ID_ARR.length)
		return 0;
	else 
		return 1;
}
public ExSType illeszt(szr.card.Szin aktC, szr.card.Jelzes aktJ, szr.card.Szin cardC, szr.card.Jelzes cardJ) {
	int idx = getLerakhatoJelIdx(cardJ);
	if (idx < megLerakhatoJelek.size() && !(getSzint() == 0 && !new TuristaJel(TuristaJel.ID_ARR[0]).megegyezik(cardJ))) {
		megLerakhatoJelek.removeElementAt(idx);
		return new ExSTCombo(megLerakhatoJelek, addedCards);
	} else
		return null;
}
public void szinCsere(OsSzin c, PlayerData ki) {
	int i = 0;
	Card card = ki.getCard(i);
	while (card != null) {
		if (!addedCards.contains(card)) {
			ki.replaceCardAt(new Card(c, card.getJelzes()), i);
		}
		i++;
		card = ki.getCard(i);
	}
}
public String toString() {
	return SubstituteStr.doIt(SubstituteStr.doIt(resInfoTexts.getString("COMBO!_<%lev>_%istr"), "%lev", getLevelStr()), "%istr", getInfoStr());//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
}
}
