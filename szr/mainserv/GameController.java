package szr.mainserv;

import szr.msg.*;
import java.util.*;
import szr.caut.*;
import szr.card.*;
import szr.shared.*;
import szr.pakli.*;
/**
 * A szerver játékvezérlõ osztálya
 * Creation date: (2002.01.27. 13:17:00)
 */
public class GameController {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	public final static int OSZTOTTLAPOK_MAX = 12;
	public final static int OSZTOTTLAPOK_MIN = 6;
	private final Vector players;
	private GameRec properties = null;
	private Pakli pakli = null;
	private final Vector lerakottLapok = new Vector();
	private szr.card.Card jelzeslap = null;
	private final GameServer parentServer;
	private Irany jatekIrany = null;
	private PlayerData activePlayer = null;
	private final LAut gameLAut;
	private int actionCounter;	// GameAction számláló
	private Irany kertIrany;
	private final WinnerQueue winnerQueue;	// potenciális nyertesek sora
	private UtSzam utSzam = null;	// kötelezõ húzás
	private boolean cardAcceptFlag = false;	// kártyaelfogadás
	private boolean gameFinished = false;
	private boolean vetoBlocked = false;	// vétózási lehetõség miatt blokkolva van az aktuális játékos
	private boolean exSFlag = false;	// extra sorozás
	private AutoPasszTimer autoPasszTimer = null;
public GameController(GameRec iProperties, GameServer iParentServer) {
	super();
	parentServer = iParentServer;
	properties = iProperties.notStarted();
	jatekIrany = new Irany(Irany.Lefele);
	players = new Vector();
	winnerQueue = new WinnerQueue(this);
	gameLAut = new LAut(properties.name);
}
public void acceptedGA() {
	Logger.log(new InfoLog(SubstituteStr.doIt("GameController: GameAction #%gac accepted.", "%gac", Integer.toString(actionCounter))), properties.name, null);//$NON-NLS-2$//$NON-NLS-1$
	cancelAutoPasszTimer();
}
public void addPlayer(PlayerRec playerRec, ServerMsgHandler playerMsgHandler) {
	playerMsgHandler.send (new PEJatekosLista (getPlayerRecs()));
	if (properties.started) {
		playerMsgHandler.send (new PEReject (resInfoTexts.getString("A_játék_már_elkezdõdött,_í"))); //$NON-NLS-1$
		Logger.log(new InfoLog("GameController: game already started, addPlayer() rejected."), properties.name, playerRec.name);//$NON-NLS-1$
	} else if (playerPresent (playerRec.name)) {
		playerMsgHandler.send (new PEReject (resInfoTexts.getString("Két_azonos_nevû_játékos_ne"))); //$NON-NLS-1$
		Logger.log(new InfoLog("GameController: duplicate player name, addPlayer() rejected."), properties.name, playerRec.name);//$NON-NLS-1$
	} else {
		if (players.isEmpty()) {
			playerRec = playerRec.activate();
		} else {
			playerRec = playerRec.inactivate();
		}
		PlayerData playerData = new PlayerData (playerRec, playerMsgHandler, this);
		players.addElement (playerData);
		if (playerRec.active) {
			activePlayer = playerData;
		}
		playerData.getMsgHandler().send(new PECsatlakozott(players.size() == 1));
		sendToAll (new GECsatlakozott(playerRec));
		sendSysChatMsg(SubstituteStr.doIt(resChatTexts.getString("%p__csatlakozott_a_játékho"), "%p", playerRec.name));//$NON-NLS-2$ //$NON-NLS-1$
	}
}
public void addUt(UtSzam ur) {
	Logger.log(new InfoLog(SubstituteStr.doIt("GameController: addUt(%usz)", "%usz", ur.toString())), properties.name, null);//$NON-NLS-2$//$NON-NLS-1$
	if (utSzam == null) {
		utSzam = new UtSzam(ur.re, ur.im);
	} else {
		utSzam = utSzam.hozzaAd(ur);
	}
	sendToAll(new GEUtRec(utSzam));
}
public void autoPassz(PlayerData player) {
	Logger.log(new InfoLog("GameController: AutoPassz"), properties.name, player.getProperties().name);//$NON-NLS-1$
	if (player.getKeroState() != PlayerData.keroState_none) {
		int keroState = player.getKeroState();
		player.setKeroState(PlayerData.keroState_none);
		switch (keroState) {
			case PlayerData.keroState_szin:
				sendSysChatMsg(SubstituteStr.doIt(resChatTexts.getString("Mivel_%p1_ideje_lejárt,_ni"), "%p1", player.getProperties().name));//$NON-NLS-2$ //$NON-NLS-1$
				setKertSzin(new SzinesSzin(pakli.getSzinMask()));
				break;
				
			case PlayerData.keroState_jelzes:
				sendSysChatMsg(SubstituteStr.doIt(resChatTexts.getString("Mivel_%p1_ideje_lejárt,_ni1"), "%p1", player.getProperties().name));//$NON-NLS-2$ //$NON-NLS-1$
				setKertJel(new ExtremalJel());
				break;
				
			case PlayerData.keroState_irany:
				sendSysChatMsg(SubstituteStr.doIt(resChatTexts.getString("Mivel_%p1_ideje_lejárt,_a_"), "%p1", player.getProperties().name));//$NON-NLS-2$ //$NON-NLS-1$
				setKertIrany(getIrany());
				break;
				
			case PlayerData.keroState_lap:
				sendSysChatMsg(SubstituteStr.doIt(resChatTexts.getString("Mivel_%p1_ideje_lejárt,_au"), "%p1", player.getProperties().name));//$NON-NLS-2$ //$NON-NLS-1$
				player.setKertLapIdx(0);		
				break;
				
			case PlayerData.keroState_lap_gyenge:
				sendSysChatMsg(SubstituteStr.doIt(resChatTexts.getString("Mivel_%p1_ideje_lejárt,_má"), "%p1", player.getProperties().name));//$NON-NLS-2$ //$NON-NLS-1$
				player.setKertLapIdx(-1);		
				break;
				
			default:
				Logger.log(new ErrStrLog(SubstituteStr.doIt("GameController: autoPassz(): invalid keroState %ks.", "%ks", Integer.toString(keroState))), properties.name, player.getProperties().name);//$NON-NLS-2$//$NON-NLS-1$
				break;
		}
		gameLAut.contNextState();
	} else if (player == activePlayer) {	// ekkor passz actiont generálunk
		sendSysChatMsg(SubstituteStr.doIt(resChatTexts.getString("Mivel_%p1_ideje_lejárt,_au1"), "%p1", player.getProperties().name));//$NON-NLS-2$ //$NON-NLS-1$
		GameAction passzAction = new GameAction (GameAction.GA_PASSZ, -1, getActionCounter());
		passzAction.initActorPlayerData(player);
		getGameLAut().startNextState(passzAction);
	}
}
/**
 * Ha az aktuális játékos szállt ki
 */
private void autoPasszKiszallt(PlayerData player) {
	Logger.log(new InfoLog("GameController: AutoPasszKiszallt"), properties.name, player.getProperties().name);//$NON-NLS-1$
	cancelAutoPasszTimer();
	if (player.getKeroState() != PlayerData.keroState_none) {
		int keroState = player.getKeroState();
		if (player == activePlayer) {
			activePlayer = getNextPlayer();
			sendSysChatMsg(SubstituteStr.doIt(SubstituteStr.doIt(resChatTexts.getString("Mivel_%p1_kiszállt,_%p2_ké"), "%p1", player.getProperties().name), "%p2", activePlayer.getProperties().name));//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
			activePlayer.activate();
			activePlayer.setKeroState(keroState);
			startNewAutoPasszTimer(activePlayer);
			activePlayer.updatePState();
			GameAction ga = getGameLAut().getStartedGA();
			if (ga != null && ga.getPD() == player)
				ga.setPD(activePlayer);
		} else {
			PlayerData prevPlayer = getPlayer(getNextPlayerIdx(indexOfPlayer(player), getIrany().visszafele()));
			sendSysChatMsg(SubstituteStr.doIt(SubstituteStr.doIt(resChatTexts.getString("Mivel_%p1_kiszállt,_%p2_ké"), "%p1", player.getProperties().name), "%p2", prevPlayer.getProperties().name));//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
			prevPlayer.setKeroState(keroState);
			startNewAutoPasszTimer(prevPlayer);
			prevPlayer.updatePState();
		}
	} else if (player == activePlayer) {
		activePlayer = getNextPlayer();
		sendSysChatMsg(SubstituteStr.doIt(SubstituteStr.doIt(resChatTexts.getString("Mivel_%p1_kiszállt,_%p2_kö"), "%p1", player.getProperties().name), "%p2", activePlayer.getProperties().name));//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
		activePlayer.activate();
		if (gameLAut.getCurrState() != null && gameLAut.getCurrState().isAutoPasszState())
			startNewAutoPasszTimer(activePlayer);
		activePlayer.updatePState();
		GameAction ga = getGameLAut().getStartedGA();
		if (ga != null && ga.getPD() == player)
			ga.setPD(activePlayer);
	}
}
private void cancelAutoPasszTimer() {
	if (autoPasszTimer != null) {
		autoPasszTimer.cancel();
		autoPasszTimer = null;
	}
}
public void cardAccepted() {
	cardAcceptFlag = true;
	sendToAll(new GEAdjustTableState(getTableState()));
}
public void clearKertIrany() {
	setKertIrany(null);
}
public void clearUt() {
	utSzam = null;
	Logger.log(new InfoLog("GameController: clearUt()"), properties.name, null);//$NON-NLS-1$
	sendToAll(new GEUtRec(null));
}
public boolean createPakli() {
	pakli = Pakli.newPakli(properties.pakliIdx, this);
	Logger.log(new InfoLog(SubstituteStr.doIt("GameController: Pakli created: %pstr", "%pstr", pakli.toString())), properties.name, null);//$NON-NLS-2$//$NON-NLS-1$
	return (pakli != null);
}
public void delPlayer(PlayerData player, boolean aborted) {
	sendToAll (new GEKiszallt(player.getProperties().name));
	sendSysChatMsg(SubstituteStr.doIt(
			((aborted) ? resChatTexts.getString("%p_hálózati_kapcsolata_saj") : resChatTexts.getString("%p_kiszállt_a_játékból.")), //$NON-NLS-2$ //$NON-NLS-1$
			"%p", player.getProperties().name));//$NON-NLS-2$//$NON-NLS-1$

	if (!gameFinished && players.size() > 1
			&& (player == activePlayer || player.getKeroState() != PlayerData.keroState_none)) {
		autoPasszKiszallt(player);
	}

	player.getMsgHandler().send(new PEKiszallt());
	
	if (!players.removeElement(player)) {
		Logger.log(new ErrStrLog("GameController error: invalid player at delPlayer()"), properties.name, player.getProperties().name);//$NON-NLS-1$
		return;
	}

	if (players.isEmpty()) {
		getParentServer().delGame (this);
	} else if (properties.started && players.size() == 1 && !(gameLAut.getCurrState() instanceof LASNyert)) {
		finishGame();
		activePlayer.getMsgHandler().send(new PEChangeState(szr.maincli.ClientController.PS_EGYEDULMARADT));
	}
}
/**
 * Ha egy kliens nincs szinkronban az eseményekkel, az itt derül ki
 */
public boolean expectsGA(int counter) {
	return (counter == actionCounter);
}
/**
 * Code to perform when this object is garbage collected.
 * 
 * Any exception thrown by a finalize method causes the finalization to
 * halt. But otherwise, it is ignored.
 */
protected void finalize() throws Throwable {
	try {
		cancelAutoPasszTimer();
	} catch (Exception e) {}
	super.finalize();
}
private int findPlayer(String playerName) {
	int i = 0;
	while (i < players.size ()) {
		if (((PlayerData)players.elementAt(i)).getProperties().name.equals(playerName))
			break;
		i++;
	}
	return i;
}
public void finishedGA() {
	cardAcceptFlag = false;
	incActionCounter();
	if (gameLAut.getCurrState().isAutoPasszState()) {
		startNewAutoPasszTimer(activePlayer);
	}
	sendToAll(new GEActionFinished(actionCounter));
}
public void finishGame() {
	if (!gameFinished) {
		Logger.log(new InfoLog("GameController: Game finished."), properties.name, null);//$NON-NLS-1$
		gameFinished = true;
		cancelAutoPasszTimer();
		getWinnerQueue().reset();
	} else {
		Logger.log(new ErrStrLog("GameController error: finishGame called on a finished game"), properties.name, null);//$NON-NLS-1$
	}
}
public void forceIranyKeres() {
	try {
		sendToAll(new GEInfoText (resInfoTexts.getString("Iránykérés..._n"))); //$NON-NLS-1$
		activePlayer.setKeroState(PlayerData.keroState_irany);
		startNewAutoPasszTimer(activePlayer);
		activePlayer.updatePState();
	} catch (NullPointerException e) {
		Logger.log(new ErrExcLog(e), properties.name, null);
	}
}
public void forceJelzesKeres(Jelzes[] kerhetoJelek) {
	try {
		sendToAll(new GEInfoText (resInfoTexts.getString("Jelzéskérés..._n"))); //$NON-NLS-1$
		activePlayer.getMsgHandler().send(new PEKerhetoJelek(kerhetoJelek));
		activePlayer.setKeroState(PlayerData.keroState_jelzes);
		startNewAutoPasszTimer(activePlayer);
		activePlayer.updatePState();
	} catch (NullPointerException e) {
		Logger.log(new ErrExcLog(e), properties.name, null);
	}
}
public void forceLapKeres(PlayerData ki, boolean nemGyenge) {
	try {
		sendToAll(new GEInfoText (resInfoTexts.getString("Kártyalapválasztás..."))); //$NON-NLS-1$
		ki.setKeroState((nemGyenge) ? PlayerData.keroState_lap : PlayerData.keroState_lap_gyenge);
		startNewAutoPasszTimer(ki);
		ki.updatePState();
	} catch (NullPointerException e) {
		Logger.log(new ErrExcLog(e), properties.name, null);
	}
}
public void forceSzinKeres() {
	try {
		sendToAll(new GEInfoText (resInfoTexts.getString("Színkérés..._n"))); //$NON-NLS-1$
		activePlayer.getMsgHandler().send(new PEKerhetoSzinek(pakli.getSzinMask()));
		activePlayer.setKeroState(PlayerData.keroState_szin);
		startNewAutoPasszTimer(activePlayer);
		activePlayer.updatePState();
	} catch (NullPointerException e) {
		Logger.log(new ErrExcLog(e), properties.name, null);
	}
}
public int getActionCounter() {
	return actionCounter;
}
public PlayerData getActivePlayer() {
	return activePlayer;
}
public int getActivePlayerIdx() {
	return findPlayer(activePlayer.getProperties().name);
}
public LAut getGameLAut() {
	return gameLAut;
}
public Irany getIrany() {
	return jatekIrany;
}
public szr.card.Card getJelzeslap() {
	return jelzeslap;
}
public szr.shared.Irany getKertIrany() {
	return kertIrany;
}
public java.lang.String getName() {
	return properties.name;
}
public PlayerData getNextPlayer() {
	return getPlayer(getNextPlayerIdx(indexOfActivePlayer(), jatekIrany));
}
public PlayerData getNextPlayer(Irany irany) {
	return getPlayer(getNextPlayerIdx(indexOfActivePlayer(), irany));
}
public int getNextPlayerIdx(int i) {
	return getNextPlayerIdx(i, jatekIrany);
}
public int getNextPlayerIdx(int i, Irany irany) {
	if (i < 0 || i >= players.size() || irany == null)
		return -1;
	else if (irany.isFelfele()) {
		if (i == 0)
			return players.size() - 1;
		else
			return i - 1;
	} else { // (irany.isLefele())
		return (i + 1) % (players.size());
	}
}
public Pakli getPakli() {
	return pakli;
}
public GameServer getParentServer() {
	return parentServer;
}
public PlayerData getPlayer(int idx) {
	try {
		return (PlayerData)players.elementAt(idx);
	} catch (ArrayIndexOutOfBoundsException e) {
		return null;
	}
}
public int getPlayerCount() {
	try {
		return players.size();
	} catch (NullPointerException e) {
		if (properties.started)
			Logger.log(new ErrExcLog(e), properties.name, null);
		return 0;
	}
}
public java.util.Vector getPlayerRecs() {
	Vector playerRecs = new Vector (players.size ());
	Enumeration e = players.elements ();
	while (e.hasMoreElements())
		playerRecs.addElement(((PlayerData)e.nextElement()).getProperties());
	return playerRecs;
}
public GameRec getProperties() {
	return properties;
}
public TableStateRec getTableState() {
  try {
	GameAction ga = gameLAut.getStartedGA();
	if (!cardAcceptFlag) {
		return new TableStateRec(gameLAut.getCurrState().getSzin(),
								 gameLAut.getCurrState().getJelzes(),
								 getJelzeslap());
	} else {
		return new TableStateRec(ga.getResolvedSzin(),
								 ga.getResolvedJelzes(),
								 getJelzeslap());
	}
  } catch (NullPointerException e) {
	if (properties.started)
		Logger.log(new ErrExcLog(e), properties.name, null);
	return null;
  }
}
public UtSzam getUt() {
	return utSzam;
}
public WinnerQueue getWinnerQueue() {
	return winnerQueue;
}
private void incActionCounter() {
	actionCounter = (actionCounter + 1) % (1 << 30);
}
public int indexOfActivePlayer() {
	return indexOfPlayer(activePlayer);
}
public int indexOfPlayer(PlayerData pd) {
	if (pd == null) {
		Logger.log(new ErrStrLog("GameController error: indexOfPlayer(null)"), properties.name, null);//$NON-NLS-1$
		return -1;
	} else {
		return players.indexOf(pd);
	}
}
public boolean isKerhetoJel(Jelzes j) {
	// nem ellenõrizzük, hogy van-e a pakliban!
	return !(gameLAut.getCurrState().isUtRestricted() && !(j instanceof UtHalmozo));
}
public boolean isKerhetoSzin(SzinesSzin c) {
	return ((pakli.getSzinMask() & c.getMask()) != 0);
}
public boolean isVetoBlocked() {
	return vetoBlocked;
}
public boolean kertIranyOK(PlayerData kero, Irany kertIrany, int currGACount) {
	if (kero.getKeroState() == PlayerData.keroState_irany && expectsGA(currGACount) && kero == activePlayer) {
		cancelAutoPasszTimer();
		kero.setKeroState(PlayerData.keroState_none);
		setKertIrany(kertIrany);
		return true;
	} else {
		Logger.log(new InfoLog(SubstituteStr.doIt(SubstituteStr.doIt("GameController: kertIranyOK(..., %dir, %gac) returns false", "%dir", kertIrany.toString()), "%gac", Integer.toString(currGACount))), properties.name, kero.getProperties().name);//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
		return false;
	}
}
public boolean kertJelOK(PlayerData kero, Jelzes kert, int currGACount) {
	if (kero.getKeroState() == PlayerData.keroState_jelzes && expectsGA(currGACount) && kero == activePlayer && isKerhetoJel(kert)) {
		cancelAutoPasszTimer();
		kero.setKeroState(PlayerData.keroState_none);
		setKertJel(kert);
		return true;
	} else {
		Logger.log(new InfoLog(SubstituteStr.doIt(SubstituteStr.doIt("GameController: kertJelOK(..., %j, %gac) returns false", "%j", kert.toString()), "%gac", Integer.toString(currGACount))), properties.name, kero.getProperties().name);//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
		return false;
	}
}
public boolean kertLapIdxOK(PlayerData kero, int idx) {
	if (((kero.getKeroState() == PlayerData.keroState_lap && idx >= 0) || (kero.getKeroState() == PlayerData.keroState_lap_gyenge)) && idx < kero.getRealCardCount()) {
		cancelAutoPasszTimer();
		kero.setKeroState(PlayerData.keroState_none);
		kero.setKertLapIdx(idx);
		return true;
	} else {
		Logger.log(new InfoLog(SubstituteStr.doIt("GameController: kertLapIdxOK(..., %idx) returns false", "%idx", Integer.toString(idx))), properties.name, kero.getProperties().name);//$NON-NLS-2$//$NON-NLS-1$
		return false;
	}
}
public boolean kertSzinOK(PlayerData kero, SzinesSzin kert, int currGACount) {
	if (kero.getKeroState() == PlayerData.keroState_szin && expectsGA(currGACount) && kero == activePlayer && isKerhetoSzin(kert)) {
		cancelAutoPasszTimer();
		kero.setKeroState(PlayerData.keroState_none);
		setKertSzin(kert);
		return true;
	} else {
		Logger.log(new InfoLog(SubstituteStr.doIt(SubstituteStr.doIt("GameController: kertSzinOK(..., %c, %gac) returns false", "%c", kert.toString()), "%gac", Integer.toString(currGACount))), properties.name, kero.getProperties().name);//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
		return false;
	}
}
public void keveres() {
	pakli.fillPakli();
	Logger.log(new InfoLog("GameController: keveres() finished."), properties.name, null);//$NON-NLS-1$
}
public void mulUt(UtSzam ur) {
	Logger.log(new InfoLog(SubstituteStr.doIt("GameController: mulUt(%usz)", "%usz", ur.toString())), properties.name, null);//$NON-NLS-2$//$NON-NLS-1$
	if (utSzam != null) {
		utSzam = utSzam.megSzoroz(ur);
		sendToAll(new GEUtRec(utSzam));
	}
}
public void osztas() {
  try {
	int ai = getActivePlayerIdx();
	int s = players.size();
	for (int i = 0; i < s * properties.kezdetiLapSzam; ++i) {
		((PlayerData)players.elementAt((ai + 1 + i) % s)).addCard(pakli.popCard());
	}
	for (int i = 0; i < s; ++i) {
		((PlayerData)players.elementAt(i)).updateCardCount();
	}
	// initialize winner queue:
	winnerQueue.reset();
  } catch (Exception e) {
	Logger.log(new ErrExcLog(e), properties.name, null);
  }
}
public boolean playerPresent(String pName) {
	Enumeration e = players.elements();
	while (e.hasMoreElements()) {
		if (((PlayerData)e.nextElement()).getProperties().name.equals(pName))
			return true;
	}
	return false;
}
public Card popLerakottLap() {
	if (!lerakottLapok.isEmpty()) {
		Card c = (Card)lerakottLapok.elementAt(0);
		lerakottLapok.removeElementAt(0);
		sendToAll(new GEPopLerakottLap());
		return c;
	} else {
		sendSysChatMsg(resChatTexts.getString("Nincs_több_lerakott_lap.")); //$NON-NLS-1$
		return null;
	}
}
public void pushLerakottLap(Card newCard) {
	lerakottLapok.insertElementAt(newCard, 0);
	sendToAll(new GEPushLerakottLap(newCard));
}
public void reverse() {
	setIrany(jatekIrany.visszafele());
}
/**
 * Rendszerüzenet küldése a Csevegõbe
 */
public void sendSysChatMsg(String msgText) {
	try {
		sendToAll(new GEChat(": " + msgText));//$NON-NLS-1$
	} catch (NullPointerException e) {
		Logger.log(new ErrExcLog(e), properties.name, null);
	}
}
/**
 * Üzenet küldése az összes játékosnak
 */
public void sendToAll(szr.msg.GameEvent event) {
  try {
	Logger.log(new InfoLog(SubstituteStr.doIt("GameController: SendToAll: %evstr", "%evstr", event.toString())), properties.name, null);//$NON-NLS-2$//$NON-NLS-1$
	Enumeration e = players.elements ();
	while (e.hasMoreElements ())
		((PlayerData)e.nextElement ()).getMsgHandler().send (event);
  } catch (Exception e) {
	Logger.log(new ErrExcLog(e), properties.name, null);
  }
}
public void setActivePlayer(PlayerData newActivePlayer, boolean updatePlayerState) {
	activePlayer.inactivate();
	if (updatePlayerState)
		activePlayer.updatePState();
	activePlayer = newActivePlayer;
	activePlayer.activate();
	if (updatePlayerState)
		activePlayer.updatePState();
}
public void setIrany(Irany newIrany) {
	jatekIrany = newIrany;
	sendSysChatMsg(SubstituteStr.doIt(resChatTexts.getString("Új_játékirány__%d."), "%d", jatekIrany.toString()));//$NON-NLS-2$ //$NON-NLS-1$
	sendToAll (new GEJatekIrany(jatekIrany));
}
public void setJelzeslap(szr.card.Card newJelzeslap) {
	jelzeslap = newJelzeslap;
	sendSysChatMsg(SubstituteStr.doIt(resChatTexts.getString("Új_jelzéslap__%cj."), "%cj", jelzeslap.toString()));//$NON-NLS-2$ //$NON-NLS-1$
	sendToAll(new GEAdjustTableState(getTableState()));
}
/**
 * Ellenõrzött kéréshez hívjuk a kertIranyOK-t!
 */
public void setKertIrany(Irany newKertIrany) {
	kertIrany = newKertIrany;
	sendToAll(new GEKertIrany(kertIrany));
}
/**
 * Ellenõrzött kéréshez hívjuk a kertJelOK-t!
 */
public void setKertJel(szr.card.Jelzes kertJel) {
	gameLAut.setKertJel(kertJel);
	sendToAll(new GEAdjustTableState(getTableState()));
}
/**
 * Ellenõrzött kéréshez hívjuk a kertSzinOK-t!
 */
public void setKertSzin(szr.card.SzinesSzin kertSzin) {
	gameLAut.setKertSzin(kertSzin);
	sendToAll(new GEAdjustTableState(getTableState()));
}
public void setUt(int re, int im) {
	setUt(new UtSzam (re, im));
}
public void setUt(UtSzam ur) {
	Logger.log(new InfoLog(SubstituteStr.doIt("GameController: setUt(%usz)", "%usz", ur.toString())), properties.name, null);//$NON-NLS-2$//$NON-NLS-1$
	utSzam = ur;
	sendToAll(new GEUtRec(utSzam));
}
public void setVetoBlocked() {
	vetoBlocked = true;
}
public boolean startGame() {
	if (!properties.started) {
		if (players.size() == 1) {
			return false;
		} else if (!createPakli()) {
			return false;
		} else {
			properties = properties.started();
			parentServer.sendToAll(new szr.msg.SEJatekKezdodik(getName()));
			actionCounter = 0;
			GameAction.doLAGEInit(this);
			Logger.log(new InfoLog("GameController: Game started."), properties.name, null);//$NON-NLS-1$
		}
	} else {
		Logger.log(new ErrStrLog("GameController error: startGame called on a started game"), properties.name, null);//$NON-NLS-1$
	}
	return true;
}
private void startNewAutoPasszTimer(PlayerData pd) {
	if (autoPasszTimer != null) {
		autoPasszTimer.cancel();
		Logger.log(new InfoLog("GameController: startNewAutoPasszTimer: Previous autoPasszTimer cancelled!"), properties.name, null);//$NON-NLS-1$
	}
	autoPasszTimer = new AutoPasszTimer(this, pd, actionCounter);
	autoPasszTimer.start();
}
/**
 * A kártyalap-számok frissítése a játékosok properties-eiben
 */
public void updateAllCardCount() {
	Logger.log(new InfoLog("GameController: updateAllCardCount()"), properties.name, null);//$NON-NLS-1$
	for (int i = 0; i < players.size(); ++i) {
		((PlayerData)players.elementAt(i)).updateCardCount();
	}
}
/**
 * Játékosállapot-kódok aktualizálása (üzenet küldése)
 */
public void updateAllPlayerStates() {
	Logger.log(new InfoLog("GameController: updateAllPlayerStates()"), properties.name, null);//$NON-NLS-1$
	for (int i = 0; i < players.size(); ++i) {
		((PlayerData)players.elementAt(i)).updatePState();
	}
	try {
		ExSType exS = gameLAut.getCurrState().getExS();
		if (exS != null || exSFlag) {
			sendToAll(new GEExS(exS));
			exSFlag = (exS != null);
		}
	} catch (NullPointerException e) {}
	if (vetoBlocked)
		vetoBlocked = false;
}
}
