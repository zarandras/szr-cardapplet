package szr.caut;

import szr.card.*;
/**
 * Automata sorozó állapot
 * Creation date: (2002.03.03. 20:32:53)
 */
public class LASSorozo extends LAStateCJ {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	
public LASSorozo(Szin iC, Jelzes iJ) {
	super(iC, iJ);
}
public int getActivePlayerState() {
	return szr.maincli.ClientController.PS_ACTIVE_SOROZHAT;
}
public int getInactivePlayerState() {
	return szr.maincli.ClientController.PS_INACTIVE;
}
protected LAState nextStateBeszurCJ(int transPhase, int invokeCount) {
	getStartedGA().doLAGERejectAction(resInfoTexts.getString("Most_nem_lehet_beszúrni,_n")); //$NON-NLS-1$
	return null;
}
protected LAState nextStateLerakCJ(int transPhase, int invokeCount) {
	szr.msg.GameAction ga = getStartedGA();
	switch (transPhase) {
		case 0:
			if (ga.getResolvedSzin() == null || ga.getResolvedJelzes() == null) {
				ga.doLAGERejectAction(resInfoTexts.getString("A_sorozásra_kiválasztott_k")); //$NON-NLS-1$
				return null;
			} else if (!j.megegyezik(ga.getResolvedJelzes())) {
				ga.doLAGERejectAction(resInfoTexts.getString("Sorozni_csak_azonos_jelzés")); //$NON-NLS-1$
				return null;
			} else if (ga.getResolvedJelzes() instanceof NemSorozhatoJel) {
				ga.doLAGERejectAction(resInfoTexts.getString("Az_ilyen_jelzésû_lapokat_n")); //$NON-NLS-1$
				return null;
			} else if (ga.getResolvedSzin() instanceof Lila) {
				ga.doLAGERejectAction(resInfoTexts.getString("A_lila_lapokat_nem_lehet_s")); //$NON-NLS-1$
				return null;
			} else {
				ga.doLAGEAcceptAction();
				ga.doLAGEAcceptCard();
				return this;
			}
				
		case 1:
			if (!ga.doLAGEPreEventJ(invokeCount))
				pauseTrans();
			return this;

		case 2:
			return new LASSorozo(ga.getResolvedSzin(), ga.getResolvedJelzes());

		default:
			return null;
	}
}
protected LAState nextStatePassz(int transPhase, int invokeCount) {
	szr.msg.GameAction ga = getStartedGA();
	switch (transPhase) {
		case 0:
			ga.doLAGEAcceptAction();
			return this;
			
		case 1:
			if (!ga.doLAGEPostEventJ(j, invokeCount))
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
	return "((LASSorozo[" + c.toString() + ", " + j.toString() + "]))";;//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
}
