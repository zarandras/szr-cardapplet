package szr.shared;

import java.io.Serializable;
import szr.mainserv.*;
import szr.card.*;
/**
 * Extra sorozásokat leíró osztályok õse
 * Creation date: (2002.03.12. 6:28:02)
 */
public abstract class ExSType implements Serializable {
public ExSType() {
	super();
}
/**
 * Az extra sorozás befejezésekor hívódik
 */
public abstract void doExSFinished(GameController jv);
/**
 * Akkor hívódik, amikor egy kártyát beraknak az extra sorozásba
 */
public abstract void doExSStep(GameController jv);
/**
 * Illeszkedik-e egy adott kártya az extra sorozásba? (ha igen, akkor állapotot is vált az ExSType!)
 * Creation date: (2002.03.12. 6:31:47)
 * @return ExSType
 */
public abstract ExSType illeszt(Szin aktC, Jelzes aktJ, Szin cardC, Jelzes cardJ);
public abstract String toString();
}
