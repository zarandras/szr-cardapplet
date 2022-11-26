package szr.card;

/**
 * Színkérõ interface (implementálhatja szín v. jelzés)
 * Creation date: (2002.02.09. 21:31:03)
 */
public interface SzinKero {
/**
 * Kért szín eltárolása
 */
SzinKero applyKertSzin(SzinesSzin kertSzin);
SzinesSzin getKertSzin();
/**
 * Megfelel-e az adott szín a kérésnek?
 */
boolean keresreIlleszk(Szin c);
}
