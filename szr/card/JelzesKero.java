package szr.card;

/**
 * Jelz�sk�r� interface (implement�lhatja sz�n v. jelz�s)
 * Creation date: (2002.02.09. 21:31:03)
 */
public interface JelzesKero {
/**
 * K�rt jelz�s elt�rol�sa
 */
JelzesKero applyKertJel(Jelzes kertJel);
Jelzes[] getKerhetoJelek();
Jelzes getKertJel();
/**
 * Megfelel-e az adott jelz�s a k�r�snek?
 */
boolean keresreIlleszk(Jelzes j);
}
