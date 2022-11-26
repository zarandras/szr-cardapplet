package szr.msg;

/**
 * Exception hibaüzenet-osztály
 * Creation date: (2002.04.02. 18:38:14)
 */
public class ErrExcLog extends ErrLog {
	private final java.lang.Throwable msgException;
public ErrExcLog(Throwable iMsgException) {
	super();
	msgException = iMsgException;
}
public java.lang.Throwable getMsgException() {
	return msgException;
}
public String toString() {
	return super.toString() + " " + msgException.toString();//$NON-NLS-2$//$NON-NLS-1$
}
}
