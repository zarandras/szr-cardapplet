package szr.msg;

/**
 * T�rl�d�tt j�t�k esem�nyoszt�ly
 * Creation date: (2002.02.05. 18:28:10)
 */
public class SEJatekTorolve extends ServerEvent {
	private final String jatekNev;	// l�tez� j�t�kn�v
public SEJatekTorolve(String iJatekNev) {
	super();
	jatekNev = iJatekNev;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController ().getGameControlPanel ().jatekTorolve (jatekNev);
}
public String toString() {
	return super.toString() + " " + jatekNev;//$NON-NLS-1$
}
}
