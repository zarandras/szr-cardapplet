package szr.pakli;

import szr.card.*;
/**
 * Norm�l paklik �soszt�lya
 * Creation date: (2002.02.10. 16:28:06)
 */
public abstract class NormalPakli extends Pakli {
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	
/**
 * Az �gSz�n� lapok l�trehoz�sa �s beilleszt�se
 */
protected abstract void createACards();
/**
 * A fekete sz�n� lapok l�trehoz�sa �s beilleszt�se
 */
protected abstract void createFCards();
/**
 * A k�tsz�n� lapok l�trehoz�sa �s beilleszt�se (az �sszes lehets�ges k�tsz�n� maszkra megh�v�dik)
 */
protected abstract void createKetszinuCards(int ketszinuMask);
/**
 * A lila lapok l�trehoz�sa �s beilleszt�se
 */
protected abstract void createLCards();
/**
 * Az alapsz�n� lapok l�trehoz�sa �s beilleszt�se (minden alapsz�nre h�v�dik)
 */
protected abstract void createSzinesCards(int szinMask);
public void fillPakli() {
	int mask = getSzinMask();
	for (int i = 0; mask != 0; mask >>= 1, ++i) {
		if ((mask & 1) == 1) {
			
			createSzinesCards(1 << i);
			
			// k�tsz�n� lapok (csak az i-n�l nagyobb index� sz�nekkel kombin�ljuk, 
			// hogy minden p�ros�t�s csak egyszer forduljon el�):
			for (int mask2 = mask >> 1, j = i + 1; mask2 != 0; mask2 >>= 1, ++j) {
				if ((mask2 & 1) == 1)
					createKetszinuCards((1 << i) | (1 << j));
			}
		}
	}
	createFCards();
	createLCards();
	createACards();
}
protected final szr.card.Card nextCard() {
	// ha a pakli ki�r�lt, akkor h�v�dik meg, s ilyenkor hozz�vesz�nk egy �j paklit:
	try {
		getParentController().sendSysChatMsg(resChatTexts.getString("Elfogytak_a_paklib�l_a_lap")); //$NON-NLS-1$
	} catch (NullPointerException e) {}
	fillPakli();
	return popCard();
}
/**
 * V�letlenszer� byte -> poz�ci�
 */
protected int translateRandomByteToPos(byte b) {
	return (b + 128) % (getPushedCardCount() + 1);
}
}
