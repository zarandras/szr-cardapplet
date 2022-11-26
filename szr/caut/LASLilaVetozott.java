package szr.caut;

import szr.card.*;
/**
 * Automata lila vétózott állapot
 * Creation date: (2002.03.13. 0:02:14)
 */
public class LASLilaVetozott extends LAStateCJ {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	
public LASLilaVetozott(Lila iC, Jelzes iJ) {
	super(iC, iJ);
}
public int getActivePlayerState() {
	return szr.maincli.ClientController.PS_ACTIVE_LILA;
}
public int getInactivePlayerState() {
	return szr.maincli.ClientController.PS_INACTIVE;
}
protected LAState nextStateBeszurCJ(int transPhase, int invokeCount) {
	getStartedGA().doLAGERejectAction(resInfoTexts.getString("Az_ilyen_jelzésû_vétózott_")); //$NON-NLS-1$
	return null;
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
			
			// no ga.doLAGELPostEventJ
			
		case 1:
			if (!ga.doLAGEGeneralPost(c, j, invokeCount))
				pauseTrans();
			return this;

		case 2:
			return new LASAlap(c, j);

		default:
			return null;
	}
}
public String toString() {
	return "((LASLilaVetozott[" + c.toString() + ", " + j.toString() + "]))";//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
}
