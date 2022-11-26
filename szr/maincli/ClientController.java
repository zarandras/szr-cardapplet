package szr.maincli;

import szr.msg.*;
import java.awt.*;
import java.awt.event.*;
import szr.mainserv.*;
import szr.card.*;
import szr.shared.*;
import szr.gui.*;
/**
 * A kliens központi vezérlõ osztálya
 * Creation date: (2002.01.27. 13:15:10)
 */
public class ClientController {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	// a kliens részei:
	private final Chatter chatter;
	private final GameTable gameTable;
	private final PlayerList playerList;
	private final CardsInHand cardsInHand;
	private final GameControlPanel gameControlPanel;
	private final PlayerControlPanel playerControlPanel;
	private final ClientMsgHandler msgHandler;
	private final InfoSystem infoSystem;
	// játékos állapot:
	private int playState;
	// játékosállapot-kódok:
	public final static int PS_NOTJOINED = 24;
	public final static int PS_NONE = 0;
	public final static int PS_INACTIVE_BESZURHAT = 5;
	public final static int PS_ACTIVE_SOROZHAT = 6;
	public final static int PS_ACTIVE_VETOBLOCKED = 7;
	public final static int PS_ACTIVE_LILA = 23;
	public final static int PS_INACTIVE_VETOZHAT = 8;
	public final static int PS_ACTIVE_SZINKERO = 9;
	public final static int PS_ACTIVE_JELZESKERO = 10;
	public final static int PS_ACTIVE_KEZDOLAP = 16;
	public final static int PS_ACTIVE_IRANYKERO = 11;
	public final static int PS_ACTIVE_LAPKERO = 12;
	public final static int PS_ACTIVE_GYENGE_LAPKERO = 22;
	public final static int PS_EGYEDULMARADT = 21;
	public final static int PS_ACTIVE_UT = 20;
	public final static int PS_ACTIVE_EXS = 18;
	public final static int PS_ACTIVE_EXS_SOROZHAT = 19;
	public final static int PS_DISCONNECTED = 17;
	public final static int PS_VESZTETT = 13;
	public final static int PS_NYERT = 14;
	public final static int PS_JOINED = 1;
	public final static int PS_JOINED_FIRST = 2;
	public final static int PS_ACTIVE = 3;
	public final static int PS_INACTIVE = 4;
	
	private int currGACount = -1;	// GameAction counter
	private boolean actionSentFlag = false;		// true ha éppen el lett küldve egy üzenet
public ClientController(CardsInHand iCardsInHand, Chatter iChatter, ClientMsgHandler iClientMsgHandler,
						GameControlPanel iGameControlPanel, GameTable iGameTable,
						PlayerControlPanel iPlayerControlPanel, PlayerList iPlayerList, 
						InfoSystem iInfoSystem) {
	super();

	playState = PS_NOTJOINED;
	
	cardsInHand = iCardsInHand;
	chatter = iChatter;
	msgHandler = iClientMsgHandler;
	gameControlPanel = iGameControlPanel;
	gameTable = iGameTable;
	playerControlPanel = iPlayerControlPanel;
	playerList = iPlayerList;
	infoSystem = iInfoSystem;
	
	cardsInHand.setParentController (this);
	chatter.setParentController (this);
	msgHandler.setParentController (this);
	gameControlPanel.setParentController (this);
	gameTable.setParentController (this);
	playerControlPanel.setParentController (this);
	playerList.setParentController (this);
	infoSystem.setParentController (this);
	
	msgHandler.start();
}
public void clearActionSentFlag() {
	actionSentFlag = false;
}
public void disableActionControls(String statusInfoText) {
	cardsInHand.disableActionButtons();
	gameTable.disableKertChoices();
	infoSystem.setInfoText(statusInfoText);
}
/**
 * A kilens applet bezárásakor hívódik meg:
 */
public void endProcess() {
	if (msgHandler.isAlive()) {
		msgHandler.send(new SCABontas());
		msgHandler.halt();
	}
}
public CardsInHand getCardsInHand() {
	return cardsInHand;
}
public Chatter getChatter() {
	return chatter;
}
public int getCurrGACount() {
	return currGACount;
}
public GameControlPanel getGameControlPanel() {
	return gameControlPanel;
}
public String getGameName() {
	return getGameControlPanel().getJatekNev();
}
public GameTable getGameTable() {
	return gameTable;
}
public InfoSystem getInfoSystem() {
	return infoSystem;
}
public ClientMsgHandler getMsgHandler() {
	return msgHandler;
}
public PlayerControlPanel getPlayerControlPanel() {
	return playerControlPanel;
}
public PlayerList getPlayerList() {
	return playerList;
}
public String getPlayerName() {
	return getPlayerControlPanel().getName();
}
public int getPlayState() {
	return playState;
}
public boolean isActionSentFlag() {
	return actionSentFlag;
}
/**
 * Játékban vagyunk-e?
 */
public boolean isJoined() {
	return (playState != PS_NOTJOINED && playState != PS_DISCONNECTED);
}
public void lerakPressed() {
		disableActionControls(resInfoTexts.getString("A_kártya_és_a_szándék_küld")); //$NON-NLS-1$
		switch (playState) {
			case PS_ACTIVE:
			case PS_ACTIVE_SOROZHAT:
			case PS_ACTIVE_UT:
			case PS_ACTIVE_EXS:
			case PS_ACTIVE_EXS_SOROZHAT:
				{
					int cardServerIdx = cardsInHand.getSelectedCardServerIdx();
					if (cardServerIdx >=0) {
						setActionSentFlag();
						msgHandler.send(new GameAction(GameAction.GA_LERAK_CJ, cardServerIdx, currGACount));
					} else {
						rejectedAction(resInfoTexts.getString("Válassz_ki_egy_kártyát_nés")); //$NON-NLS-1$
					}
					break;
				}
			case PS_INACTIVE_BESZURHAT:
			case PS_INACTIVE_VETOZHAT:
				{
					int cardServerIdx = cardsInHand.getSelectedCardServerIdx();
					if (cardServerIdx >=0) {
						setActionSentFlag();
						msgHandler.send(new GameAction(GameAction.GA_BESZUR_CJ, cardServerIdx, currGACount));
					} else {
						rejectedAction(resInfoTexts.getString("Válassz_ki_egy_színben_és_")); //$NON-NLS-1$
					}
					break;
				}

			case PS_ACTIVE_LAPKERO:
				{
					int cardServerIdx = cardsInHand.getSelectedCardServerIdx();
					if (cardServerIdx >=0) {
						msgHandler.send(new GGALapKeres(cardServerIdx, currGACount));
					} else {
						rejectedAction(resInfoTexts.getString("Válassz_ki_egy_kártyát_a_s")); //$NON-NLS-1$
					}
					break;
				}

			case PS_ACTIVE_GYENGE_LAPKERO:
				{
					int cardServerIdx = cardsInHand.getSelectedCardServerIdx();
					if (cardServerIdx >=0) {
						msgHandler.send(new GGALapKeres(cardServerIdx, currGACount));
					} else {
						rejectedAction(resInfoTexts.getString("Válassz_ki_egy_kártyát_a_s1")); //$NON-NLS-1$
					}
					break;
				}

			default:
				rejectedAction(resInfoTexts.getString("Ilyenkor_nem_lehet_kártyát")); //$NON-NLS-1$
				break;
		}
}
public void passzPressed() {
		disableActionControls(resInfoTexts.getString("A_szándék_küldése..._n")); //$NON-NLS-1$
		switch (playState) {
			case PS_ACTIVE:
			case PS_ACTIVE_SOROZHAT:
			case PS_ACTIVE_UT:
			case PS_ACTIVE_LILA:
			case PS_ACTIVE_EXS:
			case PS_ACTIVE_EXS_SOROZHAT:
				setActionSentFlag();
				msgHandler.send(new GameAction(GameAction.GA_PASSZ, -1, currGACount));
				break;
				
			case PS_JOINED_FIRST:
				msgHandler.send(new SCAKezd());
				break;

			case PS_ACTIVE_KEZDOLAP:
				setActionSentFlag();
				msgHandler.send(new GameAction(GameAction.GA_KEZDOLAP_CJP, -1, currGACount));
				break;

			case PS_ACTIVE_IRANYKERO:
				Irany irany = gameTable.getKertIrany();
				if (irany != null) {
					msgHandler.send(new GGAIranyKeres(irany, currGACount));
				} else {
					rejectedAction(resInfoTexts.getString("Válassz_egy_irányt_naz_irá")); //$NON-NLS-1$
					gameTable.enableKertIrany();
				}
				break;
				
			case PS_ACTIVE_SZINKERO:
				SzinesSzin szin = gameTable.getKertSzin();
				if (szin != null) {
					msgHandler.send(new GGASzinKeres(szin, currGACount));
				} else {
					rejectedAction(resInfoTexts.getString("Válassz_egy_színt_na_színk")); //$NON-NLS-1$
					gameTable.enableKertSzin();
				}
				break;
				
			case PS_ACTIVE_JELZESKERO:
				Jelzes jelzes = gameTable.getKertJel();
				if (jelzes != null) {
					msgHandler.send(new GGAJelzesKeres(jelzes, currGACount));
				} else {
					rejectedAction(resInfoTexts.getString("Válassz_egy_jelzést_na_jel")); //$NON-NLS-1$
					gameTable.enableKertJel();
				}
				break;
				
			case PS_ACTIVE_GYENGE_LAPKERO:
				infoSystem.setInfoText(resInfoTexts.getString("Küldés..._(Végül_nem_válas")); //$NON-NLS-1$
				msgHandler.send(new GGALapKeres(-1, currGACount));
				break;

			default:
				rejectedAction(resInfoTexts.getString("Ilyenkor_nem_lehet_passzol")); //$NON-NLS-1$
				break;
		}
}
public void rejectedAction(String rejectInfoText) {
	rejectedAction(rejectInfoText, getPlayState());
}
/**
 * Figyelmeztetõ üzenet a felhasználónak (az InfoSystem PSR konstansaival is hívható):
 */
public void rejectedAction(String rejectInfoText, int customPState) {
	setPlayState(playState); // restore original state
	Logger.log(new InfoLog(rejectInfoText), getGameName(), getPlayerName());
	infoSystem.openRejectDialog(rejectInfoText, customPState);
}
private void setActionSentFlag() {
	actionSentFlag = true;
}
public void setCurrGACount(int newCurrGACount) {
	currGACount = newCurrGACount;
}
/**
 * Játékosállapot-váltás
 */
public void setPlayState(int newPlayState) {
  if (newPlayState < 0) {	// < 0 playState constants are reserved for RejectDlgHandler
	Logger.log(new ErrStrLog(SubstituteStr.doIt("Invalid setPlayState() call with pState #%psn", "%psn", Integer.toString(newPlayState))), getGameName(), getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
  } else {
	playState = newPlayState;
	if (playState == PS_ACTIVE_VETOBLOCKED) {
			// idõzítõ beindítása, ami majd PS_ACTIVE_LILA-ra váltat:
			new VetoBlocker(this).start();
	}
	gameControlPanel.resetWidgetState();
	playerControlPanel.resetWidgetState();
	cardsInHand.setButtonPState(playState);
	infoSystem.setPSInfoText(playState);
	if (playState == PS_NOTJOINED || playState == PS_DISCONNECTED) {
		chatter.resetAndDisable();
		gameTable.clear();
		clearActionSentFlag();
		setCurrGACount(0);
		cardsInHand.removeAllCards();
		infoSystem.setExS(null);
		infoSystem.setUtSzam(null);
	} else {
		chatter.enable();
	}
	gameTable.disableKertChoices();
	switch (playState) {
		case PS_ACTIVE_SZINKERO:
				gameTable.enableKertSzin();
			break;
		case PS_ACTIVE_JELZESKERO:
				gameTable.enableKertJel();
			break;
		case PS_ACTIVE_IRANYKERO:
				gameTable.enableKertIrany();
			break;
		default:
			break;
	}
	if (playState == PS_NYERT || playState == PS_VESZTETT || playState == PS_EGYEDULMARADT)
		cardsInHand.setSmileyPState(playState);
	else if (!isJoined())
		cardsInHand.clearSmileyPState();
  }
}
}
