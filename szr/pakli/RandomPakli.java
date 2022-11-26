package szr.pakli;

import szr.card.*;
/**
 * Random (_R) 'paklik' õsosztálya
 * Creation date: (2002.02.10. 16:28:51)
 */
public abstract class RandomPakli extends Pakli {
public final void fillPakli() {
	// empty
}
protected final szr.card.Card nextCard() {
	return new szr.card.Card(nextCardSzin(), nextCardJelzes());
}
/**
 * Véletlenszerû szín
 */
protected abstract OsJelzes nextCardJelzes();
/**
 * Véletlenszerû jelzés
 */
protected abstract OsSzin nextCardSzin();
}
