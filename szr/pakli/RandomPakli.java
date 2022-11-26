package szr.pakli;

import szr.card.*;
/**
 * Random (_R) 'paklik' �soszt�lya
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
 * V�letlenszer� sz�n
 */
protected abstract OsJelzes nextCardJelzes();
/**
 * V�letlenszer� jelz�s
 */
protected abstract OsSzin nextCardSzin();
}
