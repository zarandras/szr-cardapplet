package szr.maincli;

import java.awt.event.*;
/**
 * A kliens CardsInHand osztályának egéresemény-figyelõje
 * Creation date: (2002.02.13. 10:15:26)
 */
public class HandMouseAdapter extends MouseAdapter {
	private final CardsInHand parentCiH;
public HandMouseAdapter(CardsInHand iParentCiH) {
	parentCiH = iParentCiH;
}
public void mousePressed(MouseEvent e) {
	Object srcWidget = e.getSource();
	if (srcWidget instanceof szr.gui.CardComponent) {
		parentCiH.mouseClicked (e);
	}
}
}
