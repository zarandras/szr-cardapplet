package szr.caut;

import szr.shared.*;
import szr.card.*;
/**
 * Automata lila extra sorozó állapot
 * Creation date: (2002.03.12. 23:00:49)
 */
public class LASLilaExS extends LAStateCJ {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final ExSType exS;
public LASLilaExS(Lila iC, ExSJel iJ, ExSType iExS) {
	super(iC, iJ);
	exS = iExS;
}
public int getActivePlayerState() {
	return szr.maincli.ClientController.PS_ACTIVE_LILA;
}
public ExSType getExS() {
	return exS;
}
public int getInactivePlayerState() {
	return szr.maincli.ClientController.PS_INACTIVE_VETOZHAT;
}
protected LAState nextStateBeszurCJ(int transPhase, int invokeCount) {
	szr.msg.GameAction ga = getStartedGA();
	switch (transPhase) {
		case 0:
			if (ga.getResolvedSzin() == null || ga.getResolvedJelzes() == null) {
				ga.doLAGERejectAction(resInfoTexts.getString("Így_nem_lehet_vétózni,_mer")); //$NON-NLS-1$
				return null;
			} else if (!(ga.getResolvedSzin() instanceof Lila) || !j.megegyezik(ga.getResolvedJelzes())) {
				ga.doLAGERejectAction(resInfoTexts.getString("A_lila_lapokat_vétózni_csa")); //$NON-NLS-1$
				return null;
			} else {
				ga.doLAGEAcceptAction();
				ga.doLAGEAcceptCard();
				ga.doLAGEJump();
				return this;	
			}
			
		case 1:
			if (!ga.doLAGELPreEventJ(invokeCount))
				pauseTrans();
			return this;
			
		case 2:
			ga.doLAGEVetoPossible();
			return new LASLilaExS((Lila)ga.getResolvedSzin(), (ExSJel)ga.getResolvedJelzes(), ((ExSJel)ga.getResolvedJelzes()).getNewExST(ga.getResolvedSzin()));
			
		default:
			return null;
	}
}
protected LAState nextStateLerakCJ(int transPhase, int invokeCount) {
	getStartedGA().doLAGERejectAction(resInfoTexts.getString("Mivel_lila_lapot_raktál,_n")); //$NON-NLS-1$
	return null;
}
protected LAState nextStatePassz(int transPhase, int invokeCount) {
	szr.msg.GameAction ga = getStartedGA();
	switch (transPhase) {
		case 0:
			ga.doLAGEAcceptAction();
			return this;
			
		case 1:
			if (!ga.doLAGELPostEventJ(j, invokeCount))
				pauseTrans();
			return this;
				
		case 2:
			if (!ga.doLAGEGeneralPost(c, j, invokeCount))
				pauseTrans();
			return this;

		case 3:
			return new LASExSAlap((Lila)c, (ExSJel)j, exS);

		default:
			return null;
	}
}
public String toString() {
	return "((LASLilaExS[" + c.toString() + ", " + j.toString() + ", " + exS.toString() + "]))";//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
}
