package szr.shared;

import java.io.Serializable;
import szr.mainserv.*;
import szr.card.*;
/**
 * Extra soroz�sokat le�r� oszt�lyok �se
 * Creation date: (2002.03.12. 6:28:02)
 */
public abstract class ExSType implements Serializable {
public ExSType() {
	super();
}
/**
 * Az extra soroz�s befejez�sekor h�v�dik
 */
public abstract void doExSFinished(GameController jv);
/**
 * Akkor h�v�dik, amikor egy k�rty�t beraknak az extra soroz�sba
 */
public abstract void doExSStep(GameController jv);
/**
 * Illeszkedik-e egy adott k�rtya az extra soroz�sba? (ha igen, akkor �llapotot is v�lt az ExSType!)
 * Creation date: (2002.03.12. 6:31:47)
 * @return ExSType
 */
public abstract ExSType illeszt(Szin aktC, Jelzes aktJ, Szin cardC, Jelzes cardJ);
public abstract String toString();
}
