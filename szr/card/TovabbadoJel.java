package szr.card;

import java.util.Vector;
import szr.mainserv.*;
import szr.shared.*;
/**
 * Továbbadó jelzésosztály
 * Creation date: (2002.03.06. 21:53:29)
 */
public class TovabbadoJel extends AzHatJel {
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	
public boolean doLPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		szr.shared.Irany dir = jv.getKertIrany();
		if (dir == null) {
			dir = Irany.Lefele;
		} else {
			dir = dir.visszafele();
		}
		PlayerData pd = jv.getActivePlayer();
		int prevIdx = jv.getNextPlayerIdx(jv.indexOfActivePlayer(), dir);
		// elvesszük az aktuális játékos lapjait:
		Vector v = pd.captureAllCards();
		do {
			// a pd megkapja az elõzõtõl a kártyáit:
			pd.addCards(jv.getPlayer(prevIdx).captureAllCards());
			pd = jv.getPlayer(prevIdx);
			prevIdx = jv.getNextPlayerIdx(prevIdx, dir);
		} while (prevIdx != jv.indexOfActivePlayer());
		// a köv.játékos kapja v régi kártyáit
		pd.addCards(v);
		jv.updateAllCardCount();
		jv.sendSysChatMsg(szr.shared.SubstituteStr.doIt(szr.shared.SubstituteStr.doIt(resChatTexts.getString("%p1_továbbadta_a_lapjait_%"), "%p2", pd.getProperties().name), "%p1", jv.getActivePlayer().getProperties().name));//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
	} else {
		logInvalidInvokeCounter(invokeCounter);
	}
	return true;
}
public boolean doLPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		if (jv.getPlayerCount() > 2) {
			jv.forceIranyKeres();
			return false;
		} else
			return true;
	} else {
		if (invokeCounter > 1)
			logInvalidInvokeCounter(invokeCounter);
		return true;
	}
}
public boolean doPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		boolean nincsKeres = doLPreEvent(jv, 0);
		if (nincsKeres)
			return doLPostEvent(jv, invokeCounter);
		else
			return false;
	} else
		return doLPostEvent(jv, invokeCounter - 1);
}
public int getID() {
	return 24;
}
public boolean hasAImg() {
	return true;
}
public boolean hasLImg() {
	return false;
}
public String toString() {
	return resCardTexts.getString("Továbbadó"); //$NON-NLS-1$
}
}
