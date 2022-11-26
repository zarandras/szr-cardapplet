package szr.msg;

import java.util.*;
import szr.card.*;
/**
 * Kártya lecsrélése eseményosztály
 * Creation date: (2002.02.27. 9:05:57)
 */
public class PEReplaceCard extends PersonalEvent {
	private final int cardIdx;
	private final Card newCard;
public PEReplaceCard(Card iNewCard, int iCardIdx) {
	super();
	newCard = iNewCard;
	cardIdx = iCardIdx;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	szr.maincli.CardsInHand cih = cmh.getParentController().getCardsInHand();
	cih.replaceCard(newCard, cardIdx);
}
public String toString() {
	return super.toString() + " newCard: " + newCard.toString() + ", cardIndex: " + Integer.toString(cardIdx);//$NON-NLS-2$//$NON-NLS-1$
}
}
