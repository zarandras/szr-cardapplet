package szr.msg;

/**
 * Szöveges hibaüzenet-osztály
 * Creation date: (2002.04.02. 18:38:25)
 */
public class ErrStrLog extends ErrLog {
	private final java.lang.String msgStr;
public ErrStrLog(String iMsgStr) {
	super();
	msgStr = iMsgStr;
}
public String toString() {
	return super.toString() + " " + msgStr;//$NON-NLS-1$
}
}
