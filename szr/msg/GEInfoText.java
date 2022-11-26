package szr.msg;

/**
 * Egységes InfoText eseményosztály
 * Creation date: (2002.02.06. 23:46:39)
 */
public class GEInfoText extends GameEvent {
	private final java.lang.String iTxt;
public GEInfoText(String iITxt) {
	super();
	iTxt = iITxt;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().getInfoSystem().setInfoText(iTxt);
}
public String toString() {
	return super.toString() + " " + iTxt;//$NON-NLS-1$
}
}
