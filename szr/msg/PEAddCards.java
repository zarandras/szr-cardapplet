package szr.msg;

import java.util.*;
/**
 * Kártyalapok megkapása eseményosztály
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
			// hiba esetén újraküldés?
		}
	}
}
public String toString() {
	return super.toString() + " cards: " + newCards.toString();//$NON-NLS-1$
}
}
