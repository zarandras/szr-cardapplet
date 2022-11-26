package szr.caut;

import szr.card.*;
import szr.shared.*;
/**
 * Automata extrasorozó alapállapot
 * Creation date: (2002.03.12. 23:00:07)
 */
public class LASExSAlap extends LAStateCJ {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final ExSType exS;
public LASExSAlap(Szin iC, ExSJel iJ, ExSType iExS) {
	super(iC, iJ);
	exS = iExS;
}
public int getActivePlayerState() {
	return szr.maincli.ClientController.PS_ACTIVE_EXS;
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
	switch (transPhase) {
		case 0:
			if (ga.getResolvedSzin() == null || ga.getResolvedJelzes() == null) {
				ga.doLAGERejectAction(resInfoTexts.getString("A_lerakásra_kiválasztott_k")); //$NON-NLS-1$
				return null;
			} else {
				ExSType newExS = exS.illeszt(c, j, ga.getResolvedSzin(), ga.getResolvedJelzes());
				if (!(ga.getResolvedSzin() instanceof Lila) && newExS != null
					   && (!(ga.getResolvedJelzes() instanceof ExSJel) || ((ExSJel)ga.getResolvedJelzes()).getExSPriority() < ((ExSJel)j).getExSPriority())) {
					/* exS szerinti illeszkedés: */
					ga.doLAGEAcceptAction();
					ga.doLAGEAcceptCard();
					ga.doLAGEExSStep(newExS);
					return new LASExS(ga.getResolvedSzin(), ga.getResolvedJelzes(), newExS);
				} else if (!j.illeszkedikRa(ga.getResolvedSzin(), ga.getResolvedJelzes(), c)) {
					ga.doLAGERejectAction(resInfoTexts.getString("A_kártyát_nem_rakhatod_le,2")); //$NON-NLS-1$
					return null;
				} else {
					ga.doLAGEAcceptAction();
					ga.doLAGEAcceptCard();
					return this;
				}
			}
		case 1:
			if (ga.getResolvedSzin() instanceof Lila) {
				if (!ga.doLAGELPreEventJ(invokeCount))
					pauseTrans();
			} else {
				if (!ga.doLAGEPreEventJ(invokeCount))
					pauseTrans();
			}
			return this;

		case 2:
			Jelzes cardJ = ga.getResolvedJelzes();
			Szin cardC = ga.getResolvedSzin();
			if (cardC instanceof Lila) {
				ga.doLAGEVetoPossible();
				return newLilaLAState((Lila)cardC, cardJ);
			} else {
				return newSorozoLAState(cardC, cardJ);
			}
			
		default:
			return null;
	}
}
protected LAState nextStateNyer(int transPhase, int invokeCount) {
	getStartedGA().doLAGENyert();
	return new LASNyert();
}
protected LAState nextStatePassz(int transPhase, int invokeCount) {
	szr.msg.GameAction ga = getStartedGA();
	switch (transPhase) {
		case 0:
			ga.doLAGEAcceptAction();
			ga.doLAGEHuzas();
			return this;

		case 1:
			if (!ga.doLAGEGeneralPost(c, j, invokeCount))
				pauseTrans();
			return this;

		case 2:
			return new LASExSAlap(c, (ExSJel)j, exS);
			
		default:
			return null;
	}
}
public String toString() {
	return "((LASExSAlap[" + c.toString() + ", " + j.toString() + ", " + exS.toString() + "]))";//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
}
