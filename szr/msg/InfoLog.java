package szr.msg;

/**
 * Tájékoztató naplóüzenet-osztály
 * Creation date: (2002.04.02. 18:38:25)
 */
public class InfoLog extends LoggedMsg {
	private final java.lang.String msgStr;
public InfoLog(String iMsgStr) {
	super();
	msgStr = iMsgStr;
}
public String toString() {
	return super.toString() + " " + msgStr;//$NON-NLS-1$
}
}
