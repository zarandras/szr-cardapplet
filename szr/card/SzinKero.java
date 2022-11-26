package szr.card;

/**
 * Sz�nk�r� interface (implement�lhatja sz�n v. jelz�s)
 * Creation date: (2002.02.09. 21:31:03)
 */
public interface SzinKero {
/**
 * K�rt sz�n elt�rol�sa
 */
SzinKero applyKertSzin(SzinesSzin kertSzin);
SzinesSzin getKertSzin();
/**
 * Megfelel-e az adott sz�n a k�r�snek?
 */
boolean keresreIlleszk(Szin c);
}
