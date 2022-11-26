package szr.msg;

/**
 * Jelszóküldési kérelem akcióosztály
 * Creation date: (2002.02.06. 17:48:17)
 */
public class SCAJelszoKuldes extends ServerControlAction {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final java.lang.String name;
	private final java.lang.String email;
public SCAJelszoKuldes(String iName, String iEmail) {
	super();
	name = iName;
	email = iEmail;
}
/**
 * MÉG NINCS JELSZÓKEZELÉS
 */
public void doServer(szr.mainserv.ServerMsgHandler smh) {
	smh.getParentGameServer().sendPassword(name, email);
	// !!! temporary:
		smh.send(new PEReject(resInfoTexts.getString("Még_nincs_jelszókezelés,_n"))); //$NON-NLS-1$
	//
}
public String toString() {
	return super.toString() + " " + name + " <" + email + "> ";//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
}
}
