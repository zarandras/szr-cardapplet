package szr.caut;

import szr.card.*;
/**
 * Automata lila �llapot
 * Creation date: (2002.03.12. 23:45:21)
 */
public class LASLila extends LAStateCJ {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	
public LASLila(Lila iC, Jelzes iJ) {
	super(iC, iJ);
}
public int getActivePlayerState() {
	return szr.maincli.ClientController.PS_ACTIVE_LILA;
}
public int getInactivePlayerState() {
	return szr.maincli.ClientController.PS_INACTIVE_VETOZHAT;
}
protected LAState nextStateBeszurCJ(int transPhase, int invokeCount) {
	szr.msg.GameAction ga = getStartedGA();
	switch (transPhase) {
		case 0:
			if (ga.getResolvedSzin() == null || ga.getResolvedJelzes() == null) {
				ga.doLAGERejectAction(resInfoTexts.getString("�gy_nem_lehet_v�t�zni,_mer")); //$NON-NLS-1$
				return null;
			} else if (!(ga.getResolvedSzin() instanceof Lila) || !j.megegyezik(ga.getResolvedJelzes())) {
				ga.doLAGERejectAction(resInfoTexts.getString("A_lila_lapokat_v�t�zni_csa")); //$NON-NLS-1$
				return null;
			} else {
				ga.doLAGEAcceptAction();
				ga.doLAGEAcceptCard();
				ga.doLAGEJump();
				if (ga.getResolvedJelzes() instanceof SingleVetoJel) {
					return new LASLilaVetozott ((Lila)ga.getResolvedSzin(), ga.getResolvedJelzes());
				} else {
					return this;	
				}
			}
			
		case 1:
			if (!ga.doLAGELPreEventJ(invokeCount))
				pauseTrans();
			return this;
			
		case 2:
			ga.doLAGEVetoPossible();
			return new LASLila((Lila)ga.getResolvedSzin(), ga.getResolvedJelzes());
			
		default:
			return null;
	}
}
protected LAState nextStateLerakCJ(int transPhase, int invokeCount) {
	getStartedGA().doLAGERejectAction(resInfoTexts.getString("Mivel_lila_lapot_rakt�l,_n")); //$NON-NLS-1$
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
			return new LASAlap(c, j);

		default:
			return null;
	}
}
public String toString() {
	return "((LASLila[" + c.toString() + ", " + j.toString() + "]))";//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
}
