package szr.pakli;

import szr.card.*;
/**
 * Normál paklik õsosztálya
 * Creation date: (2002.02.10. 16:28:06)
 */
public abstract class NormalPakli extends Pakli {
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	
/**
 * Az ÉgSzínû lapok létrehozása és beillesztése
 */
protected abstract void createACards();
/**
 * A fekete színû lapok létrehozása és beillesztése
 */
protected abstract void createFCards();
/**
 * A kétszínû lapok létrehozása és beillesztése (az összes lehetséges kétszínû maszkra meghívódik)
 */
protected abstract void createKetszinuCards(int ketszinuMask);
/**
 * A lila lapok létrehozása és beillesztése
 */
protected abstract void createLCards();
/**
 * Az alapszínû lapok létrehozása és beillesztése (minden alapszínre hívódik)
 */
protected abstract void createSzinesCards(int szinMask);
public void fillPakli() {
	int mask = getSzinMask();
	for (int i = 0; mask != 0; mask >>= 1, ++i) {
		if ((mask & 1) == 1) {
			
			createSzinesCards(1 << i);
			
			// kétszínû lapok (csak az i-nél nagyobb indexû színekkel kombináljuk, 
			// hogy minden párosítás csak egyszer forduljon elõ):
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
	// ha a pakli kiürült, akkor hívódik meg, s ilyenkor hozzáveszünk egy új paklit:
	try {
		getParentController().sendSysChatMsg(resChatTexts.getString("Elfogytak_a_pakliból_a_lap")); //$NON-NLS-1$
	} catch (NullPointerException e) {}
	fillPakli();
	return popCard();
}
/**
 * Véletlenszerû byte -> pozíció
 */
protected int translateRandomByteToPos(byte b) {
	return (b + 128) % (getPushedCardCount() + 1);
}
}
