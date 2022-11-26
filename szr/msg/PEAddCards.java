package szr.msg;

import java.util.*;
/**
 * K�rtyalapok megkap�sa esem�nyoszt�ly
 * Creation date: (2002.02.27. 9:05:57)
 */
public class PEAddCards extends PersonalEvent {
	private final Vector newCards;
public PEAddCards(Vector iNewCards) {
	super();
	newCards = iNewCards;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	szr.maincli.CardsInHand cih = cmh.getParentController().getCardsInHand();
	for (int i = 0; i < newCards.size(); ++i) {
		try {
			cih.addCard((szr.card.Card)newCards.elementAt(i));
		} catch (ClassCastException e) {
			// hiba eset�n �jrak�ld�s?
		}
	}
}
public String toString() {
	return super.toString() + " cards: " + newCards.toString();//$NON-NLS-1$
}
}
