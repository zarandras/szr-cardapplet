package szr.card;

/**
 * Jelzéskérõ interface (implementálhatja szín v. jelzés)
 * Creation date: (2002.02.09. 21:31:03)
 */
public interface JelzesKero {
/**
 * Kért jelzés eltárolása
 */
JelzesKero applyKertJel(Jelzes kertJel);
Jelzes[] getKerhetoJelek();
Jelzes getKertJel();
/**
 * Megfelel-e az adott jelzés a kérésnek?
 */
boolean keresreIlleszk(Jelzes j);
}
