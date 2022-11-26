package szr.mainserv;

import java.util.*;
import szr.card.*;
import szr.shared.*;
import szr.msg.*;
/**
 * A szerver játékost reprezentáló osztálya
 * Creation date: (2002.01.27. 13:17:28)
 */
public class PlayerData {
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	private PlayerRec properties = null;	// az ebben lévõ kártyaszám külön frissítendõ az updateCardCount-tal
	private final ServerMsgHandler msgHandler;
	private final Vector cards = new Vector();
	private GameController parentController;
	private int kertLapIdx = -1;	// -1: nincs kért lap

	// Kérési állapotok:
	private int keroState = keroState_none;
	public static final int keroState_none = 0;
	public static final int keroState_szin = 1;
	public static final int keroState_jelzes = 2;
	public static final int keroState_irany = 3;
	public static final int keroState_lap = 4;	// kötelezõ választani
	public static final int keroState_lap_gyenge = 5;	// nem kötelezõ választani
	
	private static Random random = new java.util.Random ();
public PlayerData(PlayerRec iProperties, ServerMsgHandler iMsgHandler, GameController iParentController) {
	super();
	properties = iProperties;
	msgHandler = iMsgHandler;
	parentController = iParentController;
	msgHandler.setPlayerData (this);
}
/**
 * A stafétabot odaadása a játékosnak (õ lesz soron)
 */
public void activate() {
	properties = properties.activate();
	parentController.sendToAll(new szr.msg.GEAdjustPlayer(properties));
//	msgHandler.send(new szr.msg.PEChangeState((gameStarted) ? szr.maincli.ClientController.PS_ACTIVE : szr.maincli.ClientController.PS_JOINED_FIRST));
//		call updatePSate instead!
}
/**
 * Kártya adása
 */
public void addCard(Card c) {
	Vector cv = new Vector();
	cv.addElement(c);
	addCards(cv);
}
/**
 * Kártyák adása
 */
public void addCards(Vector newCards) {
	for (int i = 0; i < newCards.size(); ++i) {
		try {
			cards.addElement((Card)newCards.elementAt(i));
		} catch (ClassCastException e) {
			// integrity errror
		}
	}
	msgHandler.send(new PEAddCards(newCards));
	Logger.log(new InfoLog(SubstituteStr.doIt("PlayerData: %i card(s) added.", "%i", Integer.toString(newCards.size()))), parentController.getProperties().name, properties.name);//$NON-NLS-2$//$NON-NLS-1$
}
/**
 * Összes kártya elvétele
 */
public Vector captureAllCards() {
	Vector v = getCards();
	removeAllCards();
	return v;
}
/**
 * A kért lap elvétele (ha van, különben null)
 */
public Card captureKertLap() {
	Card kertLap;
	try {
		kertLap = (Card)cards.elementAt(kertLapIdx);
		removeCard(kertLapIdx);
	} catch (ArrayIndexOutOfBoundsException e) {
		kertLap = null;
		Logger.log(new InfoLog("PlayerData: kertLap is null."), parentController.getProperties().name, properties.name);//$NON-NLS-1$
	}
	kertLapIdx = -1;
	return kertLap;
}
/**
 * Véletlenszerûen választott kártya elvétele
 */
public Card captureRandomCard() {
	if (cards.size() == 0)
		return null;
	byte b[] = new byte[1];
	random.nextBytes(b);
	int i = ((b[0] + 128) % cards.size());
	Card c = (Card)cards.elementAt(i);
	removeCard(i);
	return c;
}
/**
 * Véletlenszerûen választott kártya elvétele tetszõleges kártyavektorból
 */
public static Card captureRandomCard(Vector cardVect) {
	if (cardVect.size() == 0)
		return null;
	byte b[] = new byte[1];
	random.nextBytes(b);
	int i = ((b[0] + 128) % cardVect.size());
	Card c = (Card)cardVect.elementAt(i);
	cardVect.removeElementAt(i);
	return c;
}
/**
 * Kártyalekérdezés (nem elvétel)
 */
public Card getCard(int serverIdx) {
	try {
		return (Card)cards.elementAt(serverIdx);
	} catch (ArrayIndexOutOfBoundsException e) {
		return null;
	}
}
/**
 * A kártyák vektorának másolata
 */
public java.util.Vector getCards() {
	return (Vector)cards.clone();
}
/**
 * Milyen kérési állapot van?
 */
public int getKeroState() {
	return keroState;
}
public ServerMsgHandler getMsgHandler() {
	return msgHandler;
}
public GameController getParentController() {
	return parentController;
}
public PlayerRec getProperties() {
	return properties;
}
/**
 * Valódi kártyaszám lekérdezése (a properties-ben nem mindig van a tényleges)
 */
public int getRealCardCount() {
	return cards.size();
}
/**
 * A stafétabot továbbadása (a játékos befejezte a kört)
 */
public void inactivate() {
	properties = properties.inactivate();
	setKeroState(keroState_none);
	parentController.sendToAll(new szr.msg.GEAdjustPlayer(properties));
//	msgHandler.send(new szr.msg.PEChangeState((gameStarted) ? szr.maincli.ClientController.PS_INACTIVE : szr.maincli.ClientController.PS_JOINED));
//		call updatePSate instead!
}
public static String keroStateToString(int keroState) {
	switch (keroState) {
		case keroState_none:		return "keroState_none";//$NON-NLS-1$
		case keroState_szin:		return "keroState_szin";//$NON-NLS-1$
		case keroState_jelzes:		return "keroState_jelzes";//$NON-NLS-1$
		case keroState_irany:		return "keroState_irany";//$NON-NLS-1$
		case keroState_lap:			return "keroState_lap";//$NON-NLS-1$
		case keroState_lap_gyenge:	return "keroState_lap_gyenge";//$NON-NLS-1$
		default:					return "keroState_???";//$NON-NLS-1$
	}
}
/**
 * Összes kártya eltávolítása
 */
public void removeAllCards() {
	cards.removeAllElements();
	Logger.log(new InfoLog("PlayerData: all cards removed."), parentController.getProperties().name, properties.name);//$NON-NLS-1$
	msgHandler.send(new PERemoveAllCards());
}
/**
 * Adott indexû kártya eltávolítása
 */
public void removeCard(int serverIdx) {
	try {
		cards.removeElementAt(serverIdx);
		Logger.log(new InfoLog(SubstituteStr.doIt("PlayerData: card #%idx removed.", "%idx", Integer.toString(serverIdx))), parentController.getProperties().name, properties.name);//$NON-NLS-2$//$NON-NLS-1$
		msgHandler.send(new PERemoveCard(serverIdx));
	} catch (ArrayIndexOutOfBoundsException e) {
		Logger.log(new ErrStrLog(SubstituteStr.doIt("PlayerData: invalid card index %idx, can't remove card!", "idx", Integer.toString(serverIdx))), parentController.getProperties().name, properties.name);//$NON-NLS-2$//$NON-NLS-1$
		Logger.log(new ErrExcLog(e), parentController.getProperties().name, properties.name);
	}
}
public void removeParentController() {
	Logger.log(new InfoLog("PlayerData: removing parentController"), parentController.getProperties().name, properties.name);//$NON-NLS-1$
	parentController = null;
}
/**
 * Kártya lecserélése
 */
public boolean replaceCardAt(Card newCard, int serverIdx) {
	try {
		cards.setElementAt(newCard, serverIdx);
		Logger.log(new InfoLog(SubstituteStr.doIt("PlayerData: card #%idx replaced.", "%idx", Integer.toString(serverIdx))), parentController.getProperties().name, properties.name);//$NON-NLS-2$//$NON-NLS-1$
		msgHandler.send(new PEReplaceCard(newCard, serverIdx));
		return true;
	} catch (ArrayIndexOutOfBoundsException e) {
		Logger.log(new ErrStrLog(SubstituteStr.doIt("PlayerData: invalid card index %idx, can't replace card!", "idx", Integer.toString(serverIdx))), parentController.getProperties().name, properties.name);//$NON-NLS-2$//$NON-NLS-1$
		Logger.log(new ErrExcLog(e), parentController.getProperties().name, properties.name);
		return false;
	}
}
/**
 * Kérési állapot megadása
 */
public void setKeroState(int newKeroState) {
	keroState = newKeroState;
	Logger.log(new InfoLog(SubstituteStr.doIt("PlayerData: newKeroState is %ksstr.", "%ksstr", keroStateToString(newKeroState))), parentController.getProperties().name, properties.name);//$NON-NLS-2$//$NON-NLS-1$
}
/**
 * Vélasztott lap (indexének) megadása
 */
public void setKertLapIdx(int cardIdx) {
	kertLapIdx = cardIdx;
	Logger.log(new InfoLog(SubstituteStr.doIt("PlayerData: new kertLapIdx is %idx.", "%idx", Integer.toString(cardIdx))), parentController.getProperties().name, properties.name);//$NON-NLS-2$//$NON-NLS-1$
}
public void setSmiley(String newSmiley) {
	properties = properties.applySmiley(newSmiley);
	try {
		parentController.sendToAll(new GEAdjustPlayer(properties));
	} catch (NullPointerException e) {
		Logger.log(new ErrExcLog(e), null, properties.name);
	}
}
/**
 * Aktualizálja a properties.cardCount-ot
 */
public void updateCardCount() {
	boolean hadCards = (properties.cardCount != 0);
	properties = properties.applyCardCount(cards.size());
	parentController.sendToAll(new GEAdjustPlayer(properties));
	boolean hasCards;
	switch (properties.cardCount) {
		case 0:
			parentController.sendSysChatMsg(SubstituteStr.doIt(resChatTexts.getString("%p_lapjai_elfogytak!"), "%p", properties.name));//$NON-NLS-2$ //$NON-NLS-1$
			hasCards = false;
			break;
			
		case 1:
			parentController.sendSysChatMsg(SubstituteStr.doIt(resChatTexts.getString("%p_lapjai_egy_híján_elfogy"), "%p", properties.name));//$NON-NLS-2$ //$NON-NLS-1$
			// no break
		default:
			hasCards = true;
			break;
	}

	if (hadCards && !hasCards) {
		parentController.getWinnerQueue().addPlayer(this);
	} else if (!hadCards && hasCards) {
		parentController.getWinnerQueue().removePlayer(this);
	}

}
/**
 * Az akutális játékállapot alapján aktualizált játékosállapot-konstanst elküldi a kliensnek
 */
public void updatePState() {
	int newPSConst;
	boolean gameStarted = parentController.getProperties().started;
	if (gameStarted) {
		switch (keroState) {
			case keroState_szin:
				newPSConst = szr.maincli.ClientController.PS_ACTIVE_SZINKERO;
				break;
			case keroState_jelzes:
				newPSConst = szr.maincli.ClientController.PS_ACTIVE_JELZESKERO;
				break;
			case keroState_irany:
				newPSConst = szr.maincli.ClientController.PS_ACTIVE_IRANYKERO;
				break;
			case keroState_lap:
				newPSConst = szr.maincli.ClientController.PS_ACTIVE_LAPKERO;
				break;
			case keroState_lap_gyenge:
				newPSConst = szr.maincli.ClientController.PS_ACTIVE_GYENGE_LAPKERO;
				break;
			default:	/* keroState_none */
				if (properties.active) {
					if (parentController.isVetoBlocked()) {
						newPSConst = szr.maincli.ClientController.PS_ACTIVE_VETOBLOCKED;
					} else {
						newPSConst = parentController.getGameLAut().getCurrState().getActivePlayerState();
					}
				} else {
					newPSConst = parentController.getGameLAut().getCurrState().getInactivePlayerState();
				}
				break;
		}
	} else {
		if (properties.active) {
			newPSConst = szr.maincli.ClientController.PS_JOINED_FIRST;
		} else {
			newPSConst = szr.maincli.ClientController.PS_JOINED;
		}
	}
	msgHandler.send(new szr.msg.PEChangeState(newPSConst));
}
}
