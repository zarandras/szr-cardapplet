package szr.msg;

import szr.card.*;
import szr.mainserv.*;
import szr.caut.*;
import szr.shared.*;
/**
 * J�t�kakci�-oszt�ly
 * Creation date: (2002.02.14. 14:17:15)
 */
public class GameAction extends GeneralGameAction {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	public final static int GA_LERAK_CJ = 4;	// lerak, halmoz, soroz akci�t�pus
	public final static int GA_NONE = 0;
	public final static int GA_KEZDOLAP_CJP = 2;	// kezd�laph�z�s akci�t�pus
	public final static int GA_NYER = -1;	// nyer akci�t�pus - nem lehet k�ldeni kliensb�l
	public final static int GA_PASSZ = 3;	// passz, befejez akci�t�pus
	public final static int GA_BESZUR_CJ = 1;	// besz�r, v�t�z akci�t�pus
	private final int myGAConst;	// az akci� t�pusa
	private final int myCardIdx;
	private transient Card myCard = null;	// a szerverben kap �rt�ket
	private final int counter;	// GameAction sz�ml�l� (h�nyadik GameAction lenne ez a j�t�kban?)
	private transient szr.card.Szin resolvedSzin = null;	// a szerverben kap �rt�ket
	private transient szr.card.Jelzes resolvedJelzes = null;	// a szerverben kap �rt�ket
	private transient szr.mainserv.PlayerData myPD = null;	// a szerverben kap �rt�ket
	private transient szr.mainserv.GameController myGC = null;	// a szerverben kap �rt�ket
public GameAction(int iMyGAConst, int iMyCardIdx, int iCounter) {
	super();
	myGAConst = iMyGAConst;
	myCardIdx = iMyCardIdx;
	counter = iCounter;
}
public void doLAGEAcceptAction() {
	/* a GA_NYER-re nem h�v�dik meg ez a fv., helyette a doLAGENyert */
	myGC.acceptedGA();
	myGC.sendToAll(new GEActionAccepted(myPD.getProperties().name, SubstituteStr.doIt(resInfoTexts.getString("%actstr..._n"), "%actstr", getGAString())));//$NON-NLS-2$ //$NON-NLS-1$
	if (myGAConst == GA_BESZUR_CJ) {
		myGC.sendSysChatMsg(SubstituteStr.doIt((getResolvedSzin() instanceof Lila) ? resChatTexts.getString("%p_v�t�zott!") : resChatTexts.getString("%p_besz�rt!"), "%p", myPD.getProperties().name));//$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
	} else if (myGAConst == GA_KEZDOLAP_CJP) {
		myGC.sendSysChatMsg(SubstituteStr.doIt(resChatTexts.getString("%p_kih�zta_a_kezd�lapot."), "%p", myPD.getProperties().name));//$NON-NLS-2$ //$NON-NLS-1$
	}
}
public void doLAGEAcceptCard() {
	myPD.removeCard(myCardIdx);
	myPD.updateCardCount();
	myGC.pushLerakottLap(myCard);
	myGC.cardAccepted();
}
public void doLAGEAcceptFinished() {
	myGC.finishedGA();
	if (myGAConst == GA_NYER || myGC.getWinnerQueue().getFirstPlayer() == null) {
		myGC.updateAllPlayerStates();
	} else {
		GameAction nyeroAction = new GameAction (GA_NYER, -1, myGC.getActionCounter());
		nyeroAction.initActorPlayerData(myGC.getWinnerQueue().getFirstPlayer());
		myGC.getGameLAut().startNextState(nyeroAction);
	}
}
public void doLAGEAcceptKezdolap() {
	myGC.cardAccepted();
}
public void doLAGECancelUt() {
	if (myGC.getUt() != null) {
		myGC.sendSysChatMsg(SubstituteStr.doIt(SubstituteStr.doIt(resChatTexts.getString("%p_hat�stalan�totta_a_k�te"), "%ut", myGC.getUt().toString()), "%p", myPD.getProperties().name));//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
		myGC.clearUt();
	}
}
public boolean doLAGEExecUt() {
	UtSzam ut = myGC.getUt();
	myGC.sendToAll(new GEInfoText(resInfoTexts.getString("Kimarad�s/k�telez�_h�z�s_v") + ut.toLongString() + ")"));//$NON-NLS-2$ //$NON-NLS-1$
	PlayerData prevPlayer = myGC.getPlayer(myGC.getNextPlayerIdx(myGC.indexOfPlayer(myPD), myGC.getIrany().visszafele()));
	if (ut.im < 0) {	// �tad�s (pipa-elh�z�)
		Card c = prevPlayer.captureKertLap();
		if (c == null) {	// nem volt k�r�s
			if (prevPlayer.getRealCardCount() == 0) {	// nincs t�bb lapja
				myGC.sendSysChatMsg(SubstituteStr.doIt(SubstituteStr.doIt(resChatTexts.getString("%p1_m�r_semmit_sem_tud_�ta"), "%p1", prevPlayer.getProperties().name), "%p2", myPD.getProperties().name));//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
				myGC.setUt(ut.re, 0);
				return doLAGEExecUt(); // m�r az (ut.re < 0) �gon fog folytat�dni
			} else {
				myGC.sendSysChatMsg(SubstituteStr.doIt(SubstituteStr.doIt(resChatTexts.getString("%p1_�tad_egy_lapot_%p2_kez"), "%p1", prevPlayer.getProperties().name), "%p2", myPD.getProperties().name));//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
				myGC.forceLapKeres(prevPlayer, true);
				return false;
			}
		} else {	// volt k�r�s
			prevPlayer.updateCardCount();
			myPD.addCard(c);
			myPD.updateCardCount();
			myGC.setUt(ut.re, ut.im + 1);
			return doLAGEExecUt();	// m�r nincs k�r�s
		}
	} else if (ut.re < 0) { // eldob�s (pipa)	(most m�r ut.im >= 0)
		Card c = prevPlayer.captureKertLap();
		if (c == null) {	// nem volt k�r�s
			if (prevPlayer.getRealCardCount() == 0) {	// nincs t�bb lapja
				myGC.sendSysChatMsg(SubstituteStr.doIt(SubstituteStr.doIt(resChatTexts.getString("%p1_m�r_semmit_sem_tud_eld"), "%p1", prevPlayer.getProperties().name), "%p2", myPD.getProperties().name));//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
				myGC.setUt(0, ut.im);
				return doLAGEExecUt();	// m�r az (ut.im > 0)-n fog folytat�dni
			} else {
				myGC.sendSysChatMsg(SubstituteStr.doIt(SubstituteStr.doIt(resChatTexts.getString("%p1_eldob_egy_lapot_a_pakl"), "%p1", prevPlayer.getProperties().name), "%p2", myPD.getProperties().name));//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
				myGC.forceLapKeres(prevPlayer, true);
				return false;
			}
		} else {	// volt k�r�s
			prevPlayer.updateCardCount();
			myGC.getPakli().pushCard(c);
			myGC.setUt(ut.re + 1, ut.im);
			return doLAGEExecUt();	// m�r nincs k�r�s
		}
	} else if (ut.im > 0) {	// elh�z�s 		(most m�r ut.im �s ut.re >= 0)
		int i = ut.im;
		while (i > 0 && prevPlayer.getRealCardCount() != 0) {
			myPD.addCard(prevPlayer.captureRandomCard());
			i--;
		}
		if (i != ut.im) {
			prevPlayer.updateCardCount();
			myGC.sendSysChatMsg(SubstituteStr.doIt(SubstituteStr.doIt(SubstituteStr.doIt(resChatTexts.getString("%p2_h�zott_%c_lapot_%p1_ke"), "%c", Integer.toString(ut.im - i)), "%p1", prevPlayer.getProperties().name), "%p2", myPD.getProperties().name));//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
		}
		myPD.updateCardCount();
		myGC.setUt(ut.re + i, 0);	// a marad�k lapokat a paklib�l kell felh�zni
		if (i != 0) {
			myGC.sendSysChatMsg(SubstituteStr.doIt(SubstituteStr.doIt(resChatTexts.getString("%p1_kez�b�l_m�r_nem_lehet_"), "%p1", prevPlayer.getProperties().name), "%p2", myPD.getProperties().name));//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
		}
		return doLAGEExecUt();	// m�r az (ut.re > 0)-n fog folytat�dni
	} else if (ut.re > 0) { // h�z�s		(most m�r ut.im == 0 �s ut.re >= 0)
		for (int i = 0; i < ut.re; ++i) {
			myPD.addCard(myGC.getPakli().popCard());
		}
		myGC.sendSysChatMsg(SubstituteStr.doIt(SubstituteStr.doIt(SubstituteStr.doIt(resChatTexts.getString("%p2_h�zott_%c_lapot_a_pakl"), "%c", Integer.toString(ut.re)), "%p1", prevPlayer.getProperties().name), "%p2", myPD.getProperties().name));//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
		myPD.updateCardCount();
		myGC.setUt(0, 0);	// ut == 0+0i, teljes�tve,
		return doLAGEExecUt(); // az al�bbin fog folytat�dni
	} else {	// kimarad�s (Ix) 	(ut.im �s ut.re == 0)
		myGC.sendSysChatMsg(SubstituteStr.doIt(SubstituteStr.doIt(resChatTexts.getString("%p2_kimaradt_a_k�rb�l."), "%p1", prevPlayer.getProperties().name), "%p2", myPD.getProperties().name));//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
		myGC.clearUt();
		return true;
	}
}
public void doLAGEExSFinished(ExSType exS) {
	exS.doExSFinished(myGC);
}
public void doLAGEExSStep(ExSType exS) {
	exS.doExSStep(myGC);
}
public boolean doLAGEGeneralPost(Szin c, Jelzes j, int invokeCounter) {
	switch (invokeCounter) {
		case 0:
			if (c instanceof SzinKero && ((SzinKero)c).getKertSzin() == null) {
				myGC.forceSzinKeres();
				return false;
			} else if (c instanceof JelzesKero && ((JelzesKero)c).getKertJel() == null) {
				myGC.forceJelzesKeres(((JelzesKero)c).getKerhetoJelek());
				return false;
			}
			// no break
			
		case 1:
			if (j instanceof SzinKero && ((SzinKero)j).getKertSzin() == null) {
				myGC.forceSzinKeres();
				return false;
			} else if (j instanceof JelzesKero && ((JelzesKero)j).getKertJel() == null) {
				myGC.forceJelzesKeres(((JelzesKero)j).getKerhetoJelek());
				return false;
			}
			// no break
			
		case 2:
			if (myGC.getKertIrany() != null) {
				myGC.clearKertIrany();
			}
			myGC.setActivePlayer(myGC.getNextPlayer(), false);
			return true;
			
		default:
			return true;
	}
}
public void doLAGEHuzas() {
	myPD.addCard(myGC.getPakli().popCard());
	myPD.updateCardCount();
}
public static void doLAGEInit(GameController gc) {
	gc.getGameLAut().initialize();
	gc.sendToAll(new szr.msg.GEKeveres());
	gc.keveres();
	gc.sendToAll(new szr.msg.GEInfoText(resInfoTexts.getString("Oszt�s..."))); //$NON-NLS-1$
	gc.osztas();
	gc.setJelzeslap(gc.getPakli().popCard());
	gc.finishedGA();
	gc.updateAllPlayerStates();
}
public void doLAGEJump() {
	myGC.setActivePlayer(myPD, false);
	// ha lila, akkor v�t�ztak, �gy hat�stalan�tani kell az esetleges k�telez� h�z�st:
	if (getResolvedSzin() instanceof Lila) {
		myGC.clearUt();
	}
}
public boolean doLAGELPostEventJ(Jelzes j, int invokeCounter) {
	return j.doLPostEvent(myGC, invokeCounter);
}
public boolean doLAGELPreEventJ(int invokeCounter) {
	return getResolvedJelzes().doLPreEvent(myGC, invokeCounter);
}
public void doLAGENyert() {
	myGC.acceptedGA();
	myGC.sendSysChatMsg(SubstituteStr.doIt(resChatTexts.getString("%p_megnyerte_a_j�t�kot!"), "%p", myPD.getProperties().name));//$NON-NLS-2$ //$NON-NLS-1$
	myGC.setActivePlayer(myPD, false);
	myGC.finishGame();
}
public boolean doLAGEPostEventJ(Jelzes j, int invokeCounter) {
	return j.doPostEvent(myGC, invokeCounter);
}
public boolean doLAGEPreEventJ(int invokeCounter) {
	return getResolvedJelzes().doPreEvent(myGC, invokeCounter);
}
public boolean doLAGEPreEventJ(Jelzes j, int invokeCounter) {
	return j.doPreEvent(myGC, invokeCounter);
}
public void doLAGERejectAction(String rejectMsg) {
	try {
		Logger.log(new InfoLog("------> REJECTED ACTION: ========= " + rejectMsg), myPD.getProperties().name, myGC.getProperties().name);
	} catch (NullPointerException e) {
		Logger.log(new InfoLog("------> REJECTED ACTION: ========= " + rejectMsg), null, null);
	}
	myPD.getMsgHandler().send(new PEReject(rejectMsg));
}
public void doLAGERejectNyer() {
	myGC.updateAllPlayerStates();
}
public boolean doLAGEStartEventJ(int invokeCounter) {
	return getResolvedJelzes().doStartEvent(myGC, invokeCounter);
}
public void doLAGEVetoPossible() {
	myGC.setVetoBlocked();
}
public void doServer(szr.mainserv.ServerMsgHandler smh) {
		initActorPlayerData (smh.getPlayerData());
		if (myGC.expectsGA(counter)) {
			Event rejectEvent = null;
			// Integrity check:
			if (myGAConst <= 0) {
				rejectEvent = new PEReject(resInfoTexts.getString("�rv�nytelen_akci�!_n")); //$NON-NLS-1$
			} else if (myGAConst == GA_PASSZ && !myPD.getProperties().active) {
				rejectEvent = new PEReject(resInfoTexts.getString("�gy,_amikor_nem_te_j�ssz,_")); //$NON-NLS-1$
			} else if (myGAConst == GA_LERAK_CJ && !myPD.getProperties().active) {
				rejectEvent = new PEReject(resInfoTexts.getString("�gy,_amikor_nem_te_j�ssz,_1")); //$NON-NLS-1$
			} else if (myGAConst == GA_BESZUR_CJ && myPD.getProperties().active) {
				rejectEvent = new PEReject(resInfoTexts.getString("�gy,_amikor_te_j�ssz,_nnem")); //$NON-NLS-1$
			} else if ((myGAConst == GA_LERAK_CJ || myGAConst == GA_BESZUR_CJ) && (myCard == null || myCardIdx < 0)) {
				rejectEvent = new PEReject(resInfoTexts.getString("A_szerverhez_nem_�rkezett_")); //$NON-NLS-1$
			}

			if (myGAConst == GA_KEZDOLAP_CJP) {
				myCard = myGC.getPakli().popCard();
				myGC.pushLerakottLap(myCard);
			}
			
			// start LAut state transition:
			if (rejectEvent == null) {
				myGC.getGameLAut().startNextState(this);
			} else {
				smh.send(rejectEvent);
			}
		}
}
public OsJelzes getCardJelzes() {
	try {
		return myCard.getJelzes();
	} catch (NullPointerException e) {
		return null;
	}
}
public OsSzin getCardSzin() {
	try {
		return myCard.getSzin();
	} catch (NullPointerException e) {
		return null;
	}
}
public int getGAConst() {
	return myGAConst;
}
public String getGAString() {
	switch (myGAConst) {
		case GA_LERAK_CJ:		return resInfoTexts.getString("K�rtyalerak�s"); //$NON-NLS-1$
		case GA_KEZDOLAP_CJP:	return resInfoTexts.getString("Kezd�laph�z�s"); //$NON-NLS-1$
		case GA_NYER:			return resInfoTexts.getString("Nyer�s"); //$NON-NLS-1$
		case GA_PASSZ:			return resInfoTexts.getString("Passzol�s"); //$NON-NLS-1$
		case GA_BESZUR_CJ:		return resInfoTexts.getString("Besz�r�s"); //$NON-NLS-1$
		default:				return "?_GA";//$NON-NLS-1$
	}
}
public PlayerData getPD() {
	return myPD;
}
public szr.card.Jelzes getResolvedJelzes() {
	if (resolvedJelzes == null) {
		try {
			// on-demand lek�rdez�s
			resolvedJelzes = myCard.getResolvedJelzes(myPD.getParentController().getTableState());
		} catch (NullPointerException e) {
			resolvedJelzes = null;
		}
	}
	return resolvedJelzes;
}
public szr.card.Szin getResolvedSzin() {
	if (resolvedSzin == null) {
		try {
			// on-demand lek�rdez�s
			resolvedSzin = myCard.getResolvedSzin(myPD.getParentController().getTableState());
		} catch (NullPointerException e) {
			resolvedSzin = null;
		}
	}
	return resolvedSzin;
}
/**
 * PlayerData (aki kezdem�nyezte az akci�t) �s a GameController be�ll�t�sa
 */
public void initActorPlayerData(PlayerData pd) {
	myPD = pd;
	myGC = myPD.getParentController();
	if (myCardIdx >= 0) {
		myCard = myPD.getCard(myCardIdx);
	}
}
/**
 * K�rt jelz�s be�ll�t�sa - a szerverben h�v�dik
 */
public boolean setKertJel(Jelzes kertJ) {
	boolean accepted = false;
	Szin c = getResolvedSzin();
	Jelzes j = getResolvedJelzes();
	if (c instanceof JelzesKero) {
		resolvedSzin = (Szin)((JelzesKero)c).applyKertJel(kertJ);
		accepted = true;
	}
	if (j instanceof JelzesKero) {
		resolvedJelzes = (Jelzes)((JelzesKero)j).applyKertJel(kertJ);
		accepted = true;
	}
	return accepted;
}
/**
 * K�rt sz�n be�ll�t�sa - a szerverben h�v�dik
 */
public boolean setKertSzin(SzinesSzin kertC) {
	boolean accepted = false;
	Szin c = getResolvedSzin();
	Jelzes j = getResolvedJelzes();
	if (c instanceof SzinKero) {
		resolvedSzin = (Szin)((SzinKero)c).applyKertSzin(kertC);
		accepted = true;
	}
	if (j instanceof SzinKero) {
		resolvedJelzes = (Jelzes)((SzinKero)j).applyKertSzin(kertC);
		accepted = true;
	}
	return accepted;
}
/**
 * PlayerData �t�ll�t�sa (aki kezdem�nyezte) - a szerverben h�v�dik, ha a kezdem�nyez� kisz�llt
 * 												(norm�lis esetben az initActorPlayerData h�v�dik)
 */
public void setPD(PlayerData newPD) {
	myPD = newPD;
}
public String toString() {
	return super.toString() + " ======== GA:" + getGAString() + " CardIdx:" + myCardIdx + " Card:" + ((myCard == null) ? "(null)" : myCard.toString());//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
}
