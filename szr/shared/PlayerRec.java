package szr.shared;

import java.io.*;
/**
 * Egy játékos jellemzõit leíró rekord (a PlayerList és a PlayerData számára)
 * Creation date: (2002.03.27. 0:21:51)
 */
public class PlayerRec implements Serializable {
	private static java.util.ResourceBundle resWidgetTexts = java.util.ResourceBundle.getBundle("szr.shared.WidgetTexts");  //$NON-NLS-1$
	public final String name;
	public final String smiley;
	public final String email;
	public final int cardCount;	// nem mindig a tényleges! - külön frissítendõ
	public final boolean active;
public PlayerRec(String iName, String iEmail, String iSmiley) {
	name = iName;
	email = iEmail;
	smiley = iSmiley;

	cardCount = 0;
	active = false;
}
protected PlayerRec(String iName, String iEmail, String iSmiley, int iCardCount, boolean iActive) {
	name = iName;
	email = iEmail;
	smiley = iSmiley;
	
	cardCount = iCardCount;
	active = iActive;
}
public PlayerRec activate() {
	return new PlayerRec(name, email, smiley, cardCount, true);
}
public PlayerRec applyCardCount(int newCardCount) {
	return new PlayerRec(name, email, smiley, newCardCount, active);
}
public PlayerRec applySmiley(String newSmiley) {
	return new PlayerRec(name, email, newSmiley, cardCount, active);
}
public PlayerRec inactivate() {
	return new PlayerRec(name, email, smiley, cardCount, false);
}
public String toString () {
	return ((active) ? "> " : "  ") + "(" + szr.shared.SubstituteStr.doIt(resWidgetTexts.getString("PlayerList_%l_lap"), "%l", Integer.toString (cardCount)) + ") " + smiley + " " + name + " <" + email + ">";//$NON-NLS-9$//$NON-NLS-8$//$NON-NLS-7$//$NON-NLS-6$//$NON-NLS-5$ //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
}
