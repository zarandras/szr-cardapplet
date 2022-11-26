package szr.msg;

import java.util.*;
/**
 * Egy k�rtya elv�tele esem�nyoszt�ly
 * Creation date: (2002.02.27. 9:05:57)
 */
public class PERemoveCard extends PersonalEvent {
	private final int cardIdx;
public PERemoveCard(int iCardIdx) {
	super();
	cardIdx = iCardIdx;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	szr.maincli.CardsInHand cih = cmh.getParentController().getCardsInHand();
	cih.removeCard(cardIdx);
}
public String toString() {
	return super.toString() + " cardIndex: " + Integer.toString(cardIdx);//$NON-NLS-1$
}
}
