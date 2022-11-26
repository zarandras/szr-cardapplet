package szr.caut;

import szr.card.*;
/**
 * Automata kezdõállapot
 * Creation date: (2002.02.14. 13:42:32)
 */
public class LASKezd extends LAState {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	
public LASKezd() {
	super();
}
public int getActivePlayerState() {
	return szr.maincli.ClientController.PS_ACTIVE_KEZDOLAP;
}
public int getInactivePlayerState() {
	return szr.maincli.ClientController.PS_INACTIVE;
}
public szr.card.Jelzes getJelzes() {
	return null;
}
public szr.card.Szin getSzin() {
	return null;
}
public boolean isAutoPasszState() {
	return false;
}
protected LAState nextStateBeszurCJ(int transPhase, int invokeCount) {
	getStartedGA().doLAGERejectAction(resInfoTexts.getString("Ilyenkor_még_nem_lehet_bes")); //$NON-NLS-1$
	return null;
}
protected LAState nextStateKezdolapCJ(int transPhase, int invokeCount) {
	szr.msg.GameAction ga = getStartedGA();
	switch (transPhase) {
		case 0:
			if (ga.getResolvedJelzes() == null) {
					ga.doLAGERejectAction(resInfoTexts.getString("Nem_jó_a_kihúzott_kezdõlap")); //$NON-NLS-1$
					return null;
			} else if (ga.getResolvedSzin() == null || !(ga.getResolvedSzin() instanceof szr.card.SzinesSzin)) {
					ga.doLAGERejectAction(resInfoTexts.getString("Nem_jó_a_kihúzott_kezdõlap1")); //$NON-NLS-1$
					return null;
			} else {
					ga.doLAGEAcceptAction();
					ga.doLAGEAcceptKezdolap();
					return this;
			}
				
		case 1:
			if (!ga.doLAGEStartEventJ(invokeCount))
				pauseTrans();
			return this;

		case 2:
			if (!ga.doLAGEGeneralPost(ga.getResolvedSzin(), ga.getResolvedJelzes(), invokeCount))
				pauseTrans();
			return this;

		case 3:
			Szin cardC = ga.getResolvedSzin();
			Jelzes cardJ = ga.getResolvedJelzes();
			if (cardJ instanceof ExSJel) {
				return new LASExSAlap(cardC, (ExSJel)cardJ, ((ExSJel)cardJ).getNewExST(cardC));
			} else {
				return new LASAlap(cardC, cardJ);
			}

		default:
			return null;
	}
}
protected LAState nextStateLerakCJ(int transPhase, int invokeCount) {
	getStartedGA().doLAGERejectAction(resInfoTexts.getString("Ilyenkor_még_nem_lehet_kár")); //$NON-NLS-1$
	return null;
}
protected LAState nextStatePassz(int transPhase, int invokeCount) {
	getStartedGA().doLAGERejectAction(resInfoTexts.getString("Ilyenkor_nem_lehet_passzol")); //$NON-NLS-1$
	return null;
}
public String toString() {
	return "((LASKezd))";//$NON-NLS-1$
}
}
