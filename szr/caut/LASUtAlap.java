package szr.caut;

import szr.card.*;
/**
 * Automata utasító alapállapot
 * Creation date: (2002.03.03. 16:13:21)
 */
public class LASUtAlap extends LAStateCJ {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	
public LASUtAlap(Szin iC, Jelzes iJ) {
	super(iC, iJ);
}
public int getActivePlayerState() {
	return szr.maincli.ClientController.PS_ACTIVE_UT;
}
public int getInactivePlayerState() {
	return szr.maincli.ClientController.PS_INACTIVE;
}
public boolean isUtRestricted() {
	return true;
}
protected LAState nextStateBeszurCJ(int transPhase, int invokeCount) {
	getStartedGA().doLAGERejectAction(resInfoTexts.getString("Kötelezõ_húzás_van_érvényb")); //$NON-NLS-1$
	return null;
}
protected LAState nextStateLerakCJ(int transPhase, int invokeCount) {
	szr.msg.GameAction ga = getStartedGA();
	switch (transPhase) {
		case 0:
			if (ga.getResolvedSzin() == null || ga.getResolvedJelzes() == null) {
				ga.doLAGERejectAction(resInfoTexts.getString("A_lerakásra_kiválasztott_k")); //$NON-NLS-1$
				return null;
			} else if (!((ga.getResolvedJelzes() instanceof UtHalmozo || ga.getResolvedSzin() instanceof UtHalmozo) &&
					j.illeszkedikRa(ga.getResolvedSzin(), ga.getResolvedJelzes(), c))) {
				ga.doLAGERejectAction(resInfoTexts.getString("Most_csak_kötelezõ_húzást_")); //$NON-NLS-1$
				return null;
			} else {
				ga.doLAGEAcceptAction();
				ga.doLAGEAcceptCard();
				if (ga.getResolvedJelzes() instanceof ExSJel) {
					ga.doLAGECancelUt();
				}
				return this;
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
				if (cardJ instanceof ExSJel) {
					return new LASLilaExS((Lila)cardC, (ExSJel)cardJ, ((ExSJel)cardJ).getNewExST(cardC));
				} else {
					return new LASLilaUt((Lila)cardC, cardJ);
				}
			} else {
				if (cardJ instanceof ExSJel) {
					return new LASExS(cardC, (ExSJel)cardJ, ((ExSJel)cardJ).getNewExST(cardC));
				} else {
					return new LASUtSorozo(cardC, cardJ);
				}
			}
			
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
			if (!ga.doLAGEExecUt())
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
	return "((LASUtAlap[" + c.toString() + ", " + j.toString() + "]))";//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
}
