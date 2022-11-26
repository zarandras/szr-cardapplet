package szr.msg;

/**
 * J�t�k elkezd�se esem�nyoszt�ly
 * Creation date: (2002.02.05. 18:28:10)
 */
public class SEJatekKezdodik extends ServerEvent {
	private final String jatekNev;	// l�tez� j�t�kn�v
public SEJatekKezdodik(String iJatekNev) {
	super();
	jatekNev = iJatekNev;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController ().getGameControlPanel ().jatekKezdodik (jatekNev);
}
public String toString() {
	return super.toString() + " " +jatekNev;//$NON-NLS-1$
}
}
