package szr.msg;

import java.util.*;
/**
 * �sszes k�rtya elv�tele esem�nyoszt�ly
 * Creation date: (2002.02.27. 9:05:57)
 */
public class PERemoveAllCards extends PersonalEvent {
public PERemoveAllCards() {
	super();
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	szr.maincli.CardsInHand cih = cmh.getParentController().getCardsInHand();
	cih.removeAllCards();
}
public String toString() {
	return super.toString();
}
}
