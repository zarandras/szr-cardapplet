package szr.caut;

import szr.card.*;
/**
 * Automata alapállapot
 * Creation date: (2002.03.03. 16:13:21)
 */
public class LASAlap extends LAStateCJ {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	
public LASAlap(Szin iC, Jelzes iJ) {
	super(iC, iJ);
}
public int getActivePlayerState() {
	return szr.maincli.ClientController.PS_ACTIVE;
}
public int getInactivePlayerState() {
	return szr.maincli.ClientController.PS_INACTIVE_BESZURHAT;
}
protected LAState nextStateBeszurCJ(int transPhase, int invokeCount) {
	szr.msg.GameAction ga = getStartedGA();
	switch (transPhase) {
		case 0:
			if (ga.getResolvedSzin() == null || ga.getResolvedJelzes() == null) {
				ga.doLAGERejectAction(resInfoTexts.getString("A_beszúrásra_kiválasztott_")); //$NON-NLS-1$
				return null;
			} else if (j instanceof TuristaJel && c instanceof SzinesSzin &&
					   j.megegyezik(ga.getResolvedJelzes()) &&
					   c.megegyezik(ga.getResolvedSzin())) {
				ga.doLAGEAcceptAction();
				ga.doLAGEAcceptCard();
				ga.doLAGEJump();
				return this;
			} else {
				ga.doLAGERejectAction(resInfoTexts.getString("Beszúrni_csak_olyan,_színe")); //$NON-NLS-1$
				return null;
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
protected LAState nextStateLerakCJ(int transPhase, int invokeCount) {
	szr.msg.GameAction ga = getStartedGA();
	switch (transPhase) {
		case 0:
			if (ga.getResolvedSzin() == null || ga.getResolvedJelzes() == null) {
				ga.doLAGERejectAction(resInfoTexts.getString("A_lerakásra_kiválasztott_k")); //$NON-NLS-1$
				return null;
			} else if (!j.illeszkedikRa(ga.getResolvedSzin(), ga.getResolvedJelzes(), c)) {
				ga.doLAGERejectAction(resInfoTexts.getString("A_kártyát_nem_rakhatod_le,")); //$NON-NLS-1$
				return null;
			} else {
				ga.doLAGEAcceptAction();
				ga.doLAGEAcceptCard();
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
			return new LASAlap(c, j);
			
		default:
			return null;
	}
}
public String toString() {
	return "((LASAlap[" + c.toString() + ", " + j.toString() + "]))";//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
}
