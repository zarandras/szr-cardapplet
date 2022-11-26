package szr.caut;

import szr.card.*;
import szr.shared.*;
/**
 * Automata extra sorozó állapot
 * Creation date: (2002.03.12. 23:00:07)
 */
public class LASExS extends LAStateCJ {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final ExSType exS;
public LASExS(Szin iC, Jelzes iJ, ExSType iExS) {
	super(iC, iJ);
	exS = iExS;
}
public int getActivePlayerState() {
	return szr.maincli.ClientController.PS_ACTIVE_EXS_SOROZHAT;
}
public ExSType getExS() {
	return exS;
}
public int getInactivePlayerState() {
	return szr.maincli.ClientController.PS_INACTIVE;
}
protected LAState nextStateBeszurCJ(int transPhase, int invokeCount) {
	getStartedGA().doLAGERejectAction(resInfoTexts.getString("Extra_sorozási_lehetõség_v")); //$NON-NLS-1$
	return null;
}
protected LAState nextStateLerakCJ(int transPhase, int invokeCount) {
	szr.msg.GameAction ga = getStartedGA();
	if (transPhase == 0) {
		if (ga.getResolvedSzin() == null || ga.getResolvedJelzes() == null) {
			ga.doLAGERejectAction(resInfoTexts.getString("A_lerakásra_kiválasztott_k")); //$NON-NLS-1$
			return null;
		} else if (ga.getResolvedSzin() instanceof Lila) {
			ga.doLAGERejectAction(resInfoTexts.getString("A_lila_lapok_nem_nem_illes")); //$NON-NLS-1$
			return null;
		} else {
			/* exS szerinti illeszkedés: */
			ExSType newExS = exS.illeszt(c, j, ga.getResolvedSzin(), ga.getResolvedJelzes());
			if (newExS == null) {
				ga.doLAGERejectAction(resInfoTexts.getString("A_kártyát_nem_rakhatod_le,1")); //$NON-NLS-1$
				return null;
			} else {
				ga.doLAGEAcceptAction();
				ga.doLAGEAcceptCard();
				ga.doLAGEExSStep(newExS);
				return new LASExS(ga.getResolvedSzin(), ga.getResolvedJelzes(), newExS);
			}
		}
	} else
		return null;
}
protected LAState nextStatePassz(int transPhase, int invokeCount) {
	szr.msg.GameAction ga = getStartedGA();
	switch (transPhase) {
		case 0:
			ga.doLAGEAcceptAction();
			ga.doLAGEExSFinished(exS);
			return this;
			
		case 1:
			if (!ga.doLAGEPreEventJ(j, invokeCount))
				pauseTrans();
			return this;
				
		case 2:
			if (!ga.doLAGEPostEventJ(j, invokeCount))
				pauseTrans();
			return this;
				
		case 3:
			if (!ga.doLAGEGeneralPost(c, j, invokeCount))
				pauseTrans();
			return this;

		case 4:
			return newAlapLAState(c, j);

		default:
			return null;
	}
}
public String toString() {
	return "((" + ((j instanceof ExSJel) ? "LASExS" : ((j instanceof UtJel) ? "LASExSUtSorozo" : "LASExSSorozo")) + "[" + c.toString() + ", " + j.toString() + ", " + exS.toString() + "]))";//$NON-NLS-8$//$NON-NLS-7$//$NON-NLS-6$//$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
}
