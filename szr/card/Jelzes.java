package szr.card;

import szr.shared.*;
/**
 * A jelzések õsosztálya
 * Creation date: (2002.02.09. 22:54:23)
 */
public abstract class Jelzes extends OsJelzes {
/**
 * Mi történjen ilyen jelzésû lila lap lerakásakor a befejezéskor?
 */
public boolean doLPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return true;
}
/**
 * Mi történjen ilyen jelzésû lila lap lerakása után közbetlenül?
 */
public boolean doLPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return true;
}
/**
 * Mi történjen ilyen jelzésû nem lila lap lerakásakor a befejezéskor?
 */
public boolean doPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return true;
}
/**
 * Mi történjen ilyen jelzésû nem lila lap lerakása után közbetlenül?
 */
public boolean doPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return true;
}
/**
 * Mi történjen, ha ilyen jelzésû a kezdõlap?
 */
public boolean doStartEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return true;
}
/**
 * Végtelen hasonulási lánc esetén az aktuális jelzést kell adni:
 */
public static Jelzes getLimJelzes(Jelzes aktJelzes) {
	return aktJelzes;
}
protected final Jelzes getResolvedJelzes0(TableStateRec ts) {
	return this;	// nem metajelzés
}
/**
 * Az adott színû és jelzésû kártya illeszkedik-e rá, ha a mySzin a színe?
 */
public final boolean illeszkedikRa(Szin resolvedSzin, Jelzes resolvedJelzes, Szin mySzin) {
	if (resolvedSzin == null || resolvedJelzes == null || mySzin == null)
		return false;

	// ha jelzésben egyenlõk, mindenképpen illeszkedik:
	if (megegyezik(resolvedJelzes))
		return true;

	// színbeli illeszkedés:
	boolean szinbenIlleszk = mySzin.illeszkedikRa (resolvedSzin);

	// ha ász és színben illeszkedik, akkor illeszkedik:
	if (szinbenIlleszk && resolvedJelzes instanceof AszJel)
		return true;

	// ha a mySzin valamilyen kérõlap, akkor _kell_ ahhoz illeszkedni:
	if (mySzin instanceof JelzesKero) {
		// ha a kért jelzésû, akkor az színbeli illeszkedésnek számít ilyenkor:
		szinbenIlleszk = szinbenIlleszk || ((JelzesKero)mySzin).keresreIlleszk(resolvedJelzes);
		if (!szinbenIlleszk)
			return false;
	} else if (mySzin instanceof SzinKero) {
		// ha a kért színû, akkor az szín(kérés)beli illeszkedésnek számít ilyenkor:
		szinbenIlleszk = szinbenIlleszk || ((SzinKero)mySzin).keresreIlleszk(resolvedSzin);
		if (!szinbenIlleszk)
			return false;
	}

	// ha a this valamilyen kérõlap, akkor _kell_ ahhoz illeszledni:
	boolean jelzesbenIlleszk = false;
	if (this instanceof JelzesKero) {
		// ha a kért jelzésû, akkor az jelzés(kérés)beli illeszkedésnek számít ilyenkor:
		jelzesbenIlleszk = jelzesbenIlleszk || ((JelzesKero)this).keresreIlleszk(resolvedJelzes);
		// ha nem illeszkedik a kérésre és nem jelzéskérõ, akkor nem jó:
		if (!jelzesbenIlleszk && !(resolvedSzin instanceof JelzesKero || resolvedJelzes instanceof JelzesKero))
			return false;
	} else if (this instanceof SzinKero) {
		// ha a kért színû, akkor az jelzés(kérés)beli illeszkedésnek számít ilyenkor:
		jelzesbenIlleszk = jelzesbenIlleszk || ((SzinKero)this).keresreIlleszk(resolvedSzin);
		// ha nem illeszkedik a kérésre és nem színkérõ, akkor nem jó:
		if (!jelzesbenIlleszk && !(resolvedSzin instanceof SzinKero || resolvedJelzes instanceof SzinKero))
			return false;
	}

	// végül pedig, vagy színben vagy jelzés(kérés)ben kell illeszkedjen:
	return (szinbenIlleszk || jelzesbenIlleszk);
}
public final void logInvalidInvokeCounter(int invokeCounter) {
	szr.msg.Logger.log(new szr.msg.ErrStrLog(SubstituteStr.doIt(SubstituteStr.doIt("TransState error at %j: invalid invokeCounter: %ic", "%ic", Integer.toString(invokeCounter)), "%j", toString())), null, null);//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
public final void logInvalidJelzesParam(String errMsg) {
	szr.msg.Logger.log(new szr.msg.ErrStrLog(SubstituteStr.doIt("Card error at %jobj: ", "%jobj", ((Object)this).toString()) + errMsg), null, null);//$NON-NLS-2$//$NON-NLS-1$
}
/**
 * Két kártyajelzés egyenlõségvizsgálata (kártyailleszkedéshez)
 */
public boolean megegyezik(Jelzes j) {
	try {
		return getID() == j.getID();	// csak a jelzést vizsgáljuk, egyebeket (pl. kért szín) nem
	} catch (NullPointerException e) {
		return false;
	}
}
/**
 * A jelzés extra paramétereinek (pl. kért szín) törlése / újrainicializálása
 *	(új jelzés objektumot generáljon ha módosítani kell)
 */
public Jelzes reset() {
	return this;
}
}
