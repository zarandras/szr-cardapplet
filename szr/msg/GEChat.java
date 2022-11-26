package szr.msg;

/**
 * Csevegõ üzenet eseményosztály
 * Creation date: (2002.02.06. 23:46:39)
 */
public class GEChat extends GameEvent {
	private final java.lang.String message;
public GEChat(String iMessage) {
	super();
	message = iMessage;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().getChatter().receive(message);
}
public String toString() {
	return super.toString() + " " + message.replace('\n',' ');//$NON-NLS-1$
}
}
