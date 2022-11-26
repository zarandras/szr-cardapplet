package szr.card;

import szr.shared.*;
/**
 * A jelz�sek �soszt�lya
 * Creation date: (2002.02.09. 22:54:23)
 */
public abstract class Jelzes extends OsJelzes {
/**
 * Mi t�rt�njen ilyen jelz�s� lila lap lerak�sakor a befejez�skor?
 */
public boolean doLPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return true;
}
/**
 * Mi t�rt�njen ilyen jelz�s� lila lap lerak�sa ut�n k�zbetlen�l?
 */
public boolean doLPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return true;
}
/**
 * Mi t�rt�njen ilyen jelz�s� nem lila lap lerak�sakor a befejez�skor?
 */
public boolean doPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return true;
}
/**
 * Mi t�rt�njen ilyen jelz�s� nem lila lap lerak�sa ut�n k�zbetlen�l?
 */
public boolean doPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return true;
}
/**
 * Mi t�rt�njen, ha ilyen jelz�s� a kezd�lap?
 */
public boolean doStartEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return true;
}
/**
 * V�gtelen hasonul�si l�nc eset�n az aktu�lis jelz�st kell adni:
 */
public static Jelzes getLimJelzes(Jelzes aktJelzes) {
	return aktJelzes;
}
protected final Jelzes getResolvedJelzes0(TableStateRec ts) {
	return this;	// nem metajelz�s
}
/**
 * Az adott sz�n� �s jelz�s� k�rtya illeszkedik-e r�, ha a mySzin a sz�ne?
 */
public final boolean illeszkedikRa(Szin resolvedSzin, Jelzes resolvedJelzes, Szin mySzin) {
	if (resolvedSzin == null || resolvedJelzes == null || mySzin == null)
		return false;

	// ha jelz�sben egyenl�k, mindenk�ppen illeszkedik:
	if (megegyezik(resolvedJelzes))
		return true;

	// sz�nbeli illeszked�s:
	boolean szinbenIlleszk = mySzin.illeszkedikRa (resolvedSzin);

	// ha �sz �s sz�nben illeszkedik, akkor illeszkedik:
	if (szinbenIlleszk && resolvedJelzes instanceof AszJel)
		return true;

	// ha a mySzin valamilyen k�r�lap, akkor _kell_ ahhoz illeszkedni:
	if (mySzin instanceof JelzesKero) {
		// ha a k�rt jelz�s�, akkor az sz�nbeli illeszked�snek sz�m�t ilyenkor:
		szinbenIlleszk = szinbenIlleszk || ((JelzesKero)mySzin).keresreIlleszk(resolvedJelzes);
		if (!szinbenIlleszk)
			return false;
	} else if (mySzin instanceof SzinKero) {
		// ha a k�rt sz�n�, akkor az sz�n(k�r�s)beli illeszked�snek sz�m�t ilyenkor:
		szinbenIlleszk = szinbenIlleszk || ((SzinKero)mySzin).keresreIlleszk(resolvedSzin);
		if (!szinbenIlleszk)
			return false;
	}

	// ha a this valamilyen k�r�lap, akkor _kell_ ahhoz illeszledni:
	boolean jelzesbenIlleszk = false;
	if (this instanceof JelzesKero) {
		// ha a k�rt jelz�s�, akkor az jelz�s(k�r�s)beli illeszked�snek sz�m�t ilyenkor:
		jelzesbenIlleszk = jelzesbenIlleszk || ((JelzesKero)this).keresreIlleszk(resolvedJelzes);
		// ha nem illeszkedik a k�r�sre �s nem jelz�sk�r�, akkor nem j�:
		if (!jelzesbenIlleszk && !(resolvedSzin instanceof JelzesKero || resolvedJelzes instanceof JelzesKero))
			return false;
	} else if (this instanceof SzinKero) {
		// ha a k�rt sz�n�, akkor az jelz�s(k�r�s)beli illeszked�snek sz�m�t ilyenkor:
		jelzesbenIlleszk = jelzesbenIlleszk || ((SzinKero)this).keresreIlleszk(resolvedSzin);
		// ha nem illeszkedik a k�r�sre �s nem sz�nk�r�, akkor nem j�:
		if (!jelzesbenIlleszk && !(resolvedSzin instanceof SzinKero || resolvedJelzes instanceof SzinKero))
			return false;
	}

	// v�g�l pedig, vagy sz�nben vagy jelz�s(k�r�s)ben kell illeszkedjen:
	return (szinbenIlleszk || jelzesbenIlleszk);
}
public final void logInvalidInvokeCounter(int invokeCounter) {
	szr.msg.Logger.log(new szr.msg.ErrStrLog(SubstituteStr.doIt(SubstituteStr.doIt("TransState error at %j: invalid invokeCounter: %ic", "%ic", Integer.toString(invokeCounter)), "%j", toString())), null, null);//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
public final void logInvalidJelzesParam(String errMsg) {
	szr.msg.Logger.log(new szr.msg.ErrStrLog(SubstituteStr.doIt("Card error at %jobj: ", "%jobj", ((Object)this).toString()) + errMsg), null, null);//$NON-NLS-2$//$NON-NLS-1$
}
/**
 * K�t k�rtyajelz�s egyenl�s�gvizsg�lata (k�rtyailleszked�shez)
 */
public boolean megegyezik(Jelzes j) {
	try {
		return getID() == j.getID();	// csak a jelz�st vizsg�ljuk, egyebeket (pl. k�rt sz�n) nem
	} catch (NullPointerException e) {
		return false;
	}
}
/**
 * A jelz�s extra param�tereinek (pl. k�rt sz�n) t�rl�se / �jrainicializ�l�sa
 *	(�j jelz�s objektumot gener�ljon ha m�dos�tani kell)
 */
public Jelzes reset() {
	return this;
}
}
